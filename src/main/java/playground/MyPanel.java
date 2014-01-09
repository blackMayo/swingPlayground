package main.java.playground;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MyPanel extends JPanel {
  private ResizedImage leftResizedImage;
  private ResizedImage rightResizedImage;
  private ImageBoundary rightImageBoundary;
  private ImageBoundary leftImageBoundary;
  private BattlefieldsMouseMotionListener motionListener;

  public MyPanel() throws IOException {
    addImagesToPanel();
    motionListener = new BattlefieldsMouseMotionListener(leftImageBoundary, rightImageBoundary, this);
    addMouseMotionListener(motionListener);
  }

  @Override
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    leftResizedImage.updateBoundary(new Rectangle(x, y, Math.round(width / 2), height));
    rightResizedImage.updateBoundary(new Rectangle((x + Math.round(width / 2)), y, Math.round(width / 2), height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    leftResizedImage.draw((Graphics2D) g);
    rightResizedImage.draw((Graphics2D) g);
    highlightImageWhenMouseHovers((Graphics2D) g);
  }

  private void addImagesToPanel() throws IOException {
    addLeftImage(getLeftImage());
    addRightImage(getRightImage());
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

  private void addLeftImage(BufferedImage leftImage) throws IOException {
    leftResizedImage = new ResizedImage(leftImage, getBounds());
    leftImageBoundary = new ImageBoundary(leftResizedImage);
  }

  private void addRightImage(BufferedImage rightImage) throws IOException {
    rightResizedImage = new ResizedImage(rightImage, getBounds());
    rightImageBoundary = new ImageBoundary(rightResizedImage);
  }

  private void highlightImageWhenMouseHovers(Graphics2D g) {
    if (motionListener.isMouseWithinLeftImageBoundaries()) {
      leftImageBoundary.draw(g);
    } else if (motionListener.isMouseWithinRightImageBoundaries()) {
      rightImageBoundary.draw(g);
    }
  }
}
