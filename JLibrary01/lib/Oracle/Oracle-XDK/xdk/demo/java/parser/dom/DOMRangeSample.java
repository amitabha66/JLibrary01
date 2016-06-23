/* $Header: DOMRangeSample.java 15-jun-2001.11:01:14 anmanian Exp $ */
 
/* Copyright (c) 2000, 2001, Oracle Corporation.  All rights reserved.  */

/**
 * DESCRIPTION
 * This file demonstrates use of XML Range
 * This program takes as input the file class.xml 
 * class.xml is expected to be in the directory in which this program is run
 */
 
import java.net.URL;

import org.w3c.dom.ranges.Range;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLNode;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLDocumentFragment;

public class DOMRangeSample 
{
   public static void main(String[] argv)
   {
      try
      {
         // Get an instance of the parser
         DOMParser parser = new DOMParser();

         // Generate a URL from the filename.
         URL url = DemoUtil.createURL("../common/class.xml");

         // Set various parser options: validation on,
         // warnings shown, error stream set to stderr.
         parser.setErrorStream(System.err);
         parser.setValidationMode(DOMParser.DTD_VALIDATION);
         parser.showWarnings(true);

         // Parse the document.
         parser.parse(url);

         // Obtain the document.
         XMLDocument doc = parser.getDocument();

         // create a range
         Range r = (Range) doc.createRange();
         XMLNode c = (XMLNode) doc.getDocumentElement();

         // set the boundaries
         r.setStart(c,0);
         r.setEnd(c,1); 

         // print the contents of the range
         System.out.println(r.toString());
         System.out.println("**********");

         // select the contents of a node as the range
         r.selectNodeContents(c);

         // print the contents of the range
         System.out.println(r.toString());
         System.out.println("**********");

         // clone the contents of the range
         XMLDocumentFragment df =(XMLDocumentFragment) r.cloneContents();
         df.print(System.out);
         System.out.println("**********");

         // obtain the start and end containers of the range 
         c = (XMLNode) r.getStartContainer();
         System.out.println(c.getText());
         c = (XMLNode) r.getEndContainer();
         System.out.println(c.getText()); 
         System.out.println("**********");
 
         // select a node as the boundaries of the range 
         r.selectNode(c);
         System.out.println(r.toString());
         System.out.println("**********");      

         // check if the range is collapsed
         if (r.getCollapsed())
            System.out.println("Range is collapsed");
         else
            System.out.println("Range is not collapsed");          

        // create another range
        Range s = (Range)doc.createRange();
        c = (XMLNode)doc.getDocumentElement();
        s.setStart(c,0);
        s.setEnd(c,2);
        System.out.println(s.toString());
        System.out.println("**********"); 

        // redefine boundaries of the first range 
        r.setStart(c,0);
        r.setEnd(c,1);

        //compare both the ranges
        System.out.println(r.compareBoundaryPoints(Range.START_TO_START,s));
        System.out.println(r.compareBoundaryPoints(Range.START_TO_END,s));
        System.out.println(r.compareBoundaryPoints(Range.END_TO_START,s));
        System.out.println(r.compareBoundaryPoints(Range.END_TO_END,s));               System.out.println("**********"); 

        // delete the contents of the range

        r.deleteContents() ;

        // check if the deletion is reflected in the document

        doc.print(System.out);
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }
}
