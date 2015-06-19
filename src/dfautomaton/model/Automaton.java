/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Allan Leon
 */
public class Automaton {
    
    private Set<State> states;
    private Set<Character> alphabet;
    private Set<State> acceptedStates;
    private ArrayList<Transition> transitions;
    private Queue<Configuration> configurations;
    private String currentWord;
    private State initialState;
    
    public Automaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        acceptedStates = new HashSet<>();
        transitions = new ArrayList<>();
        initialState = new State("", false);
        configurations = new LinkedList<>();
        currentWord = "";
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

    public ArrayList<Transition> getTransitions() {
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
    
    public void addState(State state) {
        states.add(state);
    }
    
    public void addAcceptedState(State state) {
        acceptedStates.add(state);
    }
    
    public void addAlphabetSymbol(char symbol) {
        alphabet.add(symbol);
    }
    
    public void addTransition(Transition transition) {
        transitions.add(transition);
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
        
    }
}
