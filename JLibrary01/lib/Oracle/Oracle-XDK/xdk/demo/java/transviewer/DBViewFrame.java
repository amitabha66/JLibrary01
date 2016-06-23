import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import oracle.jdeveloper.layout.*;

public class DBViewFrame extends JFrame {
  JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem menuFileExit = new JMenuItem();
  JMenuItem menuListCustomerClaims = new JMenuItem();

  public DBViewFrame() {
    super();
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(new GridLayout(1,1));
    this.setSize(new Dimension(600, 550));
    menuFile.setText("File");
    menuFileExit.setText("Exit");
    menuListCustomerClaims.setText("List Claims");
    menuFileExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fileExit_ActionPerformed(e);
      }
    });
    menuListCustomerClaims.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         ListCustomerClaims_ActionPerformed(e);

      }
    });
    menuFile.add(menuFileExit);
    menuFile.add(menuListCustomerClaims);
    menuBar1.add(menuFile);
    this.setJMenuBar(menuBar1);
    this.setBackground(SystemColor.controlLtHighlight);
  }
  void fileExit_ActionPerformed(ActionEvent e) {
    System.exit(0);
  }
  void ListCustomerClaims_ActionPerformed(ActionEvent e) {
    this.getContentPane().removeAll();
    this.getContentPane().add(new DBViewClaims());
    this.getContentPane().paintAll(this.getGraphics());
  }
}

