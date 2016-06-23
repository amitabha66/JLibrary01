/* $Header: XSLSample2.java 01-jan-2002.23:50:48 sasriniv Exp $ */
 
/* Copyright (c) 2000, 2002, Oracle Corporation.  All rights reserved.  */

/**
 * DESCRIPTION
 * This file gives a simple example of how to use the XSL processing 
 * capabilities of the Oracle XML Parser V2.0. An input XML document is
 * transformed using a given input stylesheet
 * This Sample streams the result of XSL transfromations directly to 
 * a stream, hence can support xsl:output features.
 */

import java.net.URL;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLDocumentFragment;
import oracle.xml.parser.v2.XSLStylesheet;
import oracle.xml.parser.v2.XSLProcessor;

public class XSLSample2
{
   /**
    * Transforms an xml document using a stylesheet
    * @param args input xml and xml documents
    */
   public static void main (String args[]) throws Exception
   {
      DOMParser parser;

      XMLDocument xml, xsldoc, out;

      URL xslURL;
      URL xmlURL;

      try 
      {

         if (args.length != 2) 
         {
            // Must pass in the names of the XSL and XML files
            System.err.println("Usage: java XSLSample2 xslfile xmlfile");
            System.exit(1);
         }

         // Parse xsl and xml documents
         
         parser = new DOMParser();
         parser.setPreserveWhitespace(true);

         // parser input XSL file
         xslURL = DemoUtil.createURL(args[0]);
         parser.parse(xslURL);
         xsldoc = parser.getDocument();
         
         // parser input XML file
         xmlURL = DemoUtil.createURL(args[1]);
         parser.parse(xmlURL);
         xml = parser.getDocument();

         // instantiate a stylesheet
         XSLProcessor processor = new XSLProcessor();
         processor.setBaseURL(xslURL);
         XSLStylesheet xsl = processor.newXSLStylesheet(xsldoc);


         // display any warnings that may occur
         processor.showWarnings(true);
         processor.setErrorStream(System.err);

         // Process XSL
         processor.processXSL(xsl, xml, System.out);

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
