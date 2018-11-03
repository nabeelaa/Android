package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.seclass.utilities.ExternalWebService;


public class ScrambleGame {
    // ScrambleGame is a singleton class
    private static ScrambleGame instance;

    private static ExternalWebService ews = ExternalWebService.getInstance();

    private static Database db;

    private static Context appContext;

    private Player currentPlayer;
    private Scramble currentScramble;

    private ScrambleGame() {}

    public static synchronized ScrambleGame getInstance(Context appContext) {
        if (instance == null) {
            instance = new ScrambleGame();
            ScrambleGame.appContext = appContext;
            db = Database.getInstance(appContext);

            try {
                FileInputStream fis = appContext.openFileInput("persistent");
                ObjectInputStream is = new ObjectInputStream(fis);
                ews.initializePlayers((List<List<String>>) is.readObject());
                ews.initializeScramble((List<List<String>>) is.readObject());
                is.close();
                fis.close();
            } catch (ClassNotFoundException| IOException | NullPointerException ex) {
//                Log.d("EX:", ex.getMessage());
            }

        }
        return instance;
    }

    // getters
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Scramble getCurrentScramble() {
        return currentScramble;
    }

    // attempts to login
    // if successful, sets currentPlayer
    // returns a boolean based on whether the username exists or not
    public boolean verifyLogIn(String username) {
        List<List<String>> playersInfo = ews.retrievePlayerListService();
        boolean usernameExists = false;
        for (List<String> player : playersInfo) {
            if (player.get(0).equals(username)) {
                usernameExists = true;
                currentPlayer = new Player(player.get(0), player.get(1), player.get(2), player.get(3));
                for (int i=4; i < player.size(); i++) {
                    currentPlayer.addScrambleSolved(player.get(i));
                }
                break;
            }
        }
        return usernameExists;
    }

    public void logOut() {
        currentPlayer = null;
    }

    // adds new Player to EWS
    // sets currentPlayer to newly created Player
    // returns unique username for new player
    public String createUser(String username, String firstname, String lastname, String email) {
        String uniqueUsername = "";
        try {
            uniqueUsername = ews.newPlayerService(username, firstname, lastname, email);
            currentPlayer = new Player(uniqueUsername, firstname, lastname, email);
        } catch (SocketTimeoutException | IllegalArgumentException ex) {
            Log.d("EX:", ex.getMessage());
        }
        return uniqueUsername;
    }

    // adds new scramble to EWS
    public String createScramble(String answer, String scrambled, String clue) {
        String uniqueID = "";
        try {
            uniqueID = ews.newScrambleService(answer, scrambled, clue, currentPlayer.getUserName());
        } catch (SocketTimeoutException | IllegalArgumentException ex) {
            Log.d("EX:", ex.getMessage());
        }
        return uniqueID;
    }

    // uses scrambleID to set currentScramble
    public void openScramble(String id) {
        List<List<String>> scramblesInfo = ews.retrieveScrambleService();
        for (List<String> scramble : scramblesInfo) {
            if (scramble.get(0).equals(id)) {
                currentScramble = new Scramble(id, scramble.get(1), scramble.get(2), scramble.get(3));
                List<String> inProgressScrambles = db.retrieveScramblesInProgress(currentPlayer.getUserName());
                if (inProgressScrambles.contains(id)) {
                    List<String> inProgressPhrase = db.returnInProgressPhrase(id, currentPlayer.getUserName());
                    currentScramble.setLastAttempt(inProgressPhrase.get(1));
                }
                break;
            }
        }
    }

    // checks solution
    // if correct, reports solved status to EWS and sets currentScramble to null
    // if incorrect, sets last attempt to the inputted solution
    // finally returns a boolean whether or not solution was correct or not
    public boolean solveScramble(String solution) {
        boolean correctSolution = currentScramble.checkSolution(solution);
        if (correctSolution) {
            currentPlayer.addScrambleSolved(currentScramble.getID());
            ews.reportSolveService(currentScramble.getID(), currentPlayer.getUserName());
            db.deleteInProgressRecord(currentScramble.getID(), currentPlayer.getUserName());
            currentScramble.setSolved(true);
        } else {
            currentScramble.setLastAttempt(solution);
        }
        return correctSolution;
    }

    private void updateScrambleAttempt(String lastAttempt) {
        db.updateScrambleProgress(currentScramble.getID(), currentPlayer.getUserName(), currentScramble.getScrambledPhrase(), lastAttempt);
    }

    public void exitScramble(String current) {
        if (!currentScramble.isSolved()) {
            if (current.isEmpty()) {
                updateScrambleAttempt(currentScramble.getLastAttempt());
            } else {
                updateScrambleAttempt(current);
            }
        }
        currentScramble = null;
    }

