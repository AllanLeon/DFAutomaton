/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import dfautomaton.model.basics.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Allan Leon
 */
public class Transition {
    
    private State initialState;
    private Set<Character> symbols;
    private State nextState;
    
    public Transition(State initialState, State nextState) {
        this.initialState = initialState;
        this.symbols = new HashSet<>();
        this.nextState = nextState;
    }

    public Transition(State initialState, char symbol, State nextState) {
        this.initialState = initialState;
        this.symbols = new HashSet<>();
        this.nextState = nextState;
        symbols.add(symbol);
    }

    /**
     * @return the initialState
     */
    public State getInitialState() {
        return initialState;
    }

    /**
     * @return the symbols
     */
    public Set<Character> getSymbols() {
        return symbols;
    }
    
    public void addSymbol(Character symbol) {
        symbols.add(symbol);
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
     * @param symbols the symbols to set
     */
    public void setSymbols(Set<Character> symbols) {
        this.symbols = symbols;
    }

    /**
     * @param nextState the nextState to set
     */
    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
    
    public List<Configuration> execute(Configuration current) throws TransitionException {
        List<Configuration> nextConfigurations = new ArrayList<>();
        if (current.getState().equals(initialState)) {    
            for (Character symbol : symbols) {
                if (!current.getWord().equals("")) {
                    if (symbol == '\u03B5') {
                        nextConfigurations.add(new Configuration(nextState, current.getWord()));
                    } else if (current.getWord().charAt(0) == symbol) {
                        nextConfigurations.add(new Configuration(nextState, current.getWord().substring(1)));
                    }
                }
            }
        }
        return nextConfigurations;
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
