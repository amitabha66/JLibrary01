/* $Header: EventSample.java 01-apr-2003.13:38:43 kkarun Exp $ */
 
/* Copyright (c) 2000, 2003, Oracle Corporation.  All rights reserved.  */

/**
 * DESCRIPTION
 * This file illustrates the basic use of DOMMutationEvents
 * This illustrates how to create an event listener,register it and listen 
 * to events
 */

import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import oracle.xml.parser.v2.XMLNode;
import oracle.xml.parser.v2.XMLText;
import oracle.xml.parser.v2.XMLAttr;
import oracle.xml.parser.v2.XMLElement;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLDOMImplementation;

public class EventSample 
{
   public static void main (String args[])
   { 
      // instantiate the event listener
      // see end of this file for implementation of the listener
      eventlistener evtlist = new eventlistener();
      
      // create a new document
      XMLDocument doc1 = new XMLDocument();
      DOMImplementation impl = doc1.getImplementation();
      
      System.out.println("The impl supports Events "+
                         impl.hasFeature("Events", "2.0"));
      System.out.println("The impl supports Mutation Events "+
                         impl.hasFeature("MutationEvents", "2.0"));
      System.out.println();
      
      // register the event listener on the document node for 
      // below listed type of events
      doc1.addEventListener("DOMNodeRemoved", evtlist, false);
      doc1.addEventListener("DOMNodeInserted", evtlist, false);
      doc1.addEventListener("DOMCharacterDataModified", evtlist, false);

      //see below to find how to register a listener on different type of nodes
      // and listen to different kind of events
      XMLElement el = (XMLElement)doc1.createElement("element");
      XMLElement el1= (XMLElement)doc1.createElement("element1");
      XMLText text = (XMLText) doc1.createTextNode("abcde");
      text.addEventListener("DOMCharacterDataModified", evtlist, false);
      el.addEventListener("DOMNodeRemoved", evtlist, false);
      el.addEventListener("DOMNodeRemovedFromDocument", evtlist, false);
      el.addEventListener("DOMCharacterDataModified", evtlist, false); 
      
      el1.addEventListener("DOMNodeRemoved", evtlist, false);
      el1.addEventListener("DOMNodeRemovedFromDocument", evtlist, false);
      el1.addEventListener("DOMCharacterDataModified", evtlist, false); 
      
      el.addEventListener("DOMNodeInserted", evtlist, false);
      el.addEventListener("DOMNodeInsertedIntoDocument", evtlist, false);
      
      el1.addEventListener("DOMNodeInserted", evtlist, false);
      el1.addEventListener("DOMNodeInsertedIntoDocument", evtlist, false);
      
      doc1.appendChild(el);
      el.setAttribute("attr", "val");
      
      doc1.removeEventListener("DOMNodeInserted", evtlist, false);
      
      NamedNodeMap nl = (NamedNodeMap)el.getAttributes();
      XMLAttr att = (XMLAttr) nl.item(0);
      
      att.addEventListener("DOMAttrModified", evtlist, false);
      el.addEventListener("DOMAttrModified", evtlist, false);
      doc1.addEventListener("DOMAttrModified", evtlist, false);
      
      // mutations that triggers the events !
      att.setNodeValue("abc");
      el.appendChild(el1);
      el.appendChild(text);
      text.setNodeValue("xyz");
      doc1.removeChild(el);    
   }
}


class eventlistener implements EventListener 
{
   public eventlistener()
   {
   }
   
   public void handleEvent(Event e)
   {
      String s = " Event "+e.getType()+" received " + "\n";
      s += " Event is cancelable :"+e.getCancelable()+"\n";
      s += " Event is bubbling event :"+e.getBubbles()+"\n";
      s += " The Target is " + ((Node)(e.getTarget())).getNodeName() + "\n\n";
      System.out.println(s);
   }
}




