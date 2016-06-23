import javax.swing.*;
import java.awt.*;
import oracle.jdeveloper.layout.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;
import oracle.xml.sql.query.*;
import oracle.xml.dbaccess.DBAccess;
import oracle.xml.xmldbaccess.XMLDBAccess;




class dbpanel extends JPanel {
  private Vector actionListeners;
  JLabel HostName = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JTextField HostNameTF = new JTextField();
  JLabel SID = new JLabel();
  JTextField SIDTF = new JTextField();
  JLabel UserId = new JLabel();
  JTextField UserIdTF = new JTextField();
  JLabel Password = new JLabel();
  JPanel queryPanel = new JPanel();
  JTextArea QueryTA = new JTextArea();
  XYLayout xYLayout3 = new XYLayout();
  JButton SubmitBT = new JButton();
  JPanel clobPanel = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  JSeparator jSeparator1 = new JSeparator();
  JButton loadBT = new JButton();
  JButton listBT = new JButton();
  JLabel errorTextLB = new JLabel();
  JSeparator jSeparator2 = new JSeparator();
  JSeparator jSeparator3 = new JSeparator();
  JLabel port = new JLabel();
  JTextField portTF = new JTextField();
  JButton clobNamesBT = new JButton();
  DefaultListModel lmTables=new DefaultListModel();
  JList listTableLST = new JList(lmTables);
  DefaultListModel lmNames=new DefaultListModel();
  JList listNamesLST = new JList(lmNames);
  // the XML text from the database
  String xmltext="";
  // this object contains all information for the XML data being processed
  JScrollPane jScrollPane1 = new JScrollPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  JSeparator jSeparator4 = new JSeparator();
  JPasswordField PasswordTF = new JPasswordField();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel BufferNameLB = new JLabel();
  JLabel jLabel3 = new JLabel();
  JSeparator jSeparator5 = new JSeparator();
  JScrollPane jScrollPane3 = new JScrollPane();

  String QueryLabelText="Type a query retrive database result set into XML:";
  String HostnameText="Hostname";
  String UserIdText="User Id";
  String PasswordText="Password";
  String SIDText="SID";
  String ListDataNamesText="List Data Names";
  String PortText="Port";
  String ListCLOBText="List Tables";
  String RetrieveResultSetText="Retrieve result set";
  String RetrieveText="Load data";
  String ImportLabelText="Import data from Database table:";
  String BlankText=""; 
  String tableName = "";
  String xmlDataName = "";
  String username;
  String password;
  String hostname;
  String portname;
  String instancename;

  Connection con = null;
  String driverclass   = "oracle.jdbc.driver.OracleDriver";

  String [] tableList = { "CLOB Type", "XMLType"};
  JComboBox TableTypeCB = new JComboBox(tableList);
  String tableType = "CLOB Type";

  boolean buffer_loaded = false;
  compstreamdata compstr = new compstreamdata();


  /* constructor initializing the GUI */

