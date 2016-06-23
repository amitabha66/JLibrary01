// This sample illustrates compression of a DOM tree using 
// java serialization API - writeExternal()
// This program takes an input XML file, parses it and 
// builds a DOM tree and serializes the DOM tree to 
// generate a compressed output file "xml.ser".

import java.net.URL;
import java.net.MalformedURLException;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLParser;
import oracle.xml.parser.v2.XMLParseException;

public class DOMCompression
{
   static OutputStream out = System.out;
   public static void main(String[] args)
   {
      XMLDocument doc = new XMLDocument();
      DOMParser parser = new DOMParser();
      try
      {
        parser.setValidationMode(XMLParser.SCHEMA_VALIDATION);
        parser.setPreserveWhitespace(false);
        parser.retainCDATASection(true);
        parser.parse(createURL(args[0]));
        doc = parser.getDocument();

        OutputStream os = new FileOutputStream("xml.ser");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        doc.writeExternal(oos);
        oos.close();

      } catch (XMLParseException pe) {
        System.out.println("XMLParseException: " + pe.toString());
        pe.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static URL createURL(String fileName)
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
