import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import oracle.xml.treeviewer.*;
import oracle.xml.parser.v2.*;
import org.w3c.dom.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JSplitPane;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.srcviewer.*;
import oracle.xml.parser.v2.*;
import javax.swing.event.*;
import oracle.jdeveloper.layout.*;
import oracle.xml.xmlcomp.*;

public class xmlcompressutil extends JPanel  {

  private JTabbedPane tabmain= new JTabbedPane(JTabbedPane.TOP);

  JMenuBar loadSavMenu = new JMenuBar();
  JMenu optionMenu = new JMenu("Options");
  JMenu loadMenu = new JMenu("Load");
  JMenu saveMenu = new JMenu("Save");


  // JMenu saveMenu = new JMenu("Save Options");

  JRadioButtonMenuItem fileDBButton,fileDBButton1,fileDBButton2;
  ButtonGroup RBgroupload = new ButtonGroup();
  ButtonGroup RBgroupsave = new ButtonGroup();
  JSeparator jsep1 = new JSeparator();
  JPanel mesgPanel = new JPanel();
  JLabel mesgLB = new JLabel();
  JLabel statLB = new JLabel("Status:");
  JLabel logLB1 = new JLabel();
  JLabel logLB2 = new JLabel();
  JLabel logLB3 = new JLabel();

  JPanel loadSavPanel = new JPanel();
  JButton compuncompBT = new JButton("Compress/Uncompress");
  JButton clearBufBT = new JButton("Clear Buffers");
  boolean compSelected = true;
  boolean decompSelected = true;
  boolean dbload = false;
  boolean dbsave = false;
  boolean fileload = true;
  boolean filesave = true;




  static compstreamdata compStr = new compstreamdata();
  XMLDocument xmldoc = new XMLDocument();



  private static JPanel mainPanel1 = new JPanel();
  private static JPanel mainPanel2 = new JPanel();
  private static filepanel filePanel1 = new filepanel(compStr);
  private static dbpanel dbPanel1 = new dbpanel(compStr);
  private static filepanel filePanel2 = new filepanel(compStr);
  private static dbpanel dbPanel2 = new dbpanel(compStr);
  private String compressTabName = "Compress";
  private String unCompressTabName = "Uncompress";
  private String viewName = "View Tree";
  private String loadName = "Load";
  private String saveName = "Name";
  private String frmFile = "From FileSystem";
  private String frmDB = "From Database";
  private String toFile = "To FileSystem";
  private String toDB = "To Database";

  XMLTreeView xmlTreeView = new XMLTreeView();
  XMLCompress xmlCompObj = new XMLCompress();

  // private JButton exitButton = new JButton();




  // Add initUI method to set up the UI


