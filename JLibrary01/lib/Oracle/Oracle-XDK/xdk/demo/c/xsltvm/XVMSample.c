/* Copyright (c) 2002, 2003, Oracle.  All rights reserved.  */

/*

   NAME
     XVMSample.c 

   DESCRIPTION
     Sample usage of C XSLT Virtual Machine & Compiler

*/

#include <stdio.h>

#ifndef ORATYPES_ORACLE
# include <oratypes.h>
#endif

#ifndef XML_ORACLE
#include <xml.h>
#endif

/* ==========================================================================
                                main
  ---------------------------------------------------------------------------*/
int main(sword argc, char *argv[])
{
    xmlctx       *xctx = NULL;
    xmlxvmcomp   *comp = NULL;
    xmlxvm       *vm   = NULL;
    xmlerr        err;
    ub2          *code;
    ub4           len;
    oratext      *xmlFile, *xslFile;

    if (argc < 3) {
        printf("Usage is: XVMSample <xmlfile> <xslfile>\n");
        return 1;  
    }

    xmlFile = (oratext*)argv[1];
    xslFile = (oratext*)argv[2];

    /* Create meta-context */
    xctx = XmlCreate(&err, (oratext *) "sample",
                     "data_encoding", "US-ASCII", NULL);
    if (!xctx) {
          printf("Failed to create xctx: %d\n", err);     
        goto fail;  
    }

    /* Create compiler */
    comp = XmlXvmCreateComp (xctx);
    if (!comp) {
          printf("Failed to create Compiler.");       
        goto fail;
    }

    /* Create VM */
    vm = XmlXvmCreate (xctx, NULL);
    if (!vm) {
        printf("Failed to create VM");       
        goto fail;
    }
    
    /* Compile the XSLT stylesheet */
    code = XmlXvmCompileFile (comp, xslFile, NULL, XMLXVM_STRIPSPACE, &err);
    if (err != XMLERR_OK) {
        printf("Failed to Compile: %u\n", err);
        goto fail;
    }

    /* Set the compiled bytecode to the VM */
    len = XmlXvmGetBytecodeLength (code, &err);
    err = XmlXvmSetBytecodeBuffer (vm, code, len);
    if (err != XMLERR_OK) {
        printf("Failed to set the bytecode: %u\n", err);
        goto fail;
    }

    /* Transform the input XML document. The default output mode is stream. */
    err = XmlXvmTransformFile (vm, xmlFile, NULL);
    if (err != XMLERR_OK) {
        printf("Failed to transform: %u\n", err);
        goto fail;
    }
    return 0;	/* success */
 
  fail:
    /* Clean the memory */
    if (vm)
        XmlXvmDestroy(vm);
    if (comp)
        XmlXvmDestroyComp(comp);
    if (xctx)
        XmlDestroy(xctx);
    return 2;
}

/* end of file XVMSample.c */
