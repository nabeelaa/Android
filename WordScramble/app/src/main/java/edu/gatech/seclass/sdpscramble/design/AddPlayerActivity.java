package edu.gatech.seclass.sdpscramble.design;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;

/**
 * Registers new user
 */
public class AddPlayerActivity extends AppCompatActivity implements View.OnClickListener{
    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);
    //Email Validation
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    Button addPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        addPlayer = (Button) findViewById(R.id.add_player_button);
        addPlayer.setOnClickListener(this);

    }


    // Upon clicking submit button
    @Override
    public void onClick(View v) {

        TextInputLayout firstInputLayout = (TextInputLayout) findViewById(R.id.first_input_layout);
        TextInputLayout secondInputLayout = (TextInputLayout) findViewById(R.id.second_input_layout);
        TextInputLayout thirdInputLayout = (TextInputLayout) findViewById(R.id.third_input_layout);
        TextInputLayout fourthInputLayout = (TextInputLayout) findViewById(R.id.fourth_input_layout);

        // clear errors
        firstInputLayout.setError(null);
        secondInputLayout.setError(null);
        thirdInputLayout.setError(null);
        fourthInputLayout.setError(null);

        // Get username, first name, last name and email
        EditText getUsername = (EditText) findViewById(R.id.username);
        String username = getUsername.getText().toString();
        EditText getFirstName = (EditText) findViewById(R.id.first_name);
        String firstName = getFirstName.getText().toString();
        EditText getLastName = (EditText) findViewById(R.id.last_name);
        String lastName = getLastName.getText().toString();
        EditText getEmail = (EditText) findViewById(R.id.email);
        String email = getEmail.getText().toString();

        // Set errors, if they exist
        if (TextUtils.isEmpty(username)) {
            firstInputLayout.setError("This field is required");
        }
        else if (TextUtils.isEmpty(firstName)) {
            secondInputLayout.setError("This field is required");
        }
        else if (TextUtils.isEmpty(lastName)) {
            thirdInputLayout.setError("This field is required");
        }
        else if (TextUtils.isEmpty(email)) {
            fourthInputLayout.setError("This field is required");
        }
        else if (!validateEmail(email)){
            fourthInputLayout.setError("Invalid Email");
        }
        else {
            //Send new user to ScrambleGame Instance
            sg.createUser(username,firstName,lastName,email);

            SharedPreferences pref = getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean("logged_in", true);
            edit.commit();

            SharedPreferences curruser = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit2 = curruser.edit();
            edit2.putString("current_user", sg.getCurrentPlayer().getUserName());
            edit2.commit();

            AlertDialog alertDialog = new AlertDialog.Builder(AddPlayerActivity.this).create();
            alertDialog.setTitle("Registered.");
            alertDialog.setMessage("You have successfully registered. Your username is " + sg.getCurrentPlayer().getUserName());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Login",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AddPlayerActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sg.savePersistent();
    }
}
