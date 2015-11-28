/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import dfautomaton.data.Constants;
import dfautomaton.model.basics.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Allan Leon
 */
public class Transition {
    
    private State initialState;
    private Set<TransitionInfo> options;
    private State nextState;
    private Point startPos;
    private Point endPos;
    
    public Transition(State initialState, State nextState) {
        this.initialState = initialState;
        this.nextState = nextState;
        this.options = new HashSet<>();
        calculatePos();
    }

    /**
     * @return the initialState
     */
    public State getInitialState() {
        return initialState;
    }
    
    public Set<TransitionInfo> getOptions() {
        return options;
    }
    
    public void addOption(TransitionInfo option) {
        options.add(option);
    }

    /**
     * @return the nextState
     */
    public State getNextState() {
        return nextState;
    }

    /**
     * @param initialState the initialState to set
     */
    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }
    
    public void setOptions(Set<TransitionInfo> options) {
        this.options = options;
    }

    /**
     * @param nextState the nextState to set
     */
    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
    
    public List<ConfigurationStack> executeWithStack(ConfigurationStack current) throws TransitionException {
        List<ConfigurationStack> nextConfigurations = new ArrayList<>();
        
        if (current.getState().equals(initialState)) {
            for (TransitionInfo info : options) {
                if (current.matches(info.getTop())) {
                    if (info.getSymbol() == Constants.EPSILON) {
                        nextConfigurations.add(new ConfigurationStack(nextState,
                                current.getWord(), current.updateStack(info.getNextTop())));
                    } else if (!current.wordIsEmpty()) {
                        if (current.getWord().charAt(0) == info.getSymbol()) {
                        nextConfigurations.add(new ConfigurationStack(nextState,
                                current.getWord().substring(1), current.updateStack(info.getNextTop())));
                        }
                    }
                }
            }
        }
        return nextConfigurations;
    }
    
    public static Point getCircleLineIntersectionPoint(Point pointA, Point pointB,
            Point center, boolean start) {
        double baX = pointB.getX() - pointA.getX();
        double baY = pointB.getY() - pointA.getY();
        double caX = center.getX() - pointA.getX();
        double caY = center.getY() - pointA.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - Constants.STATE_RADIUS * Constants.STATE_RADIUS;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        
        if (start) {
            return new Point((int) (pointA.getX() - baX * abScalingFactor2),
                            (int) (pointA.getY() - baY * abScalingFactor2));
        }

        return new Point((int) (pointA.getX() - baX * abScalingFactor1), (int) (pointA.getY()
                - baY * abScalingFactor1));
    }
    
    public boolean checkPointCollision(Point point) {
        if (Math.abs(point.getDistanceTo(initialState.getPos()) +
                point.getDistanceTo(nextState.getPos()) -
                initialState.getPos().getDistanceTo(nextState.getPos())) < 0.5) {
            return true;
        }
        return false;
    }
    
    public String getTransitionText() {
        if (options.isEmpty()) {
            return "";
        } else {
            String transitionText = "";
            for (TransitionInfo info : options) {
                transitionText += info.toString() + ";";
            }
            transitionText = transitionText.substring(0, transitionText.length() - 1);
            return transitionText;
        }
    }
    
    /**
     * @return the startPos
     */
    public Point getStartPos() {
        return startPos;
    }

    /**
     * @return the endPos
     */
    public Point getEndPos() {
        return endPos;
    }
    
    public void calculatePos() {
        startPos = getCircleLineIntersectionPoint(initialState.getPos(), nextState.getPos(), initialState.getPos(), true);
        endPos = getCircleLineIntersectionPoint(initialState.getPos(), nextState.getPos(), nextState.getPos(), false);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.initialState);
        hash = 79 * hash + Objects.hashCode(this.nextState);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transition other = (Transition) obj;
        if (!Objects.equals(this.initialState, other.initialState)) {
            return false;
        }
        if (!Objects.equals(this.nextState, other.nextState)) {
            return false;
        }
        return true;
    }
}
