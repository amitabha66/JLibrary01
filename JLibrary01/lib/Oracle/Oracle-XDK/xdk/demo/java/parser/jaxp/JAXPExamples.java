import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.MalformedURLException;

import org.xml.sax.*;
import org.xml.sax.ext.*;
import org.xml.sax.helpers.*;
import org.w3c.dom.*;

public class JAXPExamples
{
	public static void main(String argv[])
   	throws TransformerException, TransformerConfigurationException,
      IOException, SAXException,
      ParserConfigurationException, FileNotFoundException
  	{
    	try {
         URL xmlURL = createURL("jaxpone.xml");
         String xmlID = xmlURL.toString();
         URL xslURL = createURL("jaxpone.xsl");
         String xslID = xslURL.toString();
         //
         System.out.println("--- basic ---");
         basic(xmlID, xslID);
         System.out.println();
         System.out.println("--- identity ---");
         identity(xmlID);
         //
         URL generalURL = createURL("general.xml");
         String generalID = generalURL.toString();
         URL ageURL = createURL("age.xsl");
         String ageID = ageURL.toString();
         System.out.println();
         System.out.println("--- namespaceURI ---");
         namespaceURI(generalID, ageID);
         //
         System.out.println();
         System.out.println("--- templatesHandler ---");
         templatesHandler(xmlID, xslID);
         System.out.println();
         System.out.println("--- contentHandler2contentHandler ---");
         contentHandler2contentHandler(xmlID, xslID);
         System.out.println();
         System.out.println("--- contentHandler2DOM ---");
         contentHandler2DOM(xmlID, xslID);
         System.out.println();
         System.out.println("--- reader ---");
         reader(xmlID, xslID);
         System.out.println();
         System.out.println("--- xmlFilter ---");
         xmlFilter(xmlID, xslID);
         //
         URL xslURLtwo = createURL("jaxptwo.xsl");
         String xslIDtwo = xslURLtwo.toString();
         URL xslURLthree = createURL("jaxpthree.xsl");
         String xslIDthree = xslURLthree.toString();
         System.out.println();
         System.out.println("--- xmlFilterChain ---");
         xmlFilterChain(xmlID, xslID, xslIDtwo, xslIDthree);
      } catch(Exception err) {
      	err.printStackTrace();
      }
   }
   //
   public static void basic(String xmlID, String xslID)
      throws TransformerException, TransformerConfigurationException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      Transformer transformer = tfactory.newTransformer(new StreamSource(xslID));
      StreamSource source = new StreamSource(xmlID);
      transformer.transform(source, new StreamResult(System.out));
   }
   //
   public static void identity(String xmlID)
   	throws TransformerException, TransformerConfigurationException
  	{
   	TransformerFactory tfactory = TransformerFactory.newInstance();
      Transformer transformer = tfactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.METHOD, "html");
      transformer.setOutputProperty(OutputKeys.INDENT, "no");
      StreamSource source = new StreamSource(xmlID);
	   transformer.transform(source, new StreamResult(System.out));
  	}
   //
   public static void namespaceURI(String xmlID, String xslID)
      throws TransformerException, TransformerConfigurationException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      Transformer transformer
         = tfactory.newTransformer(new StreamSource(xslID));
      System.out.println("default: 99");
      transformer.transform( new StreamSource(xmlID),
         new StreamResult(System.out));
      transformer.setParameter("{http://www.oracle.com/ages}age", "20");
      System.out.println();
      System.out.println("should say: 20");
      transformer.transform( new StreamSource(xmlID),
         new StreamResult(System.out));
      transformer.setParameter("{http://www.oracle.com/ages}age", "40");
      transformer.setOutputProperty(OutputKeys.METHOD, "html");
      System.out.println();
      System.out.println("should say: 40");
      transformer.transform( new StreamSource(xmlID),
         new StreamResult(System.out));
   }
   //
   public static void templatesHandler(String xmlID, String xslID)
      throws TransformerException, TransformerConfigurationException,
      IOException, SAXException,
      ParserConfigurationException, FileNotFoundException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      if (tfactory.getFeature(SAXTransformerFactory.FEATURE))
      {
         SAXTransformerFactory stfactory = (SAXTransformerFactory) tfactory;
         TemplatesHandler handler = stfactory.newTemplatesHandler();
         handler.setSystemId(xslID);
         // JDK 1.1.8
         Properties driver = System.getProperties();
         driver.put("org.xml.sax.driver", "oracle.xml.parser.v2.SAXParser");
         System.setProperties(driver);
         /** JDK 1.2.2
         System.setProperty("org.xml.sax.driver", "oracle.xml.parser.v2.SAXParser");
         */
         XMLReader reader = XMLReaderFactory.createXMLReader();
         reader.setContentHandler(handler);
         reader.parse(xslID);
         Templates templates = handler.getTemplates();
         Transformer transformer = templates.newTransformer();
         transformer.transform(new StreamSource(xmlID), new StreamResult(System.out));
      }
   }
   //
   public static void reader(String xmlID, String xslID)
      throws TransformerException, TransformerConfigurationException,
      SAXException, IOException, ParserConfigurationException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      SAXTransformerFactory stfactory = (SAXTransformerFactory)tfactory;
      StreamSource streamSource = new StreamSource(xslID);
      XMLReader reader = stfactory.newXMLFilter(streamSource);
      ContentHandler contentHandler = new oraContentHandler();
      reader.setContentHandler(contentHandler);
      InputSource is = new InputSource(xmlID);
      reader.parse(is);
   }
   //
   public static void xmlFilter(String xmlID, String xslID)
      throws TransformerException, TransformerConfigurationException,
      SAXException, IOException, ParserConfigurationException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      XMLReader reader = null;
      try {
	      javax.xml.parsers.SAXParserFactory factory=
	         javax.xml.parsers.SAXParserFactory.newInstance();
	      factory.setNamespaceAware(true);
         javax.xml.parsers.SAXParser jaxpParser=
	         factory.newSAXParser();
	      reader = jaxpParser.getXMLReader();
      } catch(javax.xml.parsers.ParserConfigurationException ex) {
      	throw new org.xml.sax.SAXException(ex);
      } catch(javax.xml.parsers.FactoryConfigurationError ex1) {
      	throw new org.xml.sax.SAXException(ex1.toString());
      } catch(NoSuchMethodError ex2) {
      }
      if (reader == null)
         reader = XMLReaderFactory.createXMLReader();
      XMLFilter filter
         = ((SAXTransformerFactory) tfactory).newXMLFilter(new StreamSource(xslID));
      filter.setParent(reader);
      filter.setContentHandler(new oraContentHandler());
      filter.parse(new InputSource(xmlID));
   }
   //
   public static void xmlFilterChain(
      String xmlID, String xslID_1,
      String xslID_2, String xslID_3)
      throws TransformerException, TransformerConfigurationException,
      SAXException, IOException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      if (tfactory.getFeature(SAXSource.FEATURE))
      {
         SAXTransformerFactory stf = (SAXTransformerFactory)tfactory;
         XMLReader reader = null;
         try {
	         javax.xml.parsers.SAXParserFactory factory =
      	      javax.xml.parsers.SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
	         javax.xml.parsers.SAXParser jaxpParser =
	            factory.newSAXParser();
	         reader = jaxpParser.getXMLReader();
         } catch(javax.xml.parsers.ParserConfigurationException ex) {
	         throw new org.xml.sax.SAXException( ex );
         } catch(javax.xml.parsers.FactoryConfigurationError ex1) {
	         throw new org.xml.sax.SAXException( ex1.toString() );
         } catch(NoSuchMethodError ex2) {
         }
         if (reader == null ) reader = XMLReaderFactory.createXMLReader();
         XMLFilter filter1 = stf.newXMLFilter(new StreamSource(xslID_1));
         XMLFilter filter2 = stf.newXMLFilter(new StreamSource(xslID_2));
         XMLFilter filter3 = stf.newXMLFilter(new StreamSource(xslID_3));
         if (filter1 != null && filter2 != null && filter3 != null)
         {
            filter1.setParent(reader);
            filter2.setParent(filter1);
            filter3.setParent(filter2);
            filter3.setContentHandler(new oraContentHandler());
            filter3.parse(new InputSource(xmlID));
         }
      }
   }
   //
   public static void contentHandler2contentHandler(String xmlID, String xslID)
      throws TransformerException,
      TransformerConfigurationException,
      SAXException, IOException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();

      if (tfactory.getFeature(SAXSource.FEATURE))
      {
         SAXTransformerFactory stfactory = ((SAXTransformerFactory) tfactory);
         TransformerHandler handler
            = stfactory.newTransformerHandler(new StreamSource(xslID));
         Result result = new SAXResult(new oraContentHandler());
         handler.setResult(result);
         XMLReader reader = null;
         try {
	         javax.xml.parsers.SAXParserFactory factory=
	            javax.xml.parsers.SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
	         javax.xml.parsers.SAXParser jaxpParser=
	            factory.newSAXParser();
            reader=jaxpParser.getXMLReader();
         } catch( javax.xml.parsers.ParserConfigurationException ex ) {
	         throw new org.xml.sax.SAXException( ex );
         } catch( javax.xml.parsers.FactoryConfigurationError ex1 ) {
	         throw new org.xml.sax.SAXException( ex1.toString() );
         } catch( NoSuchMethodError ex2 ) {
         }
         if( reader == null ) reader = XMLReaderFactory.createXMLReader();
         reader.setContentHandler(handler);
         reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
         InputSource inputSource = new InputSource(xmlID);
         reader.parse(inputSource);
      }
   }
   //
   public static void contentHandler2DOM(String xmlID, String xslID)
      throws TransformerException, TransformerConfigurationException,
      SAXException, IOException, ParserConfigurationException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();

      if (tfactory.getFeature(SAXSource.FEATURE)
         && tfactory.getFeature(DOMSource.FEATURE))
      {
         SAXTransformerFactory sfactory = (SAXTransformerFactory) tfactory;

         DocumentBuilderFactory dfactory
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
         org.w3c.dom.Document outNode = docBuilder.newDocument();

         TransformerHandler handler
            = sfactory.newTransformerHandler(new StreamSource(xslID));
         handler.setResult(new DOMResult(outNode));

         XMLReader reader = null;

         try {
	         javax.xml.parsers.SAXParserFactory factory =
	            javax.xml.parsers.SAXParserFactory.newInstance();
	         factory.setNamespaceAware(true);
	         javax.xml.parsers.SAXParser jaxpParser=
   	         factory.newSAXParser();
	         reader = jaxpParser.getXMLReader();
         } catch(javax.xml.parsers.ParserConfigurationException ex) {
	         throw new org.xml.sax.SAXException(ex);
         } catch(javax.xml.parsers.FactoryConfigurationError ex1) {
	         throw new org.xml.sax.SAXException(ex1.toString());
         } catch(NoSuchMethodError ex2) {
         }
         if(reader == null ) reader = XMLReaderFactory.createXMLReader();
         reader.setContentHandler(handler);
         reader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
         reader.parse(xmlID);
         printDOMNode(outNode);
      }
   }
   //
   private static void printDOMNode(Node node)
      throws TransformerException, TransformerConfigurationException, SAXException, IOException,
      ParserConfigurationException
   {
      TransformerFactory tfactory = TransformerFactory.newInstance();
      Transformer serializer = tfactory.newTransformer();
      serializer.setOutputProperty(OutputKeys.METHOD, "xml");
      serializer.setOutputProperty(OutputKeys.INDENT, "no");
      serializer.transform(new DOMSource(node),
         new StreamResult(System.out));
   }
   //
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
