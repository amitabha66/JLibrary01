import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import oracle.xml.parser.v2.*;
import oracle.xml.srcviewer.*;
import oracle.xml.differ.*;
import org.w3c.dom.*;

public class XMLDiffFrame extends JFrame implements ActionListener {

  public XMLDiffFrame(XMLDiffSample dfApp)
  {
    super();
    mydfApp = dfApp;
    init();
  }


  public void makeSrcPane(XMLDocument doc1, XMLDocument doc2)
  {
      //undo srcviewer highlighting here
      XMLSourceView XmlSrcView1 = new XMLSourceView();
      try
      {
        XmlSrcView1.setXMLDocument(doc1);
      }catch(Exception e)
      {
         JOptionPane.showMessageDialog(this, "Error:"+e.getMessage(),
             "XMLDiffer Message", JOptionPane.PLAIN_MESSAGE);
      }
      XmlSrcView1.setTagForeground(Color.black);
      XmlSrcView1.setAttributeValueForeground(Color.black);
      XmlSrcView1.setPIDataForeground(Color.black);
      XmlSrcView1.setCommentDataForeground(Color.black);
      XmlSrcView1.setCDATAForeground(Color.black);

      XmlSrcView1.setBackground(Color.lightGray);
      XmlSrcView1.getJTextPane().setBackground(Color.white);
      XmlSrcView1.add(new JLabel(filename1,SwingConstants.CENTER),
                      BorderLayout.NORTH);

      XMLSourceView XmlSrcView2 = new XMLSourceView();
      try
      {
        XmlSrcView2.setXMLDocument(doc2);
      }catch(Exception e)
      {
         JOptionPane.showMessageDialog(this, "Error:"+e.getMessage(),
             "XMLDiffer Message", JOptionPane.PLAIN_MESSAGE);
      }
      XmlSrcView2.setTagForeground(Color.black);
      XmlSrcView2.setAttributeValueForeground(Color.black);
      XmlSrcView2.setPIDataForeground(Color.black);
      XmlSrcView2.setCommentDataForeground(Color.black);
      XmlSrcView2.setCDATAForeground(Color.black);

      XmlSrcView2.setBackground(Color.lightGray);
      XmlSrcView2.getJTextPane().setBackground(Color.white);
      XmlSrcView2.add(new JLabel(filename2,SwingConstants.CENTER),
                      BorderLayout.NORTH);

      XmlSrcView2.updateUI();
      XmlSrcView1.updateUI();

      srcPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                           XmlSrcView1, XmlSrcView2);
      srcPane.setSize(FRAMEWIDTH,FRAMEHEIGHT);
      srcPane.setDividerLocation(0.5);
      srcPane.validate();


  }

  public void makeDiffSrcPane(XMLDiffSrcView srcView1, XMLDiffSrcView srcView2)
  {
      srcView1.setBackground(Color.lightGray);
      srcView2.setBackground(Color.lightGray);

      srcView1.add(new JLabel(filename1,SwingConstants.CENTER),BorderLayout.NORTH);
      srcView2.add(new JLabel(filename2,SwingConstants.CENTER),BorderLayout.NORTH);

      JScrollBar vscrollBar = srcView2.getScrollPane().getVerticalScrollBar();

      // make the diffSrcView divider fixed.
      srcView1.getScrollPane().setVerticalScrollBar(vscrollBar);
      srcView1.getScrollPane().setMinimumSize(
        new Dimension(FRAMEWIDTH/2,srcView1.getScrollPane().getPreferredSize().height));
      srcView2.getScrollPane().setMinimumSize(
        new Dimension(FRAMEWIDTH/2,srcView2.getScrollPane().getPreferredSize().height));

      srcView2.getScrollPane().updateUI();
      srcView1.getScrollPane().updateUI();

      srcView2.getTextPane().updateUI();
      srcView1.getTextPane().updateUI();

      srcView2.updateUI();
      srcView1.updateUI();

      diffSrcPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                            srcView1, srcView2);
      diffSrcPane.setSize(FRAMEWIDTH,FRAMEHEIGHT);
      diffSrcPane.setDividerLocation(0.5);
      diffSrcPane.validate();

  }
  public void makeTabbedPane()
  {
    tabbedPane = new JTabbedPane();

    tabbedPane.addTab("SourceView", null , srcPane, "Source View of Files being Diffed");
    tabbedPane.addTab("SourceDiffView", null , diffSrcPane, "Source View of File Diffs");
    tabbedPane.addTab("TreeDiffView", null , diffTreePane, "DOM Tree View of File Diffs");
    tabbedPane.setSelectedIndex(1);
    tabbedPane.setSize(FRAMEWIDTH,FRAMEHEIGHT);

    this.getContentPane().add(tabbedPane);
    this.setVisible(true);


  }

  public void makeXslPane(XMLDocument doc, String title)
  {
     xslSrcView = new XMLSourceView();
     try
     {
       xslSrcView.setXMLDocument(doc);
     }catch(Exception e)
     {
        JOptionPane.showMessageDialog(this, "Error:"+e.getMessage(),
            "XMLDiffer Message", JOptionPane.PLAIN_MESSAGE);
     }
     xslSrcView.setTagForeground(Color.black);
     xslSrcView.setAttributeValueForeground(Color.black);
     xslSrcView.setPIDataForeground(Color.black);
     xslSrcView.setCommentDataForeground(Color.black);
     xslSrcView.setCDATAForeground(Color.black);

     xslSrcView.setBackground(Color.lightGray);
     xslSrcView.getJTextPane().setBackground(Color.white);
     xslSrcView.add(new JLabel(title,SwingConstants.CENTER),
                      BorderLayout.NORTH);
     this.enableTransformItem(true);
  }


  public void makeResultFilePane(XMLDocument doc)
  {
    resultDoc = doc;
    XMLSourceView resultSrcView = new XMLSourceView();
    try
    {
      resultSrcView.setXMLDocument(doc);
    }catch(Exception e)
    {
      JOptionPane.showMessageDialog(this, "Error:"+e.getMessage(),
            "XMLDiffer Message", JOptionPane.PLAIN_MESSAGE);
    }
    resultSrcView.setTagForeground(Color.black);
    resultSrcView.setAttributeValueForeground(Color.black);
    resultSrcView.setPIDataForeground(Color.black);
    resultSrcView.setCommentDataForeground(Color.black);
    resultSrcView.setCDATAForeground(Color.black);

    resultSrcView.setBackground(Color.lightGray);
    resultSrcView.getJTextPane().setBackground(Color.white);
    resultSrcView.add(new JLabel("XSLT Result File",SwingConstants.CENTER),
                      BorderLayout.NORTH);

    tabbedPane.addTab("ResultSourceView", null , resultSrcView,
                         "Source View of XSLT on File1");
    tabbedPane.setSelectedIndex(3);
    this.enableSaveAsItem(true);
  }

  public void makeXslTabbedPane()
  {
    tabbedPane = new JTabbedPane();

    tabbedPane.addTab("SourceView", null , srcPane, "Source View of XML Files being Diffed");
    tabbedPane.addTab("SourceDiffView", null , diffSrcPane, "Source View of File Diffs");
    tabbedPane.addTab("XSL Script",null,xslSrcView, "Source View of Diff XSL script");
    tabbedPane.setSelectedIndex(2);
    tabbedPane.setSize(FRAMEWIDTH,FRAMEHEIGHT);

    this.getContentPane().add(tabbedPane);
    this.setVisible(true);


  }

  public void actionPerformed(ActionEvent evt)
  {
    File selectedFile1, selectedFile2;
    BufferedReader file1, file2;
    String arg, temp;

    if(evt.getSource() instanceof JMenuItem)
    {

      arg = evt.getActionCommand();

      if(arg.equals("Compare XML Files"))
      {
        JFileChooser jFC = new JFileChooser();
	      jFC.setCurrentDirectory(new File("."));
	      int retval = jFC.showOpenDialog(this);

	      switch (retval)
	      {

	        case JFileChooser.APPROVE_OPTION:
            selectedFile1 = jFC.getSelectedFile();
	          temp = selectedFile1.getName();
            jFC.cancelSelection();
            jFC.updateUI();
	          switch(jFC.showOpenDialog(this))
	          {
	            case JFileChooser.APPROVE_OPTION:
              selectedFile2 = jFC.getSelectedFile();
     	        filename2 = selectedFile2.getName();
              filename1 = temp;

              this.getContentPane().removeAll();
              this.enableSaveAsItem(false);
              
   	          mydfApp.showDiffs(selectedFile1, selectedFile2);
              break;

              case JFileChooser.CANCEL_OPTION:
     	        break; //filename1 = null; // filename1 also null
            }// switch (jFC.showOpenDialog(this))
          break;

          case JFileChooser.CANCEL_OPTION:
          break;
        }
      }// if(arg.equals("Compare XML Files"))
      else if(arg.equals("Apply XSL to 1st Input File"))
      {
        mydfApp.doXSLTransform();

      }
      else if(arg.equals("Save As"))
      {
        JFileChooser jFC = new JFileChooser();
        jFC.setCurrentDirectory(new File("."));
        int retval = jFC.showOpenDialog(this);

	      if (retval == JFileChooser.APPROVE_OPTION)
        {
          File file = jFC.getSelectedFile();
          try
          {
            resultDoc.print(new FileOutputStream(file));
          }catch (IOException e)
          {
             JOptionPane.showMessageDialog(this,
             "Error:"+e.getMessage(),
             "XMLDiffer Message",
             JOptionPane.PLAIN_MESSAGE);
          }


        }
      }
      else if(arg.equals("Exit"))
      {
        System.exit(0);
      }

    }
  }


  public void addTransformMenu()
  {
    JMenuItem  item;

    JMenu jmenu = new JMenu("Transform");

    item = new JMenuItem("Apply XSL to 1st Input File");
    item.addActionListener(this);
    item.setEnabled(false);
    jmenu.add(item);

    this.getJMenuBar().add(jmenu);

  }

  protected void enableTransformItem(boolean flag)
  {
    this.getJMenuBar().getMenu(1).getItem(0).setEnabled(flag);
  }

  protected void enableSaveAsItem(boolean flag)
  {
    this.getJMenuBar().getMenu(0).getItem(1).setEnabled(flag);
  }

  private void init()
  {
    try
    {
      this.setTitle("XMLDiffer");
      this.getContentPane().setLayout( new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
      // make the Differ window non-resizable
      this.setResizable(false);
      this.getContentPane().setBackground(SystemColor.control);
      addMenu();

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = this.getSize();

      // set Frame size based on screen size such that there is room around it
      FRAMEWIDTH = screenSize.width - 100;
      FRAMEHEIGHT = screenSize.height - 200;
      this.setSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));

      // put Differ window in the center of the screen
      this.setLocation((screenSize.width - FRAMEWIDTH)/2, (screenSize.height - FRAMEHEIGHT)/2);
      this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }});

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void addMenu()
  {
    JMenuItem  item;

    JMenuBar jmenubar = new JMenuBar();
    JMenu jmenu = new JMenu("File");

    item = new JMenuItem("Compare XML Files");
    item.addActionListener(this);
    jmenu.add(item);

    item = new JMenuItem("Save As");
    item.addActionListener(this);
    item.setEnabled(false);
    jmenu.add(item);

    jmenu.addSeparator();

    item = new JMenuItem("Exit");
    item.addActionListener(this);
    jmenu.add(item);

    jmenubar.add(jmenu);
    this.setJMenuBar(jmenubar);

  }


  protected static int LEFT_TOP = 0;
  protected static int RIGHT_TOP = 1;
  protected static int CENTER = 2;

  private int FRAMEWIDTH =0;
  private int FRAMEHEIGHT =0;

  private XMLDocument resultDoc;
  private XMLSourceView xslSrcView;
  private XMLDiffSample mydfApp;
  private String filename1, filename2;
  private JTabbedPane tabbedPane;
  private JSplitPane diffTreePane, srcPane,diffSrcPane;
}

