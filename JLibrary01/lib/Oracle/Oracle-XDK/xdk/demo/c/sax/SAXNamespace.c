/* Copyright (c) 1999, 2003, Oracle Corporation.  All rights reserved.  */

/** SAXNamespace.c
 ** This file demonstrates a simple use of the Namespace extensions to 
 ** the SAX APIs.
 **/

#include <stdio.h>

#ifndef XML_ORACLE
# include <xml.h>
#endif

#define DOCUMENT "NSExample.xml"

/* SAX callback context */

typedef struct {
    xmlctx  *xctx;
    ub4      depth;
} cbctx;

/*------------------------------------------------------------------------
                             SAX Interface
  ------------------------------------------------------------------------*/

static XMLSAX_START_DOC_F(sax_startdocument, ctx)
{
    puts("\nEVENT: StartDocument");
    return 0;
}


static XMLSAX_END_DOC_F(sax_enddocument, ctx)
{
    puts("\nEVENT: EndDocument");
    return 0;
}


static XMLSAX_END_ELEM_F(sax_endelement, ctx, name)
{
    printf("\nEVENT: EndElement (%s)\n", name);
    return 0;
}

#define PRINT_EA(what, label, str) \
    if (str) printf("    %s %14s: \"%s\"\n", what, label, str); \
    else     printf("    %s %14s: Nil\n", what, label);

#define PRINT_E(label, str)	PRINT_EA("ELEM", label, str)
#define PRINT_A(label, str)	PRINT_EA("ATTR", label, str)

static XMLSAX_START_ELEM_NS_F(sax_nsstartelement, ctx,
			      qname, local, uri, attrs)
{
    cbctx       *sc = (cbctx *) ctx;
    xmlctx      *xctx = sc->xctx;
    xmlattrnode *attr;
    oratext     *aqname, *aprefix, *alocal, *auri, *avalue;
    ub4          i;

    printf("\nEVENT: StartElement (%s)\n", qname);

    putchar('\n');
    PRINT_E("Qualified Name", qname);
    PRINT_E("Local Name",     local);
    PRINT_E("Namespace",      uri);

    if (attrs)
    {
	for (i = 0; i < XmlDomGetNodeListLength(xctx, attrs); i++)
	{
	    attr = (xmlattrnode *) XmlDomGetNodeListItem(xctx, attrs, i);

	    aqname = XmlDomGetAttrName(xctx, attr);
	    aprefix = XmlDomGetAttrPrefix(xctx, attr);
	    alocal = XmlDomGetAttrLocal(xctx, attr);
	    auri = XmlDomGetAttrURI(xctx, attr);
	    avalue = XmlDomGetAttrValue(xctx, attr);

	    putchar('\n');
	    PRINT_A("Qualified Name", aqname);
	    PRINT_A("Prefix",         aprefix);
	    PRINT_A("Local Name",     alocal);
	    PRINT_A("Namespace",      auri);
	    PRINT_A("Value",          avalue);
	}
    }
    return 0;
}

/*------------------------------------------------------------------------
                           SAX callback structure
  ------------------------------------------------------------------------*/

xmlsaxcb sax_callback = {
    sax_startdocument,
    sax_enddocument,
    0,
    sax_endelement,
    0, 0, 0, 0, 0,
    sax_nsstartelement
};

/*------------------------------------------------------------------------
                                MAIN
  ------------------------------------------------------------------------*/

int main()
{
    xmlctx     *xctx;
    xmldocnode *doc;
    xmlerr      ecode;
    cbctx       sc;

    puts("SAX Namespace example.");

    /* initialize LPX context */
    if (!(xctx = XmlCreate(&ecode, (oratext *) "saxnamespace_xctx", NULL)))
    {
	printf("Failed to create XML context, error %u\n", (unsigned) ecode);
	return -1;
    }

    /* set up sax context */
    sc.xctx = xctx;
    sc.depth = 0;

    /* parse the document */
    printf("Parsing '%s' ...\n", DOCUMENT);
    ecode = XmlLoadSax(xctx, &sax_callback, &sc, "file", DOCUMENT,
		       "validate", TRUE, "discard_whitespace", TRUE, NULL);
    if (ecode)
	printf("\nParse failed, code %u\n", (unsigned) ecode);
    else
	puts("\nParse succeeded.");

    /* terminate */
    XmlDestroy(xctx);

    return ecode ? -1 : 0;
}

/* end of SAXNamespace.c */
