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
    private List<List<Configuration>> configurations;
    //private String currentWord;
    private State initialState;
    private int currentIteration;
    
    public Automaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        acceptedStates = new HashSet<>();
        transitions = new ArrayList<>();
        initialState = new State("", false);
        configurations = new ArrayList<>();
        currentIteration = 0;
        //currentWord = "";
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

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }
    
    public int getCurrentIteration() {
        return currentIteration;
    }
    
    public void setCurrentIteration(int next) {
        currentIteration = next;
    }
    
    public void addState(State state) {
        states.add(state);
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
        if (states.contains(transition.getInitialState()) &&
            states.contains(transition.getNextState()) &&
            alphabet.contains(transition.getSymbol())) {
            transitions.add(transition);
        }
    }
    
    public void removeState(State state) {
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
    
    public void read(String word) {
        start(word);
        while (next()) {
            for (Configuration current : configurations.get(currentIteration - 1)) {
                System.out.printf("%s %s %b %b\n", current.getState().getName(),
                        current.getWord(), current.isDead(), current.isValid());
            }
        }
    }
    
    public void start(String word) {
        configurations.clear();
        currentIteration = 0;
        List<Configuration> startList = new ArrayList<>();
        startList.add(new Configuration(initialState, word));
        configurations.add(startList);
    }
    
    public boolean next() {
        List<Configuration> nextList = new ArrayList<>();
        boolean ok;
        for (Configuration current : configurations.get(currentIteration)) {
            if (!current.isDead()) {
                ok = false;
                for (Transition transition : transitions) {
                    try {
                        nextList.add(transition.execute(current));
                        ok = true;
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
        configurations.add(nextList);
        currentIteration++;
        return nextList.size() > 0;
    }
}
