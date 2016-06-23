/* Copyright (c) 2002, 2003, Oracle.  All rights reserved.  */

/*

   NAME
     XVMXPathSample.c 

   DESCRIPTION
     Sample usage of C XVM/XPath Virtual Machine & Compiler

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
int main (sword argc, char *argv[])
{
    xmlctx       *xctx;
    xmlxvmcomp   *comp = NULL;
    xmlxvm       *vm = NULL;
    xmldocnode   *xmldom;
    xvmobj       *obj;
    int           i;
    oratext      *xpath; 
    oratext      *xmlFile     = NULL;
    oratext      *baseuri     = NULL;
    ub2          *code        = NULL;
    xmlerr        err         = XMLERR_OK;

    if (argc < 3) {   
        printf("Usage is: XVMXPathSample <xmlfile> <xpath>\n");
        return -1;  
    }

    xmlFile = (oratext*)argv[1];
    xpath   = (oratext*)argv[2];

    /* create xml meta-context */
    xctx = XmlCreate(&err, (oratext *) "xvmxpath",
		     "data_encoding", (oratext*)"utf-8", NULL);
    if (!xctx) {
        printf("Failed to create xctx: %d\n", err);     
        return -1;  
    }

    /* create XSL/XPath Compiler */
    comp = XmlXvmCreateComp (xctx);
    if (!comp) {
        printf("Failed to create Compiler");       
        goto clean;
    }

    /* create XSLT Virtual Machine (XVM) */
    vm = XmlXvmCreate (xctx, NULL);
    if (!vm) {
        printf("Failed to create VM");       
        goto clean;
    }

    /* Compile the XPath expression */
    code = XmlXvmCompileXPath (comp, xpath, NULL, &err);
    if (!code) {
        printf("Failed to compile xpath: %d\n", err);     
        return -1;  
    }
    err = XmlXvmSetBytecodeBuffer (vm, code, 
                XmlXvmGetBytecodeLength(code, &err));

    /* Parse the XML file */
    xmldom = XmlLoadDom(xctx, &err, "file", xmlFile, NULL);
    XmlDomSetDocOrder(xctx, (xmlnode *) xmldom, 0);

    /* evaluate the XPath expression */
    obj  = (xvmobj*)XmlXvmEvaluateXPath (vm, code, 1, 1,
                                 XmlDomGetDocElem(xctx, xmldom));
    if (!obj) {
        printf("Failed to evaluate xpath\n");     
        return -1;  
    }
    switch (XmlXvmGetObjectType(obj)) 
    {
        case XMLXVM_TYPE_BOOL: 
            printf("Boolean Value : %d\n", 
                (int)XmlXvmGetObjectBoolean(obj));
            break;
	case XMLXVM_TYPE_NUM:
            printf("Numeric Value : %10.2f\n",
                XmlXvmGetObjectNumber(obj));
            break;
        case XMLXVM_TYPE_FRAG:
            printf("Obtained result tree fragment\n");
            break;
        case XMLXVM_TYPE_STR:
            printf("String Value : %s\n", 
                XmlXvmGetObjectString(obj));
            break;
        case XMLXVM_TYPE_NDSET:
            printf("NodeSet:\n");
            for (i = 0; i < XmlXvmGetObjectNSetNum (obj); i++) {
                xmlnode      *node;
                xmlnodetype   type;

                node = XmlXvmGetObjectNSetNode (obj, i);
                type = XmlDomGetNodeType(xctx, node);
                if (type == XMLDOM_ELEM || type == XMLDOM_ATTR) {
                    printf("Node Name : %s\n", 
                            XmlDomGetNodeName(xctx, node));
		} else {
                    printf("Node Value : %s\n", 
                            XmlDomGetNodeValue(xctx, node));
		}
	    }
            break;
    }


clean:
    /* =========================  Clean  =========================== */
    if (vm)
        XmlXvmDestroy(vm);
    if (comp)
        XmlXvmDestroyComp(comp);
    if (xctx)
        XmlDestroy(xctx);

    return 0;
}

