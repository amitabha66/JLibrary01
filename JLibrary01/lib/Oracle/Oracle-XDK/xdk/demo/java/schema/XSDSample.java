
//import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.*;

import java.net.*;
import java.io.*;
import org.w3c.dom.*;
import java.util.*;

public class XSDSample 
{
   public static void main(String[] args) throws Exception 
   {
      if (args.length != 1)
      {
         System.out.println("Usage: java XSDSample <filename>");
         return;
      }
      process (args[0]);
   }
   
   public static void process (String xmlURI) throws Exception 
   {
      
      DOMParser dp  = new DOMParser();
      URL       url = createURL (xmlURI);
      
      // Set Schema Validation to true
      dp.setValidationMode(XMLParser.SCHEMA_VALIDATION);
      dp.setPreserveWhitespace (true);
      
      dp.setErrorStream (System.out);
      
      try 
      {
         System.out.println("Parsing "+xmlURI);
         dp.parse (url);
         System.out.println("The input file <"+xmlURI+"> parsed without errors");
      } 
      catch (XMLParseException pe) 
      {
         System.out.println("Parser Exception: " + pe.getMessage());
      }
      catch (Exception e) 
      { 
         System.out.println("NonParserException: " + e.getMessage()); 
      }

  }

   // Helper method to create a URL from a file name
   static URL createURL(String fileName)
   {
      URL url = null;
      try
      {
         url = new URL(fileName);
      }
      catch (MalformedURLException ex)
      {
         File f = new File(fileName);
         try
         {
            String path = f.getAbsolutePath();
            // This is a bunch of weird code that is required to
            // make a valid URL on the Windows platform, due
            // to inconsistencies in what getAbsolutePath returns.
            String fs = System.getProperty("file.separator");
            if (fs.length() == 1)
            {
               char sep = fs.charAt(0);
               if (sep != '/')
                  path = path.replace(sep, '/');
               if (path.charAt(0) != '/')
                  path = '/' + path;
            }
            path = "file://" + path;
            url = new URL(path);
         }
         catch (MalformedURLException e)
         {
            System.out.println("Cannot create url for: " + fileName);
            System.exit(0);
         }
      }
      return url;
   }

}
