package edu.gatech.seclass.sdpscramble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 10/8/17.
 */

public class Player {
    private String firstName, lastName, userName, email;
    private List<String> scramblesSolved;

    public Player(String userName, String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        scramblesSolved = new ArrayList<String>();
    }

    // getters
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public List<String> getScramblesSolved() {
        return scramblesSolved;
    }


    public void addScrambleSolved(String id) {
        scramblesSolved.add(id);
    }
}
