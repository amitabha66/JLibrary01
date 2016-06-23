import java.awt.*;
import java.awt.event.*;
import java.util.*;
import oracle.xml.srcviewer.*;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import oracle.jdeveloper.layout.*;
import org.xml.sax.*;
import oracle.xml.parser.schema.*;
import oracle.xml.schemavalidator.XSDValidator;
import oracle.xml.schemavalidator.DocErrorHandler;


/**
  * This is a demo for XMLSourceView and DTDSourceView. 
  * The Source Viewers and DTD Viewer are initialized (initUIComponents).
  * Each XMLSourceView object is set as a Component of a JPanel
  * (goButton_actionPerformed). 
  * The XML File to be viewed is parsed and the resultant XML Document is set 
  * in the XMLSourceView object (makeSrcPane). The highlighting and DTD 
  * display properties are specified at this time.
  * For performing schema validation, in addition to an XML Document, you need 
  * to build the schema object as well (makeSchemaValPane). You can can check 
  * for errors and display the source code accordingly with different 
  * highlights. A list or schema validation errors can be retreived from the 
  * XMLSourceView (dumpErrors).
  */  

public class XMLSrcViewPanel extends JPanel
{

  XMLSrcViewPanel()
  {
    try
    {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      initUIComponents();
    }catch (Exception e)
    {
    }
  }

  /* Parse the XML data and return the XML Document (DOM tree) */
  private XMLDocument getXMLDocument(URL is)
          throws XMLParseException, IOException,SAXException
  {    
       DOMParser parser = new DOMParser();
       parser.parse(is);
       
       return parser.getDocument();
  }
  
