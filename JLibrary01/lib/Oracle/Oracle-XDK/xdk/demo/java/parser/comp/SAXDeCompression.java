// This sample illustrates the decompression of a binary compressed
// file to generate the SAX events. 

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import oracle.xml.comp.CXMLParser;

public class SAXDeCompression extends DefaultHandler
{

   static public void main(String[] args) throws SAXException, IOException
   {
      try 
      {
        SampleSAXHandler xmlHandler = new SampleSAXHandler();
        CXMLParser parser = new CXMLParser();
        parser.setContentHandler(xmlHandler);
        parser.parse(args[0]);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (SAXException e)
      {
          e.printStackTrace();
      }
      catch (Exception e)
      {
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

   /**
    * Receive notification of the start of a Namespace mapping.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass to take specific actions at the start of
    * each Namespace prefix scope (such as storing the prefix mapping).</p>
    *
    * @param prefix The Namespace prefix being declared.
    * @param uri The Namespace URI mapped to the prefix.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.ContentHandler#startPrefixMapping
    */
   public void startPrefixMapping (String prefix, String uri)
   throws SAXException
   {
      System.out.println("Start Prefix :"+prefix+"="+uri);
   }
   
   
   /**
    * Receive notification of the end of a Namespace mapping.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass to take specific actions at the end of
    * each prefix mapping.</p>
    *
    * @param prefix The Namespace prefix being declared.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.ContentHandler#endPrefixMapping
    */
   public void endPrefixMapping (String prefix)
   throws SAXException
   {
      System.out.println("End Prefix :"+prefix);
   }
   
   
   /**
    * Receive notification of the start of an element.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass to take specific actions at the start of
    * each element (such as allocating a new tree node or writing
    * output to a file).</p>
    *
    * @param name The element type name.
    * @param attributes The specified or defaulted attributes.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.ContentHandler#startElement
    */
   public void startElement (String uri, String localName,
                             String qName, Attributes atts)
   throws SAXException
   {
      System.out.println("StartElement - "+uri+":"+localName+"["+qName+"]");

      for (int i=0; i < atts.getLength(); i++)
      {
         String aname = atts.getQName(i);
         String nsName = atts.getURI(i);
         String lName = atts.getLocalName(i);

         // Get type by name and index
         String type = atts.getType(i);
         String nameType1 = atts.getType(aname);
         String nameType2 = atts.getType(nsName, lName);

         //if (!type.equals(nameType1) || !type.equals(nameType2))
            //System.out.println("Error retrieving type"); 

         // get value by name and index
         String value = atts.getValue(i);
         String nameValue1 = atts.getValue(aname);
         String nameValue2 = atts.getValue(nsName, lName);

         if (!value.equals(nameValue1) || !value.equals(nameValue2))
            System.out.println("Error retrieving value"); 

         System.out.println("   QNAME="+aname);
         System.out.println("   Namespace ="+nsName);
         System.out.println("   LocalName ="+localName);
         System.out.println("   Type ="+type);
         System.out.println("   Value ="+value);
      }
   }
   
   
   /**
    * Receive notification of the end of an element.
    *
    * <p>By default, do nothing.  Application writers may override this
    * method in a subclass to take specific actions at the end of
    * each element (such as finalising a tree node or writing
    * output to a file).</p>
    *
    * @param name The element type name.
    * @param attributes The specified or defaulted attributes.
    * @exception org.xml.sax.SAXException Any SAX exception, possibly
    *            wrapping another exception.
    * @see org.xml.sax.ContentHandler#endElement
    */
   public void endElement (String uri, String localName, String qName)
   throws SAXException
   {
      System.out.println("EndElement - "+uri+":"+localName+"["+qName+"]");
   }

   public void characters(char[] cbuf, int start, int len) 
                           throws SAXException
   {
      //System.out.println(new String(cbuf,start,len));
   }
}

