package main.java.playground;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Swing_Playground {

  public static void main(String[] args) throws IOException {
    final JFrame frame = new JFrame("*** Battleships Playground ***");
    MyPanel contentPane = new MyPanel();

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setContentPane(contentPane);
    frame.setSize(900, 700);

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        frame.setVisible(true);
      }
    });
  }
}