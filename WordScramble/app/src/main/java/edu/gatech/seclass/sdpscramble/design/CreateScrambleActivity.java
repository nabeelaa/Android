package edu.gatech.seclass.sdpscramble.design;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;

public class CreateScrambleActivity extends AppCompatActivity {

    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    TextView scrambledPhrase;
    EditText getPhrase;
    EditText getHint;

    TextInputLayout phraseInputLayout;
    TextInputLayout hintInputLayout;
    TextInputLayout scrambledInputLayout;

    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_scramble);

        getPhrase = (EditText) findViewById(R.id.input_phrase);
        getHint = (EditText) findViewById(R.id.input_hint);
        scrambledPhrase = (TextView) findViewById(R.id.input_scrambled_phrase);
        phraseInputLayout = (TextInputLayout) findViewById(R.id.phrase_input_layout);
        hintInputLayout = (TextInputLayout) findViewById(R.id.hint_input_layout);

        errorText = (TextView) findViewById(R.id.error_text);

    }

    // Upon clicking "Scramble"
    public void scramblePhrase (View view){
        errorText.setText("");
        phraseInputLayout.setError(null);
        hintInputLayout.setError(null);

        //get phrase and hint
        final String phrase = getPhrase.getText().toString();
        final String hint = getHint.getText().toString();

        if (TextUtils.isEmpty(phrase)) {
            phraseInputLayout.setError("Please enter a phrase");
        }
        else {
            displayScrambledPhrase(phrase);
        }

    }

    // Display scrambled phrase
    public void displayScrambledPhrase(String phrase){
        String word0 [] = phrase.split("[^a-zA-Z]+");
        String word [];
        if (word0[0].equals("")) {
            word = Arrays.copyOfRange(word0, 1, word0.length);
        } else {
            word = word0;
        }
        String separators [] = phrase.split("[a-zA-Z]+");
        Queue q = new LinkedList<String>(Arrays.asList(separators));
        String scrambled = "";
        System.out.println(phrase);
        System.out.println(Arrays.asList(word).toString());
        System.out.println(Arrays.asList(separators).toString());

        for(int i =0; i < word.length; i++){
            ArrayList<Character> chars = new ArrayList<Character>(word[i].length());
            ArrayList<Boolean> isUpperCase = new ArrayList<Boolean>();

            for ( char c : word[i].toCharArray() ) {
                isUpperCase.add(Character.isUpperCase(c));
                chars.add(Character.toLowerCase(c));
            }
            Collections.shuffle(chars);
            char[] shuffled = new char[chars.size()];
            for ( int j = 0; j < shuffled.length; j++ ) {
                if (isUpperCase.get(j) == true) {
                    shuffled[j] = Character.toUpperCase(chars.get(j));
                }
                else {
                    shuffled[j] = chars.get(j);
                }
            }
            String shuffledWord = new String(shuffled);
            if (q.isEmpty()) {
                scrambled = scrambled + shuffledWord;
            } else {
                scrambled = scrambled + q.remove() + shuffledWord;
            }
        }
        if (!q.isEmpty()){
            scrambled += q.remove();
        }
        scrambledPhrase.setText(scrambled);
    }


    // Upon clicking save scramble
    public void saveScramble (View view){
        if (TextUtils.isEmpty(getPhrase.getText().toString())) {
            phraseInputLayout.setError("Please enter a phrase");
        }
        else if (TextUtils.isEmpty(getHint.getText().toString())) {
            hintInputLayout.setError("Please enter a hint");
        }
        else if (TextUtils.isEmpty(scrambledPhrase.getText()) || scrambledPhrase.getText().equals("Scrambled Phrase")) {
            errorText.setText("Please scramble the phrase");
        }
        else {

            sg.createScramble(getPhrase.getText().toString().trim(), scrambledPhrase.getText().toString().trim(), getHint.getText().toString().trim());

            // go back to home screen
            AlertDialog alertDialog = new AlertDialog.Builder(CreateScrambleActivity.this).create();
            alertDialog.setTitle("Created.");
            alertDialog.setMessage("You have successfully created a new Scramble.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Back",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(CreateScrambleActivity.this, MainActivity.class);
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
