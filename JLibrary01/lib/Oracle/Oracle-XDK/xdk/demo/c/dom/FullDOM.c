/* Copyright (c) 2002, 2003, Oracle Corporation.  All rights reserved.  

   NAME
     FullDOM.c - Unified Full DOM demo
*/

#ifndef ORATYPES_ORACLE
# include <oratypes.h>
#endif

#ifndef XML_ORACLE
# include <xml.h>
#endif

#include <ctype.h>

#define TEST_DOCUMENT	(oratext *) "FullDOM.xml"

static char *nil = "Nil!";

static xmlctx  *unitest(oratext *encoding, oratext *xname, xmldocnode **udoc);
static oratext *strlit(boolean unicode, ub2 **dest, const char *s);
static void     print(boolean unicode, oratext *s);
 
int main() 
{ 
    xmlctx     *xctxa, *xctx8;
    xmldocnode *doca,  *doc8;

    puts("*** C XML Full Unified DOM Test ***\n");

    puts("--- ASCII ENCODING ---\n");
    xctxa = unitest((oratext *) "ascii", (oratext *) "unidemo_single", &doca);

    puts("\n--- UTF-8 ENCODING ---\n");
    xctx8 = unitest((oratext *) "utf-8", (oratext *) "unidemo_multi", &doc8);

    puts("Freeing documents...");
    XmlFreeDocument(xctxa, doca);
    XmlFreeDocument(xctx8, doc8);

    puts("\nDestroying contexts..."); 
    XmlDestroy(xctxa);
    XmlDestroy(xctx8);
 
    puts("Done, success."); 
    return 0; 
} 

#define FAIL { puts("Failed!"); exit(1); } 