    // returns a Map of scramble ID and scrambled phrase
    // only includes scrambles that are npt solved yet and not created by current player
    public Map<String, List<String>> getUnsolvedScrambles() {

        Map<String, List<String>> unsolvedScrambles = new LinkedHashMap<String, List<String>>();

        List<String> inProgressScrambles = db.retrieveScramblesInProgress(currentPlayer.getUserName());
        for (String id: inProgressScrambles) {
            List<String> inProgressInfo = db.returnInProgressPhrase(id, currentPlayer.getUserName());
            unsolvedScrambles.put(id, inProgressInfo);
        }

        List<String> solvedScrambles = currentPlayer.getScramblesSolved();
        List<List<String>> scramblesInfo = ews.retrieveScrambleService();
        for (List<String> scramble : scramblesInfo) {
            if (!solvedScrambles.contains(scramble.get(0)) && !scramble.get(4).equals(currentPlayer.getUserName()) && !unsolvedScrambles.containsKey(scramble.get(0))) {
                List<String> info = new ArrayList<String>();
                info.add(scramble.get(2));
                unsolvedScrambles.put(scramble.get(0), info);
            }
        }
        return unsolvedScrambles;
    }

    // returns all relevant stats as a List: scramble ID, scrambled phrase, solved or created by current player, total times solved
    // the List of list is already sorted in descending order by total times solved
    public List<List<String>> getScrambleStats() {
        List<List<String>> scramblesInfo = ews.retrieveScrambleService();
        List<String> solvedScrambles = currentPlayer.getScramblesSolved();
        List<List<String>> scramblesStats = new ArrayList<List<String>>();

        List<List<String>> playersInfo = ews.retrievePlayerListService();
        Map<String, Integer> totalSolved = new HashMap<String, Integer>();
        for (List<String> scramble : scramblesInfo) {
            totalSolved.put(scramble.get(0), 0);
        }
        for (List<String> player : playersInfo) {
            for (int i=4; i < player.size(); i++) {
                String scrambleID = player.get(i);
                Integer count = totalSolved.get(scrambleID);
                count++;
                totalSolved.put(scrambleID, count);
            }
        }

        for (List<String> scramble : scramblesInfo) {
            List<String> stat = new ArrayList<String>();
            stat.add(scramble.get(0)); // add ID
            stat.add(scramble.get(2)); // add scrambled phrase
            if (scramble.get(4).equals(currentPlayer.getUserName())) {
                stat.add("Created");
            } else if (solvedScrambles.contains(scramble.get(0))) {
                stat.add("Solved");
            } else {
                stat.add("");
            }
            stat.add(Integer.toString(totalSolved.get(scramble.get(0))));
            scramblesStats.add(stat);
        }

        Collections.sort(scramblesStats, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> l1, List<String> l2) {
                return l2.get(3).compareTo(l1.get(3));
            }
        });

        return scramblesStats;
    }

    // returns all relevant stats as a List: player username, # of solved scrambles, # of created scrambles, average # of scrambles solved by others
    // the List of list is already sorted in descending order by # of solved scrambles
    public List<List<String>> getPlayerStats() {
        List<List<String>> playersInfo = ews.retrievePlayerListService();
        List<List<String>> scramblesInfo = ews.retrieveScrambleService();
        List<List<String>> playersStats = new ArrayList<List<String>>();

        Map<String, List<String>> createdScrambles = new HashMap<String, List<String>>();
        Map<String, Integer> totalSolved = new HashMap<String, Integer>();
        for (List<String> player : playersInfo) {
            createdScrambles.put(player.get(0), new ArrayList<String>());
        }
        for (List<String> scramble : scramblesInfo) {
            totalSolved.put(scramble.get(0), 0);
        }

        for (List<String> scramble : scramblesInfo) {
            String createdBy = scramble.get(4);
            createdScrambles.get(createdBy).add(scramble.get(0));
        }

        for (List<String> player : playersInfo) {
            for (int i=4; i < player.size(); i++) {
                String scrambleID = player.get(i);
                Integer count = totalSolved.get(scrambleID);
                count++;
                totalSolved.put(scrambleID, count);
            }
        }

        for (List<String> player : playersInfo) {
            List<String> stat = new ArrayList<String>();
            stat.add(player.get(0));
            stat.add(Integer.toString(player.size()-4));
            List<String> created = createdScrambles.get(player.get(0));
            stat.add(Integer.toString(created.size()));
            if (created.size() == 0) {
                stat.add("0.00");
            } else {
                double avgSolvedByOthers = 0;
                for (String s : created) {
                    int solved = totalSolved.get(s);
                    avgSolvedByOthers += solved;
                }
                avgSolvedByOthers = avgSolvedByOthers / created.size();
                stat.add(String.format("%.2f", avgSolvedByOthers));
            }
            playersStats.add(stat);
        }

        Collections.sort(playersStats, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> l1, List<String> l2) {
                return l2.get(1).compareTo(l1.get(1));
            }
        });
        return playersStats;
    }

    public void savePersistent() {
        try {
            FileOutputStream fos = appContext.openFileOutput("persistent", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(ews.retrievePlayerListService());
            os.writeObject(ews.retrieveScrambleService());
            os.close();
            fos.close();
        } catch (IOException | NullPointerException ex) {
            Log.d("EX:", ex.getMessage());
        }
    }
}
