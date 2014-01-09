package main.java.playground;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

class BattlefieldsMouseMotionListener extends MouseInputAdapter {
  private MyPanel myPanel;
  private final ImageBoundary leftImageBoundary;
  private final ImageBoundary rightImageBoundary;
  private boolean mouseLiesWithinLeftImageBoundaries;
  private boolean mouseLiesWithinRightImageBoundaries;

  public BattlefieldsMouseMotionListener(ImageBoundary leftImageBoundary, ImageBoundary rightImageBoundary, MyPanel myPanel) {
    this.leftImageBoundary = leftImageBoundary;
    this.rightImageBoundary = rightImageBoundary;
    this.myPanel = myPanel;
  }

  public boolean isMouseWithinLeftImageBoundaries() {
    return mouseLiesWithinLeftImageBoundaries;
  }

  public boolean isMouseWithinRightImageBoundaries() {
    return mouseLiesWithinRightImageBoundaries;
  }

  @Override
  public void mouseMoved(MouseEvent mouseEvent) {
    mouseLiesWithinLeftImageBoundaries = isMousePositionInsideOfLeftImageBounds(mouseEvent);
    mouseLiesWithinRightImageBoundaries = isMousePositionInsideOfRightImageBounds(mouseEvent);
    myPanel.repaint();
  }

  private boolean isMousePositionInsideOfRightImageBounds(MouseEvent mouseEvent) {
    return isMousePositionWithinRangeOfXAxes(mouseEvent.getX(), rightImageBoundary) &&
            isMousePositionWithinRangeOfYAxes(mouseEvent.getY(), rightImageBoundary);
  }

  private boolean isMousePositionInsideOfLeftImageBounds(MouseEvent mouseEvent) {
    return isMousePositionWithinRangeOfXAxes(mouseEvent.getX(), leftImageBoundary) &&
        isMousePositionWithinRangeOfYAxes(mouseEvent.getY(), leftImageBoundary);
  }

  private boolean isMousePositionWithinRangeOfYAxes(int mousePositionOnYAxis, ImageBoundary imageBoundary) {
    return (mousePositionOnYAxis >= imageBoundary.getY()) &&
        (mousePositionOnYAxis <= (imageBoundary.getY() + imageBoundary.getHeight()));
  }

  private boolean isMousePositionWithinRangeOfXAxes(int mousePositionOnXAxis, ImageBoundary imageBoundary) {
    return (mousePositionOnXAxis >= imageBoundary.getX()) &&
        (mousePositionOnXAxis <= (imageBoundary.getX() + imageBoundary.getWidth()));
  }
}
