package main.java.playground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class MyPanel extends JPanel {
  private final ResizedImage resizedImage;
  private ImageBoundary imageBoundary;
  private BattlefieldsMouseMotionListener motionListener;

  public MyPanel() throws IOException {
    BufferedImage image = getLeftImage();

    resizedImage = new ResizedImage(image, getBounds());
    imageBoundary = new ImageBoundary(resizedImage);

    motionListener = new BattlefieldsMouseMotionListener(imageBoundary, this);
    addMouseMotionListener(motionListener);

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
    if (motionListener.isMouseWithinImageBoundaries()) {
      imageBoundary.draw((Graphics2D) g);
    }
  }


  private static class BattlefieldsMouseMotionListener extends MouseInputAdapter {
    private boolean mouseLiesWithinImageBoundaries;
    private final ImageBoundary imageBoundary;
    private MyPanel myPanel;

    public BattlefieldsMouseMotionListener(ImageBoundary imageBoundary, MyPanel myPanel) {
      this.imageBoundary = imageBoundary;
      this.myPanel = myPanel;
    }

    private boolean isMouseWithinImageBoundaries() {
      return mouseLiesWithinImageBoundaries;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
      mouseLiesWithinImageBoundaries = isMousePositionInsideOfImageBounds(mouseEvent);
      myPanel.repaint();
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
}
