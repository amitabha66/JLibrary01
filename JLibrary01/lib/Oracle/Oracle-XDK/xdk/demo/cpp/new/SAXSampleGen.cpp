/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SAXSampleGen.cpp - Sample DOM usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of SAX parser
*/
 
extern "C" {

#include <stdio.h>
}

#include <SAXSampleGen.hpp>

class MyHandler : public SAXHandler< xmlnode> {

  void startDocument() { printf( "Start Document\n"); }
  void endDocument() { printf( "End Document\n"); }
  void startElement( oratext* name,
		     NodeListRef< xmlnode>* attrs_refp)
  { }
  void startElementNS( oratext* qname, oratext* local,
		  oratext* ns_URI, NodeListRef< xmlnode>* attrs_refp)
  { printf( "Element: %s\n", (char*)qname); }
  void endElement( oratext* name) { printf( "Element end\n");}
  void characters( oratext* ch, ub4 size)
  { printf( "Data: %s\n", (char*)ch); }
  void whitespace( oratext* data, ub4 size) { }
  void processingInstruction( oratext* target, oratext* data)
  { }
  void notationDecl( oratext* name,
		     oratext* public_id, oratext* system_id)
  { }
  void comment( oratext* data) { }
  void elementDecl( oratext *name, oratext *content) { }
  void attributeDecl( oratext* attr_name,
		      oratext *name, oratext *content)
  { }
  void XMLDecl( oratext* version,
		boolean is_encoding, sword standalone)
  { }
  void CDATA( oratext* data, ub4 size) { }
  void parsedEntityDecl( oratext* name, oratext* value,
         oratext* public_id, oratext* system_id, boolean general)
  { }
  void unparsedEntityDecl( oratext* name,
		       oratext* public_id, oratext* system_id,
				       oratext* notation_name)
  { }
};

template< typename TCtx, typename Tnode> unsigned
sample_sax( char* fname) {

  TCtx* ctxp;

  printf( "XML C++ SAX sample\n");

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

  printf("Creating SAX parser\n");

  SAXParser< TCtx>* parserp;

  try 
  {
    parserp = fp->createSAXParser( SAXParCXml, NULL);
  }
  catch (FactoryException& fe1)
  {
    unsigned ecode = fe1.getCode();

    printf( "Failed to create parser, error %u\n", ecode);
    return ecode;
  }

  printf( "Create file source\n");

  FileSource* isrcp = new FileSource( (oratext*)fname);

  try
  {
    printf("set SAX handler\n");

    MyHandler* saxhndlrp = new MyHandler();

    parserp->setSAXHandler( saxhndlrp);

    printf("Parsing '%s' ...\n", fname);

    parserp->parse( isrcp);

    printf("Parsing completed\n");

  }
  catch (ParserException& pe)
  {
    unsigned ecode = pe.getCode();

    printf( "Failed to parse the document, error %u\n", ecode);
    return ecode;
  }
  return 0;
}

