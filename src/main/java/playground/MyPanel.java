package main.java.playground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* Copyright (c) GMX GmbH, Muenchen, 2014. All rights reserved.
* <p/>
*
* @author Ioanna Vletsou ioanna.vletsou@1und1.de
*/
public class MyPanel extends JPanel {
  private final ResizedImage resizedImage;
  private ImageBoundary imageBoundary;
  private JFrame parentFrame;

  public MyPanel(JFrame frame) throws IOException {
    this.parentFrame = frame;
    BufferedImage image = getLeftImage();
    resizedImage = new ResizedImage(parentFrame, image, getBounds());
    imageBoundary = new ImageBoundary(parentFrame, resizedImage);
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

  private boolean isMousePositionInsideOfImageBounds(MouseEvent mouseEvent) {
    return isMousePositionWithinRangeOfXAxes(mouseEvent.getX()) &&
        isMousePositionWithinRangeOfYAxes(mouseEvent.getY());
  }

  private boolean isMousePositionWithinRangeOfYAxes(int mousePositionOnYAxis) {
    return (mousePositionOnYAxis >= imageBoundary.getY()) &&
        (mousePositionOnYAxis <= (imageBoundary.getY() + imageBoundary.getHeight()));
  }

  private boolean isMousePositionWithinRangeOfXAxes(int mousePositionOnXAxis) {
    return (mousePositionOnXAxis >= imageBoundary.getX()) &&
        (mousePositionOnXAxis <= (imageBoundary.getX() + imageBoundary.getWidth()));
  }

}
