import oracle.xml.parser.v2.*;
import oracle.xml.parser.schema.XMLSchema;
import oracle.xml.parser.schema.XSDBuilder;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import oracle.xml.treeviewer.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import oracle.xml.schemavalidator.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import oracle.jdeveloper.layout.*;
import org.w3c.dom.Node;
import oracle.xml.srcviewer.*;

/**
 *  This Demo displays a parsed XML Instance Document in the form a 
 *  Tree using the oracle.xml.treeviewer. The tree can also be
 *  displayed with the validation errors that occured when the 
 *  validation is done against a Schema object.This class essentially
 *  sets up all the GUI components and implements all the listeners.
 *  XML schema object can be supplied as input for validation.
 *  Internally the class calls the schemaValidator methods to validate
 *  and report errors.
 */


class TreeViewerValidate extends JPanel{

  XMLTreeView xmlDocView = new XMLTreeView();
  XMLTreeView xmlSchemaView = new XMLTreeView();
  XMLSourceView xmlEditorView =new XMLSourceView();

  Vector actionListeners;

  private JTabbedPane tabmain= new JTabbedPane(JTabbedPane.TOP);
  private JPanel filePanel = new JPanel();
  private JPanel errPanel = new JPanel();

  private JTextPane logPane = new JTextPane();

  JLabel errorLabel = new JLabel("Status: ");
  JLabel errorStatus = new JLabel();


  JLabel loadXMLfileLabel = new JLabel("Instance Doc");
  JLabel loadSchemaLabel = new JLabel("Schema Doc");
  JButton browseButton1 = new JButton("Browse");
  JButton browseButton2 = new JButton("Browse");
  JButton viewTreeButton = new JButton("Parse & View Tree");
  JSeparator jSeparator1 = new JSeparator();
  JTextPane xmlTextPane = new JTextPane();

  JTextField loadXMLfileField = new JTextField();
  JTextField loadSchemaField = new JTextField();
  XYLayout xYLayout1 = new XYLayout();
  JPanel treePanel = new JPanel();
  JCheckBox validateOptionCB = new JCheckBox("Do Schema Validation");

  URL xmldocIs,xmlschemaIs;

  String xmlFileName,schemaFileName;
  String xmltext,xmloldtext;
  boolean xmlBuffer_modified = false;

  boolean validateFlag;

  XMLDocument xmldoc;
  XMLDocument xmlschemadoc;
  XMLSchema xmlschemaObj;



  public TreeViewerValidate()
  {
    try
      {
	this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	validateFlag = false;
	initUI();
	xmlDocView.setXMLDocument(new XMLDocument());
	xmlSchemaView.setXMLDocument(new XMLDocument());
	xmlEditorView.setXMLDocument(new XMLDocument());
      }
    catch(Exception e){
      errorStatus.setText(e.toString());
    }
  }


  
  /* Initialize the UI objects */

