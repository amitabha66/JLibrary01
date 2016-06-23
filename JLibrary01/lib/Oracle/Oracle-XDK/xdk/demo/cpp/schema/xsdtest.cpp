// Copyright (c) 1999, 2003, Oracle.  All rights reserved.

///////////////////////////////////////////////////////////////////////////////
//   NAME		validate.c
//   DESCRIPTION	Sample usage of C XML Schema processor
///////////////////////////////////////////////////////////////////////////////
 
#include <iostream.h>
#include <string.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

#ifndef ORAXSD_CPP_ORACLE
# include <oraxsd.hpp>
#endif
 
int main(int argc, char **argv)
{
    XMLSchema   schema;
    XMLParser   parser;
    Element    *root;
    xmlctx     *ctx;
    char       *doc, *uri;
    uword       ecode;
 
    cout << "XML C++ Schema processor\n";

    if ((argc < 2) || (argc > 3))
    {
	cout << "usage: validate <xml document> [schema]\n";
	return -1;
    }
    doc = argv[1];
    uri = (argc > 2) ? argv[2] : 0;

    cout << "Initializing XML package...\n";
 
    if (ecode = parser.xmlinit())
    {
        cout << "Failed to initialize XML parser, error " << ecode;
        return 1;
    }

    cout << "Parsing '" << doc << "'...\n";
    if (ecode = parser.xmlparse((oratext *) doc, (oratext *) 0,
				XML_FLAG_DISCARD_WHITESPACE))
    {
        cout << "Parse failed, error " << ecode << "\n";
        return 2;
    }

    cout << "Initializing Schema package...\n";

    if (ecode = schema.initialize(&parser))
    {
	cout << "Failed, code " << ecode << "!\n";
	return 3;
    }

    cout << "Validating document...\n";

    root = parser.getDocumentElement();
    if (ecode = schema.validate(root, (oratext *) uri))
    {
        cout << "Validation failed, error " << ecode << "\n";
        schema.terminate();
        return 4;
    }

    cout << "Document is valid.\n";
    schema.terminate();
    return 0;
}
