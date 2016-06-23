/* $Header: SAX2Namespace.java 08-mar-2001.10:37:14 kkarun Exp $ */
 
/* Copyright (c) Oracle Corporation 2000, 2001. All Rights Reserved. */

/**
 * DESCRIPTION
 * This file demonstrates a simple use of the Namespace extensions to 
 * the SAX 2.0 APIs.
 */

import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import oracle.xml.parser.v2.SAXParser;

public class SAX2Namespace 
{

   static public void main(String[] args) 
   {
     
      String fileName;

      //Get the file name
      if (args.length == 0)
      {
         System.err.println("USAGE: java SAX2Namespace <filename>");
         return;
      }
      else
      {
         fileName = args[0];
      }
      
      try 
      {
         // Create handlers for the parser
         // For all the other interface use the default provided by
         // Handler base
         DefaultHandler defHandler = new XMLDefaultHandler();
         
         // Get an instance of the parser
         SAXParser parser = new SAXParser();
         
         // set validation mode
         ((SAXParser)parser).setValidationMode(SAXParser.DTD_VALIDATION);
         
         parser.setContentHandler(defHandler);
         parser.setErrorHandler(defHandler);
         parser.setEntityResolver(defHandler);
         parser.setDTDHandler(defHandler);
         
         try 
         {
            parser.parse(DemoUtil.createURL(args[0]).toString());
         }
         catch (SAXParseException e) 
         {
            System.err.println(args[0] + ": " + e.getMessage());
         }
         catch (SAXException e) 
         {
            System.err.println(args[0] + ": " + e.getMessage());
         }  
      }
      catch (Exception e) 
      {
         System.err.println(e.toString());
      }
   }
}


class XMLDefaultHandler extends DefaultHandler
{

   public void XMLDefaultHandler()
   {
   }

      
   public void startElement(String uri, String localName,
                            String qName, Attributes atts) 
   throws SAXException 
   {

      System.out.println("ELEMENT Qualified Name:" + qName);
      System.out.println("ELEMENT Local Name    :" + localName);
      System.out.println("ELEMENT Namespace     :" + uri);

      for (int i=0; i<atts.getLength(); i++)
      {

         qName = atts.getQName(i);
         localName = atts.getLocalName(i);
         uri = atts.getURI(i);

         System.out.println(" ATTRIBUTE Qualified Name   :" + qName);
         System.out.println(" ATTRIBUTE Local Name       :" + localName);
         System.out.println(" ATTRIBUTE Namespace        :" + uri);

         // You can get the type and value of the attributes either
         // by index or by the Qualified Name.

         String type = atts.getType(qName);
         String value = atts.getValue(qName);

         System.out.println(" ATTRIBUTE Type             :" + type);
         System.out.println(" ATTRIBUTE Value            :" + value);

         System.out.println();

      }      
   }

   public void endElement(String uri, String localName,
                          String qName) throws SAXException 
   {
      System.out.println("ELEMENT Qualified Name:" + qName);
      System.out.println("ELEMENT Local Name    :" + localName);
      System.out.println("ELEMENT Namespace     :" + uri);
   }
   
}
