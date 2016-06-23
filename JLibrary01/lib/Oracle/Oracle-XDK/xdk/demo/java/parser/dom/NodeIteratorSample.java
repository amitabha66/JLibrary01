/* $Header: NodeIteratorSample.java 31-mar-2003.22:11:51 kkarun Exp $ */
 
/* Copyright (c) 2000, 2003, Oracle Corporation.  All rights reserved.  */

/**
 * DESCRIPTION
 * This file demonstrates the use of NodeIterator 
 * This program needs as input the file traversal.xml
 * This program expects traversal.xml to be in the directory in which its run
 */

import java.net.URL;

import org.w3c.dom.Node;

import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.NodeFilter;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLNode;

public class NodeIteratorSample 
{
   public static void main(String[] argv)
   {

      try
      {
         // Get an instance of the parser
         DOMParser parser = new DOMParser();

         // Generate a URL from the filename.
         URL url = DemoUtil.createURL("../common/traversal.xml");

         // Set various parser options: validation off,
         // warnings shown, error stream set to stderr.
         parser.setErrorStream(System.err);
         parser.setValidationMode(DOMParser.NONVALIDATING);
         parser.showWarnings(true);

         // Parse the document.
         parser.parse(url);

         // Obtain the document.
         XMLDocument doc = parser.getDocument();

         // instantiate the node filter
         NodeFilter n2 = new nf2();

         // create Node Iterator     
         NodeIterator ni = doc.createNodeIterator(doc.getDocumentElement(),
                                                  NodeFilter.SHOW_ALL,n2,true);
         // move forward in the list

         XMLNode nn =(XMLNode) ni.nextNode();
   
         // traverse the list in forward direction 
         while (nn != null)
         {
            System.out.println(nn.getNodeName() + " " + nn.getNodeValue());
            nn = (XMLNode)ni.nextNode();
         }

         System.out.println("*********");

         // move backward
         nn = (XMLNode)ni.previousNode();
    
         // traverse the list in backward direction 
         while (nn != null)
         {
            System.out.println(nn.getNodeName() + " " + nn.getNodeValue());
            nn = (XMLNode)ni.previousNode();
         }
         System.out.println("*********");

         // detach the node iterator from the document
         ni.detach();    
      
         
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }
}

class nf2 implements NodeFilter
{
  public short acceptNode(Node node)
  {
    short type = node.getNodeType();
  
    if ((type == Node.ELEMENT_NODE) || (type == Node.ATTRIBUTE_NODE))
       return FILTER_ACCEPT;
    if ((type == Node.ENTITY_REFERENCE_NODE))
       return FILTER_REJECT;
    return FILTER_SKIP; 
  }
} 

