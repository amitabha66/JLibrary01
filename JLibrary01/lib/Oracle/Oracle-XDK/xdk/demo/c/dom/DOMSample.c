/* Copyright (c) 1999, 2003, Oracle.  All rights reserved.  */
 
/*
   NAME
     DOMSample.c - Sample DOM usage
 
   DESCRIPTION
     Sample usage of C XML parser via DOM interface
*/
 
#include <stdio.h>
 
#ifndef XML_ORACLE
# include <xml.h>
#endif
 
#define DOCUMENT "cleo.xml"
 
void dump(xmlctx *ctx, xmlnode *node);
void dumppart(xmlctx *ctx, xmlnode *node, boolean indent);
 
int main()
{
    xmlctx     *xctx;
    xmldocnode *doc;
    xmlerr       ecode;
 
    puts("XML C DOM sample");
 
    puts("Initializing XML package...");
 
    if (!(xctx = XmlCreate(&ecode, (oratext *) "domsample_xctx", NULL)))
    {
	printf("Failed to create XML context, error %u\n", (unsigned) ecode);
	return 1;
    }

    printf("Parsing '%s' ...\n", DOCUMENT);
    if (!(doc = XmlLoadDom(xctx, &ecode, "file", DOCUMENT, "validate", TRUE,
			   "discard_whitespace", TRUE, NULL)))
    {
	printf("Parse failed, error %u\n", (unsigned) ecode);
	return 1;
    }

    puts("Outlining...");
    dump(xctx, XmlDomGetDocElem(xctx, doc));

    XmlFreeDocument(xctx, doc);
    XmlDestroy(xctx);

    return 0;
}

void dump(xmlctx *xctx, xmlnode *node)
{
    oratext     *name;
    xmlnodelist *nodes;
    ub4          i, n_nodes;
 
    name = XmlDomGetNodeName(xctx, node);
    if (!strcmp((char *) name, "ACT"))
        dumppart(xctx, node, FALSE);
    else if (!strcmp((char *) name, "SCENE"))
        dumppart(xctx, node, TRUE);
    if (XmlDomHasChildNodes(xctx, node))
    {
        nodes = XmlDomGetChildNodes(xctx, node);
        n_nodes = XmlDomGetNodeListLength(xctx, nodes);
        for (i = 0; i < n_nodes; i++)
            dump(xctx, XmlDomGetNodeListItem(xctx, nodes, i));
    }
}
 
void dumppart(xmlctx *xctx, xmlnode *node, boolean indent)
{
    void *title = XmlDomGetFirstChild(xctx, node);
 
    if (indent) 
       fputs("    ", stdout);
    puts((char *) XmlDomGetNodeValue(xctx, XmlDomGetFirstChild(xctx, title)));
}
 
/* end of DOMSample.c */
