/* $Header: DOM2Namespace.java 08-mar-2001.10:37:14 kkarun Exp $ */
 
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
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import oracle.xml.parser.v2.DOMParser;

public class DOM2Namespace
{
   static public void main(String[] argv)
   {
      try
      {
         if (argv.length != 1) 
         {
            // Must pass in the name of the XML file.
            System.err.println("Usage: DOM2Namespace filename");
            System.exit(1);
         }

         DOMParser parser = new DOMParser();

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
      Element nsElement;

      String prefix;
      String localName;
      String nsName;
      
      System.out.println("The elements are: ");
      for (int i=0; i < nl.getLength(); i++)
      {
         nsElement = (Element)nl.item(i);
         
         prefix = nsElement.getPrefix();
         System.out.println("  ELEMENT Prefix Name :" + prefix);

         localName = nsElement.getLocalName();
         System.out.println("  ELEMENT Local Name    :" + localName);
         
         nsName = nsElement.getNamespaceURI();
         System.out.println("  ELEMENT Namespace     :" + nsName);
      }
      
      System.out.println();
   }

   static void printElementAttributes(Document doc)
   {
      NodeList nl = doc.getElementsByTagName("*");
      Element e;

      Attr nsAttr;

      String attrpfx;
      String attrname;
      String attrval;

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
               nsAttr = (Attr) nnm.item(i);

               attrpfx = nsAttr.getPrefix();
               attrname = nsAttr.getLocalName();
               attrval = nsAttr.getNodeValue();

               System.out.println(" " + attrpfx + ":" + attrname + " = " + attrval);
            }
         }
         System.out.println();
      }
   }
}
