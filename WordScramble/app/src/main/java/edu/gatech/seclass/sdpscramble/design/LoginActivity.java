package edu.gatech.seclass.sdpscramble.design;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    Button loginButton;
    Button addPlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this);

        addPlayerButton = (Button) findViewById(R.id.addPlayerBtn);
        addPlayerButton .setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.loginBtn:

                TextInputLayout loginInputLayout = (TextInputLayout) findViewById(R.id.login_input_layout);
                loginInputLayout.setError(null);

                // get username
                EditText getUsername = (EditText) findViewById(R.id.username);
                String username = getUsername.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    loginInputLayout.setError("This field is required");
                    break;
                }
                else {

                    boolean loggedIn = sg.verifyLogIn(username);

                    if (loggedIn) {
                        //remove this
                        Toast.makeText(getBaseContext(), "You are logged in " + username, Toast.LENGTH_SHORT).show();

                        SharedPreferences pref = getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putBoolean("logged_in", true);
                        edit.commit();

                        SharedPreferences curruser = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit2 = curruser.edit();
                        edit2.putString("current_user", sg.getCurrentPlayer().getUserName());
                        edit2.commit();

                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        finish();
                        break;
                    } else {
                        loginInputLayout.setError("Username not Found, Please Create New Account");
                        break;
                    }

                }

            case R.id.addPlayerBtn:
                Intent intent_add_player = new Intent(this, AddPlayerActivity.class);
                startActivity(intent_add_player);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sg.savePersistent();
    }
}
