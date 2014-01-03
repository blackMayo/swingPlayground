package main.java.playground;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

class BattlefieldsMouseMotionListener extends MouseInputAdapter {
  private boolean mouseLiesWithinImageBoundaries;
  private final ImageBoundary imageBoundary;
  private MyPanel myPanel;

  public BattlefieldsMouseMotionListener(ImageBoundary imageBoundary, MyPanel myPanel) {
    this.imageBoundary = imageBoundary;
    this.myPanel = myPanel;
  }

  public boolean isMouseWithinImageBoundaries() {
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
