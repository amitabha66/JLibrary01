/* Copyright (c) 2002, 2003, Oracle Corporation.  All rights reserved.  */

/*

   NAME
     XSLXPathSample.c 

   DESCRIPTION
     Sample usage of C XSL/XPath processor

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
    xmlctx     *xctx;
    xpctx      *xptctx;
    xpexpr     *exp;
    xpobj      *obj;
    xmlerr      err;
    xmlnode    *node;
    xmldocnode *xmldoc;
    boolean     bool;
    double      num;
    oratext    *str;
    ub4         i;
    oratext    *lang = (oratext*)"american";
    oratext    *domencoding = (oratext*)"utf-8";
    oratext    *baseuri;    
    oratext    *xmlFile, *xpathexpr;

    if (argc < 3) {
        printf("Usage is: XSLXPathSample <xmlfile> <xpath>\n");
        return 1;  
    }

    xmlFile = (oratext*)argv[1];
    xpathexpr = (oratext*)argv[2];
   
    baseuri   = NULL;

    /* create xml meta-context */
    xctx = XmlCreate(&err, (oratext*)"xslxpath",
		     "data_encoding", (oratext*)"utf-8", NULL);
    if (!xctx) {
        printf("Failed to create xctx: %d\n", err);     
        return -1;  
    }

    /* Parsing the xml file */
    xmldoc = XmlLoadDom(xctx, &err, "file", xmlFile, 
                                    "base_uri", baseuri, NULL);
    /* Prepare the tree for xpath evaluation */
    XmlDomSetDocOrder(xctx, (xmlnode *) xmldoc, 0);

    /* create XPath processor (context) */
    xptctx = XmlXPathCreateCtx(xctx, NULL, xmldoc, 0, 0);
 
    exp = XmlXPathParse (xptctx, xpathexpr, &err);
    if (!exp) {
        printf("Failed to parse xpath: %d\n", err);     
        return -1;  
    }

    obj = XmlXPathEval (xptctx, exp,  &err);
    if (!obj) {
        printf("Failed to evaluate xpath: %d\n", err);     
        return -1;  
    }

    switch (XmlXPathGetObjectType(obj))
    {
    case XMLXSL_TYPE_NDSET:
        printf("NodeSet:\n");
        for (i = 0; i < XmlXPathGetObjectNSetNum(obj); i++) {
           xmlnode     *node = XmlXPathGetObjectNSetNode(obj, i);
           xmlnodetype  type = XmlDomGetNodeType(xctx, node);

           if (type == XMLDOM_ELEM || type == XMLDOM_ATTR) {
               printf("Node Name : %s\n", 
                            XmlDomGetNodeName(xctx, node));
           } else {
               printf("Node Value : %s\n", 
                            XmlDomGetNodeValue(xctx, node));
            }

	}
        break;
    case XMLXSL_TYPE_BOOL:
        bool = XmlXPathGetObjectBoolean(obj);
        printf("Boolean Value : %d\n", bool);
        break;
    case XMLXSL_TYPE_NUM:
        num = XmlXPathGetObjectNumber(obj);
        printf("Numeric Value : %10.2f\n", num);
        break;
    case XMLXSL_TYPE_STR:
        str = XmlXPathGetObjectString(obj);
        printf("String Value : %s\n", str);
        break;
    }

    XmlDestroy(xctx);
    XmlXPathDestroyCtx (xptctx);

    return 0;
}
