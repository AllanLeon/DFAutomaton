/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Allan Leon
 */
public class Automaton {
    
    private Set<State> states;
    private Set<Character> alphabet;
    private Set<State> acceptedStates;
    private List<Transition> transitions;
    private List<Set<Configuration>> configurations;
    private State initialState;
    private int createdStatesQuantity;
    
    public Automaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        acceptedStates = new HashSet<>();
        transitions = new ArrayList<>();
        initialState = null;
        configurations = new ArrayList<>();
        createdStatesQuantity = 0;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<Character> alphabet) {
        this.alphabet = alphabet;
    }

    public Set<State> getAcceptedStates() {
        return acceptedStates;
    }

    public void setAcceptedStates(Set<State> acceptedStates) {
        this.acceptedStates = acceptedStates;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
    
    public List<Set<Configuration>> getConfigurations() {
        return configurations;
    }
    
    public int getCreatedStatesQuantity() {
        return createdStatesQuantity;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }
    
    public void addState(State state) {
        states.add(state);
        createdStatesQuantity++;
    }
    
    public void addAcceptedState(State state) {
        if (states.contains(state)) {
            acceptedStates.add(state);
        }
    }
    
    public void addAlphabetSymbol(char symbol) {
        alphabet.add(symbol);
    }
    
    public void addTransition(Transition transition) {
        transitions.add(transition);
    }
    
    public void removeState(State state) {
        if (state == initialState) {
            initialState = null;
        }
        states.remove(state);
    }
    
    public void removeAcceptedState(State state) {
        acceptedStates.remove(state);
    }
    
    public void removeAlphabetSymbol(char symbol) {
        alphabet.remove(symbol);
    }
    
    public void removeTransition(Transition transition) {
        transitions.remove(transition);
    }
    
    public void start(String word) {
        configurations.clear();
        Set<Configuration> startList = new HashSet<>();
        startList.add(new Configuration(initialState, word));
        configurations.add(startList);
    }
    
    public boolean next() {
        Set<Configuration> nextList = new HashSet<>();
        boolean ok;
        for (Configuration current : configurations.get(0)) {
            if (!current.isDead() && !current.isValid()) {
                ok = false;
                for (Transition transition : transitions) {
                    try {
                        for (Configuration newConf : transition.execute(current)) {
                            nextList.add(newConf);
                            ok = true;
                        }
                    } catch (TransitionException ex) {
                        Logger.getLogger(Automaton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!ok) {
                    current.setDead(true);
                    nextList.add(current);
                }
            }
        }
        if (nextList.size() > 0) {
            configurations.add(nextList);
            configurations.remove(0);
            return true;
        }
        return false;
    }
    
    private boolean checkAcceptedStates() {
        for (State state : states) {
            if (state.isAccepted()) {
                return true;
            }
        }
        return false;
    }
    
    public void validate() throws AutomatonException {
        if (initialState == null) {
            throw new AutomatonException("No existe estado inicial");
        } else if (!checkAcceptedStates()) {
            throw new AutomatonException("Debe existir por lo menos un estado aceptado");
        }
    }
}
