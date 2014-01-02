package main.java.playground;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
* Copyright (c) GMX GmbH, Muenchen, 2014. All rights reserved.
* <p/>
*
* @author Ioanna Vletsou ioanna.vletsou@1und1.de
*/
class ImageBoundary {

  private final ResizedImage resizedImage;
  private JFrame parentFrame;

  public ImageBoundary(JFrame parentFrame, ResizedImage resizedImage) {
    this.parentFrame = parentFrame;
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
