

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DOMException;

import oracle.xml.async.DOMBuilder;
import oracle.xml.async.DOMBuilderEvent;
import oracle.xml.async.DOMBuilderListener;
import oracle.xml.async.DOMBuilderErrorEvent;
import oracle.xml.async.DOMBuilderErrorListener;
import oracle.xml.async.XSLTransformer;
import oracle.xml.async.XSLTransformerEvent;
import oracle.xml.async.XSLTransformerListener;
import oracle.xml.async.XSLTransformerErrorEvent;
import oracle.xml.async.XSLTransformerErrorListener;
import oracle.xml.async.ResourceManager;
import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XSLStylesheet;
import oracle.xml.parser.v2.*;

public class AsyncTransformSample
{
  /**
   *  uses DOMBuilder bean
   */
   void runDOMBuilders ()
   {   
      rm = new ResourceManager (numXMLDocs);

      for (int i = 0; i < numXMLDocs; i++)
      {
         rm.getResource();

         try
         {            
            DOMBuilder builder = new DOMBuilder(i);

            URL  xmlURL = createURL(basedir + "/" + 
                                  (String)xmlfiles.elementAt(i));
            if (xmlURL == null)
               exitWithError("File " + (String)xmlfiles.elementAt(i) + 
                             " not found");

            builder.setPreserveWhitespace(true);
            builder.setBaseURL (createURL(basedir + "/"));
            builder.addDOMBuilderListener (new DOMBuilderListener() {
               public void domBuilderStarted(DOMBuilderEvent p0) {}
               public void domBuilderError(DOMBuilderEvent p0) {}
               public synchronized void domBuilderOver(DOMBuilderEvent p0)
               {
                  DOMBuilder bld = (DOMBuilder)p0.getSource();
                  runXSLTransformer (bld.getDocument(), bld.getId());
               }
            });
            builder.addDOMBuilderErrorListener (new DOMBuilderErrorListener() {
               public void domBuilderErrorCalled(DOMBuilderErrorEvent p0)
               {
                  int id = ((DOMBuilder)p0.getSource()).getId();
                  exitWithError("Error occurred while parsing " + 
                     xmlfiles.elementAt(id) + ": " +
                     p0.getException().getMessage());
               }
            });
            builder.parse (xmlURL);

            System.err.println("Parsing file " + xmlfiles.elementAt(i));
         }
         catch (Exception e)
         {
            exitWithError("Error occurred while parsing " + 
                          (String)xmlfiles.elementAt(i) + ": " + 
                          e.getMessage());
         }
      }
   }

  /**
   *  uses XSLTransformer bean
   */
   void runXSLTransformer (XMLDocument xml, int id) 
   {
      try
      {
         XSLTransformer processor = new XSLTransformer (id);
         XSLStylesheet  xsl       = new XSLStylesheet (xsldoc, xslURL);

         processor.showWarnings (true);
         processor.setErrorStream (errors);
         processor.addXSLTransformerListener (new XSLTransformerListener() {
            public void xslTransformerStarted (XSLTransformerEvent p0) {}
            public void xslTransformerError(XSLTransformerEvent p0) {}
            public void xslTransformerOver (XSLTransformerEvent p0)
            {
               XSLTransformer trans = (XSLTransformer)p0.getSource();
               saveResult (trans.getResult(),  trans.getId());
            } 
         });
         processor.addXSLTransformerErrorListener (new XSLTransformerErrorListener() {
            public void xslTransformerErrorCalled(XSLTransformerErrorEvent p0)
            {
               int i = ((XSLTransformer)p0.getSource()).getId();
               exitWithError("Error occurred while processing " +                      
                     xmlfiles.elementAt(i) + ": " +
                     p0.getException().getMessage());
             }
         });
         processor.processXSL (xsl, xml);         // transform xml document
      }
      catch (Exception e)
      {
         exitWithError("Error occurred while processing " + xslFile + ": " + 
                       e.getMessage());         
      }
    }

    void saveResult (DocumentFragment result, int id)
    {
      System.err.println("Transforming '" + xmlfiles.elementAt(id) + 
               "' to '" + xmlfiles.elementAt(id) + ".log'" +
               " applying '" + xslFile);
      
      try
      {
          File resultFile = new File((String)xmlfiles.elementAt(id) + ".log");
              
          ((XMLNode)result).print(new FileOutputStream(resultFile));
      }
      catch (Exception e)
      {
         exitWithError("Error occurred while generating output : " + 
                       e.getMessage());         
      }

      rm.releaseResource();
   }

   void makeXSLDocument ()
   {
      System.err.println ("Parsing file " + xslFile);
      try
      {
         DOMParser parser = new DOMParser();
         parser.setPreserveWhitespace (true);
         xslURL = createURL (xslFile);
         parser.parse (xslURL);
         xsldoc = parser.getDocument();
      }
      catch (Exception e)
      {
         exitWithError("Error occurred while parsing " + xslFile + ": " + 
                       e.getMessage());
      }
   }

   private URL createURL(String fileName) throws Exception
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
            exitWithError("Cannot create url for: " + fileName);
         }
      }

      return url;
   }
 
   boolean init () throws Exception
   {
      File     directory = new File (basedir);
      String[] dirfiles = directory.list();
      for (int j = 0; j < dirfiles.length; j++)
      {
         String dirfile = dirfiles[j];

         if (!dirfile.endsWith(".xml"))
             continue;

          xmlfiles.addElement(dirfile);
      }

      if (xmlfiles.isEmpty()) {
         System.out.println("No files in directory were selected for processing");
         return false;
      }
      numXMLDocs = xmlfiles.size();

      return true;
   }

   private void exitWithError(String msg)
   {
      PrintWriter errs = new PrintWriter(errors);
      errs.println(msg);
      errs.flush();
      System.exit(1);
   }

   void asyncTransform () throws Exception 
   {
      System.err.println (numXMLDocs + 
               " XML documents will be transformed" + 
               " using XSLT stylesheet specified in " + xslFile + 
               " with " +  numXMLDocs + " threads");
     
      makeXSLDocument ();
      runDOMBuilders ();

      // wait for the last request to complete
      while (rm.activeFound()) 
         Thread.sleep(100); 
      
   } 
   String       basedir = new String (".");
   OutputStream errors = System.err;

   Vector xmlfiles = new Vector();
   int    numXMLDocs = 1;
  
   String      xslFile = new String ("doc.xsl");
   URL         xslURL;
   XMLDocument xsldoc;

   private ResourceManager rm;

   /**
    *   main
    */
   public static void main (String args[])
   {
      AsyncTransformSample inst = new AsyncTransformSample();

      try
      {
         if (!inst.init())
            System.exit(0);

         inst.asyncTransform ();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      
      System.exit(0);
   }
}