  /**
    * Initialize the graphical user components 
    */
  void initUIComponents()
  {
      xYLayout1.setHeight(105);
      xYLayout1.setWidth(106);
    
      /* create the panels */
      filePanel = new JPanel();
      filePanel.setLayout(xYLayout1);
      xMLSourceView = new XMLSourceView();
      dtdSourceView = new DTDSourceView();
      srcSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
      errTextPane = new JTextPane();
      JScrollPane paneScrollPane = new JScrollPane(errTextPane);
      initStylesForTextPane(errTextPane); 
      errorStatusLabel = new JLabel();
      filePanel.add(errorStatusLabel, new XYConstraints(15, 485, 450, 20));  

      /* create the viewing options */
      viewLabel = new JLabel("View");
      filePanel.add(viewLabel, new XYConstraints(15, 19, 80, 20));
      viewText = new JTextField();
      filePanel.add(viewText, new XYConstraints(80, 19, 150, 20));
      viewbrowseButton = new JButton("Browse");
      filePanel.add(viewbrowseButton, new XYConstraints(230, 19, 230, 20));
      viewbrowseButton.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(ActionEvent e){
                     viewbrowseButton_actionPerformed(e);
        }
      });   
      /* create radio buttons for viewing options */
      xmlRadioButton = new JRadioButton("XML", true);
      dtdRadioButton = new JRadioButton("DTD", false);
      schemaRadioButton = new JRadioButton("Schema", false);
      /* group radio buttons */
      viewButtonGroup = new ButtonGroup();
      viewButtonGroup.add(xmlRadioButton);
      viewButtonGroup.add(dtdRadioButton);
      viewButtonGroup.add(schemaRadioButton);     
      /* add listener for radio buttons */

      filePanel.add(xmlRadioButton, new XYConstraints( 15, 54, 65, 20) );      
      xmlRadioButton.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(ActionEvent e){
                     xmlRadioButton_actionPerformed(e);
        }
      });    
      filePanel.add(dtdRadioButton, new XYConstraints( 105, 54, 120, 20) );
        
      dtdRadioButton.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(ActionEvent e){
                     dtdRadioButton_actionPerformed(e);
        }
      }); 
      filePanel.add(schemaRadioButton, new XYConstraints( 225, 55, 240, 21) );        
      schemaRadioButton.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(ActionEvent e){
                     schemaRadioButton_actionPerformed(e);
        }
      }); 

      /* Create radio buttons for validation options */
      validateLabel = new JLabel("Validate");
      filePanel.add(validateLabel, new XYConstraints( 15, 90, 80, 20) );
      novalidRadioButton = new JRadioButton("No Validation", true);
      dtdvalidRadioButton = new JRadioButton("DTD", false);
      schemavalidRadioButton = new JRadioButton("Schema", false);
      /* group radio buttons */
      validButtonGroup = new ButtonGroup();
      validButtonGroup.add(novalidRadioButton);
      validButtonGroup.add(dtdvalidRadioButton);
      validButtonGroup.add(schemavalidRadioButton);
      /* add listener for radio buttons */

      filePanel.add(novalidRadioButton, new XYConstraints( 15, 120, 140, 20) );
      novalidRadioButton.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(ActionEvent e){
                     novalidRadioButton_actionPerformed(e);
        }
      });  
      filePanel.add(dtdvalidRadioButton, new XYConstraints(15, 150, 85, 20) ); 
      dtdvalidRadioButton.addActionListener(new java.awt.event.ActionListener()      {
        public void actionPerformed(ActionEvent e){
                     dtdvalidRadioButton_actionPerformed(e);
        }
      });  
      filePanel.add(schemavalidRadioButton, 
                    new XYConstraints(15, 180, 85, 20) );      
      schemavalidRadioButton.addActionListener(new java.awt.event.ActionListener(){
        public void actionPerformed(ActionEvent e){
                     schemavalidRadioButton_actionPerformed(e);
        }
      });  
      /* select schema for validation */    
      schemaText = new JTextField();
      filePanel.add(schemaText, new XYConstraints(98, 180, 158, 20));      
      schemabrowseButton = new JButton("Browse");
      filePanel.add(schemabrowseButton, new XYConstraints(260, 180, 200, 20)); 
      disableSchemaValidation(); 
      schemabrowseButton.addActionListener(new java.awt.event.ActionListener()       {
        public void actionPerformed(ActionEvent e) {
          schemabrowseButton_actionPerformed(e);
        }
      });   

      /* Go ahead with viewing the xml document  */        
      goButton = new JButton("GO");
      filePanel.add(goButton, new XYConstraints(15, 210, 140, 20));      
      goButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          goButton_actionPerformed(e);
        }
      });      
      /* Clear all the fields */     
      clearButton = new JButton("Clear");
      filePanel.add(clearButton, new XYConstraints(15, 240, 140, 20));      
      clearButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          clearButton_actionPerformed(e);
        }
      });        
      /* create the tabs */
      mainTabPanel= new JTabbedPane(JTabbedPane.TOP);     
      mainTabPanel.add(fileTabName, filePanel);
      mainTabPanel.add(errorTabName, errTextPane);
      mainTabPanel.add(viewTabName, srcSplitPanel);
      add(mainTabPanel);          
  }

  /* Select to browse the file system to select a file */
  void viewbrowseButton_actionPerformed(ActionEvent e)
  {
    viewFileName = chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
    if (viewFileName!=null) 
       viewText.setText(viewFileName);
  }

  /* Select to view and xml file */
  void xmlRadioButton_actionPerformed(ActionEvent e)
  {
    enableValidation();
  }

  /* Select to view a dtd file */
  void dtdRadioButton_actionPerformed(ActionEvent e)
  {
    disableValidation();
  }

  /* Select to view an XML Schema file */
  void schemaRadioButton_actionPerformed(ActionEvent e)
  {
    disableValidation();
  }
  
  /* Set no validation mode */
  void novalidRadioButton_actionPerformed(ActionEvent e)
  {
    disableSchemaValidation();
  }

  /* Set DTD Validation mode */
  void dtdvalidRadioButton_actionPerformed(ActionEvent e)
  {
    disableSchemaValidation();
  }

  /* Set Schema validation mode */
  void schemavalidRadioButton_actionPerformed(ActionEvent e)
  {
    enableSchemaValidation();
  }
  
  /* Browse file system to select a Schema file */
  void schemabrowseButton_actionPerformed(ActionEvent e)
  {
    schemaFileName = chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
    if (schemaFileName!=null) 
       schemaText.setText(schemaFileName);  
  }

  /* View XML/DTD file */
  void goButton_actionPerformed(ActionEvent e)
  {
    clearError(); 
    srcSplitPanel.removeAll();  
    if ((getViewFile() != null) || (getViewFile() != ""))
    {
      XMLDocument doc;
      if (xmlRadioButton.isSelected()) /* view XML file */
      {
        if (novalidRadioButton.isSelected()) /* NO validation mode */
        {
           doc = makeSrcPane(getViewFile(), XMLConstants.NONVALIDATING); 
           srcSplitPanel.setLeftComponent(xMLSourceView);        
        }
        else if (dtdvalidRadioButton.isSelected()) /* DTD validation mode */
        {
           doc = makeSrcPane(getViewFile(), XMLConstants.AUTO_VALIDATION); 
           /* show dtd if it exists otherwise raise error */
           DTD dtddoc = (DTD)doc.getDoctype();
           if (dtddoc != null)
           {
              makeDTDPane(dtddoc);
              srcSplitPanel.setLeftComponent(xMLSourceView);
              srcSplitPanel.setRightComponent(dtdSourceView);
              srcSplitPanel.setDividerLocation((int)240);
           }
           else
           {
              setErrorStatus("DTD not available for validation.");
              srcSplitPanel.setLeftComponent(xMLSourceView); 
           }
        }
        else /* Schema validation mode */
        {
           doc = makeSchemaValPane(getViewFile(), getSchemaFile());
           if (doc != null)
           { 
             srcSplitPanel.setLeftComponent(xMLSourceView);
             srcSplitPanel.setRightComponent(schemaSourceView);
             srcSplitPanel.setDividerLocation((int)240);       
           }
        }
      } 
      else if (schemaRadioButton.isSelected())  /* view Schema */
      {
         doc = makeSrcPane(getViewFile(), XMLConstants.NONVALIDATING);
         srcSplitPanel.setRightComponent(xMLSourceView);  
      }
      else  /* view DTD */
      {
         makeDTDPane(getViewFile());  
         srcSplitPanel.setRightComponent(dtdSourceView);  
      }   
    }    
    else
    {
      setErrorStatus("File to view is not set");
    }
  }    

  XMLDocument makeSrcPane(String fname, int validation_mode)
  {
      XMLDocument doc = parseXML(fname, validation_mode);
      if (doc == null)
      {
        setErrorStatus("Null document");
        return doc;
      }
      try
      {
        xMLSourceView.displayIntDTD();
        xMLSourceView.setXMLDocument(doc);
        xMLSourceView.setBackground(Color.white);
        xMLSourceView.setPINameForeground(Color.cyan);
        xMLSourceView.setPIDataForeground(Color.red);
        xMLSourceView.setDTDForeground(Color.red);
        xMLSourceView.setDTDInternalForeground(Color.magenta);
      }
      catch (Exception e)
      {
        setError("Error displaying xml:  see Error Log for details", 
                  e.toString());
      }
      return doc;
  }

  /* Perform schema validation and create a pane to display the results */
  XMLDocument makeSchemaValPane(String xmlname, String schemaname)
  {
      XMLDocument doc = parseXML(xmlname, XMLConstants.NONVALIDATING);
      XMLDocument schemadoc = parseXML(schemaname, XMLConstants.NONVALIDATING);
      if (schemadoc == null)
      {
        setErrorStatus("Schema not available for validation.");
        return null;
      }
      try
      {
        /* Create a schema object */ 
        XSDBuilder xsdbuild = new XSDBuilder();
        XMLSchema schemaobj = (XMLSchema)xsdbuild.build(schemadoc, 
                                              createURL(schemaname+"builder"));
        /* set the schema object, this will enable schema validation */
        xMLSourceView.setSchemaObj(schemaobj);
        /* set the xml doc to view */
        xMLSourceView.setXMLDocument(doc);
      
        /* check for shema validation errors */
        if (isSchemaValidErrors(xMLSourceView))
        {
          /* On schema validation error, display all source in black and
           display the errors in red */
           xMLSourceView.setAttributeValueForeground(Color.black);
           xMLSourceView.setTagForeground(Color.black);
           xMLSourceView.setCommentDataForeground(Color.black);
           xMLSourceView.setErrorAncestorForeground(Color.black);
           xMLSourceView.setErrorNodeForeground(Color.red);           
           dumpErrors();
        }
        else
        {
          xMLSourceView.setBackground(Color.white);
          xMLSourceView.setPINameForeground(Color.cyan);
          xMLSourceView.setPIDataForeground(Color.red); 
        }
        schemaSourceView.setXMLDocument(schemadoc); 
      }
      catch (XSDException e)
      {
        setError("XSDException: See error log for details", e.getMessage());
      }
      catch (SAXException e)
      {
        setError("SAXException: See error log for details", e.getMessage());
      }
      catch (IOException e)
      {
        setError("IOException: See error log for details", e.getMessage());
      }      
      catch (Exception e)
      {
        setError("Exception: See error log for details", e.getMessage());
      }      
      
      return schemadoc;
  }

  /* Parse and display the XML data */
  XMLDocument parseXML(String fname, int validation_mode)
  {
      DOMParser parser = new DOMParser();
      try
      {
        PipedWriter pw = new PipedWriter();
        PipedReader pr = new PipedReader(pw);
        PrintWriter prw = new PrintWriter(pw);
        FileInputStream in = new FileInputStream(fname);
        parser.setValidationMode(validation_mode);  
        parser.setErrorStream (prw);
        parser.setPreserveWhitespace(true);
        parser.setBaseURL(createURL(fname));
        parser.parse(in);
        prw.close();
        pw.close();
        if (validation_mode != XMLConstants.NONVALIDATING)
        {
          if (pr.read() != -1)
          {
            setError("Validation error (see Error Log for details)", pr);
          }
          else
            setErrorStatus("Validation successfull!"); 
        }
        else
          setErrorStatus("Parsing successful!");  
        in.close();
        pr.close();
      } catch(XMLParseException e)
      {
        setError("Error parsing xml file" + fname, e.toString());
      }
      catch(Exception e)
      {
         setError("Exception: see Error Log", e.getMessage());
      }
      return ((XMLDocument)parser.getDocument());   
  } 

  /* Display the DTD using input DTD object*/
  void makeDTDPane(DTD dtddoc) 
  { 
      dtdSourceView.setDTD(dtddoc);
      setDTDAttr();
  }

  /* display the DTD using DTD name */
  void makeDTDPane(String fname)
  { 
      try
      {
        dtdSourceView.setDTD(new FileInputStream(fname));
      }
      catch(FileNotFoundException e)
      {
        setError("Error displaying DTD: See Error Log for details", 
                  e.toString());
      }
      setDTDAttr();
      setErrorStatus("DTD parsing successful!");  
  }

  /* Sets viewing attributes for DTD, called by the DTD display functions*/
  void setDTDAttr()
  {  
      dtdSourceView.setBackground(Color.white);
      dtdSourceView.setTagForeground(Color.red); 
      dtdSourceView.setElementNameForeground(Color.blue);
      dtdSourceView.setElementBodyForeground(Color.pink);
      dtdSourceView.setAttributeNameForeground(Color.green);
      dtdSourceView.setAttributeBodyForeground(Color.magenta);
      dtdSourceView.setEntityNameForeground(Color.cyan);
      dtdSourceView.setEntityBodyForeground(Color.orange);
      dtdSourceView.setNotationNameForeground(Color.red);
      dtdSourceView.setNotationBodyForeground(Color.orange);  
  }

  String getViewFile()
  {
    viewFileName = viewText.getText();
    return viewFileName;
  } 

  String getSchemaFile()
  {
    return schemaText.getText();
  } 

  void clearError()
  {
    setErrorStatus("");
    setErrorLog("");
  }     

  void setErrorLog(String errstr)
  {
    errTextPane.setText(errstr + "\n");
  }

  void setError(String statusMesg, String logMesg)
  {
    setErrorStatus(statusMesg);
    setErrorLog(logMesg);
  }

  void setError(String statusMesg, Reader reader)
  {
    setErrorStatus(statusMesg);
    setErrorLog(reader);
  }  

  void setErrorLog(Reader err_reader)
  {
    try
    {
      BufferedReader br = new BufferedReader(err_reader);
      String errstr;
      javax.swing.text.Document doc = errTextPane.getDocument();
      while (((errstr = br.readLine()) != null) && br.ready())
      {
        doc.insertString(doc.getLength(), errstr + "\n\n", 
                         errTextPane.getStyle("bold"));
      }
      br.close();
    }catch(Exception e)
    {
       setErrorLog(e.getMessage());
    }
  }  

  void setErrorStatus(String errstr)
  {
    errorStatusLabel.setText(errstr);
  }
    
  void clearButton_actionPerformed(ActionEvent e)
  {
    viewText.setText("");
    schemaText.setText("");
    viewFileName = null;
    schemaFileName = null;
    clearError();
  }  

  void disableSchemaValidation()
  {
    schemaText.setEnabled(false);
    schemabrowseButton.setEnabled(false);
  }

  void enableSchemaValidation()
  {
    schemaText.setEnabled(true);
    schemabrowseButton.setEnabled(true);
  }

  void disableValidation()
  {
    novalidRadioButton.setEnabled(true);
    dtdvalidRadioButton.setEnabled(false);
    schemavalidRadioButton.setEnabled(false);
    disableSchemaValidation();
  }

  void enableValidation()
  {
    
    dtdvalidRadioButton.setEnabled(true);
    schemavalidRadioButton.setEnabled(true);
    enableSchemaValidation();
  }
  
  /* Select file, called on clicking browse button */
  String chooseFile(int mode)
  {
    String fileName = "";
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
    int retval = chooser.showOpenDialog(this);
    switch (retval)
      {
      case JFileChooser.APPROVE_OPTION:
        fileName = chooser.getSelectedFile().toString();
        break;
      case JFileChooser.CANCEL_OPTION:
        fileName=null;
        break;
      case JFileChooser.ERROR_OPTION:
        fileName=null;
        break;
      }
    return fileName;
  }

  /* Check if any schema validation errors exist */
  boolean isSchemaValidErrors(XMLSourceView sourceview)
  {
    XMLError  err = (sourceview.getXSchemaVal()).getError();  
    int errCount = ((DocErrorHandler)err.getErrorHandler()).getNumErrors();
    if (errCount == 0)
       return false;
    else
       return true;
  }

  /* Display the error in a separate pane */
  private void dumpErrors()
  {

     String treepathstr = "Tree Path: Root ";
     String endline = "\n";
     String arrStr = "--> ";
     boolean error = false;

     XSDValidator schemaVal = xMLSourceView.getXSchemaVal();
     XMLError  err = schemaVal.getError();   
     Enumeration enum =  
            ((DocErrorHandler)err.getErrorHandler()).getErrorList().elements();
     Enumeration enum1 =  schemaVal.getStackList().elements();
     Stack tempStack; 
     XMLNode xnode;
     setErrorLog("");
     javax.swing.text.Document doc = errTextPane.getDocument();
     try{
          while (enum.hasMoreElements() && enum1.hasMoreElements())
          {
            error = true;
            doc.insertString(doc.getLength(), (String)enum.nextElement(),
                                                 errTextPane.getStyle("bold"));
            doc.insertString(doc.getLength(), endline, null);
            doc.insertString(doc.getLength(), treepathstr, null);
            tempStack = (Stack)enum1.nextElement();
	    while (!(tempStack.empty()))
	    {
	      xnode = (XMLNode)((tempStack).pop());
              if (xnode.getNodeType() == Node.ELEMENT_NODE)
	      {
                doc.insertString(doc.getLength(), arrStr,
                                               errTextPane.getStyle("italic"));
	        doc.insertString(doc.getLength(), xnode.getNodeName(),
                                                 errTextPane.getStyle("bold"));
	      }
	    }
            doc.insertString(doc.getLength(), endline, null);
            doc.insertString(doc.getLength(), endline, null);
	 }
      }
      catch (Exception e) 
      {
         setError("Error displaying validation errors: see Error Log", 
                   e.toString());
      }
      if (error)
         setErrorStatus("Validation errors (see Error Log for details)");
      else
         setErrorStatus("Validation successfull!");
  }

  void initStylesForTextPane(JTextPane textPane) 
  {
    /* Initialize some font styles. */
        Style def = StyleContext.getDefaultStyleContext().
                                        getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = textPane.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = textPane.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = textPane.addStyle("bold", regular);
        StyleConstants.setBold(s, true);
    
  }
    
  URL createURL(String fileName)
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
            setErrorLog(e.toString());
            setErrorLog("Cannot create url for: " + fileName);
         }
      }
      return url;
   }

  XMLSourceView xMLSourceView = new XMLSourceView();
  DTDSourceView dtdSourceView = new DTDSourceView();
  XMLSourceView schemaSourceView = new XMLSourceView();
  XYLayout xYLayout1 = new XYLayout();
  JPanel filePanel;
  JSplitPane srcSplitPanel; 
  JPanel srcPanel;  
  JTextPane errTextPane;
  JScrollPane errScrollPane;
  JTabbedPane mainTabPanel;  
  JLabel errorStatusLabel;
  JLabel viewLabel;
  JTextField viewText;
  JButton viewbrowseButton;
  ButtonGroup viewButtonGroup;
  JRadioButton xmlRadioButton;
  JRadioButton dtdRadioButton;
  JRadioButton schemaRadioButton;
  JLabel validateLabel;
  ButtonGroup validButtonGroup;
  JRadioButton novalidRadioButton;
  JRadioButton dtdvalidRadioButton;
  JRadioButton schemavalidRadioButton;
  JTextField schemaText;
  JButton schemabrowseButton;
  JButton goButton;
  JButton clearButton;
  String viewFileName, schemaFileName;
  static String fileTabName = "File";
  static String viewTabName = "Source View";
  static String errorTabName = "Error Log";
}
