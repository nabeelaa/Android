package edu.gatech.seclass.sdpscramble.design;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import edu.gatech.seclass.sdpscramble.R;
import edu.gatech.seclass.sdpscramble.ScrambleGame;

public class HomeFragment extends Fragment implements View.OnClickListener {
    //Create ScrambleGame Instance
    ScrambleGame sg = ScrambleGame.getInstance(SplashActivity.appContext);

    Button btn;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        btn = (Button) view.findViewById(R.id.create_scramble_button);

        btn.setOnClickListener(this);

        TableLayout scrambleTable = (TableLayout) view.findViewById(R.id.unsolvedscrambles);

        Map<String, List<String>> unsolvedScrambles = sg.getUnsolvedScrambles();

        for (Map.Entry<String, List<String>> entry : unsolvedScrambles.entrySet()) {
            View row = LayoutInflater.from(getActivity()).inflate(R.layout.activity_unsolved_scrambles,null,false);

            String scrambleID = entry.getKey();
            TextView invisible = (TextView) row.findViewById(R.id.invisible);
            invisible.setText(scrambleID);

            TextView phrase  = (TextView) row.findViewById(R.id.col1);
            List<String> info = entry.getValue();

            phrase.setText(info.get(0));

            TextView status  = (TextView) row.findViewById(R.id.col2);
            if (info.size()>1) {
                status.setText("In Progress..");
                status.setTextColor(getResources().getColor(R.color.colorAccent));
                status.setTypeface(null, Typeface.ITALIC);
            } else {
                status.setText("New!");
                status.setTextColor(getResources().getColor(R.color.new_scramble));
            }

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow tr = (TableRow) v;
                    TextView idView = (TextView) tr.getChildAt(2);
                    sg.openScramble(idView.getText().toString());
                    Intent intent = new Intent(getActivity(), SolveScrambleActivity.class);
                    startActivity(intent);
                }
            });
            //Add row to view
            scrambleTable.addView(row);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), CreateScrambleActivity.class);
        startActivity(intent);
    }
}
