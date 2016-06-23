import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import oracle.xml.parser.v2.XMLDocument;
import oracle.jdeveloper.layout.*;

class filepanel extends JPanel implements Serializable {

  Vector actionListeners;
  JLabel loadfileNameLabel = new JLabel();
  JLabel savefileNameLabel = new JLabel();
  JTextField loadfileTextField = new JTextField();
  JTextField savefileTextField = new JTextField();
  JButton browseButton1 = new JButton();
  JButton browseButton2 = new JButton();
  JButton okButton1 = new JButton("Ok");
  JButton okButton2 = new JButton("Ok");
  XYLayout xYLayout1 = new XYLayout();
  JSeparator jSeparator1 = new JSeparator();


  String FileNameLabel="Load File:";
  String SaveNameLabel="Save File to:";
  String BrowseButtonText="Browse";
  String loadfileName;
  String savefileName;

  compstreamdata compstr = new compstreamdata();
  InputStream xmltextInputstream ;
  InputStream binInputStream;


  /* Constructor to initialize the GUI stuff */

  public filepanel(compstreamdata compstr)
  {
    super();
    try {
      this.compstr = compstr;
      jbInit();
    }
    catch (Exception e)
      {
        e.printStackTrace();
      }
  }



  /* Main Methods which sets the GUI and the listeners */

  private void jbInit() throws Exception
  {
    browseButton1.setText(BrowseButtonText);
    browseButton2.setText(BrowseButtonText);
    loadfileNameLabel.setText(FileNameLabel);
    savefileNameLabel.setText(SaveNameLabel);

    xYLayout1.setHeight(105);
    xYLayout1.setWidth(206);

    browseButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseButton1_actionPerformed(e);
      }
    });


    browseButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        browseButton2_actionPerformed(e);
      }
    });

    okButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadfileTextField_actionPerformed(e);   
      }
    });


    okButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        savefileTextField_actionPerformed(e);
      }
    });


    loadfileTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadfileTextField_actionPerformed(e);
      }
    });


    savefileTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        savefileTextField_actionPerformed(e);
      }
    });



    this.setLayout(xYLayout1);
    this.add(loadfileNameLabel, new XYConstraints(15, 19, 76, 20));
    this.add(savefileNameLabel, new XYConstraints(15, 59, 76, 20));
    this.add( loadfileTextField , new XYConstraints( 104, 19, 210,20) );
    this.add( savefileTextField , new XYConstraints( 104, 59, 210,20) );
    this.add( browseButton1 , new XYConstraints( 326, 19,90,20) );
    this.add( okButton1,  new XYConstraints( 420, 19,70,20) );
    this.add( browseButton2 , new XYConstraints( 326, 59,90,20));
    this.add( okButton2,  new XYConstraints( 420, 59,70,20));
    this.add( jSeparator1 , new XYConstraints(10,94,475,2));
  }



  /* Method for actionPermed while Loading */

  void browseButton1_actionPerformed(ActionEvent e)
  {
    loadfileName=chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
    if (loadfileName!=null) {
      try {
        loadfileTextField.setText(loadfileName);
        compstr.inputStream = new FileInputStream(loadfileName);
        compstr.origFile = new File(loadfileName);
        compstr.statstr = new String(loadfileName + " is loaded");
        savefileTextField.setEnabled(true);
        fireActionPerformed(new ActionEvent(this,1,"fileopen"));
      }
      catch (Exception e1) {
        compstr.statstr = new String(loadfileName + " doesn't exist");
      }
    }
  }



  /* Method for actionPermed while Saving */

  void browseButton2_actionPerformed(ActionEvent e)
  {
    savefileName=chooseFile(JFileChooser.FILES_AND_DIRECTORIES);
    if (savefileName!=null) {
      try {
        savefileTextField.setText(savefileName);
        compstr.outputStream = new FileOutputStream(savefileName);
        compstr.compFile = new File(savefileName);
        compstr.statstr = new String(savefileName + " is Selected");
        fireActionPerformed(new ActionEvent(this,1,"fileopen"));
      }
      catch (Exception e1) {
         compstr.statstr = new String(loadfileName + " doesn't exist");
      }
    }
  }



  /* Method which sets the InputStreams for the XMLCompress object */

  void loadfileTextField_actionPerformed(ActionEvent e) {
    loadfileName=loadfileTextField.getText();
    try {
      compstr.origFile = new File(loadfileName);
      compstr.inputStream = new FileInputStream(loadfileName);
      compstr.statstr = new String(loadfileName + " is loaded");
      fireActionPerformed(new ActionEvent(this,1,"fileopen"));
    }
    catch (Exception e1) {
         compstr.statstr = new String(loadfileName + " doesn't exist");
  }
  }



  /* Method which sets the OutputStreams for the XMLCompress object */

  void savefileTextField_actionPerformed(ActionEvent e) {
    savefileName=savefileTextField.getText();
    try {
      compstr.compFile = new File(savefileName);
      compstr.outputStream = new FileOutputStream(savefileName);
      compstr.statstr = new String(savefileName + " is Selected");
      fireActionPerformed(new ActionEvent(this,1,"fileopen"));
    }
    catch (Exception e1) {}
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



  void disableComponents()
  {
    loadfileTextField.setEnabled(false);
    browseButton1.setEnabled(false);
       
  }


  void enableComponents()
  {
    loadfileTextField.setEnabled(true);
    browseButton1.setEnabled(true);
  }


  void clearFields()
  {
    loadfileTextField.setText("");
    savefileTextField.setText("");
    loadfileName = "";
    savefileName= "";
  }
}
