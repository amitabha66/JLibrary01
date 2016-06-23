/* $Header: SAXSample.java 08-mar-2001.10:37:14 kkarun Exp $ */
 
/* Copyright (c) Oracle Corporation 2000, 2001. All Rights Reserved. */

/**
 * DESCRIPTION
 * This file demonstates a simple use of the parser and SAX API.
 * The XML file that is given to the application is parsed and 
 * prints out some information about the contents of this file.
 */

import java.net.URL;

import org.xml.sax.Parser;
import org.xml.sax.Locator;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import oracle.xml.parser.v2.SAXParser;

public class SAXSample extends HandlerBase
{
   // Store the locator
   Locator locator;

   static public void main(String[] argv)
   {
      try
      {
         if (argv.length != 1)
         {
            // Must pass in the name of the XML file.
            System.err.println("Usage: SAXSample filename");
            System.exit(1);
         }
         // Create a new handler for the parser
         SAXSample sample = new SAXSample();
	
         // Get an instance of the parser
         Parser parser = new SAXParser();
         
         // set validation mode
         ((SAXParser)parser).setValidationMode(SAXParser.DTD_VALIDATION);
         // Set Handlers in the parser
         parser.setDocumentHandler(sample);
         parser.setEntityResolver(sample);
         parser.setDTDHandler(sample);
         parser.setErrorHandler(sample);
    
         // Convert file to URL and parse
         try
         {
            parser.parse(DemoUtil.createURL(argv[0]).toString());
         }
         catch (SAXParseException e) 
         {
            System.out.println(e.getMessage());
         }
         catch (SAXException e) 
         {
            System.out.println(e.getMessage());
         }  
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }

   //////////////////////////////////////////////////////////////////////
   // Sample implementation of DocumentHandler interface.
   //////////////////////////////////////////////////////////////////////

   public void setDocumentLocator (Locator locator)
   {
      System.out.println("SetDocumentLocator:");
      this.locator = locator;
   }

   public void startDocument() 
   {
      System.out.println("StartDocument");
   }

   public void endDocument() throws SAXException 
   {
      System.out.println("EndDocument");
   }
      
   public void startElement(String name, AttributeList atts) 
                                                  throws SAXException 
   {
      System.out.println("StartElement:"+name);
      for (int i=0;i<atts.getLength();i++)
      {
         String aname = atts.getName(i);
         String type = atts.getType(i);
         String value = atts.getValue(i);

         System.out.println("   "+aname+"("+type+")"+"="+value);
      }
      
   }

   public void endElement(String name) throws SAXException 
   {
      System.out.println("EndElement:"+name);
   }

   public void characters(char[] cbuf, int start, int len) 
   {
      System.out.print("Characters:");
      System.out.println(new String(cbuf,start,len));
   }

   public void ignorableWhitespace(char[] cbuf, int start, int len) 
   {
      System.out.println("IgnorableWhiteSpace");
   }
   
   
   public void processingInstruction(String target, String data) 
              throws SAXException 
   {
      System.out.println("ProcessingInstruction:"+target+" "+data);
   }
   

      
   //////////////////////////////////////////////////////////////////////
   // Sample implementation of the EntityResolver interface.
   //////////////////////////////////////////////////////////////////////


   public InputSource resolveEntity (String publicId, String systemId)
                      throws SAXException
   {
      System.out.println("ResolveEntity:"+publicId+" "+systemId);
      System.out.println("Locator:"+locator.getPublicId()+" "+
                  locator.getSystemId()+
                  " "+locator.getLineNumber()+" "+locator.getColumnNumber());
      return null;
   }

   //////////////////////////////////////////////////////////////////////
   // Sample implementation of the DTDHandler interface.
   //////////////////////////////////////////////////////////////////////

   public void notationDecl (String name, String publicId, String systemId)
   {
      System.out.println("NotationDecl:"+name+" "+publicId+" "+systemId);
   }

   public void unparsedEntityDecl (String name, String publicId,
	        		   String systemId, String notationName)
   {
      System.out.println("UnparsedEntityDecl:"+name + " "+publicId+" "+
                         systemId+" "+notationName);
   }

   //////////////////////////////////////////////////////////////////////
   // Sample implementation of the ErrorHandler interface.
   //////////////////////////////////////////////////////////////////////


   public void warning (SAXParseException e)
              throws SAXException
   {
      System.out.println("Warning:"+e.getMessage());
   }

   public void error (SAXParseException e)
              throws SAXException
   {
      throw new SAXException(e.getMessage());
   }


   public void fatalError (SAXParseException e)
              throws SAXException
   {
      System.out.println("Fatal error");
      throw new SAXException(e.getMessage());
   }
}
