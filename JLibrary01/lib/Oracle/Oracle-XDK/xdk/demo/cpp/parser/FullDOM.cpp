// Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

//////////////////////////////////////////////////////////////////////////////
// NAME
//   FullDOM.cpp
//
// DESCRIPTION
//   Sample code to test full C++ DOM interface
//////////////////////////////////////////////////////////////////////////////

#include <iostream.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

#define TEST_DOC	(oratext *) "FullDOM.xml"

void dump(Node *node, uword level);
void dumpnode(Node *node, uword level);

static char *ntypename[] = {
    (char *) "0",
    (char *) "ELEMENT",
    (char *) "ATTRIBUTE",
    (char *) "TEXT",
    (char *) "CDATA",
    (char *) "ENTREF",
    (char *) "ENTITY",
    (char *) "PI",
    (char *) "COMMENT",
    (char *) "DOCUMENT",
    (char *) "DTD",
    (char *) "DOCFRAG",
    (char *) "NOTATION"
};

#define FAIL { cout << "Failed!\n"; return 1; }

int main()
{
    XMLParser     parser;
    Document     *doc;
    Element      *root, *elem, *subelem;
    Attr         *attr, *attr1, *attr2, *gleep1, *gleep2;
    Text         *text, *subtext;
    Node         *node, *pi, *comment, *entref, *cdata, *clone,
		 *deep_clone, *frag, *fragelem, *fragtext, *sub2,
		 *fish, *food, *food2, *repl;
    NodeList     *subs, *nodes;
    NamedNodeMap *attrs, *notes, *entities;
    DocumentType *dtd;
    uword         i, ecode, level;

    cout << "XML C++ Full DOM test\n";

    cout << "Initializing XML parser...\n";


    if (ecode = parser.xmlinit())
    {
	cout << "Failed to initialze XML parser, error " << ecode << "\n";
	return 1;
    }

    cout << "\nCreating new document...\n";
    if (!(doc = parser.createDocument((oratext *) 0, (oratext *) 0,
				      (DocumentType *) 0)))
	FAIL

    cout << "Document from root node:\n";
    dump(parser.getDocument(), 0);

    cout << "\nCreating root element ('ROOT')...\n";
    if (!(elem = doc->createElement((oratext *) "ROOT")))
	FAIL

    cout << "Setting as root element...\n";
    if (!doc->appendChild(elem))
	FAIL

    cout << "Document from 'ROOT' element:\n";
    dump(root = parser.getDocumentElement(), 0);

    cout << "Adding 7 children to 'ROOT' element...\n";
    if (!(text = doc->createTextNode((oratext *) "Gibberish")) ||
        !elem->appendChild(text))
	FAIL

    if (!(comment = doc->createComment((oratext*) "Bit warm today, innit?")) ||
        !elem->appendChild(comment))
	FAIL

    if (!(pi = doc->createProcessingInstruction((oratext *) "target",
						(oratext *) "PI-contents")) ||
        !elem->appendChild(pi))
	FAIL

    if (!(cdata = doc->createCDATASection((oratext *) "See DATA")) ||
        !elem->appendChild(cdata))
	FAIL

    if (!(entref = doc->createEntityReference((oratext *) "EntRef")) ||
        !elem->appendChild(entref))
	FAIL

    if (!(fish = doc->createElement((oratext *) "FISH")) ||
	!elem->appendChild(fish))
	FAIL

    if (!(food = doc->createElement((oratext *) "FOOD")) ||
	!elem->appendChild(food))
	FAIL

    cout << "Document from 'ROOT' element with its 7 children:\n";
    dump(root, 0);

    cout << "\nTesting node insertion...\n";
    cout << "Adding 'Pre-Gibberish' text node and 'Ask about the weather' comment node...\n";
    if (!(node = doc->createTextNode((oratext *) "Pre-Gibberish")) ||
        !elem->insertBefore(node, text))
	FAIL

    if (!(node = doc->createComment((oratext *) "Ask about the weather:")) ||
        !elem->insertBefore(node, comment))
	FAIL

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "\nTesting nextSibling links starting at first child...\n";
    for (node = elem->getFirstChild(); node; node = node->getNextSibling())
	dump(node, 1);

    cout << "\nTesting previousSibling links starting at last child...\n";
    for (node = elem->getLastChild(); node; node = node->getPreviousSibling())
	dump(node, 1);

    cout << "\nTesting setting node value...\n";
    cout << "Original node:\n";
    dump(pi, 1);
    pi->setValue((oratext *) "New PI contents");
    cout << "Node after new value:\n";
    dump(pi, 1);

    cout << "\nAdding another element level, i.e., 'SUB'...\n";
    if (!(subelem = doc->createElement((oratext *) "SUB")) ||
	!elem->insertBefore(subelem, cdata) ||
	!(subtext = doc->createTextNode((oratext *) "Lengthy SubText")) ||
        !subelem->appendChild(subtext))
	FAIL

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "\nAdding a second 'SUB' element...\n";
    if (!(sub2 = doc->createElement((oratext *) "SUB")) ||
	!elem->insertBefore(sub2, cdata))
	FAIL

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "\nGetting all SUB nodes - note the distinct hex addresses...\n";
    if (!(subs = doc->getElementsByTagName(root, (oratext *) "SUB")))
	FAIL
    for (i = 0; i < subs->getLength(); i++)
	dumpnode(subs->item(i), 1);

    cout << "\nTesting parent links...\n";
    for (level = 1, node = subtext; node; node = node->getParentNode(), level++)
	dumpnode(node, level);

    cout << "\nTesting owner document of node...\n";
    dumpnode(subtext, 1);
    dumpnode(subtext->getOwnerDocument(), 1);

    cout << "\nTesting node replacement...\n";
    if (!(node = doc->createTextNode((oratext *) "REPLACEMENT, 1/2 PRICE")) ||
        !pi->replaceChild(node))
	FAIL

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "\nTesting node removal...\n";
    if (!entref->removeChild())
	FAIL

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "\nNormalizing...\n";
    elem->normalize();

    cout << "Document from 'ROOT' element:\n";
    dump(root, 0);

    cout << "\nCreating and populating document fragment...\n";
    if (!(frag = doc->createDocumentFragment()) ||
	!(fragelem = doc->createElement((oratext *) "FragElem")) ||
	!(fragtext = doc->createTextNode((oratext *) "FragText")) ||
	!frag->appendChild(fragelem) ||
	!frag->appendChild(fragtext))
	FAIL
    dump(frag, 1);

    cout << "Insert document fragment...\n";
    if (!elem->insertBefore(frag, comment))
	FAIL
    dump(elem, 1);

    cout << "\nCreate two attributes...\n";
    if (!(attr1 = doc->createAttribute((oratext*)"Attr1",(oratext*)"Value1")) ||
	!(attr2 = doc->createAttribute((oratext*)"Attr2",(oratext*)"Value2")))
	FAIL
    cout << "Setting attributes...\n";
    if (!subelem->setAttributeNode(attr1, NULL) ||
	!subelem->setAttributeNode(attr2, NULL))
	FAIL
    dump(subelem, 1);

    cout << "\nAltering attribute1 value...\n";
    attr1->setValue((oratext *) "New1");
    dump(subelem, 1);

    cout << "\nFetching attribute by name (Attr2)...\n";
    if (!(node = subelem->getAttributeNode((oratext *) "Attr2")))
	FAIL
    dump(node, 1);

    cout << "\nRemoving attribute by name (Attr1)...\n";
    subelem->removeAttribute((oratext *) "Attr1");
    dump(subelem, 1);

    cout << "\nAdding new attribute...\n";
    if (!subelem->setAttribute((oratext *) "Attr3", (oratext *) "Value3"))
	FAIL
    dump(subelem, 1);

    cout << "\nRemoving attribute by pointer (Attr2)...\n";
    if (!subelem->removeAttributeNode(attr2))
	FAIL
    dump(subelem, 1);

    cout << "\nAdding new attribute w/same name (test replacement)...\n";
    dump(subelem, 1);
    if (!(attr = doc->createAttribute((oratext*)"Attr3", (oratext*)"Zoo3")))
	FAIL
    if (!subelem->setAttributeNode(attr, NULL))
	FAIL
    dump(subelem, 1);

    cout << "\nTesting node (attr) set by name...\n";
    cout << "Adding 'GLEEP' attr and printing out hex addresses of node set\n";
    attrs = subelem->getAttributes();
    if (!(gleep1=doc->createAttribute((oratext*)"GLEEP",(oratext*)"GLEEP1")) ||
	!attrs->setNamedItem(gleep1, NULL))
	FAIL
    dump(subelem, 0);

    cout << "\nTesting node (attr) set by name...\n";
    cout << "Replacing 'GLEEP' element - note the changed hex address\n";
    if (!(gleep2=doc->createAttribute((oratext*)"GLEEP",(oratext*)"GLEEP2")) ||
	!attrs->setNamedItem(gleep2, &repl))
	FAIL
    dump(subelem, 0);
    cout << "Replaced node was:\n";
    dump(repl, 1);

    cout << "\nTesting node removal by name...\n";
    cout << "Removing 'GLEEP' attribute\n";
    if (!attrs->removeNamedItem((oratext *) "GLEEP"))
	FAIL
    dump(subelem, 0);

    cout << "\nOriginal SubROOT...\n";
    dump(subelem, 1);
    cout << "Cloned SubROOT (not deep)...\n";
    clone = subelem->cloneNode(FALSE);
    dump(clone, 1);
    cout << "Cloned SubROOT (deep)...\n";
    deep_clone = subelem->cloneNode(TRUE);
    dump(deep_clone, 1);

    cout << "\nSplitting text...\n";
    dump(subelem, 1);
    subtext->splitText(3);
    dump(subelem, 1);

    cout << "\nTesting string operations...\n";
    cout << "    CharData = \"" << (char *) subtext->getData() << "\"\n";
    cout << "Setting new data...\n";
    subtext->setData((oratext *) "0123456789");
    cout << "    CharData = \"" << (char *) subtext->getData() << "\"\n";
    cout << "    CharLength = " << (int) subtext->getLength() << "\n";
    cout << "    Substring(0,5) = \"" <<
	(char *) subtext->substringData(0, 5) << "\"\n";
    cout << "    Substring(8,2) = \"" <<
	(char *) subtext->substringData(8, 2) << "\"\n";
    cout << "Appending data...\n";
    subtext->appendData((oratext *) "ABCDEF");
    cout << "    CharData = \"" << (char *) subtext->getData() << "\"\n";
    cout << "Inserting data...\n";
    subtext->insertData(10, (oratext *) "*foo*");
    cout << "    CharData = \"" << (char *) subtext->getData() << "\"\n";
    cout << "Deleting data...\n";
    subtext->deleteData(0, 10);
    cout << "    CharData = \"" << (char *) subtext->getData() << "\"\n";
    cout << "Replacing data...\n";
    subtext->replaceData(1, 3, (oratext *) "bamboozle");
    cout << "    CharData = \"" << (char *) subtext->getData() << "\"\n";

    cout << "Cleaning up...\n";
    parser.xmlclean();

    if (parser.getDocument())
    {
	cout << "Problem, document is not gone!!\n";
	return 1;
    }

    cout << "Parsing test document...\n";
    if (ecode = parser.xmlparse(TEST_DOC, (oratext *) 0, 0))
    {
	cout << "Parse failed, code " << ecode << "\n";;
	return ecode;
    }

    cout << "Document from root node:\n" << flush;
    dump(parser.getDocument(), 0);

    cout << "Testing getNotations...\n" << flush;
    dtd = parser.getDocType();
    if (notes = dtd->getNotations())
    {
	cout << "# of notations = " << notes->getLength() << "\n" << flush;
	for (i = 0; i < notes->getLength(); i++)
	    dump(notes->item(i), 1);
    }
    else
	cout << "No defined notations\n" << flush;

    cout << "Testing getEntities...\n" << flush;
    if (entities = dtd->getEntities())
    {
	cout << "# of entities = " << entities->getLength() << "\n" << flush;
	for (i = 0; i < entities->getLength(); i++)
	    dump(entities->item(i), 1);
    }
    else
	cout << "No defined entities\n" << flush;

    cout << "Cleaning up...\n";
    parser.xmlclean();

    if (parser.getDocument())
    {
	cout << "Problem, document is not gone!!\n";
	return 1;
    }

    cout << "\nTerminating parser...\n";
    parser.xmlterm();

    cout << "Success.\n";
    return 0;
}

