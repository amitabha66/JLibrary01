// This sample program illustreates the SAX compression
// feature. XML document could be compressed using the 
// SAX Events throws by a SAX Parser.

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import oracle.xml.parser.v2.SAXParser;
import oracle.xml.parser.v2.XMLParseException;
import oracle.xml.parser.v2.XMLConstants;
import oracle.xml.comp.CXMLHandlerBase;

public class SAXCompression extends DefaultHandler
{
   static public void main(String[] args) throws SAXException, IOException
   {
      URL url = createURL(args[0]);

      try
      {
         String compFile = "xml.ser";
         FileOutputStream outStream = new FileOutputStream(compFile);
         ObjectOutputStream out = new ObjectOutputStream(outStream);
         CXMLHandlerBase cxml = new CXMLHandlerBase(out);
         SAXParser parser = new SAXParser();
         parser.setDocumentHandler(cxml);
         parser.setEntityResolver(cxml);
         parser.setValidationMode(XMLConstants.NONVALIDATING);
         parser.parse(url);
      }
      catch (XMLParseException e)
      {
         System.out.println("XMLParseException: " + e.toString());
         e.printStackTrace();
      }
      catch (SAXException e)
      {
         System.out.println("SAXException: " + e.toString());
         e.printStackTrace();
      }
      catch (IOException e)
      {
         System.out.println("IOException: " + e.toString());
         e.printStackTrace();
      }
      catch (Exception e)
      {
         System.out.println("Exception: " + e.toString());
         e.printStackTrace();
      }
   }

   static public URL createURL(String filename) 
   {
      File file = new File(filename);
      String path = file.getAbsolutePath();
      String fSep = System.getProperty("file.separator");
      if (fSep != null && fSep.length() == 1)
         path = path.replace(fSep.charAt(0), '/');
      if (path.length() > 0 && path.charAt(0) != '/')
         path = '/' + path;
      try 
      {
         return new URL("file", null, path);
      }
      catch (java.net.MalformedURLException e) 
      {
         // According to the spec this could only happen if the file
         // protocol were not recognized.
         throw new Error("unexpected MalformedURLException");
      }
   }
}
