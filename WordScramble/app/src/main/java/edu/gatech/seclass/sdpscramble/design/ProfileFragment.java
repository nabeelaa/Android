package edu.gatech.seclass.sdpscramble.design;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    //Create Buttons and Textview
    Button logoutButton;
    TextView usernameView;
    TextView firstnameView;
    TextView lastnameView;
    TextView emailView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_player_profile, container, false);

        logoutButton = (Button) view.findViewById(R.id.logOutBtn);
        logoutButton.setOnClickListener(this);

        usernameView = (TextView) view.findViewById(R.id.userNameValue);
        firstnameView = (TextView) view.findViewById(R.id.fNameValue);
        lastnameView = (TextView) view.findViewById(R.id.lNameValue);
        emailView = (TextView) view.findViewById(R.id.EmailValue);

        String username = sg.getCurrentPlayer().getUserName();
        String firstName = sg.getCurrentPlayer().getFirstName();
        String lastName = sg.getCurrentPlayer().getLastName();
        String email = sg.getCurrentPlayer().getEmail();

        usernameView.setText(username);
        firstnameView.setText(firstName);
        lastnameView.setText(lastName);
        emailView.setText(email);

        return view;
    }

    @Override
    public void onClick(View v) {

        SharedPreferences loggedin = this.getActivity().getSharedPreferences("LoginActivity", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = loggedin.edit();
        edit1.putBoolean("logged_in", false);
        edit1.commit();

        SharedPreferences curruser = this.getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit2 = curruser.edit();
        edit2.putString("current_user", null);
        edit2.commit();

        sg.logOut();

        Intent intent = new Intent(this.getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
