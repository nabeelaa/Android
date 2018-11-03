package edu.gatech.seclass.sdpscramble;

/**
 * Created by steven on 10/8/17.
 */

public class Scramble {
    private String scrambleID, answer, clue, scrambled, lastAttempt;
    private boolean solved;

    public Scramble(String uniqueID, String answer, String scrambled, String clue) {
        this.answer = answer;
        this.scrambled = scrambled;
        this.clue = clue;
        this.scrambleID = uniqueID;
        solved = false;
        lastAttempt = "";
    }

    // getters
    public String getID() {
        return scrambleID;
    }
    public String getClue() {
        return clue;
    }
    public String getScrambledPhrase() {
        return scrambled;
    }
    public String getLastAttempt() {
        return lastAttempt;
    }

    public boolean checkSolution(String solution) {
        return answer.equals(solution);
    }

    public void setLastAttempt(String attempt) {
        lastAttempt = attempt;
    }

    public boolean isSolved() {
        return solved;
    }
    public void setSolved(boolean status) {
        solved = status;
    }
}
