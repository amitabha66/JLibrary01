/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     FullDOMGen.cpp - Sample usage of all DOM functionality
 
   DESCRIPTION
     Sample usage of C++ interfaces of all DOM functionality
*/

extern "C" {

#include <stdio.h>

#include <string.h>
}

#include <FullDOMGen.hpp>

template< typename Tnode> void dumpTreeIter( NodeRef< Tnode>& elref);

template< typename Tnode> void dumpTreeWalk( NodeRef< Tnode>& elref);

template< typename TCtx, typename Tnode> unsigned
full_dom( char* fname) {

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

    printf("Dump the tree using an iterator\n");

    dumpTreeIter< Tnode>( elref);

    printf("Dump the tree using a tree walker\n");

    dumpTreeWalk< Tnode>( elref);

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

template< typename Tnode> void dumpTreeIter( ElementRef< Tnode>& elref)
{
  DocumentTraversal< Tnode>* dtrp = new DocumentTraversal< Tnode>;

  NodeIterator< Tnode>* iterp =
    dtrp->createNodeIterator( elref, SHOW_ELEMENT, TRUE);

  NodeRef< Tnode> nref( elref);
  Tnode* np = NULL;

  printf( "Iteration forward\n");

  while (np = iterp->nextNode())
  {
    nref.resetNode( np);
    if (nref.getNodeType() != ELEMENT_NODE)
    {
      printf( "Non-element node selected");
      return;
    }
    oratext* name = nref.getNodeName();
    printf( "Element: %s\n", name);
  }

  printf( "Iteration backward\n");

  while (np = iterp->previousNode())
  {
    nref.resetNode( np);
    if (nref.getNodeType() != ELEMENT_NODE)
    {
      printf( "Non-element node selected");
      return;
    }
    oratext* name = nref.getNodeName();
    printf( "Element: %s\n", name);
  }
}

template< typename Tnode> void dumpTreeWalk( ElementRef< Tnode>& elref)
{
  DocumentTraversal< Tnode>* dtrp = new DocumentTraversal< Tnode>;

  TreeWalker< Tnode>* walkp =
    dtrp->createTreeWalker( elref, SHOW_TEXT, TRUE);

  NodeRef< Tnode> nref( elref);
  Tnode* np = NULL;

  printf( "Iteration forward\n");

  while (np = walkp->nextNode())
  {
    nref.resetNode( np);
    if (nref.getNodeType() != TEXT_NODE)
    {
      printf( "Non-element node selected");
      return;
    }
    oratext* value = nref.getNodeValue();
    printf( "Value: %s\n", value);
  }

  printf( "Iteration backward\n");

  while (np = walkp->previousNode())
  {
    nref.resetNode( np);
    if (nref.getNodeType() != TEXT_NODE)
    {
      printf( "Non-element node selected");
      return;
    }
    oratext* value = nref.getNodeValue();
    printf( "Value: %s\n", value);

    np = walkp->parentNode();
    if (np == NULL)
      return;
    nref.resetNode( np);
    oratext* name = nref.getNodeName();
    printf( "Parent: %s\n", name);
  }
}


