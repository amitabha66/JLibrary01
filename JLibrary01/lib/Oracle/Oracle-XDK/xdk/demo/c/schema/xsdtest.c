/* Copyright (c) 1999, 2003, Oracle.  All rights reserved.  */
 
/*
   NAME
     xsdtest.c - Sample Schema validation
 
   DESCRIPTION
     Sample usage of C XML Schema processor
*/
 
#include <stdio.h>

#ifndef XML_ORACLE
# include <xml.h>
#endif

int main(int argc, char **argv)
{
    xmlctx     *xctx;
    xmlerr      xerr;
    xmldocnode *docnode;
    xsdctx     *scctx;
    char       *doc, *schema;
    xmlnode    *root;

    puts("XML C Schema processor");

    if ((argc < 2) || (argc > 3))
    {
        puts("usage: validate <xml document> [schema]");
        return -1;
    }
    doc = argv[1];
    schema = (argc > 2) ? argv[2] : 0;

    puts("Initializing XML package...");

    xctx = XmlCreate(&xerr, (oratext *) "xsdtest", NULL);
    if (xerr)
    {
        printf("Failed to initialze XML meta context, error %u\n",
                           (unsigned) xerr);
            return 1;
    }

    printf("Parsing '%s' ...\n", doc);
    if (!(docnode = XmlLoadDom(xctx, &xerr, "uri", doc, NULL)))
    {
        printf("Parse of %s failed, error %u\n", doc, (unsigned) xerr);
        return 2;
    }

    root = XmlDomGetDocElem(xctx, docnode);

    puts("Initializing Schema package...");

    if (!(scctx = XmlSchemaCreate(xctx, &xerr, NULL)))
    {
        printf("Failed, code %u!\n", (unsigned) xerr);
        return 3;
    }

    puts("Validating document...");

    if (schema)
    {
        xerr = XmlSchemaLoad(scctx, (oratext *)schema, (ub4) 0);
        if (xerr)
        {
            printf("Failed to load %s, error %u\n", schema, (unsigned) xerr);
            return 4;
        }
    }

    if (xerr = XmlSchemaValidate(scctx, xctx, root))
    {
        printf("Validation failed, error %u\n", (unsigned) xerr);
        return 5;
    }

    puts("Document is valid.");
    XmlSchemaDestroy(scctx); 
    XmlDestroy(xctx);
    return 0;
}
