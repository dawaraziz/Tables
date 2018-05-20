/*package com.example.owner.tables;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Matcher extends Fragment {

    LinearLayout matcherLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            this.buttonSize = savedInstanceState.getInt("bSize");
            return matcherLayout;
        }

        else {
            matcherLayout = new LinearLayout(getActivity());
            buttonArray = new ArrayList<>();
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            matcherLayout.setLayoutParams(lparams);
            matcherLayout.setOrientation(LinearLayout.VERTICAL);
            matcherLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));


            for (int i = 0; i < squareSize; i++) {
                LinearLayout row = new LinearLayout(getActivity());
                LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                row.setLayoutParams(rowParams);

                for (int j = 0; j < squareSize; j++) {
                    final Button box = new Button(getActivity());
                    //LayoutParams boxParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
                    //box.setLayoutParams(boxParams);
                    //box.setClickable(true);
                    //TextView text = new TextView(getActivity());
                    box.setMinWidth(buttonSize);
                    box.setMinHeight(buttonSize);

                    if (this.getmatcherID() == 1) box.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.green_button));
                    else box.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_button));
                    box.setTextColor(Color.BLACK);
                    box.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callSendInfo(box);
                        }
                    });
                    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    box.setLayoutParams(btnParams);

                    int textSize = ((4 * buttonSize) / 10);
                    box.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

                    buttonArray.add(box);
                    row.addView(box);
                }

                matcherLayout.addView(row);
            }

            return matcherLayout;
        }
    }
}*/
