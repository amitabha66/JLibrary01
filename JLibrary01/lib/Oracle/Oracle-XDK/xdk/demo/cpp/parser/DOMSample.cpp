// Copyright (c) 1999, 2003, Oracle.  All rights reserved.  

///////////////////////////////////////////////////////////////////////////////
// NAME
//   DOMSample.cpp
//
// DESCRIPTION
//   Sample usage of C++ XML parser via DOM interface
//
// PUBLIC FUNCTION(S)
//
// PRIVATE FUNCTION(S)
//
// NOTES
//   none
///////////////////////////////////////////////////////////////////////////////

#include <iostream.h>
#include <string.h>

#ifndef ORAXML_CPP_ORACLE
# include <oraxml.hpp>
#endif

#define DOCUMENT	"cleo.xml"
#define DEFAULT_SPEAKER	"Soothsayer"

void dump(Node *node);
void dumpspeech(Node *node);

char *speaker;
char *act, *scene;
uword n_speech;

int main(int argc, char **argv)
{
    XMLParser   parser;
    ub4         flags;
    uword       ecode;
    flags = XML_FLAG_VALIDATE | XML_FLAG_DISCARD_WHITESPACE;

    cout << "XML C++ DOM sample\n";

    speaker = (argc > 1) ? argv[1] : (char *) DEFAULT_SPEAKER;

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

    cout << "Dumping " << speaker << " speeches...\n";
    cout.flush();
    cout << "-----------------------------------------------------------\n";
    act = scene = (char *) "";
    n_speech = 0;
    dump(parser.getDocumentElement());

    (void) parser.xmlterm();	// terminate LPX package

    return 0;
}

void dump(Node *node)
{
    Node *title, *speak;
    char *name, *who;
    uword i, n_nodes;

    name = (char *) node->getName();
    if (!strcmp((char *) name, "ACT"))
    {
	title = node->getFirstChild();
	act = (char *) title->getFirstChild()->getValue();
    }
    else if (!strcmp((char *) name, "SCENE"))
    {
	title = node->getFirstChild();
	scene = (char *) title->getFirstChild()->getValue();
    }
    else if (!strcmp((char *) name, "SPEECH"))
    {
	speak = node->getFirstChild();
	who = (char *) speak->getFirstChild()->getValue();
	if (!strcmp(who, speaker))
	    dumpspeech(node);
    }

    if (node->hasChildNodes())
    {
	n_nodes = node->numChildNodes();
	for (i = 0; i < n_nodes; i++)
	    dump(node->getChildNode(i));
    }
}

// <SPEECH>
// <SPEAKER>Soothsayer</SPEAKER>
// <LINE>Your will?</LINE>
// </SPEECH>

// <SPEECH>
// <SPEAKER>CLEOPATRA</SPEAKER>
// <LINE><STAGEDIR>Aside to DOMITIUS ENOBARBUS</STAGEDIR>  What means this?</LINE>
// </SPEECH>

void dumpspeech(Node *node)
{
    Node    *kid, *part, *partkid;
    uword    i, j, n_node, n_part;
    oratext *partname, *partval;

    if (n_speech++)
	cout << "\n";
    cout << act << ", " << scene << "\n";
    n_node = node->numChildNodes();
    for (i = 0; i < n_node; i++)	// skip speaker
    {
	kid = node->getChildNode(i);	// line #i
	if (!strcmp((char *) kid->getName(), "LINE"))
	{
	    n_part = kid->numChildNodes();
	    for (j = 0; j < n_part; j++)
	    {
		part = kid->getChildNode(j);
		if (part->getType() == TEXT_NODE)
		    cout << "    " << (char *) part->getValue() << "\n";
		else
		{
		    partname = part->getName();
		    partval = part->getFirstChild()->getValue();
		    if (!strcmp((char *) partname, "STAGEDIR"))
			cout << "    [" << (char *) partval << "]\n";
		    else
			cout << "    {" << (char *) partval << "}\n";
		}
	    }
	}
    }
    cout.flush();
}

// end of DOMSample.c
