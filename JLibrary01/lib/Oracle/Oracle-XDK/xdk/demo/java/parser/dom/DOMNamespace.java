/* $Header: DOMNamespace.java 08-mar-2001.10:37:14 kkarun Exp $ */
 
/* Copyright (c) Oracle Corporation 2000, 2001. All Rights Reserved. */

/**
 * DESCRIPTION
 * This file demonstates a simple use of the parser and Namespace
 * extensions to the DOM APIs. 
 * The XML file that is given to the application is parsed and the
 * elements and attributes in the document are printed.
 */

import java.net.URL;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

// Extensions to DOM Interfaces for Namespace support.
import oracle.xml.parser.v2.XMLElement;
import oracle.xml.parser.v2.XMLAttr;

import oracle.xml.parser.v2.DOMParser;

public class DOMNamespace
{
   static public void main(String[] argv)
   {
      try
      {
         if (argv.length != 1) 
         {
            // Must pass in the name of the XML file.
            System.err.println("Usage: DOMNamespace filename");
            System.exit(1);
         }

         // Get an instance of the parser
         Class cls = Class.forName("oracle.xml.parser.v2.DOMParser");
         DOMParser parser = (DOMParser)cls.newInstance();

	 // Generate a URL from the filename.
	 URL url = DemoUtil.createURL(argv[0]);

         //Set Validation mode
         parser.setValidationMode(DOMParser.NONVALIDATING);

	 // Parse the document.
         parser.parse(url);

         // Obtain the document.
         Document doc = parser.getDocument();

         // Print document elements
         printElements(doc);

         // Print document element attributes
         System.out.println("The attributes of each element are: ");
         printElementAttributes(doc);
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }

   static void printElements(Document doc)
   {
      NodeList nl = doc.getElementsByTagName("*");
      XMLElement nsElement;

      String qName;
      String localName;
      String nsName;
      String expName;
      
      System.out.println("The elements are: ");
      for (int i=0; i < nl.getLength(); i++)
      {
         nsElement = (XMLElement)nl.item(i);

         // Use the methods getQualifiedName(), getLocalName(), getNamespace()
         // and getExpandedName() in NSName interface to get Namespace
         // information.
         
         qName = nsElement.getQualifiedName();
         System.out.println("  ELEMENT Qualified Name:" + qName);
         
         localName = nsElement.getLocalName();
         System.out.println("  ELEMENT Local Name    :" + localName);
         
         nsName = nsElement.getNamespace();
         System.out.println("  ELEMENT Namespace     :" + nsName);
         
         expName = nsElement.getExpandedName();
         System.out.println("  ELEMENT Expanded Name :" + expName);
      }
      
      System.out.println();
   }

   static void printElementAttributes(Document doc)
   {
      NodeList nl = doc.getElementsByTagName("*");
      Element e;

      XMLAttr nsAttr;

      String attrname;
      String attrval;
      String attrqname;

      NamedNodeMap nnm;
      int i, len;

      len = nl.getLength();

      for (int j=0; j < len; j++)
      {
         e = (Element) nl.item(j);
         System.out.println(e.getTagName() + ":");

         nnm = e.getAttributes();

         if (nnm != null)
         {
            for (i=0; i < nnm.getLength(); i++)
            {
               nsAttr = (XMLAttr) nnm.item(i);

               // Use the methods getQualifiedName(), getLocalName(), 
               // getNamespace() and getExpandedName() in NSName 
               // interface to get Namespace information.

               attrname = nsAttr.getExpandedName();
               attrqname = nsAttr.getQualifiedName();
               attrval = nsAttr.getNodeValue();

               System.out.println(" " + attrqname + "(" + attrname + ")" + " = " + attrval);
            }
         }
         System.out.println();
      }
   }
}
