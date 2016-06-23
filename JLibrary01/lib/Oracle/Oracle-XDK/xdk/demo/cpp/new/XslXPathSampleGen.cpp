/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XslXPathSampleGen.cpp - Sample XPath usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XPath processors
*/
 
extern "C" {

#include <stdio.h>
}

#include <XslXPathSampleGen.hpp>

template< typename TCtx, typename Tnode> unsigned sample_xpath(
                                          char* dname, char* xpath_exp) {

  TCtx* ctxp = NULL;

  printf( "XML C++ XPath sample\n");

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

  printf("Creating XPath processor\n");

  XPath::Processor< TCtx, Tnode>* prp = NULL;

  try 
  {
    prp = fp->createXPathProcessor( XPathPrCXml, NULL);
  }
  catch (FactoryException& fe1)
  {
    unsigned ecode = fe1.getCode();

    printf( "Failed to create XPath processor, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source for the instance document\n");

  InputSource* isrcp = new FileSource( (oratext*)dname);

  printf("Processing '%s' ...", dname);
  printf("  with  '%s' ...\n", xpath_exp);

  XPathObject< Tnode>* objp = NULL;

  try
  {
    objp = prp->process (isrcp, (oratext*)xpath_exp);

  }
  catch (XPathException& xpe)
  {
    unsigned ecode = xpe.getCode();

    printf( "Failed to process the document, error %u\n", ecode);
    return ecode;
  }

  NodeSet< Tnode>* np = NULL;
  boolean varb = FALSE;
  double num = 0.0;
  oratext* str = NULL;
  unsigned i = 0;

  switch (objp->getObjType())
  {
  case XPOBJ_TYPE_NDSET:
    np = objp->getNodeSet();
    printf("NodeSet:\n");
    for (i = 0; i < np->getSize(); i++ )
    {
      NodeRef< Tnode>* nrefp = np->getNode( i);
      switch( nrefp->getNodeType())
      {
      case ELEMENT_NODE:
      case ATTRIBUTE_NODE:
	printf("Node Name : %s\n", 
	       nrefp->getNodeName());
	break;
      default:
	printf("Other Node \n");
	break;
      }
    }
    break;
  case XPOBJ_TYPE_BOOL:
    varb = objp->getObjBoolean();
    printf("Boolean Value : %d\n", varb);
    break;
  case XPOBJ_TYPE_NUM:
    num = objp->getObjNumber();
    printf("Numeric Value : %10.2f\n", num);
    break;
  case XPOBJ_TYPE_STR:
    str = objp->getObjString();
    printf("String Value : %s\n", str);
    break;
  default:
    printf( "Failed to create valid object\n");
  }
  return 0;
}
