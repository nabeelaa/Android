package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DatabaseTest {

    private Database db;
    @Test
    public void updateScrambleProgressTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new Database(appContext, null);

        String scrambleId = "TestScramble";
        String username = "Tester";
        String scrambledPhrase = "Test";
        String testAttempt1 = "attempt1";
        String testAttempt2 = "attempt2";

        db.updateScrambleProgress(scrambleId, username, scrambledPhrase, testAttempt1);
        assertEquals(new ArrayList<>(Arrays.asList(scrambledPhrase, testAttempt1)), db.returnInProgressPhrase(scrambleId, username));
        db.updateScrambleProgress(scrambleId, username, scrambledPhrase, testAttempt2);
        assertNotEquals(new ArrayList<>(Arrays.asList(scrambledPhrase, testAttempt1)), db.returnInProgressPhrase(scrambleId, username));
        assertEquals(new ArrayList<>(Arrays.asList(scrambledPhrase, testAttempt2)), db.returnInProgressPhrase(scrambleId, username));
    }

    @Test
    public void retrieveScramblesInProgressTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new Database(appContext, null);

        String username1 = "Tester1";
        String scrambleId1 = "TestScramble1";
        String scrambleId2 = "TestScramble2";
        String scrambleId3 = "TestScramble3";
        String scrambledPhrase = "Test";
        String testAttempt = "attempt";

        assertEquals(new ArrayList<>(), db.retrieveScramblesInProgress(username1));

        db.updateScrambleProgress(scrambleId1, username1, scrambledPhrase, testAttempt);
        assertEquals(new ArrayList<>(Arrays.asList(scrambleId1)), db.retrieveScramblesInProgress(username1));
        db.updateScrambleProgress(scrambleId2, username1, scrambledPhrase, testAttempt);
        db.updateScrambleProgress(scrambleId3, username1, scrambledPhrase, testAttempt);
        assertEquals(new ArrayList<>(Arrays.asList(scrambleId1, scrambleId2, scrambleId3)), db.retrieveScramblesInProgress(username1));
    }

    @Test
    public void returnInProgressPhraseTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new Database(appContext, null);

        String username1 = "Tester1";
        String scrambleId1 = "TestScramble1";
        String scrambledPhrase = "Test";
        String testAttempt1 = "attempt1";
        String testAttempt2 = "attempt2";

        assertEquals(new ArrayList<>(Arrays.asList()), db.returnInProgressPhrase(scrambleId1, username1));
        db.updateScrambleProgress(scrambleId1, username1, scrambledPhrase, testAttempt1);
        assertEquals(new ArrayList<>(Arrays.asList(scrambledPhrase, testAttempt1)), db.returnInProgressPhrase(scrambleId1, username1));
        db.updateScrambleProgress(scrambleId1, username1, scrambledPhrase, testAttempt2);
        assertEquals(new ArrayList<>(Arrays.asList(scrambledPhrase, testAttempt2)), db.returnInProgressPhrase(scrambleId1, username1));
    }

    @Test
    public void deleteInProgressRecordTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        db = new Database(appContext, null);

        String username1 = "Tester1";
        String scrambleId1 = "TestScramble1";
        String scrambleId2 = "TestScramble2";
        String scrambleId3 = "TestScramble3";
        String scrambledPhrase = "Test";
        String testAttempt1 = "attempt1";

        db.updateScrambleProgress(scrambleId1, username1, scrambledPhrase, testAttempt1);
        assertEquals(new ArrayList<>(Arrays.asList(scrambleId1)), db.retrieveScramblesInProgress(username1));
        db.deleteInProgressRecord(scrambleId1, username1);
        assertEquals(new ArrayList<>(), db.retrieveScramblesInProgress(username1));
        db.updateScrambleProgress(scrambleId1, username1, scrambledPhrase, testAttempt1);
        db.updateScrambleProgress(scrambleId2, username1, scrambledPhrase, testAttempt1);
        db.updateScrambleProgress(scrambleId3, username1, scrambledPhrase, testAttempt1);
        db.deleteInProgressRecord(scrambleId1, username1);
        assertEquals(new ArrayList<>(Arrays.asList(scrambleId2, scrambleId3)), db.retrieveScramblesInProgress(username1));
        db.deleteInProgressRecord(scrambleId2, username1);
        assertEquals(new ArrayList<>(Arrays.asList(scrambleId3)), db.retrieveScramblesInProgress(username1));
    }
}
