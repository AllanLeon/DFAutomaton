/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import dfautomaton.data.Constants;
import java.util.Objects;
import java.util.Stack;

/**
 *
 * @author Allan Leon
 */
public class ConfigurationStack {
    private State state;
    private String word;
    private Stack<Character> stack;
    private boolean dead;

    public ConfigurationStack(State state, String word, Stack<Character> stack) {
        this.state = state;
        this.word = word;
        this.stack = stack;
        this.dead = false;
    }
    
    public ConfigurationStack(State state, String word, Stack<Character> stack, boolean dead) {
        this.state = state;
        this.word = word;
        this.dead = dead;
        this.stack = stack;
    }
    
    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @return the stack
     */
    public Stack<Character> getStack() {
        return stack;
    }

    /**
     * @return the dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @param stack the stack to set
     */
    public void setStack(Stack<Character> stack) {
        this.stack = stack;
    }

    /**
     * @param dead the dead to set
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    public Stack<Character> updateStack(String top) {
        Stack<Character> aux = (Stack<Character>) stack.clone();
        if (!aux.empty()) {
            aux.pop();
            if (!top.equals(Constants.EPSILON + "")) {
                for (int i = top.length() - 1; i >= 0; i--) {
                    aux.push(top.charAt(i));
                }
            }
        }
        return aux;
    }
    
    public boolean stackIsEmpty() {
        return stack.empty();
    }
    
    public boolean wordIsEmpty() {
        return word.length() == 0;
    }
    
    public boolean isValid() {
        return wordIsEmpty() && state.isAccepted();
    }
    
    public boolean matches(Character top) {
        return stack.lastElement().equals(top);
    }
    
    private String stackToString() {
        String res = "";
        for (int i = stack.size() - 1; i >= 0; i--) {
            res += stack.get(i);
        }
        return res;
    }
    
    @Override
    public String toString() {
        String word = this.word;
        if (wordIsEmpty()) {
            word = Constants.EPSILON + "";
        }
        return String.format("%s, %s, %s", word, state.getName(), stackToString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.state);
        hash = 37 * hash + Objects.hashCode(this.word);
        hash = 37 * hash + Objects.hashCode(this.stack);
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
        final ConfigurationStack other = (ConfigurationStack) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.word, other.word)) {
            return false;
        }
        if (!Objects.equals(this.stack, other.stack)) {
            return false;
        }
        return true;
    }
}
