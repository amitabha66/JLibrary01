

import java.awt.*;
import oracle.xml.srcviewer.*;
import oracle.xml.treeviewer.*;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ViewSample 
{

  public static void main(String[] args) 
  {
     String fileName = new String ("booklist.xml");
     if (args.length > 0) {
        fileName = args[0];
     }

     JFrame        frame         = setFrame ("XMLViewer");
     XMLDocument   xmlDocument   = getXMLDocumentFromFile (fileName);
     XMLSourceView xmlSourceView = setXMLSourceView (xmlDocument);
     XMLTreeView   xmlTreeView   = setXMLTreeView (xmlDocument);
     JTabbedPane   jtbPane       = new JTabbedPane ();
     
     jtbPane.addTab ("Source", null, xmlSourceView, "XML document sorce view");
     jtbPane.addTab ("Tree", null, xmlTreeView, "XML document tree view");
     jtbPane.setPreferredSize (new Dimension(400,300));
     frame.getContentPane().add (jtbPane);

     frame.setTitle    (fileName);
     frame.setJMenuBar (setMenuBar());
     frame.setVisible  (true);
  }

  static JFrame setFrame (String title) 
  {
    JFrame frame = new JFrame (title);
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize  = frame.getSize();
    if (frameSize.height > screenSize.height) {
       frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation ((screenSize.width - frameSize.width)/2, 
                      (screenSize.height - frameSize.height)/2);
    frame.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) { 
          System.exit(0); 
       } 
    });
    frame.getContentPane().setLayout (new BorderLayout());
    frame.setSize(new Dimension(400, 300));
    frame.setVisible (false);
    frame.setTitle (title);

    return frame;
  }

  static JMenuBar setMenuBar ()
  {
     JMenuBar menuBar = new JMenuBar();
     JMenu    menu    = new JMenu ("Exit");
     menu.addMenuListener ( new MenuListener () {
       public void menuSelected (MenuEvent ev) { System.exit(0); }
       public void menuDeselected (MenuEvent ev) {}
       public void menuCanceled (MenuEvent ev) {}
     });
     menuBar.add (menu);
     return menuBar;
  }

  /**
   * creates  XMLSourceView object
   */
  static XMLSourceView setXMLSourceView(XMLDocument xmlDocument) 
  {
    XMLSourceView xmlView = new XMLSourceView();
    try
    {
       xmlView.setXMLDocument(xmlDocument);
       xmlView.setBackground(Color.yellow);
       xmlView.setEditable(true);
    }catch(Exception ex)
    {
       ex.printStackTrace();
    }    
    return xmlView;
  }
  /**
   * creates  XMLTreeView object
   */
  static XMLTreeView setXMLTreeView(XMLDocument xmlDocument) 
  {
    XMLTreeView xmlView = new XMLTreeView();

    xmlView.setXMLDocument(xmlDocument);
    xmlView.setBackground(Color.yellow);
    return xmlView;
  }

  static XMLDocument getXMLDocumentFromFile (String fileName) 
  {
    XMLDocument doc = null;

    try  {
      DOMParser parser = new DOMParser();
      try {
         String dir= "" ;
         FileInputStream in = new FileInputStream(fileName);
         parser.setPreserveWhitespace(false);
         parser.setBaseURL(createURL(dir));
         parser.parse(in);
         in.close();
      } catch (Exception ex) {
         ex.printStackTrace();
         System.exit(0);
      }

      doc = (XMLDocument)parser.getDocument();

      try {
         doc.print(System.out);
      } catch (Exception ie) {
         ie.printStackTrace();
         System.exit(0);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return doc;
  }

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

 
