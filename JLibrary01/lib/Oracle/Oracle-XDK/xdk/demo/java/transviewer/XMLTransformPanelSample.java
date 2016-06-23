

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import oracle.xml.transviewer.XMLTransformPanel;


public class XMLTransformPanelSample
{
  XMLTransformPanel transformPanel = new XMLTransformPanel();

  /**
   * Adjust frame size and add transformPanel to it.
   */
  public XMLTransformPanelSample () 
  {
    Frame     frame      = new JFrame();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(510,550);
    transformPanel.setPreferredSize(new Dimension(510,550));
    Dimension frameSize = frame.getSize();
 
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation ((screenSize.width - frameSize.width)/2,
                      (screenSize.height - frameSize.height)/2);
    frame.addWindowListener(new WindowAdapter() { 
       public void windowClosing(WindowEvent e) { System.exit(0); }
    });
    frame.setVisible(true);

    ((JFrame)frame).getContentPane().add (transformPanel);
    frame.pack();
  }

  /**
   *  main(). Only creates XMLTransformPanelSample object.
   */
  public static void main (String[] args) 
  {
    new XMLTransformPanelSample ();
  }
}

 
