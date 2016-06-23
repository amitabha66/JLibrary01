// This file demonstrates the DOM Compression feature.
// This sample program illustrated the reading of the binary comrpressed
// file called "xml.ser" using java serailaziation API's 
// and generates an in-memory DOM tree.

import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;

import oracle.xml.parser.v2.XMLDocument;

public class DOMDeCompression
{

   static OutputStream out = System.out;

   public static void main(String[] args)
   {
      InputStream is;
      ObjectInputStream ois;
      XMLDocument serializedDoc = null;
      try
      {
         is = new FileInputStream("xml.ser");
         ois = new ObjectInputStream(is);
         serializedDoc = new XMLDocument();
         serializedDoc.readExternal(ois);
         serializedDoc.print(System.out);
         ois.close();
      }
      catch (EOFException eof)
      {
         eof.printStackTrace();
         System.exit(0);
      }
      catch (Exception ex)
      {
        System.out.println("Exception in ois.readObject(): " +
                ex.toString() );
        ex.printStackTrace();
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
