package edu.gatech.seclass.sdpscramble;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//TODO must add a csv of tests that can be automatically populated
public class Test_Player_Class {
    Player testvar = new Player("TestUserName", "Testfname",  "TestLname", "Testemail@email.com");

    @Test
    public void test_getters_and_constructor(){
        assertEquals("TestUserName", testvar.getUserName());
        assertEquals("Testfname", testvar.getFirstName());
        assertEquals("TestLname", testvar.getLastName());
        assertEquals("Testemail@email.com", testvar.getEmail());
    }

    @Test
    public void check_scramble_solved(){
        //Makes sure the solved scrambles list is not created
        assertTrue(testvar.getScramblesSolved().isEmpty());
        //Adds an id of 1 to the list
        testvar.addScrambleSolved("1");
        //Checks that the list is not null and it contains one
        assertFalse(testvar.getScramblesSolved().isEmpty());
        assertEquals("1", testvar.getScramblesSolved().get(0));
    }
}
