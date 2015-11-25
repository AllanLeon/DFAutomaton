/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import java.util.Objects;

/**
 *
 * @author Allan Leon
 */
public class TransitionInfo {
    
    private Character symbol;
    private Character top;
    private String nextTop;

    public TransitionInfo(Character symbol, Character top, String nextTop) {
        this.symbol = symbol;
        this.top = top;
        this.nextTop = nextTop;
    }

    /**
     * @return the symbol
     */
    public Character getSymbol() {
        return symbol;
    }

    /**
     * @return the top
     */
    public Character getTop() {
        return top;
    }

    /**
     * @return the nextTop
     */
    public String getNextTop() {
        return nextTop;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    /**
     * @param top the top to set
     */
    public void setTop(Character top) {
        this.top = top;
    }

    /**
     * @param nextTop the nextTop to set
     */
    public void setNextTop(String nextTop) {
        this.nextTop = nextTop;
    }
    
    @Override
    public String toString() {
        return symbol + "," + top + "|" + nextTop;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.symbol);
        hash = 97 * hash + Objects.hashCode(this.top);
        hash = 97 * hash + Objects.hashCode(this.nextTop);
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
        final TransitionInfo other = (TransitionInfo) obj;
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        if (!Objects.equals(this.top, other.top)) {
            return false;
        }
        if (!Objects.equals(this.nextTop, other.nextTop)) {
            return false;
        }
        return true;
    }
}
