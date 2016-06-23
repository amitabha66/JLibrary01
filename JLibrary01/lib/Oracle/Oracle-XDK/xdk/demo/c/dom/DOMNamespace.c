/* Copyright (c) 1999, 2003, Oracle Corporation.  All rights reserved.  */

/** DOMNamespace.c
 ** This file demonstates a simple use of the parser and Namespace
 ** extensions to the DOM APIs. 
 ** The XML file that is given to the application is parsed and the
 ** elements and attributes in the document are printed.
 **/

#ifndef XML_ORACLE
# include <xml.h>
#endif

#define DOCUMENT "NSExample.xml"

/*------------------------------------------------------------------------
                             printAttrs
  ------------------------------------------------------------------------*/

#define PRINT_A(label, str) \
    if (str) printf("    ATTR %10s: \"%s\"\n", label, str); \
    else     printf("    ATTR %10s: Nil\n", label);

static void printAttrs(xmlctx *xctx, xmlnode *n)
{
    xmlnamedmap *attrs;
    xmlattrnode *a;
    oratext     *value, *qname, *uri, *local, *prefix;
    ub4          i, na;

    if (attrs = XmlDomGetAttrs(xctx, n))
    {
	printf("\n    ELEM %s ATTRIBUTES: \n", XmlDomGetNodeName(xctx, n));
	for (na = XmlDomGetNodeMapLength(xctx, attrs), i = 0; i < na; i++)
	{ 
	    /* get attr qualified name, local name, uri, and prefix */
	    a = (xmlattrnode *) XmlDomGetNodeMapItem(xctx, attrs, i);

	    qname  = XmlDomGetAttrName(xctx, a);
	    uri    = XmlDomGetAttrURI(xctx, a);
	    local  = XmlDomGetAttrLocal(xctx, a);
	    prefix = XmlDomGetAttrPrefix(xctx, a);
	    value  = XmlDomGetAttrValue(xctx, a);

	    putchar('\n');
	    PRINT_A("QName",      qname);
	    PRINT_A("Value",      value);
	    PRINT_A("Namespace",  uri);
	    PRINT_A("Local Name", local);
	    PRINT_A("Prefix",     prefix);
	}
    }
}

/*------------------------------------------------------------------------
                             printElements
  ------------------------------------------------------------------------*/

#define PRINT_E(label, str) \
    if (str) printf("    ELEM %10s: \"%s\"\n", label, str); \
    else     printf("    ELEM %10s: Nil\n", label);

static void printElements(xmlctx *xctx, xmlnode *n)
{
    xmlnodelist *nodes;
    oratext     *qname, *uri, *local, *prefix;
    ub4          i, nn;

    if (n && (nodes = XmlDomGetChildNodes(xctx, n)))
    {
	for (nn = XmlDomGetNodeListLength(xctx, nodes), i = 0; i < nn; i++)
	{ 
	    /* get node qualified name, local name, namespace, and prefix */
	    qname  = XmlDomGetNodeName(xctx, n);
	    prefix = XmlDomGetNodePrefix(xctx, n);
	    local  = XmlDomGetNodeLocal(xctx, n);
	    uri    = XmlDomGetNodeURI(xctx, n);

	    putchar('\n');
	    PRINT_E("QName",      qname);
	    PRINT_E("Prefix",     prefix);
	    PRINT_E("Local Name", local);
	    PRINT_E("Namespace",  uri);

	    printAttrs(xctx, n);         
	    printElements(xctx, XmlDomGetNodeListItem(xctx, nodes, i));
	}
    }
}

/*------------------------------------------------------------------------
                                MAIN
  ------------------------------------------------------------------------*/

int main()
{
    xmlctx     *xctx;
    xmldocnode *doc;
    void       *saxcbctx;
    const xmlsaxcb *saxcb;
    xmlerr      ecode;
    ub4         flags;

    puts("DOM Namespace example.");

    /* make an XML context */
    if (!(xctx = XmlCreate(&ecode, (oratext *) "namespace_xctx", NULL)))
    {
	printf("Failed to create XML context, error %u\n", (unsigned) ecode);
	return -1;
    }

    /* parse the document */
    printf("Parsing '%s'...\n", DOCUMENT);
    doc = XmlLoadDom(xctx, &ecode, "file", DOCUMENT,
		     "validate", TRUE, "discard_whitespace", TRUE, NULL);

    if (doc)
    {
	puts("Parse succeeded, traversing DOM:");
	printElements(xctx, XmlDomGetDocElem(xctx, doc));
    }
    else
	printf("Parse failed, code %u\n", (unsigned) ecode);

    /* terminate */
    puts("\nFinished, cleaning up.");
    XmlFreeDocument(xctx, doc);
    (void) XmlDestroy(xctx);

    return ecode ? -1 : 0;
}

/* end of DOMNamespace.c */
