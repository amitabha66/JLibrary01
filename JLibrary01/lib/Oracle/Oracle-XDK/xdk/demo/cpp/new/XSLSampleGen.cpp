/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XSLSampleGen.cpp - Sample XSLT usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XSLT processor
*/
 
extern "C" {

#include <stdio.h>
}

#include <XSLSampleGen.hpp>

template< typename Tnode> void XslDumpTree( NodeRef< Tnode>& elref);

template< typename TCtx, typename Tnode> unsigned sample_xsl(
                                          char* fname, char* dname) {

  TCtx* ctxp = NULL;

  printf( "XML C++ XSLT sample\n");

  printf( "Initializing context\n");

  try
  {
    ctxp = new TCtx();
  }
  catch (XmlException& e)
  {
    unsigned ecode = e.getCode();

    printf( "Failed to initialize XML context, error %u\n", ecode);
    return ecode;
  }

  printf("Initializing Tools Factory\n");

  Factory< TCtx, Tnode>* fp = NULL;

  try 
  {
    fp = new Factory< TCtx, Tnode>( ctxp);
  }
  catch (FactoryException& fe)
  {
    unsigned ecode = fe.getCode();

    printf( "Failed to create factory, error %u\n", ecode);
    return ecode;
  }

  printf("Creating XSL Transformer\n");

  Transformer< TCtx, Tnode>* trp = NULL;

  try 
  {
    trp = fp->createXslTransformer( XslTrCXml, NULL);
  }
  catch (FactoryException& fe1)
  {
    unsigned ecode = fe1.getCode();

    printf( "Failed to create transformer, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source for the XSLT document\n");

  FileSource* isrcp = new FileSource( (oratext*)fname);

  printf( "Set the XSLT document to the transformer\n");

  try
  {
    trp->setXSL (isrcp);
  }
  catch (XslException& xsle)
  {
    unsigned ecode = xsle.getCode();

    printf( "Failed to set the XSLT document, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source for the instance document\n");

  isrcp = new FileSource( (oratext*)dname);

  printf("Transforming '%s' ...\n", dname);

  try
  {
    NodeRef< Tnode>* nrefp =
      trp->transform (isrcp);

    if (nrefp == NULL)
    {
      printf("NULL document reference\n");
      return 1;
    }

    printf("Dump the tree\n");

    XslDumpTree< Tnode>( nrefp);

    printf("Delete the DOM tree\n");

    nrefp->markToDelete();

    delete nrefp;

    printf("Finished\n");
  }
  catch (XslException& xsle1)
  {
    unsigned ecode = xsle1.getCode();

    printf( "Failed to transform the instance document, error %u\n", ecode);
    return ecode;
  }
  return 0;
}

template< typename Tnode> void XslDumpTree( NodeRef< Tnode>* ndrefp)
{
  oratext* name = ndrefp->getNodeName();

  if (name == NULL)
    printf("No Name Node\n");
  else
    printf( " Node Name: %s\n", (char*)name);

  if (ndrefp->hasChildNodes())
  {
    NodeList< Tnode>* lp = ndrefp->getChildNodes();
    NodeListRef< Tnode>* lrefp = new NodeListRef< Tnode>( (*ndrefp), lp);

    ub4 len = lrefp->getLength();
    for (int i = 0; i < len; i++)
    {
      Tnode* np = lrefp->item( i);
      NodeRef< Tnode>* elemrp = new NodeRef< Tnode>( (*ndrefp), np);

      if (elemrp->getNodeType() == ELEMENT_NODE)
	XslDumpTree( elemrp);
      delete elemrp;
    }
    delete lrefp;
  }
}

