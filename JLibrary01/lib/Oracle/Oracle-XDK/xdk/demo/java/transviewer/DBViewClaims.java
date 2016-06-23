import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import oracle.jdeveloper.layout.*;
import oracle.xml.dbviewer.*;

public class DBViewClaims extends JPanel {
  DBViewer dbPanel= new DBViewer();
  JButton searchButton = new JButton();
  XYLayout xYLayout1 = new XYLayout();
  JLabel titleLabel = new JLabel();
  JLabel nameLabel = new JLabel();
  JLabel policyLabel = new JLabel();
  JTextField nameTF = new JTextField();
  JTextField policyTF = new JTextField();
  JButton viewXMLButton = new JButton();
  JButton viewXSLButton = new JButton();
  JButton viewHTMLButton = new JButton();
  public DBViewClaims() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    setBackground(SystemColor.controlLtHighlight);
    this.setLayout(xYLayout1);
    searchButton.setText("searchButton");
    searchButton.setLabel("Search");
    xYLayout1.setHeight(464);
    xYLayout1.setWidth(586);
    titleLabel.setText("List of Claims");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setBackground(new Color(192, 192, 255));
    titleLabel.setFont(new Font("Dialog", 1, 16));
    nameLabel.setText("Last Name");
    policyLabel.setText("Policy:");
    viewXMLButton.setText("viewXMLButton");
    viewXMLButton.setLabel("view XML");
    viewXMLButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewXMLButton_actionPerformed(e);
      }
    });
    viewXSLButton.setText("viewXSLButton");
    viewXSLButton.setLabel("view XSL");
    viewXSLButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewXSLButton_actionPerformed(e);
      }
    });
    viewHTMLButton.setText("viewHTMLButton");
    viewHTMLButton.setLabel("view HTML");
    viewHTMLButton.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        viewHTMLButton_actionPerformed(e);
      }
    });

     searchButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          searchButton_actionPerformed(e);
        }
     });

    this.add(dbPanel, new XYConstraints(16, 55, 552, 302));
    this.add(searchButton, new XYConstraints(413, 415, 154, 29));
    this.add(titleLabel, new XYConstraints(79, 10, 413, 31));
    this.add(nameLabel, new XYConstraints(333, 373, 72, -1));
    this.add(policyLabel, new XYConstraints(334, 395, 59, -1));
    this.add(nameTF, new XYConstraints(413, 368, 155, -1));
    this.add(policyTF, new XYConstraints(413, 391, 156, -1));
    this.add(viewXMLButton, new XYConstraints(19, 359, 94, 29));
    this.add(viewXSLButton, new XYConstraints(19, 390, 94, 29));
    this.add(viewHTMLButton, new XYConstraints(19, 421, 94, 29));
    updateUI();
  }
  void searchButton_actionPerformed(ActionEvent e) {
    String sqlText="select * from s_claim c ";
    try {
      if (!nameTF.getText().equals("")) {
         sqlText=sqlText+" where c.claimpolicy.primaryinsured.lastname="+
                 "'"+nameTF.getText()+"'";
      } else if (!policyTF.getText().equals("")) {
         sqlText=sqlText+" where c.claimpolicy.policyid="+
                 policyTF.getText();
      }
      dbPanel.setUsername("scott");
      dbPanel.setPassword("tiger");
      dbPanel.setInstancename("orcl0");
      dbPanel.setHostname("localhost");
      dbPanel.setPort("1521");
      dbPanel.loadXMLBufferFromSQL(sqlText);
      dbPanel.loadXslBuffer("xslfiles","CLAIM.XSL");
      dbPanel.transformToRes();
      dbPanel.setResHtmlView(true);
    } catch (Exception e1) {
      System.out.println(e1);
    }
  }
  void viewXMLButton_actionPerformed(ActionEvent e) {
    dbPanel.setXmlSourceEditView(true);
  }
  void viewXSLButton_actionPerformed(ActionEvent e) {
    dbPanel.setXslSourceEditView(true);
  }
  void viewHTMLButton_actionPerformed(ActionEvent e) {
    dbPanel.setResHtmlView(true);
  }
}