void dump(Node *node, uword level)
{
    NodeList *nodes;
    uword     i, n_nodes;

    if (node)
    {
	dumpnode(node, level);
	if ((node->getType() != DOCUMENT_TYPE_NODE) && node->hasChildNodes())
	{
	    nodes = node->getChildNodes();
	    n_nodes = node->numChildNodes();
	    for (i = 0; i < n_nodes; i++)
		dump(nodes->item(i), level + 1);
	}
    }
}

void dumpnode(Node *node, uword level)
{
    const oratext *name, *value;
    short          type;
    NamedNodeMap  *attrs;
    Attr          *attr;
    uword          i, n_attrs;

    if (node)
    {
	for (i = 0; i <= level; i++)
	    cout << "    ";
	type = node->getType();
	cout << (char *) ntypename[type];
	if ((name = node->getName()) && (*name != '#'))
	    cout << " \"" << (char *) name << "\"";
	if (value = node->getValue())
	    cout << " = \"" << (char *) value << "\"";
	if ((type == ELEMENT_NODE) && (attrs = node->getAttributes()))
	{
	    cout << " [";
	    n_attrs = attrs->getLength();
	    for (i = 0; i < n_attrs; i++)
	    {
		if (i) cout << ", ";
		attr = (Attr *) attrs->item(i);
		cout << (char *) attr->getName();
		if (attr->getSpecified())
		    cout << "*";
		cout << "=\"" << (char *) attr->getValue() << "\"";
	    }
	    cout << "]";
	}
	cout << "\n";
    }
}

// end of FullDOM.cpp
