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
public class Configuration {
    
    private State state;
    private String word;

    public Configuration(State state, String word) {
        this.state = state;
        this.word = word;
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
    
    public void cutWord() {
        word = word.substring(1);
    }
    
    public boolean isEmpty() {
        if (word.length() == 0) {
            return true;
        }
        return false;
    }
}
 