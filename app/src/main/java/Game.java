import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.owner.tables.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Game extends AppCompatActivity implements Grid.MyGridInterface {

    Grid gridOne;
    Grid gridTwo;
    Button firstButton;
    TextView timer;
    TextView level_display;
    TextView firstNum;
    TextView secondNum;
    CountDownTimer clock;

    int firstButtonId;
    int firstButtonIndex;
    int firstButtonValue;
    int firstGridId;
    int level;
    int arraySize;
    int bigSquareSize;
    int smallSquareSize;
    int firstNumValue;
    int secondNumValue;
    int topRand;
    int bottomRand;
    int numTop;
    int numBottom;
    boolean buttonClicked = false;
    int gridsDone;
    String stringPlaceHolder;
    String timerText;

    ArrayList<String> stringArray = new ArrayList<>();
    ArrayList<Integer> numArray = new ArrayList<>();

    private static final String FORMAT = "%02d";
    private static final String tagOne = "smallergrid";
    private static final String tagTwo = "biggergrid";

    int height;
    int width;
    int gridSize;
    int textsize;
    int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_game);

       if (savedInstanceState != null) {
           firstButtonId = savedInstanceState.getInt("FBID");
           firstButtonIndex = savedInstanceState.getInt("FBIN");
           firstButtonValue = savedInstanceState.getInt("FBV");
           firstGridId = savedInstanceState.getInt("FGID");
           level = savedInstanceState.getInt("level");
           arraySize = savedInstanceState.getInt("arraySize");
           bigSquareSize = savedInstanceState.getInt("BSS");
           smallSquareSize = savedInstanceState.getInt("SSS");
           firstNumValue = savedInstanceState.getInt("FNV");
           secondNumValue = savedInstanceState.getInt("SNV");
           topRand = savedInstanceState.getInt("topRand");
           bottomRand = savedInstanceState.getInt("bottomRand");
           numTop = savedInstanceState.getInt("nTop");
           numBottom = savedInstanceState.getInt("nBottom");
           gridOne = (Grid) getSupportFragmentManager().findFragmentByTag(tagOne);
           gridTwo = (Grid) getSupportFragmentManager().findFragmentByTag(tagTwo);
           buttonClicked = savedInstanceState.getBoolean("CLICK");
           gridsDone = savedInstanceState.getInt("gridsDone");
           stringPlaceHolder = savedInstanceState.getString("SPH");
           timerText = savedInstanceState.getString("timerText");



           for (int i = 0; i < arraySize; i++) {
               stringPlaceHolder = "number" + i;
               numArray.add(savedInstanceState.getInt(stringPlaceHolder));
               stringPlaceHolder = "string" + i;
               stringArray.add(savedInstanceState.getString(stringPlaceHolder));
           }


           if (firstGridId == 1) firstButton = gridOne.buttonArray.get(firstButtonIndex);
           else firstButton = gridTwo.buttonArray.get(firstButtonIndex);

           int orientation = getApplicationContext().getResources().getConfiguration().orientation;
           createLayout(orientation, savedInstanceState);
        }
        else {
           Random rand = new Random();
           numTop = 5;
           numBottom = 1;
           firstNumValue = rand.nextInt(numTop - numBottom) + numBottom;
           secondNumValue = rand.nextInt(numTop - numBottom) + numBottom;
           topRand = 10;
           bottomRand = 1;
           while (secondNumValue == firstNumValue) secondNumValue = rand.nextInt(numTop - numBottom) + numBottom;
           level = 1;
           smallSquareSize = 2;
           bigSquareSize = 3;
           gridOne = new Grid();
           createNumberArray(topRand, bottomRand, bigSquareSize);
           gridOne.setButtonValuesArray(numArray);
           gridOne.setValue(firstNumValue);
           gridTwo = new Grid();
           gridTwo.setButtonValuesArray(numArray);
           gridTwo.setValue(secondNumValue);
           seconds = 15;
           gridsDone = smallSquareSize * smallSquareSize;
           int orientation = getApplicationContext().getResources().getConfiguration().orientation;
           createLayout(orientation, null);
       }
    }

    /*@Override

    protected void onStart() {
        super.onStart();
    }*/


