// Copyright (c) 1999, 2003, Oracle.  All rights reserved.

//////////////////////////////////////////////////////////////////////////////
// NAME		smain.cpp
// DESCRIPTION	Demo program using C++ Class Generator classes derived
//              from a DTD (in sample.xml)
//////////////////////////////////////////////////////////////////////////////

#include <iostream.h>

#ifndef ORAXSD_CPP_ORACLE
# include <oraxsd.hpp>
#endif

#include "sample.h"	// Header produced by class generator

#define DTD_DOCUMENT	"sample.xml"
#define OUT_DOCUMENT	"sample.out"

int main()
{
    uword  ecode;

    // Initialize XML parser
    XMLParser *parser = new XMLParser();
    cout << "Initializing XML parser...\n";
    if (ecode = parser->xmlinitenc(NULL, (oratext *) "ISO-8859-1"))
    {
	cout << "Failed to initialize parser, code " << ecode << "\n";
        return 1;
    }

    // Parse the document containing a DTD; parsing just a DTD is not
    // possible yet, so the file must contain a valid document (which
    // is parsed but we're ignoring).
    cout << "Loading DTD from " << DTD_DOCUMENT << "...\n";
    if (ecode = parser->xmlparse((oratext *) DTD_DOCUMENT, (oratext *) 0,
				 XML_FLAG_VALIDATE))
    {
	cout << "Failed to parse DTD document " << DTD_DOCUMENT <<
	    ", code " << ecode << "\n";
	return 2;
    }

    // Fetch dummy document
    cout << "Fetching dummy document...\n";
    Document *doc = parser->getDocument();

    // Create the constituent parts of a Sample
    cout << "Creating components...\n";
    AE5rdvE3rk *a = new AE5rdvE3rk(doc, (String) "An aardvark is apprehensive");
    D0nepr *d = new D0nepr(doc, (String) "Mmmm, river");
    d->setfoo((String) "foo's attribute value");
    Frisky2 *f1 = new Frisky2(doc, (String) "Frisky2 the 1st");
    Frisky2 *f2 = new Frisky2(doc, (String) "Frisky2 the 2nd");
    Egalitarian *e = new Egalitarian(doc, f1, f2);
    Frisky2 *f = new Frisky2(doc, (String) "Top-level Frisky2");

    // Create the sample
    cout << "Creating top-level element...\n";
    sample *samp = new sample(doc, a, d, e, f);

    // Validate the construct
    cout << "Validating...\n";
    if (ecode = parser->validate(samp))
    {
	cout << "Validation failed, code " << ecode << "\n";
	return 3;
    }

    // Write out doc
    cout << "Writing document to " << OUT_DOCUMENT << "\n";
    FILE *out = new FILE;
    if (!(out = fopen(OUT_DOCUMENT, "w")))
    {
	cout << "Failed to open output stream\n";
	return 4;
    }
    samp->print(out);
    fclose(out);

    // Everything's OK
    cout << "Success.\n";

    // Shut down
    parser->xmlterm();
    return 0;
}

// end of smain.cpp
