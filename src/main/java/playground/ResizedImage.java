package main.java.playground;

import java.awt.*;
import java.awt.image.BufferedImage;

class ResizedImage {
  public static final double PADDING = 0.1;
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

  private void drawResizedImage(Graphics2D g2) {
    g2.drawImage(image, resizedBoundary.x, resizedBoundary.y, resizedBoundary.width, resizedBoundary.height, null);
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

  private double getImageRatio() {
    return (double) image.getWidth() / image.getHeight();
  }

  private double getTargetRatio() {
    return (double) boundary.width / boundary.height;
  }

  private void calculateDimensionsForImageRatioSmallerThatTargetRatio() {
    newHeight = (int) Math.round(boundary.height - (2 * PADDING * boundary.height));
    newWidth = newHeight * getImageRatio();
  }

  private void calculateDimensionsForImageRatioBiggerThatTargetRatio() {
    newWidth = (int) Math.round(boundary.width - (2 * PADDING * boundary.width));
    newHeight = newWidth / getImageRatio();
  }

  public Rectangle getResizedBoundary() {
    return new Rectangle(calculateXCoordinate(), calculateYCoordinate(), (int) Math.round(newWidth),
            (int) Math.round(newHeight));
  }

  private int calculateXCoordinate() {
    return (int) Math.round(boundary.x + (boundary.width - newWidth) / 2);
  }

  private int calculateYCoordinate() {
    return (int) Math.round(boundary.y + (boundary.height - newHeight) / 2);
  }
}
