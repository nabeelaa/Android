package edu.gatech.seclass.sdpscramble.design;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;

/**
 * Created by nabeela on 10/10/2017.
 */

public class SplashActivity  extends AppCompatActivity{

    public static Context appContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appContext = getApplicationContext();

        // Navigate to MainActivity or LoginActivity
        SharedPreferences pref = getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
        if (pref.getBoolean("logged_in", false)) {
            SharedPreferences pref2 = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            String curruser = pref2.getString("current_user", null);
            ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);
            if (sg.verifyLogIn(curruser) ) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
