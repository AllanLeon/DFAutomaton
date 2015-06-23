/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import dfautomaton.data.Constants;
import dfautomaton.model.basics.Point;

/**
 *
 * @author Allan Leon
 */
public class Transition {
    
    private State initialState;
    private char symbol;
    private State nextState;

    public Transition(State initialState, char symbol, State nextState) {
        this.initialState = initialState;
        this.symbol = symbol;
        this.nextState = nextState;
    }

    /**
     * @return the initialState
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
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

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * @param nextState the nextState to set
     */
    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
    
    public Configuration execute(Configuration current) throws TransitionException {
        if (!current.getWord().equals("")) {
            if (current.getState().equals(initialState) && symbol == '\u03B5') {
                return new Configuration(nextState, current.getWord());
            } else if (current.getState().equals(initialState) && current.getWord().charAt(0) == symbol) {
                return new Configuration(nextState, current.getWord().substring(1));
            }
        }
        throw new TransitionException("Transition not found");
    }
    
    public boolean checkPointCollision(Point point) {
        if (Math.abs(point.getDistanceTo(initialState.getPos()) +
                point.getDistanceTo(nextState.getPos()) -
                initialState.getPos().getDistanceTo(nextState.getPos())) < 0.5) {
            return true;
        }
        return false;
    }
}
