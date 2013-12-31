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

  private static class MyPanel extends JPanel {
    private final ResizedImage resizedImage;
    private ImageBoundary imageBoundary;

    private MyPanel() throws IOException {
      BufferedImage image = getLeftImage();
      resizedImage = new ResizedImage(image, getBounds());
      imageBoundary = new ImageBoundary(resizedImage);
    }

    private BufferedImage getLeftImage() throws IOException {
      try (InputStream input = getClass().getResourceAsStream("/main/resources/images/MyrtosBeach.JPG")) {
        BufferedImage leftImage = ImageIO.read(input);
        return leftImage;
      }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
      super.setBounds(x, y, width, height);
      resizedImage.updateBoundary(new Rectangle(x, y, width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      resizedImage.draw((Graphics2D) g);
      imageBoundary.draw((Graphics2D) g);
    }

    private static class ResizedImage {
      private double newWidth;
      private double newHeight;
      private final BufferedImage image;
      private Rectangle boundary;
      private Rectangle resizedBoundary;

      public ResizedImage(BufferedImage image, Rectangle boundary) {
        this.image = image;
        this.boundary = boundary;
      }


      public void draw(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();

        drawResizedImage(g2);
      }

      private void updateBoundary(Rectangle boundary) {
        this.boundary = boundary;

        calculateNewImageSize();
        resizedBoundary = getResizedBoundary();
      }


      private void calculateNewImageSize() {
        if (getImageRatio() >= getTargetRatio()) {
          calculateDimensionsForImageRatioBiggerThatTargetRatio();
        } else {
          calculateDimensionsForImageRatioSmallerThatTargetRatio();
        }
      }

      private void drawResizedImage(Graphics2D g2) {
        g2.drawImage(image, resizedBoundary.x, resizedBoundary.y, resizedBoundary.width, resizedBoundary.height, null);
      }

      private int calculateYCoordinate() {
        return (int) Math.round((boundary.height - newHeight) / 2);
      }

      private int calculateXCoordinate() {
        return (int) Math.round((boundary.width - newWidth) / 2);
      }

      private void calculateDimensionsForImageRatioSmallerThatTargetRatio() {
        newHeight = (int) Math.round(boundary.height - (2 * 0.1 * boundary.height));
        newWidth = newHeight * getImageRatio();
      }

      private void calculateDimensionsForImageRatioBiggerThatTargetRatio() {
        newWidth = (int) Math.round(boundary.width - (2 * 0.1 * boundary.width));
        newHeight = newWidth / getImageRatio();
      }

      private double getImageRatio() {
        return (double) image.getWidth() / image.getHeight();
      }

      private double getTargetRatio() {
        return (double) boundary.width / boundary.height;
      }

      public Rectangle getResizedBoundary() {
        return new Rectangle(calculateXCoordinate(), calculateYCoordinate(), (int) Math.round(newWidth),
            (int) Math.round(newHeight));
      }
    }

    private static class ImageBoundary {

      private final ResizedImage resizedImage;

      private ImageBoundary(ResizedImage resizedImage) {
        this.resizedImage = resizedImage;
      }

      public void draw(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(Color.CYAN);
        g2.draw(getRectangleAroundImage());
      }

      private Rectangle getRectangleAroundImage() {
        return resizedImage.getResizedBoundary();
      }
    }
  }
}