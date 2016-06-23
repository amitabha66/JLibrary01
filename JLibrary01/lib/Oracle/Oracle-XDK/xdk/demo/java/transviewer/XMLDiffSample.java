
import oracle.xml.parser.v2.*;
import oracle.xml.async.*;
import oracle.xml.differ.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.net.URL;
import java.net.MalformedURLException;

public class XMLDiffSample
{
  /**
   * Constructor
   */
  public XMLDiffSample() {
  }

  /**
   * main
   * @param args
   */
  public static void main(String[] args)
  {

    dfxApp = new XMLDiffSample();
    diffFrame = new XMLDiffFrame(dfxApp);
    diffFrame.addTransformMenu();
    xmlDiff = new XMLDiff();

    if (args.length == 3)
      outFile = args[2];
    /* Use the default outFile name = XMLDiffSample.xsl */
    if(args.length >= 2)
      dfxApp.showDiffs(new File(args[0]), new File(args[1]));

    diffFrame.setVisible(true);
 }


  public void showDiffs(File file1, File file2)
  {
   try
   {
      xmlDiff.setFiles(file1, file2);

      /* Check if files are equal */
      if(!xmlDiff.diff())
      {
        JOptionPane.showMessageDialog(diffFrame,
          "Files are equivalent in XML representation",
          "XMLDiffSample Message",
          JOptionPane.PLAIN_MESSAGE);
      }

     /* generate xsl file */
    xmlDiff.generateXSLFile(outFile);
    /* parse the xsl file created, alternately you can use
       generateXSLDoc to get the xsl as a document tree instead of a file */
    parseXSL();
    /* Display the document trees created by the xmlDiff object */
    diffFrame.makeSrcPane(xmlDiff.getDocument1(), xmlDiff.getDocument2());
    diffFrame.makeDiffSrcPane(new XMLDiffSrcView(xmlDiff.getDiffPane1()),
                              new XMLDiffSrcView(xmlDiff.getDiffPane2()));
    diffFrame.makeXslPane(xslDoc, "Diff XSL Script");
    diffFrame.makeXslTabbedPane();
    }catch (FileNotFoundException e)
    {
      JOptionPane.showMessageDialog(diffFrame,
          "File Not Found: "+e.getMessage(),
          "XMLDiffSample Error Message",
          JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(diffFrame,
          "Error: "+e.getMessage(),
          "XMLDiffSample Error Message",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void doXSLTransform()
  {
    try
    {
      doc1 = xmlDiff.getDocument1();
      doc2 = xmlDiff.getDocument2();

      XSLProcessor xslProc = new XSLProcessor();

      /* Using the xsl stylesheet generated (xslDoc), transform the first file
        (doc1) into the second file (resultDocFrag) */
       XMLDocumentFragment resultDocFrag = xslProc.processXSL(new XSLStylesheet
                                      (xslDoc, createURL(outFile)), doc1);
      XMLDocument resultDoc = new XMLDocument();
      /* The XML declaration has to be copied over to the transformed XML doc,
         the xsl will not generate it automatically */
      if (doc1.getFirstChild() instanceof XMLDeclPI)
      if (doc1.getFirstChild() instanceof XMLDeclPI)
      {
         XMLNode xmldecl = (XMLNode) resultDoc.importNode(doc1.getFirstChild(), 
                                                          false);
         resultDoc.appendChild(xmldecl);
      }
     /* Create the DTD node in the transformed XML document  */
      if(doc1.getDoctype() != null)
      {
        DTD dtd = (DTD)doc1.getDoctype();  
        resultDoc.setDoctype(dtd.getName(), dtd.getSystemId(), dtd.getPublicId());
      }
      /* Create the result document tree from the document fragment */
      resultDoc.appendChild(resultDocFrag);
      diffFrame.makeResultFilePane(resultDoc);
    } catch (XSLException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(diffFrame,
          "Error: "+e.getMessage(),
          "XMLDiffSample Error Message",
          JOptionPane.ERROR_MESSAGE);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(diffFrame,
       "Error:"+e.getMessage(),
       "XMLDiffSample Error Message",
       JOptionPane.ERROR_MESSAGE);
    }
  }

  /* Parse the XSL file generated into a DOM tree */
  protected void parseXSL()
  {
    try
    {
      BufferedReader xslFile = new BufferedReader(new FileReader(outFile));
      DOMParser domParser = new DOMParser();
      domParser.parse(xslFile);
      xslDoc = domParser.getDocument();

    }catch (FileNotFoundException e)
    {
      JOptionPane.showMessageDialog(diffFrame,
          "File Not Found: "+e.getMessage(),
          "XMLDiffSample Message",
          JOptionPane.PLAIN_MESSAGE);
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(diffFrame,
       "Error:"+e.getMessage(),
       "XMLDiffSample Error Message",
       JOptionPane.ERROR_MESSAGE);
    }
  }

  // create a URL from a file name
  protected URL createURL(String fileName)
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
            // to handle Windows platform
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
          JOptionPane.showMessageDialog(diffFrame,
          "Cannot create url for: " + fileName,
          "XMLDiffSample Error Message",
          JOptionPane.ERROR_MESSAGE);

         }
      }
      return url;
  }

  protected XMLDocument doc1;   /* DOM tree for first file */
  protected XMLDocument doc2;   /* DOME tree for second file */
  protected static XMLDiffFrame diffFrame; /* GUI frame */
  protected static XMLDiffSample dfxApp;   /* XMLDiff sample application */
  protected static XMLDiff xmlDiff;        /* XML diff object */
  protected static XMLDocument xslDoc;     /* parsed xsl file */
  protected static String outFile = new String("XMLDiffSample.xsl"); /* output
                                                              xsl file name */
}

