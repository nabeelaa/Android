package edu.gatech.seclass.sdpscramble.design;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;


public class PlayerStatisticsFragment extends Fragment {

    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    public PlayerStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_player_stats, container, false);

        List<List<String>> playerData = sg.getPlayerStats();

        for (List<String> player: playerData)

        {

            TableLayout tableLayout=(TableLayout)view.findViewById(R.id.playerStatsTable);

            View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.activity_table_row,null,false);
            TextView playerUserName  = (TextView) tableRow.findViewById(R.id.col1);
            TextView scramblesSolved  = (TextView) tableRow.findViewById(R.id.col2);
            TextView scramblesCreated = (TextView) tableRow.findViewById(R.id.col3);
            TextView avgScramblesSolved  = (TextView) tableRow.findViewById(R.id.col4);

            playerUserName.setText(player.get(0));
            scramblesSolved.setText(player.get(1));
            scramblesCreated.setText(player.get(2));
            avgScramblesSolved.setText(player.get(3));

            if (player.get(0).equals(sg.getCurrentPlayer().getUserName())) {
                playerUserName.setTypeface(null, Typeface.BOLD_ITALIC);
                scramblesSolved.setTypeface(null, Typeface.BOLD_ITALIC);
                scramblesCreated.setTypeface(null, Typeface.BOLD_ITALIC);
                avgScramblesSolved.setTypeface(null, Typeface.BOLD_ITALIC);
            }

            tableLayout.addView(tableRow);

        }


        return view;
    }
}
