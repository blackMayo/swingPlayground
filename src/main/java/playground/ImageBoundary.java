package main.java.playground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JFrame;

class ImageBoundary {

  private final ResizedImage resizedImage;

  public ImageBoundary(ResizedImage resizedImage) {
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

  /*
  Getters for some properties of the image boundary rectangle
   */
  public int getX() {
    return getRectangleAroundImage().x;
  }

  public int getY() {
    return getRectangleAroundImage().y;
  }

  public double getHeight() {
    return getRectangleAroundImage().getHeight();
  }

  public double getWidth() {
    return getRectangleAroundImage().getWidth();
  }
}