  private void initUI()
  {    

    xYLayout1.setHeight(105);
    xYLayout1.setWidth(106);

    filePanel.setLayout(xYLayout1);
    filePanel.add(loadXMLfileLabel, new XYConstraints(15, 19, 120, 20));
    filePanel.add(loadSchemaLabel, new XYConstraints(15, 59, 120, 20));
 
    filePanel.add( loadXMLfileField , new XYConstraints( 120, 19, 210,20));
    loadXMLfileField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadXMLfileField_actionPerformed(e);
      }
    });

    
    filePanel.add( loadSchemaField , new XYConstraints( 120, 59, 210,20) );
    loadSchemaField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadSchemaField_actionPerformed(e);
      }
    });


    filePanel.add( browseButton1 , new XYConstraints( 366, 19,90,20) );
    browseButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseButton1_actionPerformed(e);
      }
    });

    filePanel.add( browseButton2 , new XYConstraints( 366, 59,90,20));
  
    browseButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseButton2_actionPerformed(e);
      }
    });

    filePanel.add(validateOptionCB,new XYConstraints(15,100,220,20));
    validateOptionCB.setSelected(false);

    validateOptionCB.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        validateFlag = !(validateFlag);
        xmlDocView.setSchemaValidation(validateFlag);
        if (validateFlag)
	  enableSchemaComponents();
        else
	  disableSchemaComponents();
      }
    });


    filePanel.add(viewTreeButton, new XYConstraints(270,100,186,20));

    viewTreeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewTreeButton_actionPerformed(e);
      }
    });
    filePanel.add(jSeparator1, new XYConstraints(15, 135, 441, 190));
    errPanel.setLayout(new BoxLayout(errPanel,BoxLayout.X_AXIS));
    errorLabel.setFont(new Font("Dialog",Font.BOLD,14));
    errorStatus.setFont(new Font("Dialog",Font.ITALIC,13));
    errorStatus.setForeground(Color.red);

    errPanel.add(errorLabel);
    errPanel.add(errorStatus);
    errPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    filePanel.add(errPanel,new XYConstraints(15,505,470,30));

    JScrollPane paneScrollPane = new JScrollPane(logPane);
    paneScrollPane.setVerticalScrollBarPolicy(
					      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    paneScrollPane.setPreferredSize(new Dimension(250, 155));
    paneScrollPane.setMinimumSize(new Dimension(10, 10));
    //treePanel.setLayout(new BoxLayout(treePanel,BoxLayout.Y_AXIS));
    tabmain.add("File",filePanel);
    tabmain.add("View XMLDoc", xmlDocView);
    tabmain.add("View XMLSchema", xmlSchemaView);
    tabmain.add("Edit Source",xmlEditorView);
    tabmain.add("View Error Log", logPane);

    xmlEditorView.setEditable(true);
    xmlTextPane=xmlEditorView.getJTextPane();

    disableSchemaComponents();
    // tabmain.addChangeListener(new javax.swing.event.ChangeListener() {   
    //public void stateChanged(ChangeEvent e) {
    //  tabChanged(e);
    // }


    add(tabmain);
  }



  /* Parses the Input string and returns the XMLDocument */
   
  private XMLDocument getXMLDocument(String is)
    throws XMLParseException, IOException,SAXException
  {    
    DOMParser parser = new DOMParser();
    parser.parse(new StringBufferInputStream(is));
       
    return parser.getDocument();
  }

  
  
  
  /* Parses the Input URL  and returns the XMLDocument */

  private XMLDocument getXMLDocument(URL is)
    throws XMLParseException, IOException,SAXException
  {    
    DOMParser parser = new DOMParser();
    parser.parse(is);
       
    return parser.getDocument();
  }


  /* Method to check if the source is edited */

  boolean xmlTextPaneChanged() {

    xmltext=xmlEditorView.getEditedText();
    xmloldtext=xmlEditorView.getText();
    if (xmltext.compareTo(xmloldtext)!=0) {
      xmlBuffer_modified = true;
      return true;
    } else {
      return false;
    }
  }
  
  
  
  void viewTreeButton_actionPerformed(ActionEvent e)
  {

    try {

      if (xmlTextPaneChanged())
	{
	  xmldoc = getXMLDocument(xmltext);
	  xmlDocView.setXMLDocument(xmldoc);
	  if (validateFlag)
	    dumpErrors();
	  else
	    tabmain.setEnabledAt(4,false);
	  tabmain.setSelectedIndex(1);
	}
      else
        parseandset();
    }
    catch(XMLParseException e1)
      {
	errorStatus.setText(e1.toString());
      }

    catch(SAXException e1)
      {
	errorStatus.setText(e1.toString());
      }

    catch(Exception e1)
      {
	System.out.println(e1.toString());
	// errorStatus.setText(e1.toString()+ ": Check Input");
      }
  }




  /* Core method which does the validation of the document.*/

  void parseandset()
  {

    try {

      // Set the Debug property to obtain linenumber info
      if (validateFlag == true)
	{
	  Properties p = new Properties(System.getProperties());
	  p.load(new FileInputStream("demo.properties"));
	  System.setProperties(p);
	}
     
   
      if (xmldocIs != null)
	{
	  xmldoc = getXMLDocument(xmldocIs);
	}

    /* Only if the validateflag is set then Schema validation is done
       Otherwise only a regular parsing is done */


      if (validateFlag == true)
	{
	  if (xmlschemaIs !=null)    
            xmlschemadoc = getXMLDocument(xmlschemaIs);

	  XSDBuilder xsdbuild = new XSDBuilder();
	  xmlschemaObj = (XMLSchema)xsdbuild.build(xmlschemadoc, 
						   createURL(schemaFileName+"builder"));

	  /* set the schema & XML Document */

	  xmlDocView.setXMLSchemaDocument(xmlschemaObj);
	  xmlSchemaView.setXMLDocument(xmlschemadoc);
        
	}
      xmlDocView.setXMLDocument(xmldoc);
      xmlEditorView.setXMLDocument(xmldoc);


      if (validateFlag)
	{
	  dumpErrors();
	  errorStatus.setText("Parsed the Document.Check for Validation Errors");
	}
      else
	errorStatus.setText("Parsed the Instance Document successfully");

      // Write the Error log to the textArea

      


      tabmain.setSelectedIndex(1);
    }

    catch(XMLParseException e1)
      {
	// errorStatus.setText(e1.toString());
      }

    catch(SAXException e1)
      {
	errorStatus.setText(e1.toString());
      }

    catch(Exception e1)
      {
	errorStatus.setText(e1.toString()+ ": Check Input file");
      }
  }