  private void initUI() 
  {  
    XYLayout xylayout = new XYLayout();
    fileDBButton2 = new JRadioButtonMenuItem(frmFile);
    fileDBButton2.setSelected(true);

    fileDBButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadFile_ActionPerformed(e);
      }
    });
    
 
    RBgroupload.add(fileDBButton2);
    loadMenu.add(fileDBButton2);


    fileDBButton1 = new JRadioButtonMenuItem(frmDB);
    RBgroupload.add(fileDBButton1);
    loadMenu.add(fileDBButton1);
     
    fileDBButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadDB_ActionPerformed(e);
      }
    });


    optionMenu.add(loadMenu);

    fileDBButton = new JRadioButtonMenuItem(toFile);
    fileDBButton.setSelected(true);
    RBgroupsave.add(fileDBButton);
    saveMenu.add(fileDBButton);
    fileDBButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveFile_ActionPerformed(e);
      }
    });


    fileDBButton = new JRadioButtonMenuItem(toDB);
    RBgroupsave.add(fileDBButton);
    fileDBButton.setEnabled(false);
    // saveMenu.add(fileDBButton);
    fileDBButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveDB_ActionPerformed(e);
      }
    });

    optionMenu.add(saveMenu);

    compuncompBT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        compuncompBT_ActionPerformed(e);
      }
    });  



    clearBufBT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          compStr.outputStream = null;
          if (compStr.inputStream != null)
	    compStr.inputStream.close();
          xmldoc = new XMLDocument();
          xmlTreeView.setXMLDocument(xmldoc);
          tabmain.setEnabledAt(2,false); 
          filePanel1.clearFields();
          filePanel2.clearFields();
          dbPanel1.clearFields();
          dbPanel2.clearFields();     
          compStr.statstr = new String("Buffers Empty");   
          mesgLB.setText(compStr.statstr);
          logLB1.setText("");
          logLB2.setText("");
          logLB3.setText("");
        }
        catch (Exception e1) {
         
        }
      }
    });

  
    // file name is selected
    filePanel1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (compStr.outputStream == null)
          mesgLB.setText(compStr.statstr + ", Enter Ouptut Filename");
        else
          mesgLB.setText(compStr.statstr);
       
      }
    });

    // file name is selected
    filePanel2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (compStr.inputStream == null)
          mesgLB.setText(compStr.statstr + ", Enter Input Filename");
        else
          mesgLB.setText(compStr.statstr);
       
      }    
    });

    // DB select submited
    dbPanel1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (compStr.outputStream == null)
          mesgLB.setText(compStr.statstr + ", Enter Ouptut Filename");
        else
          mesgLB.setText(compStr.statstr);
      } 
    });

    // DB select submited
    dbPanel2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
             
      }
    });


    tabmain.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        tabChanged(e);
      }
    });
     
    optionMenu.setBackground(Color.lightGray);
    loadSavMenu.add(optionMenu);
          
 
    
    loadSavPanel.add(loadSavMenu);
    loadSavPanel.add(Box.createRigidArea(new Dimension(5,0)));
    compuncompBT.setBackground(Color.lightGray);
    loadSavPanel.add(compuncompBT);
    clearBufBT.setBackground(Color.lightGray);
    loadSavPanel.add(Box.createRigidArea(new Dimension(5,0)));
    loadSavPanel.add(clearBufBT);
    
     
    mainPanel1.setLayout(new BoxLayout(mainPanel1,BoxLayout.Y_AXIS));
    mainPanel1.add(dbPanel1);
     
    mainPanel1.add(filePanel1);
    mainPanel2.setLayout(new BoxLayout(mainPanel2,BoxLayout.Y_AXIS));
    mainPanel2.add(dbPanel2);
    mainPanel2.add(filePanel2);
    tabmain.addTab(compressTabName,mainPanel1);
    tabmain.addTab(unCompressTabName, mainPanel2); 

    tabmain.addTab(viewName,xmlTreeView);
    tabmain.setEnabledAt(2,false);

    //Enable the proper components
    dbPanel1.disableComponents();
    dbPanel2.disableComponents();
    filePanel1.enableComponents();
    filePanel2.enableComponents();


    mesgLB.setForeground(Color.red);
    // mesgLB.setFont(new Font("Dialog",Font.NORMAL,13));
    mesgLB.setText("Buffer Empty");
    mesgPanel.setLayout(xylayout);
    xylayout.setWidth(498);
    xylayout.setHeight(60);
    mesgPanel.add(jsep1,new XYConstraints(10,5,487,1));
    mesgPanel.add(mesgLB,new XYConstraints(80,9,350,17));
    mesgPanel.add(statLB,new XYConstraints(10,9,65,13));
    mesgPanel.add(logLB1,new XYConstraints(80,27,350,10));
    mesgPanel.add(logLB2,new XYConstraints(80,40,350,10));
    mesgPanel.add(logLB3,new XYConstraints(80,53,350,10));

    add(tabmain);
    add(loadSavPanel);
    // add(jsep1,new XYConstraints(4,367,487,1));
    add(mesgPanel);

    show();
  }




  void loadFile_ActionPerformed(ActionEvent e) {

    // Disable the DB Panel
    fileload = true;
    dbload = false;
    dbPanel1.disableComponents();
    dbPanel2.disableComponents();
    filePanel1.enableComponents();
    filePanel2.enableComponents();
  }

  void loadDB_ActionPerformed(ActionEvent e) {
    // Disable FilePanel
    fileload = false;
    dbload = true;
    filePanel1.disableComponents();
    filePanel2.disableComponents();
    dbPanel1.enableComponents();
    dbPanel2.enableComponents();    
  }

  void saveFile_ActionPerformed(ActionEvent e) {

    // Disable the DB Panel
    filesave = true;
    dbsave = false;
    dbPanel1.disableComponents();
    dbPanel2.disableComponents();
    filePanel1.enableComponents();
    filePanel2.enableComponents();
  }
    
  void saveDB_ActionPerformed(ActionEvent e) {

    // Disable FilePanel
    filesave = false;
    dbsave = true;
    filePanel1.disableComponents();
    filePanel2.disableComponents();
    dbPanel1.enableComponents();
    dbPanel2.enableComponents();
  }

  void compuncompBT_ActionPerformed(ActionEvent e) {

    if (compSelected == true)
      {
        if (fileload == true)
          {
            fileSelectedComp(e);
            return;
          }
        if (dbload == true)
          { 
            DBSelectedComp(e);
            return;
          }
      }

    if (decompSelected == true)
      {
        fileSelecteddeComp(e);
      }
  }
     

  
  public xmlcompressutil()
  {
    try
      {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        initUI();
      }
    catch (Exception e)
      {}
  }
  
  


  void fileSelectedComp(ActionEvent e)
  {
    try 
      {
        
        if ((compStr.outputStream !=null) && (compStr.inputStream !=null))
          {
            xmlCompObj.setBinOutputStream(compStr.outputStream);
            xmlCompObj.DOMCompress(compStr.inputStream);
            float compPercent = compStr.getFileCompPercent();
            compStr.statstr =
	      new String(" Compression Successful");
            compStr.inpfilestr =
	      new String("Size of Input XML Data: " + compStr.sizeOrig +" bytes");
            compStr.outpfilestr =
	      new String("Size of Compressed XML Data: " + compStr.sizeComp +" bytes");
            compStr.compfactstr = 
	      new String("Compression Factor: " + compPercent);

            xmldoc = xmlCompObj.getXMLDocument();
            xmlTreeView.setXMLDocument(xmldoc);
            tabmain.setEnabledAt(2,true);
            if (compStr.outputStream !=null)
	      compStr.outputStream.close();
            if (compStr.inputStream != null)
	      compStr.inputStream.close();
            mesgLB.setText(compStr.statstr); 
            logLB1.setText(compStr.inpfilestr);
            logLB2.setText(compStr.outpfilestr);
            logLB3.setText(compStr.compfactstr);
    
          }

        else
	  { 
            compStr.statstr = new String("Enter the Input/Output File");
            mesgLB.setText(compStr.statstr);
	  }
      }
    catch (Exception e1)
      {
        compStr.statstr = new String("Compression Failed");
	compStr.inpfilestr =
	  new String("Invalid File Format/Output File not specified");
        compStr.outpfilestr = new String("Clear Buffers and try");
        logLB1.setText(compStr.inpfilestr);
        logLB2.setText(compStr.outpfilestr);
        logLB3.setText("");
        mesgLB.setText(compStr.statstr);
      }
  }



  void DBSelectedComp(ActionEvent e)
  {
    try 
      { 
        if ((compStr.outputStream != null) && !(compStr.xmlData.equals("")))
          {   
            compStr.outputStream.flush();
            xmlCompObj.setBinOutputStream(compStr.outputStream);
            xmlCompObj.DOMCompress(compStr.xmlData);
            float compPercent = compStr.getDBCompPercent();
            xmldoc = xmlCompObj.getXMLDocument();
            tabmain.setEnabledAt(2,true);
            xmlTreeView.setXMLDocument(xmldoc);
              
            if (compStr.outputStream !=null)
              compStr.outputStream.close();
            if (compStr.inputStream != null)
              compStr.inputStream.close();

            compStr.statstr = new 
	      String("Compression Successful");
            compStr.inpfilestr =
	      new String("Size of Input XML Data: " + compStr.sizeOrig +" bytes");
            compStr.outpfilestr =
	      new String("Size of Compressed XML Data: " + compStr.sizeComp +" bytes");
            compStr.compfactstr = 
	      new String("Compression Factor: " + compPercent);
            mesgLB.setText(compStr.statstr); 
            logLB1.setText(compStr.inpfilestr);
            logLB2.setText(compStr.outpfilestr);
            logLB3.setText(compStr.compfactstr);
 
          }
	else
	  { 
            compStr.statstr = new String("Load Data First");
            mesgLB.setText(compStr.statstr);
	  }
      }
    catch (Exception e1)
      {
        compStr.statstr = new String("Compression Failed" );
        compStr.inpfilestr =
	  new String("Invalid File Format/Output File not specified");
        compStr.outpfilestr = new String("Clear Buffers and try");
        logLB1.setText(compStr.inpfilestr);
        logLB2.setText(compStr.outpfilestr);
        logLB3.setText("");
        mesgLB.setText(compStr.statstr);
      }
  }




  void fileSelecteddeComp(ActionEvent e)
  {
    try 
      {
      if ((compStr.outputStream !=null) && (compStr.inputStream !=null))
          {
            
            xmlCompObj.setXmlTextOutputStream(compStr.outputStream);
            xmlCompObj.DOMExpand(compStr.inputStream);
            xmldoc = xmlCompObj.getXMLDocument();
            xmlTreeView.setXMLDocument(xmldoc);
            tabmain.setEnabledAt(2,true);
            compStr.inputStream.close();
            compStr.statstr = new String("Uncompression Successful");
            mesgLB.setText(compStr.statstr);
          }
	else
	  { 
            compStr.statstr = new String("Enter Input/Output File Name");
            mesgLB.setText(compStr.statstr);
	  }
      }
     
    catch (StreamCorruptedException e2) {
      compStr.statstr = new String(e2.toString());
      mesgLB.setText(compStr.statstr);
      logLB1.setText("Bad File Format");
      logLB2.setText("");
    }

    catch (Exception e1) {
      compStr.statstr = new String("Operation Failed:");
      logLB1.setText("Specify Output/Input File: Try Clearing buffers");
      logLB2.setText("");
      mesgLB.setText(compStr.statstr);
    }
  }




  void tabChanged(ChangeEvent e)
  {
    int ind;
    String tabstr;
    JTabbedPane tp = (JTabbedPane)e.getSource();
    ind=tp.getSelectedIndex();
    tabstr=tp.getTitleAt(ind);

    if (ind == 0)
      {
        compSelected = true;
        decompSelected = false;
        fileDBButton1.setEnabled(true);
        mesgLB.setText("Compression Chosen");
        logLB1.setText("");
        logLB2.setText("");
        logLB3.setText("");
      }
    
    if (ind == 1)
      {
        compSelected = false;
        decompSelected = true;
        dbPanel2.disableComponents();
        filePanel1.loadfileTextField.setEnabled(true);
        filePanel1.browseButton1.setEnabled(true);
        fileDBButton1.setSelected(false);
        fileDBButton2.setSelected(true);
        fileDBButton1.setEnabled(false); 
        mesgLB.setText("Uncompression Chosen");
        logLB1.setText("");
        logLB2.setText("");
        logLB3.setText("");       
      }

    if (ind == 2)
      {
        mesgLB.setText("Viewing Tree");
        logLB1.setText("");
        logLB2.setText("");
        logLB3.setText("");
      }
  } 
}

     




