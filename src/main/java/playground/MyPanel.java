package main.java.playground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MyPanel extends JPanel {
  private final ResizedImage resizedImage;
  private final ResizedImage resizedImage2;
  private final ImageBoundary rightImageBoundary;
  private ImageBoundary leftImageBoundary;
  private BattlefieldsMouseMotionListener motionListener;

  public MyPanel() throws IOException {
    BufferedImage image = getLeftImage();
    resizedImage = new ResizedImage(image, getBounds());
    leftImageBoundary = new ImageBoundary(resizedImage);

    BufferedImage image2 = getRightImage();
    resizedImage2 = new ResizedImage(image2, getBounds());
    rightImageBoundary = new ImageBoundary(resizedImage2);

    motionListener = new BattlefieldsMouseMotionListener(leftImageBoundary, rightImageBoundary, this);
    addMouseMotionListener(motionListener);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    resizedImage.updateBoundary(new Rectangle(x, y, Math.round(width / 2), height));
    resizedImage2.updateBoundary(new Rectangle((x + Math.round(width / 2)), y, Math.round(width / 2), height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    resizedImage.draw((Graphics2D) g);
    resizedImage2.draw((Graphics2D) g);
    highlightImageWhenMouseHovers((Graphics2D) g);
  }

  private void highlightImageWhenMouseHovers(Graphics2D g) {
    if (motionListener.isMouseWithinLeftImageBoundaries()) {
      leftImageBoundary.draw(g);
    } else if (motionListener.isMouseWithinRightImageBoundaries()) {
      rightImageBoundary.draw(g);
    }
  }

  private BufferedImage getLeftImage() throws IOException {
    try (InputStream input = getClass().getResourceAsStream("/main/resources/images/MyrtosBeach.JPG")) {
      BufferedImage leftImage = ImageIO.read(input);
      return leftImage;
    }
  }

  public BufferedImage getRightImage() throws IOException {
    try (InputStream input = getClass().getResourceAsStream("/main/resources/images/Mykonos-Town.jpg")) {
      BufferedImage rightImage = ImageIO.read(input);
      return rightImage;
    }
  }
}
