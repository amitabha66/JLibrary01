import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import oracle.xml.xmlcomp.*;




public class compviewer {

 public static void main(String [] args)
  {
    Frame frame = new JFrame("XML Compression Utility");
    xmlcompressutil test = new xmlcompressutil();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(515,570);
    test.setPreferredSize(new Dimension(505,550));
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

    test.setPreferredSize(frameSize);
    ((JFrame)frame).getContentPane().add(test);
    frame.pack();
  }
}

