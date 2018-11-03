package edu.gatech.seclass.sdpscramble;

import org.junit.Test;

import edu.gatech.seclass.sdpscramble.design.MainActivity;

import static org.junit.Assert.*;

//TODO must add a csv of tests that can be automatically populated
public class Test_Scramble_Class {

    Scramble testvar = new Scramble("Scramble1", "test", "estt", "Test string");;

    @Test
    public void check_constructor_and_getters(){
//        ScrambleGame sg = ScrambleGame.getInstance();
        assertEquals("Scramble1", testvar.getID());
        assertEquals("Test string", testvar.getClue());
        assertEquals("", testvar.getLastAttempt());
        assertEquals("estt", testvar.getScrambledPhrase());
    }

    @Test
    public void check_solution(){
        assertTrue(testvar.checkSolution("test"));
    }

    @Test
    public void check_incorrect_solution(){
        assertFalse(testvar.checkSolution("WRONG"));
    }

    @Test
    public void check_last_attempt(){
        testvar.setLastAttempt("try");
        assertEquals("try", testvar.getLastAttempt());
    }

    @Test
    public void check_solving(){
        //Checks if it is initially not solves
        assertFalse(testvar.isSolved());
        //Sets the scramble to solved
        testvar.setSolved(true);
        assertTrue(testvar.isSolved());
    }
}
