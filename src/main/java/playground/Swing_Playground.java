package main.java.playground;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * JavaDoc
 * <p/>
 * User: ioanna
 * Date: 2013/11/22  23:59
 */
public class Swing_Playground {

  public static void main(String[] args) throws IOException {
    final JFrame frame = new JFrame("*** Battleships Playground ***");
    MyPanel contentPane = new MyPanel(frame);

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

  private static class MyPanel extends JPanel {
    private BufferedImage leftImage;
    private BufferedImage rightImage;
    private JFrame parentFrame;

    private MyPanel(JFrame frame) throws IOException {
      parentFrame = frame;
      getLeftImage();
      getRightImage();
    }

    private void getLeftImage() throws IOException {
      InputStream input =
          getClass().getResourceAsStream("/main/resources/images/MyrtosBeach.JPG");
      leftImage = ImageIO.read(input);
    }


    public void getRightImage() throws IOException {
      InputStream input =
          getClass().getResourceAsStream("/main/resources/images/Mykonos-Town.jpg");
      rightImage = ImageIO.read(input);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2 = (Graphics2D) g.create();

      RectangleAroundImage rectangleAroundImage = new RectangleAroundImage(getWidth(), getHeight());

      g2.drawImage(leftImage, rectangleAroundImage.getxCoordinate(), rectangleAroundImage.getyCoordinate(),
          rectangleAroundImage.getRectangleWidth(), rectangleAroundImage.getRectangleHeight(), null);

      Graphics2D gRect = (Graphics2D) g.create();
      gRect.setColor(Color.CYAN);
      gRect.draw(rectangleAroundImage);
    }

    private class RectangleAroundImage extends Rectangle {
      private final static double FACTOR = 1.0;
      private final static double DISTANCE_TO_OUTER_BORDER_FACTOR = 0.1;

      private final int parentComponentWidth;
      private final int parentComponentHeight;

      private double width;
      private double height;

      private double rectangleRatio;
      private double targetSizeRatio;

      private int xCoordinate;
      private int yCoordinate;

      private RectangleAroundImage(int parentComponentWidth, int parentComponentHeight) {
        rectangleRatio = leftImage.getWidth() * FACTOR / leftImage.getHeight();
        this.parentComponentWidth = parentComponentWidth;
        this.parentComponentHeight = parentComponentHeight;
        targetSizeRatio = this.parentComponentWidth * FACTOR / this.parentComponentHeight;
        calculateRectangleSize();
        calculateCoordinateLocation();
      }

      private void calculateRectangleSize() {
        if (rectangleRatio >= targetSizeRatio) {
          width =leftImage.getWidth() - (2 * DISTANCE_TO_OUTER_BORDER_FACTOR * leftImage.getWidth());
          height = width / rectangleRatio;
        } else {
          height = leftImage.getHeight() - (2 * DISTANCE_TO_OUTER_BORDER_FACTOR * leftImage.getHeight());
          width = height * rectangleRatio;
        }
      }

      private void calculateCoordinateLocation() {
        xCoordinate = (parentComponentWidth - leftImage.getWidth()) / 2;
        yCoordinate = (parentComponentHeight - leftImage.getWidth()) / 2;
      }

      @Override
      public void setBounds(int x, int y, int width, int height) {
        System.out.println("Rectangle setBounds");
        super.setBounds(getxCoordinate(), getyCoordinate(), getRectangleWidth(), getRectangleHeight());
      }

      @Override
      public void setLocation(int x, int y) {
        System.out.println("Rectangle setLocation");
        super.setLocation(getxCoordinate(), getyCoordinate());
      }

      @Override
      public void setSize(int width, int height) {
        System.out.println("Rectangle setSize");
        super.setSize(getRectangleWidth(), getRectangleHeight());
      }

      private int getRectangleHeight() {
        return (int) Math.round(getHeight());
      }

      private int getRectangleWidth() {
        return (int) Math.round(getWidth());
      }

      private int getxCoordinate() {
        return xCoordinate;
      }

      private int getyCoordinate() {
        return yCoordinate;
      }

      public double getHeight() {
        return height;
      }

      public double getWidth() {
        return width;
      }
    }
  }
}