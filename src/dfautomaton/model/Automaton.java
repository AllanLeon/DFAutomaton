package dfautomaton.model;

import dfautomaton.data.Constants;
import dfautomaton.model.basics.Point;
import dfautomaton.view.MainFrame;
import dfautomaton.view.MainFrame.DrawingState;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
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
    private List<Set<ConfigurationStack>> configurations;
    private List<State> reachableStates;
    private State initialState;
    private int createdStatesQuantity;
    
    public Automaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        acceptedStates = new HashSet<>();
        reachableStates = new ArrayList<>();
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
    
    public List<Set<ConfigurationStack>> getConfigurations() {
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
    
    public void createState(Point clickedPoint) {
        addState(new State(String.format("q%d", createdStatesQuantity), false, clickedPoint));
    }
    
    public Transition createTransition(State initial, State next) {
        Transition newTransition = new Transition(initial, next);
        int i = 0;
        boolean found = false;
        while (i < transitions.size() && !found) {
            if (transitions.get(i).equals(newTransition)) {
                newTransition = transitions.get(i);
                found = true;
            }
            i++;
        }
        if (!found) {
            addTransition(newTransition);
        }
        return newTransition;
    }
    
    public void addTransition(Transition transition) {
        transitions.add(transition);
    }
    
    public void removeState(State state) {
        if (state == initialState) {
            initialState = null;
        }
        states.remove(state);
        int i = 0;
        while (i < transitions.size()) {
            if (transitions.get(i).getInitialState().equals(state) || transitions.get(i).getNextState().equals(state)) {
                transitions.remove(i);
            } else {
                i++;
            }
        }
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
        Set<ConfigurationStack> startList = new HashSet<>();
        Stack<Character> startStack = new Stack<>();
        startStack.add(Constants.DZETA);
        startList.add(new ConfigurationStack(initialState, word, startStack));
        configurations.add(startList);
    }
    
    public boolean next() {
        Set<ConfigurationStack> nextList = new HashSet<>();
        boolean ok;
        for (ConfigurationStack current : configurations.get(0)) {
            if (!current.isDead() && !current.isValid()) {
                ok = false;
                for (Transition transition : transitions) {
                    try {
                        for (ConfigurationStack newConf : transition.executeWithStack(current)) {
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
            throw new AutomatonException("No initial state found.");
        } else if (!checkAcceptedStates()) {
            throw new AutomatonException("Must exist at least one accepted state.");
        }
    }
    
    public boolean checkReachableStates() {
        reachableStates.clear();
        if (states.isEmpty()) {
            return true;
        }
        reachableStates.add(initialState);
        for (int i = 0; i < reachableStates.size(); i++) {
            for (Transition transition : transitions) {
                if (transition.getInitialState().equals(reachableStates.get(i))) {
                    if (!reachableStates.contains(transition.getNextState())) {
                        reachableStates.add(transition.getNextState());
                    }
                }
            }
        }
        return reachableStates.size() == states.size();
   }
    
    public void removeUnreachableStates() {
        Set<State> unreachableStates = new HashSet<>();
        for (State current : states) {
            if (!reachableStates.contains(current)) {
                unreachableStates.add(current);
            }
        }
        for (State current : unreachableStates) {
            removeState(current);
        }
        MainFrame.drawingState = DrawingState.Drawing;
    }
    
    public void updateTransitions() {
        for (Transition current : transitions) {
            current.calculatePos();
        }
    }
}
