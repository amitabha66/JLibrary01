/* $Header: SAXNamespace.java 08-mar-2001.10:37:14 kkarun Exp $ */
 
/* Copyright (c) Oracle Corporation 2000, 2001. All Rights Reserved. */

/**
 * DESCRIPTION
 * This file demonstrates a simple use of the Namespace extensions to 
 * the SAX 1.0 APIs.
 */

import java.net.URL;

import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

// Extensions to the SAX Interfaces for Namespace support.
import oracle.xml.parser.v2.XMLDocumentHandler;
import oracle.xml.parser.v2.DefaultXMLDocumentHandler;
import oracle.xml.parser.v2.NSName;
import oracle.xml.parser.v2.SAXAttrList;

import oracle.xml.parser.v2.SAXParser;

public class SAXNamespace {

  static public void main(String[] args) {
     
     String fileName;

     //Get the file name
     
     if (args.length == 0)
     {
        System.err.println("No file Specified!!!");
        System.err.println("USAGE: java SAXNamespace <filename>");
        return;
     }
     else
     {
        fileName = args[0];
     }
     

     try {

        // Create handlers for the parser

        // Use the XMLDocumentHandler interface for namespace support 
        // instead of org.xml.sax.DocumentHandler
        XMLDocumentHandler xmlDocHandler = new XMLDocumentHandlerImpl();

        // For all the other interface use the default provided by
        // Handler base
        HandlerBase defHandler = new HandlerBase();

        // Get an instance of the parser
        SAXParser parser = new SAXParser();
           
        // set validation mode
        ((SAXParser)parser).setValidationMode(SAXParser.DTD_VALIDATION);

        // Set Handlers in the parser
        // Set the DocumentHandler to XMLDocumentHandler
        parser.setDocumentHandler(xmlDocHandler);

        // Set the other Handler to the defHandler
        parser.setErrorHandler(defHandler);
        parser.setEntityResolver(defHandler);
        parser.setDTDHandler(defHandler);
           
        try 
        {
           parser.parse(DemoUtil.createURL(fileName).toString());
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

/***********************************************************************
 Implementation of XMLDocumentHandler interface. Only the new
 startElement and endElement interfaces are implemented here. All other
 interfaces are implemented in the class HandlerBase.
**********************************************************************/

class XMLDocumentHandlerImpl extends DefaultXMLDocumentHandler
{

   public void XMLDocumentHandlerImpl()
   {
   }

      
   public void startElement(NSName name, SAXAttrList atts) throws SAXException 
   {

      // Use the methods getQualifiedName(), getLocalName(), getNamespace()
      // and getExpandedName() in NSName interface to get Namespace
      // information.

      String qName;
      String localName;
      String nsName;
      String expName;

      qName = name.getQualifiedName();
      System.out.println("ELEMENT Qualified Name:" + qName);

      localName = name.getLocalName();
      System.out.println("ELEMENT Local Name    :" + localName);

      nsName = name.getNamespace();
      System.out.println("ELEMENT Namespace     :" + nsName);

      expName = name.getExpandedName();
      System.out.println("ELEMENT Expanded Name :" + expName);

      for (int i=0; i<atts.getLength(); i++)
      {

      // Use the methods getQualifiedName(), getLocalName(), getNamespace()
      // and getExpandedName() in SAXAttrList interface to get Namespace
      // information.

         qName = atts.getQualifiedName(i);
         localName = atts.getLocalName(i);
         nsName = atts.getNamespace(i);
         expName = atts.getExpandedName(i);

         System.out.println(" ATTRIBUTE Qualified Name   :" + qName);
         System.out.println(" ATTRIBUTE Local Name       :" + localName);
         System.out.println(" ATTRIBUTE Namespace        :" + nsName);
         System.out.println(" ATTRIBUTE Expanded Name    :" + expName);


         // You can get the type and value of the attributes either
         // by index or by the Qualified Name.

         String type = atts.getType(qName);
         String value = atts.getValue(qName);

         System.out.println(" ATTRIBUTE Type             :" + type);
         System.out.println(" ATTRIBUTE Value            :" + value);

         System.out.println();

      }      
   }

  public void endElement(NSName name) throws SAXException 
   {
      // Use the methods getQualifiedName(), getLocalName(), getNamespace()
      // and getExpandedName() in NSName interface to get Namespace
      // information.

      String expName = name.getExpandedName();
      System.out.println("ELEMENT Expanded Name  :" + expName);
   }
   
}
