import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DBViewSample {
  public DBViewSample() {
    DBViewFrame frame = new DBViewFrame();
    frame.setVisible(true);
  }
  public static void main(String[] args) {
    try  {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    new DBViewSample();
  }
}

