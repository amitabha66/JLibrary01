/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     DOMSampleGen.cpp - Sample DOM usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of XML parser and DOM
*/
 
extern "C" {

#include <stdio.h>

#include <string.h>
}

#include <DOMSampleGen.hpp>

template< typename Tnode> void dumpTree( ElementRef< Tnode>& elref);

template< typename TCtx, typename Tnode> unsigned
sample_dom( char* fname) {

  TCtx* ctxp;

  printf( "XML C++ DOM sample\n");

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

  Factory< TCtx, Tnode>* fp;

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

  printf("Creating DOM parser\n");

  DOMParser< TCtx, Tnode>* parserp;

  try 
  {
    parserp = fp->createDOMParser( DOMParCXml, NULL);
  }
  catch (FactoryException& fe1)
  {
    unsigned ecode = fe1.getCode();

    printf( "Failed to create parser, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source\n");

  FileSource* isrcp = new FileSource( (oratext*)fname);

  printf("Parsing '%s' ...\n", fname);

  try
  {
    DocumentRef< Tnode>* docrefp = parserp->parse( isrcp);
    if (docrefp == NULL)
    {
      printf( "NULL document\n");
      return 1;
    }
    Tnode* np = docrefp->getDocumentElement();
    if (np == NULL)
    {
      printf( "Empty document\n");
      return 1;
    }
    ElementRef< Tnode> elref( (*docrefp), np);

    printf("Dump the DOM tree\n");

    dumpTree< Tnode>( elref);

    printf("Delete the DOM tree\n");

    docrefp->markToDelete();

    delete docrefp;

    printf("Finished\n");

  }
  catch (ParserException& pe)
  {
    unsigned ecode = pe.getCode();

    printf( "Failed to parse the document, error %u\n", ecode);
    return ecode;
  }
  return 0;
}

template< typename Tnode> void dumpPart(
		      ElementRef< Tnode>& elref, boolean indent);

template< typename Tnode> void dumpTree( ElementRef< Tnode>& elref)
{
  oratext* name = elref.getNodeName();
  if (name == NULL)
  {
    printf( " Node Name: NO Name\n");
    return;
  }
  if (!strcmp((char *) name, "ACT"))
    dumpPart< Tnode>( elref, FALSE);
  else if (!strcmp((char *) name, "SCENE"))
    dumpPart< Tnode>( elref, TRUE);

  Tnode* np = NULL;
  ElementRef< Tnode>* elemrp = NULL;
  if (elref.hasChildNodes())
  {
    NodeList< Tnode>* lp = elref.getChildNodes();
    NodeListRef< Tnode> lref( elref, lp);

    ub4 len = lref.getLength();
    for (int i = 0; i < len; i++)
    {
      np = lref.item( i);
      if (i == 0)
	elemrp = new ElementRef< Tnode>( elref, np);
      else
	elemrp->resetNode( np);

      if (elemrp->getNodeType() == ELEMENT_NODE)
	dumpTree( *elemrp);
    }
    if (len > 0)
      delete elemrp;
  }
}

template< typename Tnode> void dumpPart(
		 ElementRef< Tnode>& elref, boolean indent)
{
  NodeRef< Tnode>* fstrefp =
    new ElementRef< Tnode>( elref, elref.getFirstChild());
  NodeRef< Tnode>* sndrefp =
    new ElementRef< Tnode>( (*fstrefp), fstrefp->getFirstChild());

  if (indent) 
    fputs("    ", stdout);
  puts((char *) sndrefp->getNodeValue());

  delete sndrefp;
  delete fstrefp;
}