//HAVE TO DECIDE RANDOM NUMBERS
    private void createNumberArray(int top, int bottom, int num) {
        System.out.println("top is " + top + " and bottom is " + bottom);
        Random rand = new Random();
        int n;
        int total = num * num;

        for(int i = 0; i < total; i++) {
            n = rand.nextInt(top - bottom) + bottom;
            if (numArray.contains(n)) {
                i--;
            } else {
                numArray.add(n);
                stringArray.add(Integer.toString(n));
            }
            System.out.println("i is " + i);
        }
        System.out.println("thunderbolt");
    }

    private void createLayout(int orientation, Bundle savedInstanceState) {
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        RelativeLayout fc = (RelativeLayout) findViewById(R.id.first);
        RelativeLayout.LayoutParams fcparams = (RelativeLayout.LayoutParams) fc.getLayoutParams();
        RelativeLayout sc = (RelativeLayout) findViewById(R.id.second);
        RelativeLayout.LayoutParams scparams = (RelativeLayout.LayoutParams) sc.getLayoutParams();
        RelativeLayout tc = (RelativeLayout) findViewById(R.id.third);
        RelativeLayout.LayoutParams tcparams = (RelativeLayout.LayoutParams) tc.getLayoutParams();
        RelativeLayout details = (RelativeLayout) findViewById(R.id.details);
        RelativeLayout.LayoutParams dparams = (RelativeLayout.LayoutParams) details.getLayoutParams();

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridSize = (7 * width) / 20;
            textsize = (3 * gridSize) / 16;

            fcparams.width = width;
            fcparams.height = (height - gridSize) / 2;
            fc.setLayoutParams(fcparams);


            scparams.width = gridSize*2 + gridSize/4;
            scparams.height = gridSize;
            sc.setLayoutParams(scparams);


            tcparams.width = width;
            tcparams.height = (height - gridSize) / 2;
            tc.setLayoutParams(tcparams);

            dparams.width = gridSize / 4;
            dparams.height = (11 * gridSize) / 16;
            details.setLayoutParams(dparams);
        }

        else {
            gridSize = (7 * height) / 20;
            textsize = (3 * gridSize) / 16;

            fcparams.height = height;
            fcparams.width = (width - gridSize) / 2;
            fc.setLayoutParams(fcparams);

            scparams.height = gridSize*2 + gridSize/4;
            scparams.width = gridSize;
            sc.setLayoutParams(scparams);

            tcparams.height = height;
            tcparams.width = (width - gridSize) / 2;
            tc.setLayoutParams(tcparams);

            dparams.height = gridSize / 4;
            dparams.width = (11 * gridSize) / 16;
            details.setLayoutParams(dparams);
        }

        gridOne.setGridID(1);
        gridOne.setSquareSize(bigSquareSize);
        gridOne.setButtonSize(gridSize);
        gridTwo.setGridID(2);
        gridTwo.setSquareSize(smallSquareSize);
        gridTwo.setButtonSize(gridSize);


        firstNum = (TextView) findViewById(R.id.first_number);
        stringPlaceHolder = Integer.toString(firstNumValue);
        firstNum.setText(stringPlaceHolder);
        firstNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, textsize);

        timer = (TextView) findViewById(R.id.timer);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.grid_one, gridOne, tagOne);
            transaction.add(R.id.grid_two, gridTwo, tagTwo);
            transaction.commitNow();
            seconds *= 1000;
            clock = new CountDownTimer(seconds, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.format(Locale.GERMAN, FORMAT,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
                public void onFinish() {
                    String zero = "00";
                    timer.setText(zero);
                    endGame();
                }
            }.start();
        }
        else {
            seconds = Integer.parseInt(timerText) * 1000 + 400;

            clock = new CountDownTimer(seconds, 1000) { // adjust the milli seconds here
                public void onTick(long millisUntilFinished) {
                    timer.setText(String.format(Locale.GERMAN, FORMAT,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
                public void onFinish() {
                    String zero = "00";
                    timer.setText(zero);
                    endGame();
                }
            }.start();
        }
        //a

        timer.setTextSize(TypedValue.COMPLEX_UNIT_PX, textsize);

        level_display = (TextView) findViewById(R.id.level_display);
        if (level < 10) stringPlaceHolder = "0" + Integer.toString(level);
        else stringPlaceHolder = Integer.toString(level);
        level_display.setText(stringPlaceHolder);
        level_display.setTextSize(TypedValue.COMPLEX_UNIT_PX, textsize);

        secondNum = (TextView) findViewById(R.id.second_number);
        stringPlaceHolder = Integer.toString(secondNumValue);
        secondNum.setText(stringPlaceHolder);
        secondNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, textsize);
    }

    private void endGame() {
        System.out.println("da");
        Intent intent = new Intent(this, EndGame.class);

        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("FBID", firstButtonId);
        savedInstanceState.putInt("FBIN", firstButtonIndex);
        savedInstanceState.putInt("FBV", firstButtonValue);
        savedInstanceState.putInt("FGID", firstGridId);
        savedInstanceState.putInt("level", level);
        savedInstanceState.putInt("BSS", bigSquareSize);
        savedInstanceState.putInt("SSS", smallSquareSize);
        savedInstanceState.putInt("FNV", firstNumValue);
        savedInstanceState.putInt("SNV", secondNumValue);
        savedInstanceState.putInt("topRand", topRand);
        savedInstanceState.putInt("bottomRand", bottomRand);
        savedInstanceState.putInt("nTop", numTop);
        savedInstanceState.putInt("nBottom", numBottom);
        savedInstanceState.putString("timer", timer.getText().toString());
        if (gridOne.isAdded()) getSupportFragmentManager().putFragment(savedInstanceState, tagOne, gridOne);
        if (gridTwo.isAdded()) getSupportFragmentManager().putFragment(savedInstanceState, tagTwo, gridTwo);
        savedInstanceState.putBoolean("CLICK", buttonClicked);
        savedInstanceState.putInt("gridsDone", gridsDone);
        savedInstanceState.putString("SPH", stringPlaceHolder);
        savedInstanceState.putString("timerText", timer.getText().toString());

        int arraySize = numArray.size();
        for (int i = 0; i < arraySize; i++) {
            stringPlaceHolder = "number" + i;
            savedInstanceState.putInt(stringPlaceHolder, numArray.get(i));
            stringPlaceHolder = "string" + i;
            savedInstanceState.putString(stringPlaceHolder, stringArray.get(i));
        }

        savedInstanceState.putInt("arraySize", arraySize);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    //public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
      //  super.onRestoreInstanceState(savedInstanceState);


        // Restore state members from saved instance
    //}

    private void pressButton(Button button, int gID) {
        if (gID == 1) {
            button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button_pressed));
        }
        else {
            button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button_pressed));
        }
    }

    private void disableButtons(Button button) {
        firstButton.setTextSize(0);
        firstButton.setEnabled(false);
        firstButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blank));
        button.setTextSize(0);
        button.setEnabled(false);
        button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blank));
    }

    private void switchButtons(Button button, int gID) {
        if (firstGridId == 1) firstButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button));
        else firstButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
        firstButton.setTextColor(Color.BLACK);
        if (gID == 1) button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button_pressed));
        else button.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button_pressed));
        button.setTextColor(Color.WHITE);
    }

    public void buttonSelected(/*LinearLayout*/ Button button, Grid grid) {
        int id = button.getId();
        int gID = grid.getGridID();
        int val = grid.getValue();
        System.out.println(" " + gID);
        int factor = id/val;
        if (!buttonClicked) {
            System.out.println("Hello");
            System.out.println(" " + grid.gridLayout.getId());
            pressButton(button, gID);
            button.setTextColor(Color.WHITE);
            firstButtonId = id;
            firstGridId = gID;
            firstButton = button;
            firstButtonValue = val;
            firstButtonIndex = grid.buttonArray.indexOf(button);
            buttonClicked = true;
        }
        else {
            int firstFactor = (firstButtonId/firstButtonValue);
            if ((firstFactor == factor) && (firstGridId != gID)) {
                System.out.println("scaramoush");
                disableButtons(button);
                gridsDone = gridsDone - 1;
                System.out.println(gridsDone);
                buttonClicked = false;
                System.out.println("fandango");
            }
            else if ((firstFactor != factor) && (firstGridId == gID)) {
                switchButtons(button, gID);
                firstButtonId = id;
                firstGridId = gID;
                firstButton = button;
                firstButtonIndex = grid.buttonArray.indexOf(button);
                buttonClicked = true;
            }
            else if (firstFactor != factor) {
                if (firstGridId == 1) firstButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.green_button));
                else firstButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_button));
                firstButton.setTextColor(Color.BLACK);
                buttonClicked = false;
            }
            else {
                System.out.println("I am first here");
                buttonClicked = true;
            }

            if (gridsDone == 0) {
                System.out.println("yippe");
                level += 1;
                if (level > 13) {
                    smallSquareSize = 3;
                    bigSquareSize = 4;
                }
                else if (level > 5) {
                    smallSquareSize = 2;
                    bigSquareSize = 4;
                }
                else {
                    smallSquareSize = 2;
                    bigSquareSize = 3;
                }
                gridsDone = smallSquareSize * smallSquareSize;

                Random rand = new Random();

                if ((level == 1) || (level == 2)) {
                    topRand = 10;
                    bottomRand = 1;
                } else if (level == 6) {
                    topRand = 20;
                    bottomRand = 1;
                } else if ((level == 3) || (level == 4)) {
                    topRand = 15;
                    bottomRand = 5;
                } else if ((level == 5) || (level == 8) || (level == 7)) {
                    topRand = 30;
                    bottomRand = 10;
                } else if (level < 18) {
                    topRand = 20;
                    bottomRand = 1;
                } else if (level < 30){
                    topRand = 40;
                    bottomRand = 20;
                } else {
                    topRand = 80;
                    bottomRand = 40;
                }

                System.out.println("skipper");

                if ((level < 3) || ((level < 9) && (level > 5)) ||  ((level < 18) && (level > 13))) {
                    numTop = 5;
                    numBottom = 1;
                } else if (((level > 2) && (level < 6)) || ((level > 8) && (level < 12)) || (level > 17)) {
                    numTop = 10;
                    numBottom = 5;
                } else if ((level == 12) || (level == 13)) {
                    numTop = 20;
                    numBottom = 10;
                }

                int pFirstNum = firstNumValue;
                int pSecondNum = secondNumValue;
                firstNumValue = rand.nextInt(numTop - numBottom) + numBottom;
                while (firstNumValue == pFirstNum) firstNumValue = rand.nextInt(numTop - numBottom) + numBottom;
                secondNumValue = rand.nextInt(numTop - numBottom) + numBottom;
                while ((secondNumValue == firstNumValue) || (secondNumValue == pSecondNum)) secondNumValue = rand.nextInt(numTop - numBottom) + numBottom;
                stringPlaceHolder = Integer.toString(firstNumValue);
                firstNum.setText(stringPlaceHolder);
                stringPlaceHolder = Integer.toString(secondNumValue);
                secondNum.setText(stringPlaceHolder);

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                Grid firstGrid = new Grid();
                firstGrid.setSquareSize(bigSquareSize);
                firstGrid.setButtonSize(gridSize);
                firstGrid.setGridID(1);
                firstGrid.setValue(firstNumValue);
                transaction.replace(R.id.grid_one, firstGrid, tagOne);

                System.out.println("mamma");

                Grid secondGrid = new Grid();
                secondGrid.setSquareSize(smallSquareSize);
                secondGrid.setButtonSize(gridSize);
                secondGrid.setGridID(2);
                secondGrid.setValue(secondNumValue);
                transaction.replace(R.id.grid_two, secondGrid, tagTwo);

                clock.cancel();

                System.out.println("mia");

                if (level < 5) seconds = 15000;
                else if (level < 14) seconds = 30000;
                else seconds = 60000;

                clock = new CountDownTimer(seconds, 1000) { // adjust the milli seconds here
                    public void onTick(long millisUntilFinished) {
                        timer.setText(String.format(Locale.GERMAN, FORMAT,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }
                    public void onFinish() {
                        String zero = "00";
                        timer.setText(zero);
                        endGame();
                    }
                }.start();

                numArray.clear();
                stringArray.clear();

                createNumberArray(topRand, bottomRand, bigSquareSize);

                System.out.println("bismillah");

                firstGrid.setButtonValuesArray(numArray);
                secondGrid.setButtonValuesArray(numArray);
                transaction.commitNow();

                System.out.println("not let you go");

                gridOne = grid;
                gridTwo = secondGrid;
                if (level < 10) stringPlaceHolder = "0" + Integer.toString(level);
                else stringPlaceHolder = Integer.toString(level);
                level_display.setText(stringPlaceHolder);
            }
        }
    }
}



