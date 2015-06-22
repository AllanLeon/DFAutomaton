package dfautomaton;

import dfautomaton.data.Constants;
import dfautomaton.model.State;
import dfautomaton.model.basics.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private State selectedState;

    public MouseHandler() {
        selectedState = null;
    }

    private Point getClickedPoint(MouseEvent e) {
        int x = e.getX();
        int y = Constants.PANEL_HEIGHT - e.getY();
        return new Point(x, y);
    }

    private void handleStateCreation(Point clickedPoint) {
        UI.getAutomaton().addState(new State(String.format("q%d", UI.getAutomaton().getCreatedStatesQuantity()), false, clickedPoint));
        System.out.println(UI.getAutomaton().getStates().size());
    }
    
    private void handleStateDeletion(Point clickedPoint) {
        for (State current : UI.getAutomaton().getStates()) {
            if (checkPointCollision(current, clickedPoint)) {
                UI.getAutomaton().getStates().remove(current);
            }
        }
    }
    
    private void handleStateSelection(Point clickedPoint) {
        selectedState = null;
        for (State current : UI.getAutomaton().getStates()) {
            if (checkPointCollision(current, clickedPoint)) {
                selectedState = current;
            }
        }
    }

    private void handleStateMovement(Point draggedPoint) {
        if (selectedState != null) {
            selectedState.getPos().setX(draggedPoint.getX());
            selectedState.getPos().setY(draggedPoint.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clickedPoint = getClickedPoint(e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            handleStateCreation(clickedPoint);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            handleStateDeletion(clickedPoint);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point clickedPoint = getClickedPoint(e);
            handleStateSelection(clickedPoint);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point draggedPoint = getClickedPoint(e);
            handleStateMovement(draggedPoint);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    private boolean checkPointCollision(State state, Point point) {
        return Math.abs(point.getX() - state.getPos().getX()) <= Constants.STATE_RADIUS
                && Math.abs(point.getY() - state.getPos().getY()) <= Constants.STATE_RADIUS;
    }
}
