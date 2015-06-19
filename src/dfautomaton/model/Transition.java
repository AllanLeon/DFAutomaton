/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

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
        if (current.getState().equals(initialState) && current.getWord().charAt(0) == symbol) {
            current.setState(nextState);
            current.cutWord();
            return current;
        }
        throw new TransitionException("Transition not found");
    }
}
