package main.java.playground;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
* Copyright (c) GMX GmbH, Muenchen, 2014. All rights reserved.
* <p/>
*
* @author Ioanna Vletsou ioanna.vletsou@1und1.de
*/
class ResizedImage {
  private double newWidth;
  private double newHeight;
  private final BufferedImage image;
  private Rectangle boundary;
  private Rectangle resizedBoundary;
  private JFrame parentFrame;

  public ResizedImage(JFrame parentFrame, BufferedImage image, Rectangle boundary) {
    this.parentFrame = parentFrame;
    this.image = image;
    this.boundary = boundary;
  }


  public void draw(Graphics2D g) {
    Graphics2D g2 = (Graphics2D) g.create();

    drawResizedImage(g2);
  }

  public void updateBoundary(Rectangle boundary) {
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
