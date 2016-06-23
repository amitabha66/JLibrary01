import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;




public class XMLDiffSrcView extends JPanel{

    public XMLDiffSrcView(JTextPane diffPane)
    {
      textPane = diffPane;
      setBackground(Color.white);
      getScrollPane().getViewport().add(textPane);
      diffPane.setEditable(false);
      diffPane.setEnabled(false);
      setLayout(new BorderLayout());
      add(getScrollPane(), "Center");
    }

    /**
     * Returns the viewer <code>JTextPane</code> component.
     *
     * @return The <code>JTextPane</code> object used by XMLSourceViewer
     *
     */
    public JTextPane getTextPane()
    {
      return textPane;
    }

    public JScrollPane getScrollPane()
    {
        if(scrollPane == null)
            scrollPane = new JScrollPane(
                 ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    protected void processMouseEvent(MouseEvent e)
    {}

    private JTextPane textPane;
    private JScrollPane scrollPane;

}
