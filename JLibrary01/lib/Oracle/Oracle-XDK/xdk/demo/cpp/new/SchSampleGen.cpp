/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SchSampleGen.cpp - Sample XML schema validator usage
 
   DESCRIPTION
     Sample usage of C++ interfaces for XML schema validator
*/
 
extern "C" {

#include <stdio.h>

}

#include <SchSampleGen.hpp>

template< typename TCtx, typename Tnode> unsigned
sample_valid( char* fname, char* sch_name) {

  TCtx* ctxp;

  printf( "XML Schema validator C++ sample\n");

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

  ElementRef< Tnode>* elrefp = NULL;

  try
  {
    DocumentRef< Tnode>* docrefp = parserp->parse( isrcp);

    printf("Get document element\n");

    Tnode* np = docrefp->getDocumentElement();

    if (np == NULL)
    {
      printf( "Empty document\n");
      return 1;
    }

    printf("Create document element reference\n");

    elrefp = new ElementRef< Tnode>( (*docrefp), np);

    printf("Finished parsing\n");

  }
  catch (ParserException& pe)
  {
    unsigned ecode = pe.getCode();

    printf( "Failed to parse the document, error %u\n", ecode);
    return ecode;
  }

  printf("Creating Schema validator\n");

  SchemaValidator< Tnode>* valp;

  try 
  {
    valp = fp->createSchemaValidator( SchValCXml, NULL);
  }
  catch (FactoryException& fe2)
  {
    unsigned ecode = fe2.getCode();

    printf( "Failed to create schema validator, error %u\n", ecode);
    return ecode;
  }
  if (valp == NULL)
        printf( "NULL schema validator\n");

  printf("Validating document\n");

  try 
  {
    if (sch_name != NULL)
      valp->loadSchema( (oratext*)sch_name);

    valp->validate( (*elrefp));
  }
  catch (ParserException& se)
  {
    unsigned ecode = se.getCode();

    printf( "Failed to validate the document, error %u\n", ecode);
    return ecode;
  }

  printf("Document is valid\n");
  return 0;
}
