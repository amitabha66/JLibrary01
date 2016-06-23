// Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

//////////////////////////////////////////////////////////////////////////////
// NAME
//   DOMNamespace.cpp
//
// DESCRIPTION
//   This file demonstates a simple use of the parser and Namespace
//   extensions to the DOM APIs. 
//
//   The XML file that is given to the application is parsed and the
//   elements and attributes in the document are printed.
//
// PUBLIC FUNCTION(S)
//
// PRIVATE FUNCTION(S)
//
// NOTES
//   none
//////////////////////////////////////////////////////////////////////////////

#include <iostream.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

#define DOCUMENT         "NSExample.xml"

void dump(Node *node);
void dumpattrs(Node *node);

//
// main
//

int main()
{
    XMLParser   parser;
    ub4         flags;
    uword       ecode;
    flags = XML_FLAG_VALIDATE | XML_FLAG_DISCARD_WHITESPACE;

    cout << "\nXML C++ DOM Namespace\n";

    cout << "Initializing XML package...\n";

    if (ecode = parser.xmlinit())
    {
        cout << "Failed to initialize XML parser, error " << ecode;
        return 1;
    }

    cout << "Parsing '" << DOCUMENT << "'...\n";
    cout.flush();
    if (ecode = parser.xmlparse((oratext *) DOCUMENT, (oratext *) 0, flags))
	return 1;

    cout << "\nThe elements are:\n";
    dump(parser.getDocumentElement());

    (void) parser.xmlterm();	// terminate LPX package

    return 0;
}

//
// dump
//

void dump(Node *node)
{
    uword i, n_nodes;
    NodeList *nodes;
    size_t   nn;
    
    String qName;
    String localName;
    String nsName;
    String prefix;

    if (node == NULL)
       return;

    if (nodes = node->getChildNodes())
    {      
       for (nn = node->numChildNodes(), i=0; i < nn; i++)
       {
          // Use the methods getQualifiedName(), getLocalName(), 
          // getPrefix(), and getNamespace() to get Namespace
          // information.

          qName = prefix = localName = nsName = (oratext *)" ";

	  if (node->getQualifiedName() != (oratext *)NULL)
              qName = node->getQualifiedName();

	  if (node->getPrefix() != (oratext *)NULL)
              prefix = node->getPrefix();

	  if (node->getLocal() != (oratext *)NULL)
              localName = node->getLocal();

	  if (node->getNamespace() != (oratext *)NULL)
              nsName = node->getNamespace();

          cout << "  ELEMENT Qualified Name: " << (char *)qName << "\n";   
          cout << "  ELEMENT Prefix        : " << (char *)prefix << "\n";
          cout << "  ELEMENT Local Name    : " << (char *)localName << "\n";
          cout << "  ELEMENT Namespace     : " << (char *)nsName << "\n";

          dumpattrs(node);
          dump(node->getChildNode(i));
       }
    }
}

//
// dumpattrs
//

void dumpattrs(Node *node)
{
    NamedNodeMap  *attrs;
    Attr          *a;
    uword          i;
    size_t         na;

    oratext   *qname;
    oratext   *namespce;
    oratext   *local;
    oratext   *prefix;
    oratext   *value;

    if (attrs = node->getAttributes())
    {
       cout << "\n    ATTRIBUTES: \n";
       for (na = attrs->getLength(), i = 0; i < na; i++)
       { 
          /* get attr qualified name, local name, namespace, and prefix */

          a = (Attr *)attrs->item(i);

          qname = namespce = local = prefix = value = (oratext*)" ";

          if (a->getQualifiedName() != (oratext*)NULL)
             qname = a->getQualifiedName();

          if (a->getNamespace() != (oratext*)NULL)
             namespce = a->getNamespace();

          if (a->getLocal() != (oratext*)NULL)
             local = a->getLocal();

          if (a->getPrefix() != (oratext*)NULL)
             prefix = a->getPrefix();

          if (a->getValue() != (oratext*)NULL)
             value = a->getValue();

          cout << "      " << (char*)qname << " = " << (char*)value << "\n";
          cout << "      Namespace : " << (char*)namespce << "\n";
          cout << "      Local Name: " << (char*)local << "\n";
          cout << "      Prefix    : " << (char*)prefix << "\n\n";
       }
    }
    cout << "\n";
}
