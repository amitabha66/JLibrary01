/* Copyright (c) 1999, 2003, Oracle Corporation.  All rights reserved.  

   NAME
     XSLSample.c - Sample function for XSL 

   DESCRIPTION
     Sample usage of C XSL Processor
*/

#include <stdio.h>

#ifndef XML_ORACLE
# include <xml.h>
#endif

int main(int argc, char *argv[])
{
    xmlctx      *xctx = NULL;
    xslctx      *sctx = NULL;
    xmldocnode  *inst_doc = NULL, *ss_doc = NULL;
    xmlfragnode *output_frag;
    xmlerr       ecode;

    puts("XSL Example.");

    /* Check for correct usage */
    if (argc < 3)
    {
	puts("Usage is XSLSample <xmlfile> <xslfile>\n");
	return 1;
    }

    /* create the XML context */
    if (!(xctx = XmlCreate(&ecode, (oratext *) "xslsample_xctx", NULL)))
    {
        printf("Failed to create XML context, error %u\n", (unsigned) ecode);
        goto clean;
    }
 
    printf("Parsing stylesheet '%s'...\n", argv[2]);
    if (!(ss_doc = XmlLoadDom(xctx, &ecode, "file", argv[2], "validate", TRUE,
			      "discard_whitespace", TRUE, NULL)))
    {
        printf("Parse failed, error %u\n", (unsigned) ecode);
        goto clean;
    }

    puts("Creating Stylesheet context...");
    sctx = XmlXslCreate(xctx, ss_doc, (oratext *) argv[2], &ecode);

    printf("Parsing instance document '%s' ...\n", argv[1]);
    if (!(inst_doc = XmlLoadDom(xctx, &ecode, "file", argv[1], "validate", TRUE,
				"discard_whitespace", TRUE, NULL)))
    {
        printf("Parse failed, error %u\n", (unsigned) ecode);
        goto clean;
    }

    /* XSL processing; note that since XmlXslSetOutputStream has not been
       called, the result is a DOM fragment node and not an output stream so
       xsl:output is not supported. */
    puts("XSL Processing...");
    if (ecode = XmlXslProcess(sctx, inst_doc, FALSE))
    {
        printf("Processing failed, error %u\n", (unsigned) ecode);
        goto clean;
    }

    /* Get and print the result */
    output_frag = XmlXslGetOutput(sctx);
    puts("Result:");
    XmlSaveDom(xctx, &ecode, output_frag, "stdio", stdout,
	       "indent_step", 2, NULL);

    /* clean up */
clean:
    if (inst_doc)
        XmlFreeDocument(xctx, inst_doc);
    if (ss_doc)
        XmlFreeDocument(xctx, ss_doc);
    if (sctx)
        XmlXslDestroy(sctx);
    if (xctx)
        XmlDestroy(xctx);

    return 0;
}
