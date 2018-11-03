package edu.gatech.seclass.sdpscramble.design;


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

public class ScrambleStatisticsFragment extends Fragment  {

    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    public ScrambleStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_scramble_stats, container, false);

        List<List<String>> scrambleData = sg.getScrambleStats();

        for (List<String> scramble: scrambleData)
        {

            TableLayout tableLayout=(TableLayout)view.findViewById(R.id.scrambleTable);

                View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.activity_table_row,null,false);
                TextView scramble_id  = (TextView) tableRow.findViewById(R.id.col1);
                TextView phrase  = (TextView) tableRow.findViewById(R.id.col2);
                TextView solved_created = (TextView) tableRow.findViewById(R.id.col3);
                TextView times_solved  = (TextView) tableRow.findViewById(R.id.col4);

                scramble_id.setText(scramble.get(0));
                phrase.setText(scramble.get(1));
                solved_created.setText(scramble.get(2));
                times_solved.setText(scramble.get(3));

                tableLayout.addView(tableRow);

        }

        return view;
    }

}