  dbpanel(compstreamdata compstr) {
    super();
    try  {
      this.compstr = compstr;
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  // String getXMLText() {
  //  return xmltext;
  // }


  /* Main Method to initialize GUI and add listeners */ 

  private void jbInit() throws Exception {

    HostName.setText(HostnameText);
    jLabel1.setText(QueryLabelText);
    SID.setText(SIDText);
    Password.setText(PasswordText);
    UserId.setText(UserIdText);
    loadBT.setText(RetrieveText);
    clobNamesBT.setText(ListCLOBText);
    listBT.setText(ListDataNamesText);
    port.setText(PortText);
    SubmitBT.setText(RetrieveResultSetText);
    errorTextLB.setText(BlankText);

    this.setLayout(xYLayout1);
    HostName.setForeground(Color.black);
    HostName.setFont(new Font("Dialog", 0, 12));
    jLabel1.setForeground(Color.white);
    jLabel1.setFont(new Font("Dialog", 0, 12));
    
    jLabel2.setText(ImportLabelText);
    jLabel2.setForeground(Color.white);
    jLabel2.setFont(new Font("Dialog", 0, 12));
    jLabel3.setForeground(Color.darkGray);
    xYLayout1.setHeight(325);
    xYLayout1.setWidth(498);
    SID.setForeground(Color.black);
    SID.setFont(new Font("Dialog", 0, 12));
    UserId.setForeground(Color.black);
    UserId.setFont(new Font("Dialog", 0, 12));
    Password.setForeground(Color.black);
    Password.setFont(new Font("Dialog", 0, 12));
    queryPanel.setBackground(new Color(204, 204, 204));
    clobPanel.setBackground(new Color(204, 204, 204));
    
    jSeparator1.setOrientation(SwingConstants.VERTICAL);
    loadBT.setFont(new Font("Dialog", 0, 10));
    loadBT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loadBT_actionPerformed(e);
      }
    });
   
  
    listBT.setFont(new Font("Dialog", 0, 10));
    listBT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        listBT_actionPerformed(e);
      }
    });
    
    port.setForeground(Color.black);
    port.setFont(new Font("Dialog", 0, 12));
    clobNamesBT.setFont(new Font("Dialog", 0, 10));
    clobNamesBT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clobNamesBT_actionPerformed(e);
      }
    });


    listNamesLST.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        listNamesLST_mouseReleased(e);
      }
    });

    listTableLST.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        listTableLST_mouseReleased(e);
      }
    });
   
    clobPanel.setLayout(xYLayout2);
    queryPanel.setLayout(xYLayout3);
    SubmitBT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SubmitBT_actionPerformed(e);
      }
    });
    SubmitBT.setFont(new Font("Dialog", 0, 10));


    TableTypeCB.setFont(new Font("Dialog", 0, 10));
    TableTypeCB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        tableType = (String)cb.getSelectedItem();
      }
    });

    this.add(HostName, new XYConstraints(9, 9, 63, 21));
    this.add(HostNameTF, new XYConstraints(74, 8, 148, -1));
    this.add(SID, new XYConstraints(232, 12, 32, -1));
    this.add(SIDTF, new XYConstraints(264, 9, 82, -1));
    this.add(UserId, new XYConstraints(9, 38, 45, -1));
    this.add(UserIdTF, new XYConstraints(74, 35, 110, -1));
    this.add(Password, new XYConstraints(196, 38, -1, -1));


    this.add(queryPanel, new XYConstraints(9, 74, 466, 90));
    queryPanel.add(jLabel1, new XYConstraints(4, 3, 361, -1));
    queryPanel.add(SubmitBT, new XYConstraints(314, 3, 152, 19));
    queryPanel.add(jScrollPane3, new XYConstraints(3, 28, 468, 50));
    jScrollPane3.getViewport().add(QueryTA, null);

    this.add(clobPanel, new XYConstraints(10, 180, 478, 138));
    clobPanel.add(jSeparator1, new XYConstraints(239, 17, 7, 115));
    clobPanel.add(loadBT, new XYConstraints(371, 4, 100, 19));
    // clobPanel.add(listBT, new XYConstraints(309, 110, 113, 20));
    clobPanel.add(errorTextLB, new XYConstraints(10, 218, 450, -1));
    clobPanel.add(TableTypeCB, new XYConstraints(4, 110, 106, 20));
    clobPanel.add(clobNamesBT, new XYConstraints(113, 110, 116, 20));
    clobPanel.add(jScrollPane1, new XYConstraints(4, 30, 225, 71));
    jScrollPane1.getViewport().add(listTableLST, null);
    clobPanel.add(jScrollPane2, new XYConstraints(251, 29, 221, 72));
    jScrollPane2.getViewport().add(listNamesLST, null);
    clobPanel.add(jLabel2, new XYConstraints(5, 1, 219, -1));



    this.add(jSeparator2, new XYConstraints(5, 65, 479, 3));
    this.add(jSeparator3, new XYConstraints(10, 170, 476, 0));
    this.add(port, new XYConstraints(360, 14, 32, -1));
    this.add(portTF, new XYConstraints(400, 10, 36, -1));
    this.add(jSeparator4, new XYConstraints(11, 236, 476, -1));
    this.add(PasswordTF, new XYConstraints(264, 35, 82, -1));
    this.add(jLabel3, new XYConstraints(123, 484, 362, -1));
    this.add(jSeparator5, new XYConstraints(10, 503, 477, 4));
    this.add(jSeparator5, new XYConstraints(10, 320, 477, 4));
  }



  /* Method for Query Button */

  void SubmitBT_actionPerformed(ActionEvent e) {
    // Connect to the Database
    try {
      setDBParameter();
      Connection cn = getConnection();
      // Create an instance of the OracleXMLQuery handler
      OracleXMLQuery q = new OracleXMLQuery(cn,QueryTA.getText());
      // Set some XML Generation options
      q.useLowerCaseTagNames();
      compstr.xmlData = q.getXMLString();
      compstr.statstr = "Loaded, SQL="+QueryTA.getText();
      fireActionPerformed(new ActionEvent(this,1,"datareturned"));
      cn.close();
    } 
    catch (Exception e1) {
      compstr.statstr = e1.toString();
    }
  }
 

  static boolean insideOracle8i() {
    // If oracle.server.version is non null, we're running in the database
    String ver = System.getProperty("oracle.server.version");
    return (ver != null && !ver.equals(""));
  }




  public synchronized void removeActionListener(ActionListener l)
  {
    if (actionListeners != null && actionListeners.contains(l))
      {
        Vector v = (Vector) actionListeners.clone();
        v.removeElement(l);
        actionListeners = v;
      }
  }

  public synchronized void addActionListener(ActionListener l)
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



  /* Method for Listing Tables & Data Names */

  void listBT_actionPerformed(ActionEvent e) {
    // list data items into a table
    listCLOBNames();
  }



  void listCLOBNames() {
    // lists the file entries into a clob table into a
    String clobName=tableName;
      
    String dataNames[] ={};
    int i;
    try {
      setDBParameter();
      lmNames.removeAllElements();
      if (tableType.equals("CLOB Type"))
        {
          DBAccess db=new DBAccess();
          dataNames=db.getXMLNames(getConnection(),clobName);
        }
      if (tableType.equals("XMLType"))
        {
          XMLDBAccess db = new XMLDBAccess();
          dataNames=db.getXMLNames(getConnection(),clobName);
        }
         
      for (i=0;i<dataNames.length;i++) {
        lmNames.addElement(dataNames[i]);
        //System.out.println("dataname="+dataNames[i]);
      }
      closeConnection();
      //System.out.println(lmNames.getSize());
      if (lmNames.getSize()>0) {
        selectDataName(0);
      }
    } catch (Exception ex) {
      //System.out.println("list clob names exception"+ex);
    }
  }


  void loadBT_actionPerformed(ActionEvent e) {
    //System.out.println("load button pressed");
    String clobName=tableName;

    buffer_loaded = true;
      
    try {
      setDBParameter();
      getConnection();
      if (tableType.equals("CLOB Type"))
        {
          DBAccess db=new DBAccess();
          compstr.xmlData = db.getXMLData(con,clobName,xmlDataName);
          compstr.statstr = "Data loaded from CLOB Table";
        }
      if (tableType.equals("XMLType"))
        {
          XMLDBAccess db = new XMLDBAccess();
          compstr.xmlData = db.getXMLTypeData(con,clobName,xmlDataName);
          compstr.statstr = "Data loaded from XMLType Table";
        }
      closeConnection();
    } 
    catch (Exception ex) {
      xmltext=""; 
      compstr.statstr = "LOading Data Failed";
      return;
    }
    fireActionPerformed(new ActionEvent(this,1,"datareturned"));
  }

  



  void clobNamesBT_actionPerformed(ActionEvent e) {
    // list the clob tables
    listCLOBTables();
  }



  void listCLOBTables() {
    // list all tables that are in CLOB format recognized
    // by this tool
    String tableNames[] = {};
    int i;
    try {
      setDBParameter();
      lmTables.removeAllElements();
      if (tableType.equals("CLOB Type"))
        {
          DBAccess db=new DBAccess();
          tableNames=db.getXMLTableNames(getConnection(),"");
        }
      if (tableType.equals("XMLType"))
        {
          XMLDBAccess db = new XMLDBAccess();
          tableNames=db.getXMLTypeTableNames(getConnection(),"");
        }
     
      for (i=0;i<tableNames.length;i++) {
        lmTables.addElement(tableNames[i]);
           
      }
      if (lmTables.getSize()>0) {
        selectTable(0);
      }
      closeConnection();
    } catch (Exception ex) {
         
    }
    listCLOBNames();
  }

 
  void selectDataName( int i) {
    listNamesLST.setSelectedIndex(i);
    if (listNamesLST.getSelectedIndex() != -1) {
      String text=(String)listNamesLST.getSelectedValue();
      if (text!=null) xmlDataName = new String(text);
    }
  }


  void selectTable (int i) {
    listTableLST.setSelectedIndex(i);
    if (listTableLST.getSelectedIndex() != -1) {
      String text=(String)listTableLST.getSelectedValue();
      if (text!=null) tableName = new String(text);
    }
  }

  void listNamesLST_mouseReleased(MouseEvent e) {
    if (listNamesLST.getSelectedIndex() != -1) {
      String text=(String)listNamesLST.getSelectedValue();
      if (text!=null) xmlDataName = new String(text);
    }
    errorTextLB.setText(BlankText);
  }


  void listTableLST_mouseReleased(MouseEvent e) {
    if (listTableLST.getSelectedIndex() != -1) {
      String text=(String)listTableLST.getSelectedValue();
      listCLOBNames();
    }
  }


  boolean getbufferLoaded()
  {
    return buffer_loaded;
  }
  void setbufferLoaded(boolean value)
  {
    buffer_loaded = value;
  }


  Connection getConnection() throws SQLException {
    String thinConn= "jdbc:oracle:thin:@"+
      hostname+":"+
      portname+":"+
      instancename;
    String default8iConn = "jdbc:oracle:kprb:";

    Driver drv;
    if  (con != null) {
      con.close();
      con=null;
    }
    
    // Load the Oracle JDBC driverm (alternative method)
    //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    try {
      drv = (Driver)Class.forName(driverclass).newInstance();
    }
    catch (Exception e) {
      throw new SQLException("Error Loading JDBC Driver "+driverclass);
    }
    if (insideOracle8i()) {
      con=DriverManager.getConnection(default8iConn,username,password);
    } else {
      con=DriverManager.getConnection(thinConn,username,password);
    }

    return con;
  }

  void closeConnection() throws SQLException {
    if  (con != null) {
      con.close();
      con=null;
    }
  }

  /* Utility methods to play with the GUI components */

  void setDBParameter()
  {
    username=UserIdTF.getText();
    password=PasswordTF.getText();
    instancename=SIDTF.getText();
    portname=portTF.getText();
    hostname=HostNameTF.getText();
  }


  void disableComponents()
  {
    loadBT.setEnabled(false);
    listBT.setEnabled(false);
    SubmitBT.setEnabled(false);
    HostNameTF.setEnabled(false);
    SIDTF.setEnabled(false);
    UserIdTF.setEnabled(false);
    PasswordTF.setEnabled(false);
    portTF.setEnabled(false);
    QueryTA.setEnabled(false);
    clobNamesBT.setEnabled(false);
    TableTypeCB.setEnabled(false);
    this.setEnabled(false);
       
  }


  void enableComponents()
  {
    loadBT.setEnabled(true);
    listBT.setEnabled(true);
    SubmitBT.setEnabled(true);
    HostNameTF.setEnabled(true);
    SIDTF.setEnabled(true);
    UserIdTF.setEnabled(true);
    PasswordTF.setEnabled(true);
    portTF.setEnabled(true);
    QueryTA.setEnabled(true);
    clobNamesBT.setEnabled(true);
    TableTypeCB.setEnabled(true);
    this.setEnabled(true);
       
  }



  void clearFields()
  {
    HostNameTF.setText("");
    SIDTF.setText("");
    UserIdTF.setText("");
    PasswordTF.setText("");
    portTF.setText("");
    QueryTA.setText("");
    hostname = "";
    password = "";
    portname = "";
    username = "";
    instancename = "";       
  }
}
