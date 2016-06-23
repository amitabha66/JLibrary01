/* Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

   NAME
     SAXSample.c - Sample SAX usage

   DESCRIPTION
     Sample usage of C XML parser via SAX interface
*/

#include <stdio.h>

#ifndef XML_ORACLE
# include <xml.h>
#endif

#define DOCUMENT	"cleo.xml"
#define DEFAULT_KEYWORD	"death"

typedef struct {
    xmlctx  *xctx;
    oratext *keyword;
    size_t   keylen;
    oratext *elem;
    oratext speaker[80];
} saxctx;

/*------------------------------------------------------------------------
                             support functions
  ------------------------------------------------------------------------*/

static oratext *findsub(oratext *buf, size_t bufsiz,
			oratext *sub, size_t subsiz)
{
    uword i;

    if (!buf || !bufsiz || (subsiz > bufsiz))
	return NULL;
    if (!sub || !subsiz)
	return buf;
    for (i = 0; i < bufsiz - subsiz; i++, buf++)
    {
	if (!memcmp(buf, sub, subsiz))
	    return buf;
    }
    return NULL;
}

/*------------------------------------------------------------------------
                             SAX callbacks
  ------------------------------------------------------------------------*/

static XMLSAX_START_DOC_F(startDocument, ctx)
{
    puts("startDocument");
    return 0;
}

static XMLSAX_END_DOC_F(endDocument, ctx)
{
    puts("endDocument");
    return 0;
}

static XMLSAX_START_ELEM_F(startElement, ctx, name, attrs)
{
    ((saxctx *) ctx)->elem = name;
    return 0;
}

static XMLSAX_END_ELEM_F(endElement, ctx, name)
{
    ((saxctx *) ctx)->elem = (oratext *) "";
    return 0;
}

static XMLSAX_CHARACTERS_F(characters, ctx, ch, len)
{
    saxctx *sc = (saxctx *) ctx;

    if (!strcmp((char *) sc->elem, "SPEAKER"))
    {
	memcpy(sc->speaker, ch, len);	/* set current speaker */
	sc->speaker[len] = 0;
    }
    else if (findsub((oratext *) ch, len, sc->keyword, sc->keylen))
	printf("    %s: %.*s\n", sc->speaker, len, ch);
    return 0;
}

static xmlsaxcb saxcb = {
    startDocument,
    endDocument,
    startElement,
    endElement,
    characters
};

/*------------------------------------------------------------------------
				      MAIN
  ------------------------------------------------------------------------*/

int main(int argc, char **argv)
{
    xmlctx     *xctx;
    xmldocnode *doc;
    saxctx      sc;
    xmlerr      ecode;

    puts("XML C SAX sample");

    memset(&sc, sizeof(sc), 0);
    sc.keyword = (oratext *) ((argc > 1) ? argv[1] : DEFAULT_KEYWORD);
    sc.keylen = strlen(sc.keyword);
    sc.elem = (oratext *) "";

    puts("Creating XML context...");
    if (!(xctx = XmlCreate(&ecode, (oratext *) "saxsample_xctx", NULL)))
    {
        printf("Failed to create XML context, error %u\n", (unsigned) ecode);
        return 1;
    }

    printf("Parsing '%s' and looking for lines containing '%s'...\n",
	DOCUMENT, sc.keyword);
    if (argc < 2)
	puts("[Supply another word as argument to search for something else]");

    if (ecode = XmlLoadSax(xctx, &saxcb, &sc, "file", DOCUMENT,
			   "validate", TRUE, "discard_whitespace", TRUE, NULL))
	return 1;

    XmlDestroy(xctx);			/* terminate XML package */

    return 0;
}

/* End of SAXSample.c */
