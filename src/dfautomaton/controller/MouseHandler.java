package dfautomaton.controller;

import dfautomaton.view.MainFrame;
import dfautomaton.view.TransitionSymbolDialog;
import dfautomaton.view.MainFrame.DrawingState;
import dfautomaton.view.MainFrame.ModState;
import dfautomaton.data.Constants;
import dfautomaton.model.State;
import dfautomaton.model.Transition;
import dfautomaton.model.basics.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private State selectedState;
    private State startState;
    private State endState;
    private Iterator iter;
    private boolean found;
    private JFrame parent;

    public MouseHandler(JFrame parent) {
        this.parent = parent;
        selectedState = null;
        startState = null;
        endState = null;
    }
    
    public void reset() {
        selectedState = null;
        startState = null;
        endState = null;
    }

    private Point getClickedPoint(MouseEvent e) {
        int x = e.getX();
        int y = Constants.PANEL_HEIGHT - e.getY();
        return new Point(x, y);
    }

    private void handleStateCreation(Point clickedPoint) {
        MainFrame.getAutomaton().addState(new State(String.format("q%d", MainFrame.getAutomaton().getCreatedStatesQuantity()), false, clickedPoint));
        MainFrame.drawingState = DrawingState.Drawing;
    }
    
    private void handleTransitionCreation(Point clickedPoint) {
        handleStateSelection(clickedPoint);
        if (selectedState != null) {
            if (startState == null) {
                startState = selectedState;
            } else if (endState == null) {
                endState = selectedState;
            }
        }
        if (endState != null && startState != null) {
            int i = 0;
            Transition newTransition = null;
            while (i < MainFrame.getAutomaton().getTransitions().size() && newTransition == null) {
                if (MainFrame.getAutomaton().getTransitions().get(i).getInitialState() == startState &&
                    MainFrame.getAutomaton().getTransitions().get(i).getNextState() == endState) {
                    newTransition = MainFrame.getAutomaton().getTransitions().get(i);
                }
                i++;
            }
            if (newTransition == null) {
                newTransition = new Transition(startState, endState);
                MainFrame.getAutomaton().addTransition(newTransition);
            }
            new TransitionSymbolDialog(parent, newTransition);
            reset();
            MainFrame.drawingState = DrawingState.Drawing;
        }
    }
    
    private void handleTransitionDeletion(Point clickedPoint) {
        iter = MainFrame.getAutomaton().getTransitions().iterator();
        found = false;
        Transition current;
        while (iter.hasNext() && !found) {
            current = (Transition) iter.next();
            if (current.checkPointCollision(clickedPoint)) {
                MainFrame.getAutomaton().getTransitions().remove(current);
                MainFrame.drawingState = DrawingState.Drawing;
                found = true;
            }
        }
    }
    
    private void handleStateDeletion(Point clickedPoint) {
        iter = MainFrame.getAutomaton().getStates().iterator();
        found = false;
        State current = null;
        Transition currentTransition;
        int i = 0;
        while (iter.hasNext() && !found) {
            current = (State) iter.next();
            if (current.checkPointCollision(clickedPoint)) {
                MainFrame.getAutomaton().getStates().remove(current);
                MainFrame.drawingState = DrawingState.Drawing;
                found = true;
            }
        }
        if (found) {
            if (MainFrame.getAutomaton().getInitialState() == current) {
                MainFrame.getAutomaton().setInitialState(null);
            }
            while (i < MainFrame.getAutomaton().getTransitions().size()) {
                currentTransition = MainFrame.getAutomaton().getTransitions().get(i);
                if (currentTransition.getInitialState() == current || currentTransition.getNextState() == current) {
                    MainFrame.getAutomaton().getTransitions().remove(i);
                } else {
                    i++;
                }
            }
        }
    }
    
    private void handleStateSelection(Point clickedPoint) {
        selectedState = null;
        for (State current : MainFrame.getAutomaton().getStates()) {
            if (current.checkPointCollision(clickedPoint)) {
                selectedState = current;
            }
        }
    }

    private void handleStateMovement(Point draggedPoint) {
        if (selectedState != null) {
            selectedState.getPos().setX(draggedPoint.getX());
            selectedState.getPos().setY(draggedPoint.getY());
            MainFrame.drawingState = DrawingState.Drawing;
        }
    }
    
    private void handleStateAccepted(Point clickedPoint) {
        for (State current : MainFrame.getAutomaton().getStates()) {
            if (current.checkPointCollision(clickedPoint)) {
                current.updateAccepted();
                MainFrame.drawingState = DrawingState.Drawing;
            }
        }
    }
    
    private void handleStateInitial(Point clickedPoint) {
        for (State current : MainFrame.getAutomaton().getStates()) {
            if (current.checkPointCollision(clickedPoint)) {
                MainFrame.getAutomaton().setInitialState(current);
                MainFrame.drawingState = DrawingState.Drawing;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clickedPoint = getClickedPoint(e);
        if (MainFrame.modState == ModState.Creating) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                handleStateCreation(clickedPoint);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                handleStateDeletion(clickedPoint);
            }
        } else if (MainFrame.modState == ModState.Editing) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                handleStateInitial(clickedPoint);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                handleStateAccepted(clickedPoint);
            }
        } else if (MainFrame.modState == ModState.Transition) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                handleTransitionCreation(clickedPoint);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                handleTransitionDeletion(clickedPoint);
            }
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
        if (MainFrame.modState == ModState.Creating) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                Point draggedPoint = getClickedPoint(e);
                handleStateMovement(draggedPoint);
            }
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
}