static xmlctx *unitest(oratext *encoding, oratext *xname, xmldocnode **udoc)
{
    xmlctx         *xctx;
    xmldocnode     *doc;
    xmldtdnode     *dtd;
    xmlelemnode    *elem, *fish, *food, *subelem, *sub2, *fragelem;
    xmltextnode    *text, *t2, *t3, *subtext, *fragtext;
    xmlcommentnode *comment, *c2;
    xmlcdatanode   *cdata;
    xmlpinode      *pi;
    xmlentrefnode  *entref;
    xmlnodelist    *nl;
    xmlnamedmap    *nm;
    xmlfragnode    *frag;
    xmlattrnode    *attr1, *attr2, *gleep1, *gleep2;
    xmlnode        *node, *repl, *clone, *deep_clone;
    xmlerr          xerr;
    oratext        *data;
    boolean         unicode;
    ub4             i, level; 

    /* select node data, ASCII or UCS2 */
    ub2 work[1024], *u = work;
    oratext *_root, *_gib, *_cmt, *_targ, *_pic, *_seed, *_entref, *_fish,
        *_food, *_pregib, *_ask, *_newpi, *_sub, *_repl, *_frage, *_fragt,
        *_lensub, *_z9, *_foo, *_bam, *_af;
  
    printf("Creating XML context (%s encoding)...\n", encoding); 
  
    if (!(xctx = XmlCreate(&xerr, xname, "data_encoding", encoding, NULL)))
    { 
        printf("Failed to create XML context, error %u\n", (unsigned) xerr); 
        exit(1);
    } 

    unicode = !strcmp(XmlGetEncoding(xctx), "UTF-16");
    _root = strlit(unicode, &u, "ROOT");
    _gib = strlit(unicode, &u, "Gibberish");
    _cmt = strlit(unicode, &u, "Bit warm today, innit?");
    _targ = strlit(unicode, &u, "target");
    _pic = strlit(unicode, &u, "PI-contents");
    _seed = strlit(unicode, &u, "See DATA");
    _entref = strlit(unicode, &u, "EntRef");
    _fish = strlit(unicode, &u, "FISH");
    _food = strlit(unicode, &u, "FOOD");
    _pregib = strlit(unicode, &u, "Pre-Gibberish");
    _ask = strlit(unicode, &u, "Ask about the weather:");
    _newpi = strlit(unicode, &u, "New PI contents");
    _sub = strlit(unicode, &u, "SUB");
    _lensub = strlit(unicode, &u, "Lengthy SubText");
    _repl = strlit(unicode, &u, "REPLACEMENT, 1/2 PRICE");
    _frage = strlit(unicode, &u, "FragElem");
    _fragt = strlit(unicode, &u, "FragText");
    _z9 = strlit(unicode, &u, "0123456789");
    _foo = strlit(unicode, &u, "*foo*");
    _bam = strlit(unicode, &u, "bamboozle");
    _af = strlit(unicode, &u, "ABCDEF");

    puts("\nCreating new document with root element 'ROOT'..."); 
    if (!(doc = XmlCreateDocument(xctx, NULL, _root, NULL, &xerr)))
        FAIL 
 
    puts("DOM from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);

    puts("DOM from element 'ROOT':"); 
    elem = XmlDomGetDocElem(xctx, doc);
    XmlSaveDom(xctx, &xerr, elem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nAdding 7 children to element 'ROOT'..."); 
    if (!(text = XmlDomCreateText(xctx, doc, _gib)) || 
                !XmlDomAppendChild(xctx, elem, text)) 
        FAIL 
 
    if (!(comment = XmlDomCreateComment(xctx, doc, _cmt)) || 
                   !XmlDomAppendChild(xctx, elem, comment)) 
        FAIL 
 
    if (!(pi = XmlDomCreatePI(xctx, doc, _targ, _pic)) || 
              !XmlDomAppendChild(xctx, elem, pi)) 
        FAIL 
 
    if (!(cdata = XmlDomCreateCDATA(xctx, doc, _seed)) || 
                 !XmlDomAppendChild(xctx, elem, cdata)) 
        FAIL 
 
    if (!(entref = XmlDomCreateEntityRef(xctx, doc, _entref)) || 
                  !XmlDomAppendChild(xctx, elem, entref)) 
        FAIL 
 
    if (!(fish = XmlDomCreateElem(xctx, doc, _fish)) || 
                !XmlDomAppendChild(xctx, elem, fish)) 
        FAIL 
 
    if (!(food = XmlDomCreateElem(xctx, doc, _food)) || 
                !XmlDomAppendChild(xctx, elem, food)) 
        FAIL 
 
    puts("Document w/BOM from doc node with its 7 children:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1,
	       "bom", TRUE, NULL);

    puts("Document wo/BOM from doc node with its 7 children:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nTesting node insertion..."); 
    puts("Adding 'Pre-Gibberish' text node...");
    if (!(t2 = XmlDomCreateText(xctx, doc, _pregib)) || 
              !XmlDomInsertBefore(xctx, elem, t2, text)) 
        FAIL 
 
    puts("Adding 'Ask about the weather' comment node ..."); 
    if (!(c2 = XmlDomCreateComment(xctx, doc, _ask)) || 
        !XmlDomInsertBefore(xctx, elem, c2, comment)) 
        FAIL 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
#if 0
    puts("\nTesting node removal by name ..."); 
    puts("Removing 'FISH' element"); 
    if (!(nl = XmlDomGetChildNodes(xctx, elem)) || 
        !XmlDomRemoveNamedItem(xctx, nl, (oratext *) "FISH")) 
        FAIL 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
#endif
 
    puts("\nTesting nextSibling links starting at first child..."); 
    for (node = XmlDomGetFirstChild(xctx, elem); node;
         node = XmlDomGetNextSibling(xctx, node)) 
    {
	XmlSaveDom(xctx, &xerr, node, "stdio", stdout, "indent_level", 1, NULL);
    }
 
    puts("\nTesting previousSibling links starting at last child..."); 
    for (node = XmlDomGetLastChild(xctx, elem); node;
         node = XmlDomGetPrevSibling(xctx, node)) 
    {
	XmlSaveDom(xctx, &xerr, node, "stdio", stdout, "indent_level", 1, NULL);
    }
 
    puts("\nTesting setting node value..."); 
    puts("Original node:"); 
    XmlSaveDom(xctx, &xerr, pi, "stdio", stdout, "indent_level", 1, NULL);
    XmlDomSetNodeValue(xctx, pi, _newpi); 
    puts("Node after new value:"); 
    XmlSaveDom(xctx, &xerr, pi, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nAdding another element level, i.e., 'SUB' ..."); 
    if (!(subelem = XmlDomCreateElem(xctx, doc, _sub)) || 
        !XmlDomInsertBefore(xctx, elem, subelem, cdata) || 
        !(subtext = XmlDomCreateText(xctx, doc, _lensub)) || 
        !XmlDomAppendChild(xctx, subelem, subtext)) 
        FAIL 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nAdding a second 'SUB' element..."); 
    if (!(sub2 = XmlDomCreateElem(xctx, doc, _sub)) || 
        !XmlDomInsertBefore(xctx, elem, sub2, cdata)) 
        FAIL 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nGetting all SUB nodes..."); 
    if (!(nl = XmlDomGetDocElemsByTag(xctx, doc, _sub)))
        FAIL 
    for (i = 0; i < XmlDomGetNodeListLength(xctx, nl); i++) 
	XmlSaveDom(xctx, &xerr, XmlDomGetNodeListItem(xctx, nl, i),
			"stdio", stdout, "indent_level", 1, NULL); 
 
    puts("\nTesting parent links..."); 
    for (level = 1, node = subtext; node;
         node = XmlDomGetParentNode(xctx, node), level++) 
    {
	XmlSaveDom(xctx, &xerr, node, "stdio", stdout,
		   "indent_level", level, "prune", TRUE, NULL);
    }
 
    puts("\nTesting owner document of node..."); 
    XmlSaveDom(xctx, &xerr, subtext, "stdio", stdout, "indent_level", 1, NULL);
    XmlSaveDom(xctx, &xerr, XmlDomGetOwnerDocument(xctx, subtext),
		    "stdio", stdout, "indent_level", 1, "prune", TRUE, NULL);
 
    puts("\nTesting node replacement..."); 
    if (!(t3 = XmlDomCreateText(xctx, doc, _repl)) || 
              !XmlDomReplaceChild(xctx, t3, pi)) 
        FAIL 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nTesting node removal..."); 
    if (!XmlDomRemoveChild(xctx, entref)) 
        FAIL 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nNormalizing..."); 
    XmlDomNormalize(xctx, elem); 
 
    puts("Document from doc node:"); 
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nCreating and populating document fragment..."); 
    if (!(frag = XmlDomCreateFragment(xctx, doc)) || 
        !(fragelem = XmlDomCreateElem(xctx, doc, _frage)) || 
        !(fragtext = XmlDomCreateText(xctx, doc, _fragt)) || 
        !XmlDomAppendChild(xctx, frag, fragelem) || 
        !XmlDomAppendChild(xctx, frag, fragtext)) 
        FAIL 
    XmlSaveDom(xctx, &xerr, frag, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("Insert document fragment..."); 
    if (!XmlDomInsertBefore(xctx, elem, frag, comment)) 
        FAIL 
    XmlSaveDom(xctx, &xerr, elem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nCreate two attributes..."); 
    if (!(attr1 = XmlDomCreateAttr(xctx, doc, (oratext *) "Attr1",
                                              (oratext *) "Value1")) || 
        !(attr2 = XmlDomCreateAttr(xctx, doc, (oratext *) "Attr2",
                                              (oratext *) "Value2"))) 
        FAIL 
    puts("Setting attributes..."); 
    if (XmlDomSetAttrNode(xctx, subelem, attr1) || 
        XmlDomSetAttrNode(xctx, subelem, attr2))
        FAIL 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nAltering attribute1 value..."); 
    XmlDomSetAttrValue(xctx, attr1, (oratext *) "New1"); 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nFetching attribute by name (Attr2)..."); 
    if (!(node = XmlDomGetAttrNode(xctx, subelem, (oratext *) "Attr2"))) 
        FAIL 
    XmlSaveDom(xctx, &xerr, node, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nRemoving attribute by name (Attr1)..."); 
    XmlDomRemoveAttr(xctx, subelem, (oratext *) "Attr1"); 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nAdding new attribute..."); 
    XmlDomSetAttr(xctx, subelem, (oratext *) "Attr3",
                                 (oratext *) "Value3");
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nRemoving attribute by pointer (Attr2)..."); 
    if (!XmlDomRemoveAttrNode(xctx, subelem, attr2)) 
        FAIL 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nAdding new attribute w/same name (test replacement)..."); 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
    if (!(node = XmlDomCreateAttr(xctx, doc, (oratext*)"Attr3",
                                             (oratext*)"Zoo3"))) 
        FAIL 
    XmlDomSetAttrNode(xctx, subelem, node);
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nTesting node (attr) set by name ..."); 
    puts("Adding 'GLEEP' attribute..."); 
    nm = XmlDomGetAttrs(xctx, subelem);
    if (!(gleep1 = XmlDomCreateAttr(xctx, doc, (oratext*)"GLEEP",
                                               (oratext*)"gleep1")) || 
        XmlDomSetNamedItem(xctx, nm, gleep1))
        FAIL 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nTesting node set by name ..."); 
    puts("Replacing 'GLEEP' attribute..."); 
    if (!(gleep2=XmlDomCreateAttr(xctx,doc, (oratext*)"GLEEP",
                                            (oratext*)"gleep2")) ||
        !(repl = XmlDomSetNamedItem(xctx, nm, gleep2))) 
        FAIL 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
    puts("Replaced node was:"); 
    XmlSaveDom(xctx, &xerr, repl, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nOriginal SubROOT..."); 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
    puts("Cloned SubROOT (not deep)..."); 
    clone = XmlDomCloneNode(xctx, subelem, FALSE); 
    XmlSaveDom(xctx, &xerr, clone, "stdio", stdout, "indent_level", 1, NULL);
    puts("Cloned SubROOT (deep)..."); 
    deep_clone = XmlDomCloneNode(xctx, subelem, TRUE); 
    XmlSaveDom(xctx, &xerr, deep_clone, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nSplitting text..."); 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
    XmlDomSplitText(xctx, subtext, 3); 
    XmlSaveDom(xctx, &xerr, subelem, "stdio", stdout, "indent_level", 1, NULL);
 
    puts("\nTesting string operations..."); 
    fputs("    CharData = ", stdout);
    if (data = XmlDomGetCharData(xctx, subtext))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    puts("Setting new data..."); 
    XmlDomSetCharData(xctx, subtext, _z9); 
    fputs("    CharData = ", stdout);
    if (data = XmlDomGetCharData(xctx, subtext))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    printf("    CharLength = %d\n", XmlDomGetCharDataLength(xctx, subtext)); 

    fputs("    Substring(0,5) = ", stdout);
    if (data = XmlDomSubstringData(xctx, subtext, 0, 5))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    fputs("    Substring(8,2) = ", stdout);
    if (data = XmlDomSubstringData(xctx, subtext, 8, 2))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    puts("Appending data..."); 
    XmlDomAppendData(xctx, subtext, _af, NULL); 
    fputs("    CharData = ", stdout);
    if (data = XmlDomGetCharData(xctx, subtext))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    puts("Inserting data..."); 
    XmlDomInsertData(xctx, subtext, 10, _foo, NULL); 
    fputs("    CharData = ", stdout);
    if (data = XmlDomGetCharData(xctx, subtext))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    puts("Deleting data..."); 
    XmlDomDeleteData(xctx, subtext, 0, 10, NULL); 
    fputs("    CharData = ", stdout);
    if (data = XmlDomGetCharData(xctx, subtext))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    puts("Replacing data..."); 
    XmlDomReplaceData(xctx, subtext, 1, 3, _bam, NULL); 
    fputs("    CharData = ", stdout);
    if (data = XmlDomGetCharData(xctx, subtext))
    { putchar('"'); print(unicode, data); putchar('"'); putchar('\n'); }
    else fputs(nil, stdout);

    XmlFreeDocument(xctx, doc);
    puts("FINISHED!");

    puts("\n*** Parsing test document ***\n");

    if (!(doc = XmlLoadDom(xctx, &xerr, "file", TEST_DOCUMENT, NULL)))
    {
        printf("Parse failed, code %d\n", (int) xerr);
        exit(1);
    }

    puts("Document from doc node:");
    XmlSaveDom(xctx, &xerr, doc, "stdio", stdout, "indent_level", 1, NULL);

    dtd = XmlDomGetDTD(xctx, doc);

    puts("Testing DTD notations...");
    if (nm = XmlDomGetDTDNotations(xctx, dtd))
    {
        size_t n_notes = XmlDomGetNodeMapLength(xctx, nm);

        printf("# of notations = %d\n", (int) n_notes);
        for (i = 0; i < n_notes; i++)
            XmlSaveDom(xctx, &xerr, XmlDomGetNodeMapItem(xctx, nm, i),
		       "stdio", stdout, "indent_level", 1, NULL);
    }
    else
        puts("No defined notations\n");

    puts("Testing DTD entities...");
    if (nm = XmlDomGetDTDEntities(xctx, dtd))
    {
        size_t n_entities = XmlDomGetNodeMapLength(xctx, nm);

        printf("# of entities = %d\n", (int) n_entities);
        for (i = 0; i < n_entities; i++)
            XmlSaveDom(xctx, &xerr, XmlDomGetNodeMapItem(xctx, nm, i),
		       "stdio", stdout, "indent_level", 1, NULL);
    }
    else
        puts("No defined entities\n");

    *udoc = doc;
    return xctx;
}

static oratext *strlit(boolean unicode, ub2 **dest, const char *s)
{
    size_t l, i;
    ub2   *u;

    if (unicode)
    {
        l = strlen(s) + 1;
        for (u = *dest, i = 0; i < l; i++)
            u[i] = (ub2) s[i];
        *dest += l;
        return (oratext *) u;
    }
    else
        return (oratext *) s;
}

static void print(boolean unicode, oratext *s)
{
    ub2 *up, uc;

    if (unicode)
    {
        for (up = (ub2 *) s; uc = *up; up++)
        {
            if (isascii(uc))
                putchar(uc);
            else
                printf("\\u%04x", (unsigned) uc);
        }
    }
    else
        fputs((char *) s, stdout);
}

/* end of file FullDOM.c */
