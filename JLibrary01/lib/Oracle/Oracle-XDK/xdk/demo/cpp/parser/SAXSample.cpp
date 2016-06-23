// Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

///////////////////////////////////////////////////////////////////////////////
// NAME
//   SAXSample.cpp
//
// DESCRIPTION
//   Sample usage of C++ XML parser via SAX interface
///////////////////////////////////////////////////////////////////////////////

#include <iostream.h>
#include <string.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

#define DOCUMENT	"cleo.xml"
#define MAX_STRING	128
#define MAX_SPEAKER	20

oratext  elem[MAX_STRING], last_elem[MAX_STRING];
uword    n_speaker;
oratext *speakers[MAX_SPEAKER];
size_t   speakerlen[MAX_SPEAKER];

/* SAX callback functions */
 
extern "C" {

sword startDocument(void *ctx);
sword endDocument(void *ctx);
sword startElement(void *ctx, const oratext *name,
		   const struct xmlnodes *attrs);
sword endElement(void *ctx, const oratext *name);
sword characters(void *ctx, const oratext *ch, size_t len);
 
}

xmlsaxcb saxcb = {
    startDocument,
    endDocument,
    startElement,
    endElement,
    characters
};

int main()
{
    XMLParser   parser;
    ub4         flags;
    uword       ecode;
    flags = XML_FLAG_VALIDATE | XML_FLAG_DISCARD_WHITESPACE;

    cout << "XML C++ SAX sample\n";

    cout << "Initializing XML package...\n";

    if (ecode = parser.xmlinit((oratext *) 0,	// encoding
				  (void (*)(void *, const oratext *, ub4)) 0,
				  (void *) 0,		// msghdlr ctx
				  (xmlsaxcb *) &saxcb))	// SAX callback
    {
        cout << "Failed to initialize XML parser, error " << ecode;
        return 1;
    }

    cout << "Parsing '" << DOCUMENT << "' and showing speakers by scene...\n";
    cout.flush();
    if (ecode = parser.xmlparse((oratext *) DOCUMENT, (oratext *) 0, flags))
	return 1;

    (void) parser.xmlterm();	// terminate LPX package

    return 0;
}

extern "C" {

sword startDocument(void *ctx)
{
    cout << "startDocument\n";
    return 0;
}

sword endDocument(void *ctx)
{
    cout << "endDocument\n";
    return 0;
}

sword startElement(void *ctx, const oratext *name,
		   const struct xmlnodes *attrs)
{
    strcpy((char *) last_elem, (char *) elem);
    strcpy((char *) elem, (char *) name);
    return 0;
}

sword endElement(void *ctx, const oratext *name)
{
    uword i;

    if (!strcmp((char *) name, "SCENE"))
    {
	for (i = 0; i < n_speaker; i++)
	{
	    cout << "    ";
	    cout.write((const char *)speakers[i], speakerlen[i]);
	    cout << "\n";
	}
    }
    return 0;
}

sword characters(void *ctx, const oratext *ch, size_t len)
{
    uword i;

    if (!strcmp((char *) elem, "TITLE"))
    {
	if (!strcmp((char *) last_elem, "ACT"))
	{
	    cout << "\n--- ";
	    cout.write((const char *)ch, len);
	    cout << " ---\n\n";
	}
	else if (!strcmp((char *) last_elem, "SCENE"))
	{
	    n_speaker = 0;
	    cout << "  ";
	    cout.write((const char *)ch, len);
	    cout << "\n";
	}
    }
    else if (!strcmp((char *) elem, "SPEAKER"))
    {
	if (n_speaker < MAX_SPEAKER)
	{
	    for (i = 0; i < n_speaker; i++)
		if ((len == speakerlen[i]) && !strncmp((char *) speakers[i],
							(char *) ch, len))
		    break;
	    if (!n_speaker || (i == n_speaker))
	    {
		speakers[n_speaker] = new oratext[len + 1];
		strcpy((char *) speakers[n_speaker], (char *) ch);
		speakerlen[n_speaker++] = len;
	    }
	}
    }
    return 0;
}

} // extern "C"

// end of SAXSample.cpp
