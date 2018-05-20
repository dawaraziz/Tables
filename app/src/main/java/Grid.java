import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.owner.tables.R;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends Fragment {

    LinearLayout gridLayout;
    MyGridInterface gridCallback;

    ArrayList<Button> buttonArray;
    ArrayList<Integer> buttonValues;

    public int gridID;
    public int buttonSize;
    int squareSize;
    int value;

    public void setGridID(int i) {
        this.gridID = i;
    }

    public int getGridID() {
        return this.gridID;
    }

    public void setSquareSize(int i) {this.squareSize = i;}

    public void setValue(int i) {this.value = i;}

    public int getValue(){return this.value;}

    public void setButtonSize(int top) {this.buttonSize = top / squareSize;}

    public void setButtonValuesArray(ArrayList<Integer> numArray) {this.buttonValues = numArray;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            this.gridID = savedInstanceState.getInt("GID");
            this.buttonSize = savedInstanceState.getInt("bSize");
            setGridID(this.gridID);
            gridLayout.setId(this.gridID);
            return gridLayout;
        }

        else {
            gridLayout = new LinearLayout(getActivity());
            buttonArray = new ArrayList<>();
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            gridLayout.setLayoutParams(lparams);
            gridLayout.setOrientation(LinearLayout.VERTICAL);
            gridLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));


            for (int i = 0; i < squareSize; i++) {
                LinearLayout row = new LinearLayout(getActivity());
                LayoutParams rowParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
                row.setLayoutParams(rowParams);

                for (int j = 0; j < squareSize; j++) {
                    final Button box = new Button(getActivity());
                    //LayoutParams boxParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
                    //box.setLayoutParams(boxParams);
                    //box.setClickable(true);
                    //TextView text = new TextView(getActivity());
                    box.setMinWidth(buttonSize);
                    box.setMinHeight(buttonSize);

                    if (this.getGridID() == 1) box.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.green_button));
                    else box.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.red_button));
                    box.setTextColor(Color.BLACK);
                    box.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callSendInfo(box);
                        }
                    });
                    LayoutParams btnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    box.setLayoutParams(btnParams);

                    int textSize = ((4 * buttonSize) / 10);
                    box.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    //Integer num =  100+ j + 1 + (i * 4);
                    /*int textSize = ((3 * buttonSize) / 10);
                    box.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                    Integer num = 10000 + j + 1 + (i * 4);*/
                    //String s = num.toString();
                    //box.setText(s);
                    //box.setId(j + 1 + (i * 4));
                    /*text.setPadding(10, 10, 10, 10);
                    LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textParams.gravity = Gravity.CENTER;
                    text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setLayoutParams(textParams);
                    box.setPadding(10, 10, 10, 10);
                    box.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    box.addView(text);
                    box.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);*/

                    buttonArray.add(box);
                    row.addView(box);
                }

                gridLayout.addView(row);
            }

            return gridLayout;
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridLayout.setId(this.gridID);
        if (savedInstanceState == null) {
            shuffleArray(buttonValues);
            setButtonValues(buttonValues, squareSize * squareSize);
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("GID", gridID);
        savedInstanceState.putInt("bSize", buttonSize);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void callSendInfo(Button button) {
        sendInfo(button, this);
    }

    public interface MyGridInterface {
        void buttonSelected(Button button, Grid grid);
    }

    @Override
    public void onAttach(Context game) {
        super.onAttach(game);

        try {
            gridCallback = (MyGridInterface) game;
        } catch (ClassCastException e) {
            throw new ClassCastException(game.toString() +
            " must implement MyGridInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        gridCallback = null;
    }

    public void sendInfo(Button button, Grid grid) {
        gridCallback.buttonSelected(button, grid);
    }

    public void setButtonValues(ArrayList<Integer> numArray, int len) {
        Integer num;
        String str;
        for (int i = 0; i < len; i++) {
            num = value * numArray.get(i);
            str = Integer.toString(num);
            buttonArray.get(i).setText(str);
            buttonArray.get(i).setId(num);
        }
    }

    public void shuffleArray(ArrayList<Integer> numArray) {
        ArrayList<Integer> tempArray = new ArrayList<>();
        int size = numArray.size();
        for (int i = 0; i < size; i++) {
            tempArray.add(numArray.get(i));
        }
        Random rand = new Random();
        size = tempArray.size();
        buttonValues.clear();
        ArrayList<Integer> indexArray = new ArrayList<>();
        int index = rand.nextInt(size);

        for (int i = 0; i < size; i++) {
            while (!(indexArray.isEmpty()) && indexArray.contains(index)) index = rand.nextInt(size);
            buttonValues.add(tempArray.get(index));
            indexArray.add(index);
        }

        tempArray.clear();
        indexArray.clear();
    }

}
