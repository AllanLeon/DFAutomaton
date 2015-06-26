/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dfautomaton.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Allan Leon
 */
public class AutomatonTest {
    
    Automaton automaton;
    
    public AutomatonTest() {
        automaton = new Automaton();
        automaton.addAlphabetSymbol('a');
        automaton.addAlphabetSymbol('b');
        State q1 = new State("q1", false);
        State q2 = new State("q2", false);
        State q3 = new State("q3", true);
        automaton.addState(q1);
        automaton.addState(q2);
        automaton.addState(q3);
        automaton.setInitialState(q1);
        automaton.addTransition(new Transition(q1, 'a', q2));
        automaton.addTransition(new Transition(q2, 'b', q3));
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of read method, of class Automaton.
     */
    @Test
    public void testRead() {
        //automaton.read("ab");
        //System.out.println("---------------------");
        //automaton.read("aa");
    }
}
