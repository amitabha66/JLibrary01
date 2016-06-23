/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XVMSampleGen.cpp - Sample XSLT VM usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XSLT VM processor
*/
 
extern "C" {

#include <stdio.h>
}

#include <XVMSampleGen.hpp>

template< typename Tnode> void XslDumpTree( NodeRef< Tnode>& elref);

template< typename TCtx, typename Tnode> unsigned sample_xvm(
                                          char* fname, char* dname) {

  TCtx* ctxp = NULL;

  printf( "XML C++ XSLT VM sample\n");

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

  printf("Creating XSL VM Compiler\n");

  Xsl::Compiler< TCtx, Tnode>* cp = NULL;

  try 
  {
    cp = fp->createXslCompiler( XvmCompCXml, ctxp);
  }
  catch (FactoryException& fe1)
  {
    unsigned ecode = fe1.getCode();

    printf( "Failed to create XVM compiler, error %u\n", ecode);
    return ecode;
  }

  printf("Creating XSL VM Transformer\n");

  CompTransformer< TCtx, Tnode>* ctrp = NULL;

  try 
  {
    ctrp = fp->createXslExtendedTransformer( XvmTrCXml, NULL);
  }
  catch (FactoryException& fe2)
  {
    unsigned ecode = fe2.getCode();

    printf( "Failed to create XVM transformer, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source for the XSLT document\n");

  FileSource* isrcp = new FileSource( (oratext*)fname);

  printf( "Compile the XSLT document\n");

  ub2* binp = NULL;

  try
  {
    binp = cp->compile (isrcp);
  }
  catch (XslException& xsle)
  {
    unsigned ecode = xsle.getCode();

    printf( "Failed to compile the XSLT document, error %u\n", ecode);
    return ecode;
  }

  printf( "Set compiled XSLT document to the transformer\n");

  try
  {
    ctrp->setBinXsl (binp);
  }
  catch (XslException& xsle1)
  {
    unsigned ecode = xsle1.getCode();

    printf( "Failed to set compiled XSLT document, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source for the instance document\n");

  isrcp = new FileSource( (oratext*)dname);

  printf("Transforming '%s' ...\n", dname);

  try
  {
    NodeRef< Tnode>* nrefp = ctrp->transform( isrcp);

    if (nrefp == NULL)
    {
      printf("Finished\n");
      return 0;
    }

    printf("Dump the tree\n");

    XslDumpTree< Tnode>( (*nrefp));

    printf("Finished\n");
  }
  catch (XslException& xsle2)
  {
    unsigned ecode = xsle2.getCode();

    printf( "Failed to transform the XSLT document, error %u\n", ecode);
    return ecode;
  }
  return 0;
}

template< typename Tnode> void XslDumpTree( NodeRef< Tnode>& ndref)
{
  oratext* name = ndref.getNodeName();

  printf( " Node Name: %s\n", (char*)name);

  if (ndref.hasChildNodes())
  {
    NodeList< Tnode>* lp = ndref.getChildNodes();

    NodeListRef< Tnode>* lrefp = new NodeListRef< Tnode>( ndref, lp);

    ub4 len = lrefp->getLength();
    for (int i = 0; i < len; i++)
    {
      Tnode* np = lrefp->item( i);
      NodeRef< Tnode>* elemrp = new NodeRef< Tnode>( ndref, np);
      XslDumpTree( *elemrp);
    }
  }
}
