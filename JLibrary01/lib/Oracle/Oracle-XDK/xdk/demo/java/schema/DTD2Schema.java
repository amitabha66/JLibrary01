import oracle.xml.parser.schema.*;
import oracle.xml.parser.v2.*;

import java.net.*;
import java.io.*;
import org.w3c.dom.*;
import java.util.*;

public class DTD2Schema
{
   public static void main(String[] args)
      throws Exception
   {
      if (args.length != 2)
      {
         System.out.println("Usage: java DTD2Schema <dtd_file> <xml_file>");
         return;
      }

      XSDBuilder builder = new XSDBuilder();
      
      URL dtdURL = createURL(args[0]);
		
      // get the DTD object
      DTD dtd = getDTD(dtdURL, "abc");
      
      // convert DTD to Schema DOM tree
      XMLDocument dtddoc = dtd.convertDTD2Schema();
      
      // output the converted result to local file dtd2schema.xsd. 
      FileOutputStream fos = new FileOutputStream("dtd2schema.xsd.out");
      dtddoc.print(fos);
      
      // build Schema object from Schema DOM tree
      XMLSchema schemadoc = (XMLSchema)builder.build(dtddoc, null);
      
      // validate an XML file based on Schema object
      validate(args[1], schemadoc);
   }

   private static DTD getDTD(URL dtdURL, String rootName)
      throws Exception
   {
      DOMParser parser = new DOMParser();
      DTD dtd;

      parser.setValidationMode(true);
      parser.setErrorStream(System.out);
      parser.showWarnings(true);

      parser.parseDTD(dtdURL, rootName);
      dtd = (DTD)parser.getDoctype();
      return dtd;
   }

   private static void validate(String xmlURI, XMLSchema schemadoc)
      throws Exception
   {
      DOMParser dp  = new DOMParser();
      URL       url = createURL (xmlURI);

      dp.setXMLSchema(schemadoc);
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
         System.out.println ("NonParserException: " + e.getMessage());
      }
   }
   
   private static URL createURL(String fileName)
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
