/* $Header: TreeWalkerSample.java 31-mar-2003.21:41:18 kkarun Exp $ */
 
/* Copyright (c) 2000, 2003, Oracle Corporation.  All rights reserved.  */

/**
 * DESCRIPTION
 * This file demonstrates the use of TreeWalker 
 * This program needs as input the file traversal.xml
 * This program expects traversal.xml to be in the directory in which its run
 */

import java.net.URL;

//import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.TreeWalker;
import org.w3c.dom.traversal.NodeFilter;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLNode;

public class TreeWalkerSample 
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
         NodeFilter n2 = new nf();

         // create a tree walker 
         TreeWalker tw = doc.createTreeWalker(doc.getDocumentElement(),NodeFilter.SHOW_ALL,n2,true);

         // get the root element of the tree walker
         XMLNode nn = (XMLNode)tw.getRoot();

         // traverse in document order using the tree walker
         while (nn != null)
         {
           System.out.println(nn.getNodeName() + " " + nn.getNodeValue());
           nn = (XMLNode)tw.nextNode();
         }
 
         System.out.println("*********");

         // create another tree walker 
         tw = doc.createTreeWalker(doc.getDocumentElement(),NodeFilter.SHOW_ALL,n2,true);
         // get its root 
         nn = (XMLNode) tw.getRoot();

         // traverse the left depth using tree walker
         while (nn != null)
         {
            System.out.println(nn.getNodeName() + " " + nn.getNodeValue());
            nn = (XMLNode)tw.firstChild(); 
         }

         // create yet another tree walker 
         tw = doc.createTreeWalker(doc.getDocumentElement(),NodeFilter.SHOW_ALL,n2,true);
         // get its root
         nn = (XMLNode) tw.getRoot();

         System.out.println("*********");

         // traverse the right depth 
         while (nn != null)
         {
            System.out.println(nn.getNodeName() + " " + nn.getNodeValue());
            nn = (XMLNode)tw.lastChild(); 
         } 

      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }
}

class nf implements NodeFilter
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

