// Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

///////////////////////////////////////////////////////////////////////////////
// NAME
//   XSLSample.cpp
//
// DESCRIPTION
//   Sample usage of C++ XSL processor
//
// PUBLIC FUNCTION(S)
//
// PRIVATE FUNCTION(S)
//
// NOTES
//   none
///////////////////////////////////////////////////////////////////////////////

#include <iostream.h>
#include <string.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

char *speaker;
char *act, *scene;
uword n_speech;

int main(int argc, char **argv)
{
    XMLParser     xmlpar, xslpar, respar;
    XSLProcessor  xslproc;
    Node         *result;
    ub4           flags;
    uword         ecode;

    flags = XML_FLAG_VALIDATE | XML_FLAG_DISCARD_WHITESPACE;

    cout << "XSL processor sample\n";

    if (argc < 3)
    {
	cout << "Usage is XSLSample <xmlfile> <stlyesheet>\n";
	return 1;
    }
 
    // Parse the XML file
    cout << "Parsing xmlfile\n";
    cout.flush();
    if (ecode = xmlpar.xmlinit())
    {
        cout << "Failed to initialize XML parser, error " << ecode;
        return 1;
    }
    if (ecode = xmlpar.xmlparse((oratext *) argv[1], (oratext *) 0, flags))
        return 1;

    // Parse the Stylesheet file
    cout << "Parsing Stylesheet\n";
    cout.flush();
    if (ecode = xslpar.xmlinit())
    {
        cout << "Failed to initialize XML parser, error " << ecode;
        return 1;
    }
    if (ecode = xslpar.xmlparse((oratext *) argv[2], (oratext *) 0, flags))
        return 1;

    // Initialize the result context
    cout << "Initializing the result context\n";
    cout.flush();
    if (ecode = respar.xmlinit())
    {
        cout << "Failed to initialize XML parser, error " << ecode;
        return 1;
    }
    
    // XSL Processing
    cout << " XSL Processing\n";
    cout.flush();
    if (ecode = xslproc.xslprocess(&xmlpar, &xslpar, &respar, &result))
    {
        cout << "Failed in XSL Processing, error " << ecode;
        return 1;
    }  

    // Printout the result tree
    result->print();

    // Terminate the parsers
    (void) xmlpar.xmlterm();    
    (void) xslpar.xmlterm();    
    (void) respar.xmlterm();    

    return 0;
}
