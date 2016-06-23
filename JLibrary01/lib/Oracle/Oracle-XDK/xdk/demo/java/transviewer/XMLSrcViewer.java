import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;


public class XMLSrcViewer extends JFrame
{
  private transient ArrayList actionListeners = new ArrayList(2);

  XMLSrcViewer()
  {
    super("XML Source Viewer");
    XMLSrcViewPanel srcPanel = new XMLSrcViewPanel();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(505,570);
    srcPanel.setPreferredSize(new Dimension(505,570));
    Dimension frameSize = getSize();

    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }

    setLocation((screenSize.width - frameSize.width)/2, 
                (screenSize.height - frameSize.height)/2);
    addWindowListener(new WindowAdapter() 
    {public void windowClosing(WindowEvent e) { System.exit(0); } });
    setVisible(true);

    srcPanel.setPreferredSize(frameSize);
    getContentPane().add(srcPanel);
    pack();
  }

  public static void main(String [] args)
  {
    Properties p = System.getProperties();
    /* Set the Debug property to obtain line number info from validator */
    p.put("oracle.xml.parser.debugmode", "true");
 
    XMLSrcViewer srcviewer = new XMLSrcViewer();

  }

  public synchronized void addActionListener(ActionListener l)
  {
    if (!actionListeners.contains(l))
    {
      actionListeners.add(l);
    }
  }

  public synchronized void removeActionListener(ActionListener l)
  {
    actionListeners.remove(l);
  }

  protected void fireActionPerformed(ActionEvent e)
  {
    List listeners = (List)actionListeners.clone();
    int count = listeners.size();

    for (int i = 0;i < count;i++)
    {
      ((ActionListener)listeners.get(i)).actionPerformed(e);
    }
  }
}