/* Method to dump errors with line/column information and also
   the tree path from root to the node where the error occured */

  private void dumpErrors()
  {

    String treepathstr = "Tree Path: Root ";
    String endline = "\n";
    String arrStr = "--> ";

    XSDValidator schemaVal = xmlDocView.getXSchemaVal();
    XMLError  err = schemaVal.getError();   
    Enumeration enum =  ((DocErrorHandler)err.getErrorHandler()).getErrorList().elements();
    Enumeration enum1 =  schemaVal.getStackList().elements();
    Stack tempStack ; 
    XMLNode xnode ;
    logPane.setText("");
    initStylesForTextPane(logPane); 
    Document doc = logPane.getDocument();
    try{
      while (enum.hasMoreElements() && enum1.hasMoreElements())
	{
	  // System.out.println(enum.nextElement());
	  doc.insertString(doc.getLength(),(String)enum.nextElement(),logPane.getStyle("bold"));
	  doc.insertString(doc.getLength(),endline,null);
	  doc.insertString(doc.getLength(),treepathstr,null);
	  tempStack = (Stack)enum1.nextElement();
	  while (!(tempStack.empty()))
	    {
	      xnode = (XMLNode)((tempStack).pop());
	      if (xnode.getNodeType() == Node.ELEMENT_NODE)
		{
		  doc.insertString(doc.getLength(),arrStr,logPane.getStyle("italic"));
		  doc.insertString(doc.getLength(),xnode.getNodeName(),logPane.getStyle("bold"));
		}
	    }
	  doc.insertString(doc.getLength(),endline,null);
	  doc.insertString(doc.getLength(),endline,null);
	}
    }
    catch (Exception e) {
      errorStatus.setText(e.toString());
    }
  }


/* Method for setting the JTextPane for editing */

  protected void initStylesForTextPane(JTextPane textPane) {
    //Initialize some styles.
    Style def = StyleContext.getDefaultStyleContext().
      getStyle(StyleContext.DEFAULT_STYLE);

    Style regular = textPane.addStyle("regular", def);
    StyleConstants.setFontFamily(def, "SansSerif");

    Style s = textPane.addStyle("italic", regular);
    StyleConstants.setItalic(s, true);

    s = textPane.addStyle("bold", regular);
    StyleConstants.setBold(s, true);
    
  }



  private void clearFields()
  {
      
    loadXMLfileField.setText("");
    loadSchemaField.setText("");

    errorStatus.setText("Buffers Empty");
    viewTreeButton.setEnabled(false);
  }





  private void enableSchemaComponents()
  {
    // enable the schema tabs & buttons
  
    browseButton2.setEnabled(true);
    loadSchemaField.setEnabled(true);
    tabmain.setEnabledAt(2,true);
  }



  private void disableSchemaComponents()
  {
    // enable the schema tabs & buttons
  
    browseButton2.setEnabled(false);
    loadSchemaField.setEnabled(false);
    tabmain.setEnabledAt(2,false);
  }




  /* Method which sets the InputStreams for the XMLCompress object */

  void loadXMLfileField_actionPerformed(ActionEvent e) {
    xmlFileName=loadXMLfileField.getText();
    try {
      xmldocIs = createURL(xmlFileName);
      if (!(validateFlag))
	parseandset();
    }
    catch (Exception e1) {
      errorStatus.setText(e1.toString());
    }
  }


  void loadSchemaField_actionPerformed(ActionEvent e) {
    schemaFileName=loadSchemaField.getText();
    try {
      xmlschemaIs = createURL(schemaFileName);
      if (xmldocIs != null)
	parseandset(); 
    }
    catch (Exception e1) {
      errorStatus.setText(e1.toString());
    }
  }



   
  void browseButton1_actionPerformed(ActionEvent e)
  {
    xmlFileName = chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
    if (xmlFileName!=null) {
      try {
	loadXMLfileField.setText(xmlFileName);
	xmldocIs = createURL(xmlFileName);
	if (!(validateFlag))
	  parseandset();
      }
      catch(Exception e1) {
        errorStatus.setText(e1.toString());
      }
    }
  }


  void browseButton2_actionPerformed(ActionEvent e)
  {
    schemaFileName = chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
    if (schemaFileName!=null) {
      try {
	loadSchemaField.setText(schemaFileName);
	xmlschemaIs = createURL(schemaFileName);
	if (xmldocIs != null)
	  parseandset();
      }
      catch(Exception e1) {
	errorStatus.setText(e1.toString());
      }
    }     
  }




  synchronized void addActionListener(ActionListener l)
  {
    Vector v = actionListeners == null ? new Vector(2) : (Vector) actionListeners.clone();
    if (!v.contains(l))
      {
        v.addElement(l);
        actionListeners = v;
      }
  }



  protected void fireActionPerformed(ActionEvent e)
  {
    if (actionListeners != null)
      {
        Vector listeners = actionListeners;
        int count = listeners.size();
        for (int i = 0; i < count; i++)
          ((ActionListener) listeners.elementAt(i)).actionPerformed(e);
      }
  }



  /* Utility methods used in this class */

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

    
