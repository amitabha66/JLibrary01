// Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

//////////////////////////////////////////////////////////////////////////////
// NAME
//   SAXNamespace.cpp
//
// DESCRIPTION
//   This file demonstates a simple use of the parser and Namespace
//   extensions to the SAX APIs. 
//   The XML file that is given to the application is parsed and the
//   elements and attributes in the document are printed.
//////////////////////////////////////////////////////////////////////////////

#include <iostream.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

#define DOCUMENT         "NSExample.xml"

/*------------------------------------------------------------------------
                           FUNCTION PROTOTYPES
  ------------------------------------------------------------------------*/

extern "C" {

int startDocument(void *ctx);
int endDocument(void *ctx);
int endElement(void *ctx, const oratext *name);
int nsStartElement(void *ctx, const oratext *qname, 
                        const oratext *local, 
                        const oratext *nsp,
                        const struct xmlnodes *attrs);

} // extern "C"

/* SAX callback structure */

xmlsaxcb saxcb = {
    startDocument,
    endDocument,
    0,
    endElement,
    0,
    0,
    0,
    0,
    0,
    nsStartElement,
    0, 0, 0, 0, 0, 0, 0, 0
};

/*------------------------------------------------------------------------
                                MAIN
  ------------------------------------------------------------------------*/

int main()
{
   XMLParser   parser;
   ub4         flags;
   uword       ecode;

   flags = XML_FLAG_VALIDATE | XML_FLAG_DISCARD_WHITESPACE;

   cout << "XML C++ SAX Namespace\n";

   cout << "Initializing XML package...\n";

   if (ecode = parser.xmlinit((oratext *) 0,           // encoding
                                 (void (*)(void *, const oratext *, ub4)) 0,
                                 (void *) 0,           // msghdlr ctx
                                 (xmlsaxcb *) &saxcb)) // SAX callback
   {
       cout << "Failed to initialize XML parser, error " << ecode;
       return 1;
   }

   /* parse the document */

   cout << "Parsing '" << DOCUMENT << "'...\n";
   cout.flush();
   if (ecode = parser.xmlparse((oratext *) DOCUMENT, (oratext *) 0, flags))
      return 1;

   (void) parser.xmlterm();	// terminate LPX package

   return 0;
}

/*------------------------------------------------------------------------
                             SAX Interface
  ------------------------------------------------------------------------*/

extern "C" {

int startDocument(void *ctx)
{
    cout << "\nStartDocument\n\n";
    return 0;
}


int endDocument(void *ctx)
{
    cout << "\nEndDocument\n";
    return 0;
}


int endElement(void *ctx, const oratext *name)
{
    cout << "\nELEMENT Name  : " << (char*)name << "\n";
    return 0;
}


int nsStartElement(void *ctx, const oratext *qname, const oratext *local, 
                   const oratext *nsp, const struct xmlnodes *attrs)
{
    xmlnode *attr;
    uword    i;

    oratext *aqname;
    oratext *alocal;
    oratext *anamespace;
    oratext *aprefix;
    oratext *avalue;

    /*
     * Use the functions getXXXQualifiedName(), getXXXLocalName(), and
     * getXXXNamespace() to get Namespace information.
     */

    if (qname == (oratext*)NULL)
       qname = (oratext*)" ";
    if (local == (oratext*)NULL)
       local = (oratext*)" ";
    if (nsp == (oratext*)NULL)
       nsp = (oratext*)" ";
    
    cout << "ELEMENT Qualified Name: " << (char*)qname << "\n";
    cout << "ELEMENT Local Name    : " << (char*)local << "\n";
    cout << "ELEMENT Namespace     : " << (char*)nsp << "\n";

    if (attrs)
    {
       for (i = 0; i < numAttributes(attrs); i++)
       {
          attr = getAttributeIndex(attrs,i);

          aqname = alocal = anamespace = aprefix = avalue = (oratext*)" ";

          if (getAttrQualifiedName(attr))
             aqname = (oratext *) getAttrQualifiedName(attr);

          if (getAttrPrefix(attr))
             aprefix = (oratext *) getAttrPrefix(attr);

          if (getAttrLocal(attr))
             alocal = (oratext *) getAttrLocal(attr);

          if (getAttrNamespace(attr))
             anamespace = (oratext *) getAttrNamespace(attr);

          if (getAttrValue(attr))
             avalue = (oratext *) getAttrValue(attr);

         cout << " ATTRIBUTE Qualified Name   : " << (char*)aqname << "\n";
         cout << " ATTRIBUTE Prefix           : " << (char*)aprefix << "\n";
         cout << " ATTRIBUTE Local Name       : " << (char*)alocal << "\n";
         cout << " ATTRIBUTE Namespace        : " << (char*)anamespace << "\n";
         cout << " ATTRIBUTE Value            : " << (char*)avalue << "\n";
         cout << "\n";
       }
    }
    return 0;
}

} // extern "C"

// end of SAXNamespace.cpp
