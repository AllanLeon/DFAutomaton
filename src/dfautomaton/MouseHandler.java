package dfautomaton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private List<DrawableState> creatorStates;
    private DrawableState selectedState;

    public MouseHandler() {
        creatorStates = new ArrayList<>();
        selectedState = null;
    }

    public void reset() {
        creatorStates.clear();
        selectedState = null;
    }

    public List<DrawableState> getCreatorStates() {
        return creatorStates;
    }

    private DrawableState getClickedState(MouseEvent e) {
        int x = e.getX();
        int y = UI.PANEL_HEIGHT - e.getY();
        return new DrawableState(x, y, 50);
    }
/*
    private void handleStateCreation(DrawableState state) {
           creatorStates.add(state);
    }
*/
    private void handleStateSelection(DrawableState clickedState) {
        selectedState = null;
        int i = creatorStates.size();
        boolean found = false;
        while (i >= 0 && !found) {
            if (checkMouseCollision(clickedState) != -1) {
                selectedState = creatorStates.get(i - 1);
                found = true;
            }
            i--;
        }
    }

    private void handleStateMovement(DrawableState draggedState) {
        //if (selectedPoint != -1) {
        int dragX = draggedState.getPosx();
        int dragY = draggedState.getPosy();
        selectedState.setPosx(dragX);
        selectedState.setPosy(dragY);
    }

    @Override
    public void mouseClicked(MouseEvent e) { /*
        DrawableState clickedState = getClickedState(e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            handleStateCreation(clickedState);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            handleStateDeletion(clickedPoint);
        } */
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            DrawableState clickedState = getClickedState(e);
            handleStateSelection(clickedState);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            DrawableState draggedState = getClickedState(e);
            handleStateMovement(draggedState);
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

    public int checkMouseCollision(DrawableState clickedState) {
        for (int i = 0; creatorStates.size() > i; i++) {
            double dist = creatorStates.get(i).getDistanceTo(clickedState);
            if (dist <= 50) {
                return i;
            }
        }
        return -1;
    }
}
