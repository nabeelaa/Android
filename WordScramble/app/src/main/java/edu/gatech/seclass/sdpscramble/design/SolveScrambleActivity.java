package edu.gatech.seclass.sdpscramble.design;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;


public class SolveScrambleActivity extends AppCompatActivity implements View.OnClickListener{

    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    Button submit_solution;
    TextView getPhrase;
    TextView getHint;
    EditText getSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scramble_solve);

        getPhrase = (TextView) findViewById(R.id.phraseValue);
        getPhrase.setText(sg.getCurrentScramble().getScrambledPhrase());
        getHint = (TextView) findViewById(R.id.hintValue);
        getHint.setText(sg.getCurrentScramble().getClue());

        getSolution = (EditText) findViewById(R.id.solution_input);
        getSolution.setText(sg.getCurrentScramble().getLastAttempt());

        submit_solution = (Button) findViewById(R.id.submit_solution);
        submit_solution.setOnClickListener(this);


    }

    @Override
    public void onPause() {
        super.onPause();
        sg.exitScramble(getSolution.getText().toString());
        sg.savePersistent();
    }

    @Override
    public void onClick(View v) {

        TextInputLayout solutionInputLayout = (TextInputLayout) findViewById(R.id.solution_input_layout);
        solutionInputLayout.setError(null);

        String solution = getSolution.getText().toString().trim();

        if (TextUtils.isEmpty(solution)) {
            solutionInputLayout.setError("This field is required");
        }

        else {

            boolean solved = sg.solveScramble(solution);

            if (solved) {
                //if solution is correct go to home screen through dialog box
                AlertDialog alertDialog = new AlertDialog.Builder(SolveScrambleActivity.this).create();
                alertDialog.setTitle("Congrats!");
                alertDialog.setMessage("You solved the scramble!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SolveScrambleActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                alertDialog.show();

            } else {
                //set error if the solution is incorrect
                solutionInputLayout.setError("Incorrect solution");
            }
        }
    }

}
