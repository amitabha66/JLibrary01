// Copyright (c) 1999, 2003, Oracle.  All rights reserved.

//////////////////////////////////////////////////////////////////////////////
// NAME		qmain.cpp
// DESCRIPTION	Demo program using C++ Class Generator classes derived
//		from schema queue.xsd
//////////////////////////////////////////////////////////////////////////////

#include <iostream.h>

#ifndef ORAXSD_CPP_ORACLE
# include <oraxsd.hpp>
#endif

#include "queue.h"	// Header produced by class generator

#define XSD_DOCUMENT	"queue.xsd"
#define OUT_DOCUMENT	"queue.out"
#define NSP_URI		"http://www.oracle.com/AQXmlDocument"

int main()
{
    uword ecode;

    // Initialize XML parser
    cout << "Initializing XML parser...\n";
    XMLParser *parser = new XMLParser();
    if (ecode = parser->xmlinitenc(NULL, (oratext *) "ISO-8859-1"))
    {
	cout << "Failed to initialize parser, code " << ecode << "\n";
        return 1;
    }

    // Create empty document
    cout << "Creating document...\n";
    Document *doc = parser->createDocument((String) "dummy_uri",
					   (String) "dummy_qname",
					   (DocumentType *) 0);

    // Create client_operation 2nd-level element
    queue::txid *tid = new queue::txid(doc, (String) "sdasdfdsf");
    queue::client_operation *clop = new queue::client_operation(doc, tid);
    clop->set_opcode((String) "SEND");

    // Create producer_options 2nd-level element
    queue::recipient *r1 = new queue::recipient(doc, (String) "abc");
    queue::recipient_list *rl = new queue::recipient_list(doc, r1);
    queue::recipient *r2 = new queue::recipient(doc, (String) "abc");
    r2->set_lookup_type((String) "LDAP");
    rl->addNode(r2);
    queue::priority *pri = new queue::priority(doc, 23);
    queue::destination *dest = new queue::destination(doc, (String) "queue1");
    dest->set_lookup_type((String) "NORMAL");
    queue::producer_options *prop =
	new queue::producer_options(doc, dest, pri, (queue::expiration*)0, rl);
    prop->set_delivery_mode((String) "PERSISTENT");

    // Create message_set 2nd-level element
    queue::name *n = new queue::name(doc, (String) "Car");
    queue::value *v = new queue::value(doc, (String) "Toyota");
    queue::item *i1 = new queue::item(doc, n, v);
    i1->set_item_type((String) "STRING");

    n = new queue::name(doc, (String) "Color");
    v = new queue::value(doc, (String) "Blue");
    queue::item *i2 = new queue::item(doc, n, v);
    i2->set_item_type((String) "STRING");

    n = new queue::name(doc, (String) "Shape");
    v = new queue::value(doc, (String) "Circle");
    queue::item *i3 = new queue::item(doc, n, v);
    i3->set_item_type((String) "STRING");

    n = new queue::name(doc, (String) "Price");
    v = new queue::value(doc, (String) "20000");
    queue::item *i4 = new queue::item(doc, n, v);
    i4->set_item_type((String) "NUMBER");

    queue::map_data *md = new queue::map_data(doc, i1);
    md->addNode(i2);
    md->addNode(i3);
    md->addNode(i4);

    queue::reply_to *rt =
	new queue::reply_to(doc, (String) "oracle::redwoodshores::100");
    queue::userid *uid = new queue::userid(doc, (String) "scott");
    queue::appid *aid = new queue::appid(doc, (String) "AQProduct");
    queue::groupid *gid = new queue::groupid(doc, (String) "AQ");
    queue::oracle_jms_properties *ojp =
	new queue::oracle_jms_properties(doc, (queue::type *) 0, rt, uid,
					 aid, gid, (queue::group_sequence *)0,
					 (queue::timestamp *) 0,
					 (queue::recv_timestamp *) 0);

    n = new queue::name(doc, (String) "country");
    v = new queue::value(doc, (String) "USA");
    queue::property *prop1 = new queue::property(doc, n, v);
    prop1->set_property_type((String) "STRING");

    n = new queue::name(doc, (String) "State");
    v = new queue::value(doc, (String) "california");
    queue::property *prop2 = new queue::property(doc, n, v);
    prop2->set_property_type((String) "STRING");

    queue::user_properties *up = new queue::user_properties(doc, prop1);
    up->addNode(prop2);

    queue::jms_map_message *jmm = new queue::jms_map_message(doc, ojp, up, md);
    queue::message_payload *mp = new queue::message_payload(doc, jmm);

    queue::correlation *cor =
	new queue::correlation(doc, (String) "XML_40_NEW_TEST");
    queue::delay *del = new queue::delay(doc, 10);
    queue::sender_id *sid =
	new queue::sender_id(doc, (String) "scott::home::0");
    queue::message_header *mh =
	new queue::message_header(doc, (queue::message_id *) 0, cor, del,
				  (queue::priority *) 0,
				  (queue::delivery_count *) 0,
				  (queue::message_state *) 0, sid,
				  (queue::exception_queue *) 0);

    queue::message_number *mn = new queue::message_number(doc, 1);
    queue::message *msg = new queue::message(doc, mn, mh, mp);

    queue::message_count *mc = new queue::message_count(doc, 1);
    queue::message_set *msgset = new queue::message_set(doc, mc, msg);

    // Create the top-level element
    cout << "Creating top-level element...\n";
    queue::AQXmlDocument *aqdoc =
	new queue::AQXmlDocument(doc, clop, prop, msgset);
    aqdoc->setAttribute((String) "xmlns", (String) NSP_URI);
    doc->appendChild((Node *)aqdoc);

    // Initialize XML schema processor
    XMLSchema *schema = new XMLSchema();
    cout << "Initializing XML schema processor...\n";
    if (ecode = schema->initialize(parser))
    {
	cout << "Failed to initialize schema processor, code " <<
		ecode << "\n";
        return 2;
    }

    // Validate the construct
    cout << "Validating...\n";
    if (ecode = schema->validate((Element *) aqdoc, (oratext *) XSD_DOCUMENT))
    {
	cout << "Validation failed, code " << ecode << "\n";
	return 3;
    }

    // Write out composed document
    cout << "Writing document to " << OUT_DOCUMENT << "\n";
    FILE *out = new FILE;
    if (!(out = fopen(OUT_DOCUMENT, "w")))
    {
	cout << "Failed to open output stream\n";
	return 4;
    }
    aqdoc->print(out);
    fclose(out);

    // Everything's OK
    cout << "Success.\n";

    // Shut down
    schema->terminate();	// Note shuts down parser's ctx also
    return 0;
}

// end of qmain.cpp
