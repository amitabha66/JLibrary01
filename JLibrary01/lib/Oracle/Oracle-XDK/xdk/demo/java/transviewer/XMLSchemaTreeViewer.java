import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class XMLSchemaTreeViewer {

  public static void main(String [] args)
  {
    Frame frame = new JFrame("TreeViewer with Validation");

    TreeViewerValidate viewPanel = new TreeViewerValidate();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(510,575);
    viewPanel.setPreferredSize(new Dimension(510,575));
    Dimension frameSize = frame.getSize();

    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }

    frame.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
    frame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { System.exit(0); } });
    frame.setVisible(true);

    viewPanel.setPreferredSize(frameSize);
    ((JFrame)frame).getContentPane().add(viewPanel);
    frame.pack();
  }
}

