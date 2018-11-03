package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ScrambleGameTest {
    Context appContext = InstrumentationRegistry.getTargetContext();
    ScrambleGame sg = ScrambleGame.getInstance(appContext);

    @Test
    public void getInstance() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertNotNull(sg);
    }

    @Test
    public void getCurrentPlayer() throws Exception {
        String result = sg.createUser("Test","Test","Test", "Test");
        sg.verifyLogIn(result);
        assertEquals(result, sg.getCurrentPlayer().getUserName());

    }

    @Test
    public void getCurrentScramble() throws Exception {
        if (sg.getCurrentScramble() != null)
        {
            sg.exitScramble("temp");
        }
        assertNull(sg.getCurrentScramble());
        String ID = sg.createScramble("Test", "Test", "Test");
        sg.openScramble(ID);
        assertEquals("Test", sg.getCurrentScramble().getScrambledPhrase());
    }

    @Test
    public void checkLastAttempt() throws Exception {
        if (sg.getCurrentScramble() != null)
        {
            sg.exitScramble("temp");
        }
        assertNull(sg.getCurrentScramble());
        String result = sg.createUser("Test","Test","Test", "Test");
        String ID = sg.createScramble("Test", "Test", "Test");
        sg.openScramble(ID);
        sg.exitScramble("last-attempt");
        sg.openScramble(ID);
        assertEquals("last-attempt", sg.getCurrentScramble().getLastAttempt());

    }

    @Test
    public void createUser() throws Exception {
        String result = sg.createUser("Test","Test","Test", "Test");
        assertNotNull(result);
    }

    @Test
    public void verifyLogIn() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        sg.logOut();
        assertTrue(sg.verifyLogIn(username));
    }

    @Test
    public void logOut() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        sg.logOut();
        assertNull(sg.getCurrentPlayer());
    }

    @Test
    public void createScramble() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        String ID = sg.createScramble("Test", "Test", "Test");
        assertNotEquals("", ID);
    }

    @Test
    public void openScramble() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        String ID = sg.createScramble("Test", "Test", "Test");
        sg.openScramble(ID);
        assertNotEquals("", sg.getCurrentScramble().getID());
        assertEquals(ID, sg.getCurrentScramble().getID());
        assertEquals("Test", sg.getCurrentScramble().getScrambledPhrase());
        assertEquals("Test", sg.getCurrentScramble().getClue());
    }

    @Test
    public void solveScramble_correct() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        String ID = sg.createScramble("Test", "Test", "Test");
        sg.openScramble(ID);
        assertTrue(sg.solveScramble("Test"));
    }

    @Test
    public void solveScramble_incorrect() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        String ID = sg.createScramble("Test", "Test", "Test");
        sg.openScramble(ID);
        assertFalse(sg.solveScramble("WRONG!"));
    }

    @Test
    public void exitScramble() throws Exception {
        String username = sg.createUser("wuping", "wuping", "wuping","w@a");
        String ID = sg.createScramble("Test", "Test", "Test");
        sg.openScramble(ID);
        assertNotNull(sg.getCurrentScramble());
        sg.exitScramble("temp");
        assertNull(sg.getCurrentScramble());
    }

    @Test
    public void getUnsolvedScrambles() throws Exception {
        assertNotNull(sg.getUnsolvedScrambles());
    }

    @Test
    public void getScrambleStats() throws Exception {
        assertNotNull(sg.getScrambleStats());
    }

    @Test
    public void getPlayerStats() throws Exception {
        assertNotNull(sg.getPlayerStats());
    }

    @Test
    public void savePersistent() throws Exception {
        //TODO context error null reference
        Context appContext = InstrumentationRegistry.getTargetContext();
        sg.savePersistent();
    }

}