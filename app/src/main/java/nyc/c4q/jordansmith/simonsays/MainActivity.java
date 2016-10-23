package nyc.c4q.jordansmith.simonsays;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static nyc.c4q.jordansmith.simonsays.R.color.pastelBlue;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelDarkBlue;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelDarkGreen;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelDarkRed;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelDarkYellow;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelGreen;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelRed;
import static nyc.c4q.jordansmith.simonsays.R.color.pastelyellow;
import static nyc.c4q.jordansmith.simonsays.R.color.simonBlue;
import static nyc.c4q.jordansmith.simonsays.R.color.simonGreen;
import static nyc.c4q.jordansmith.simonsays.R.color.simonLightBlue;
import static nyc.c4q.jordansmith.simonsays.R.color.simonLightGreen;
import static nyc.c4q.jordansmith.simonsays.R.color.simonLightRed;
import static nyc.c4q.jordansmith.simonsays.R.color.simonLightYellow;
import static nyc.c4q.jordansmith.simonsays.R.color.simonRed;
import static nyc.c4q.jordansmith.simonsays.R.color.simonYellow;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "SIMONSAYS";
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button yellowButton;
    TextView roundTextView;
    TextView HiScoreTextView;
    private MediaPlayer mpR;
    private MediaPlayer mpG;
    private MediaPlayer mpY;
    private MediaPlayer mpB;
    private MediaPlayer mpL;
    ArrayList<Integer> playerArray = new ArrayList<>();
    ArrayList<Integer> simonArray = new ArrayList<>();
    int round = 0;
    int highScore;
    int buttonPress = -1;
    SharedPreferences.Editor editor;
    boolean pastelColor = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pastelColor = false;
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);
        roundTextView = (TextView) findViewById(R.id.score_board);
        HiScoreTextView = (TextView) findViewById(R.id.Hiscore_board);
        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        editor = prefs.edit();
        highScore = prefs.getInt("key", 0);
        String scoreText = "High Score: " + highScore;
        HiScoreTextView.setText(scoreText);
        mpG = MediaPlayer.create(getApplicationContext(), R.raw.green);
        mpR = MediaPlayer.create(getApplicationContext(), R.raw.red);
        mpY = MediaPlayer.create(getApplicationContext(), R.raw.yellow);
        mpB = MediaPlayer.create(getApplicationContext(), R.raw.blue);
        mpL = MediaPlayer.create(getApplicationContext(), R.raw.youlose);
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(1);
                mpG.start();
                if(pastelColor == true) {
                    changeColor(greenButton, pastelDarkGreen, pastelGreen, 0, 300, mpG);
                }
                else{
                    changeColor(greenButton, simonGreen, simonLightGreen, 0, 300, mpG);
                }
                buttonPress++;
                winOrLose();

            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(2);
                mpR.start();
                if(pastelColor == true) {
                    changeColor(redButton, pastelDarkRed, pastelRed, 0, 300, mpR);
                }
                else{
                    changeColor(redButton, simonRed, simonLightRed, 0, 300, mpR);
                }
                buttonPress++;
                winOrLose();

            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(3);
                mpY.start();
                if(pastelColor == true) {
                    changeColor(yellowButton,pastelDarkYellow,pastelyellow,0,300, mpY);
                }
                else{
                    changeColor(yellowButton, simonYellow, simonLightYellow, 0, 300, mpY);
                }
                buttonPress++;
                winOrLose();

            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(4);
                mpB.start();
                if(pastelColor == true) {
                    changeColor(blueButton,pastelDarkBlue,pastelBlue,0,300, mpB);
                }
                else{
                    changeColor(blueButton, simonBlue, simonLightBlue, 0, 300, mpB);
                }
                buttonPress++;
                winOrLose();
            }
        });

        if(savedInstanceState == null) {
            nextRound();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle state) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(state);
        state.putInt("round",round);
        state.putIntegerArrayList("simonArray",simonArray);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        simonArray = savedInstanceState.getIntegerArrayList("simonArray");
        round = savedInstanceState.getInt("round");
        resumeRound();
    }

    @Override
    protected void onResume(){
        Log.d(TAG, "onResume()");
        super.onResume();
        resumeRound();
    }

    @Override
    protected void onPause(){
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.onStop();
        editor.putInt("key", highScore);
        editor.apply();

    }





    public boolean arrayChecker() {
        boolean doesArrayMatch = true;
        for (int i = 0; i < simonArray.size(); i++) {
            if (!playerArray.get(i).equals(simonArray.get(i))) {
                doesArrayMatch = false;
            }
        }
        if (doesArrayMatch) {
            Toast.makeText(getApplicationContext(), "You matched the sequence!", Toast.LENGTH_SHORT).show();

        }
        return doesArrayMatch;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.skipround:
                nextRound();
                break;
            case R.id.restart:
                Toast.makeText(this, "Good Luck", Toast.LENGTH_LONG).show();
                restartGame();
                break;
            case R.id.pastel:
                pastelColor = true;
                greenButton.setBackgroundColor(getResources().getColor(pastelGreen));
                blueButton.setBackgroundColor(getResources().getColor(pastelBlue));
                redButton.setBackgroundColor(getResources().getColor(pastelRed));
                yellowButton.setBackgroundColor(getResources().getColor(pastelyellow));
                break;
            case R.id.default_theme:
                pastelColor = false;
                greenButton.setBackgroundColor(getResources().getColor(simonGreen));
                blueButton.setBackgroundColor(getResources().getColor(simonBlue));
                redButton.setBackgroundColor(getResources().getColor(simonRed));
                yellowButton.setBackgroundColor(getResources().getColor(simonYellow));


        }
        return super.onOptionsItemSelected(item);
    }

    public void restartGame(){
        round = 0;
        buttonPress = -1;
        simonArray.clear();
        nextRound();
    }

    public void changeColor(final Button b, int oldColor, int newColor, int start, int end,final MediaPlayer color) {
        // http://stackoverflow.com/a/33674062
        final int oldColorRGB = ContextCompat.getColor(getBaseContext(), oldColor);
        final int newColorRGB = ContextCompat.getColor(getBaseContext(), newColor);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setBackgroundColor(newColorRGB);
                color.start();
            }
        }, start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setBackgroundColor(oldColorRGB);
            }
        }, end);
    }


    public void simonArrayAdder() {
        Random rand = new Random();
        int newNumber = rand.nextInt(4) + 1;
        simonArray.add(newNumber);
    }

    public void simonArrayDisplay() {
//        greenButton.setClickable(false);
//        redButton.setClickable(false);
//        yellowButton.setClickable(false);
//        blueButton.setClickable(false);
        pastelColor = false;
       if(pastelColor == false) {
           for (int i = 0; i < simonArray.size(); i++) {
               int start = (i) * 1000;
               int end = ((i + 1) * 1000) - 200;
               switch (simonArray.get(i)) {
                   case 1:
                       changeColor(greenButton, simonGreen, simonLightGreen, start, end, mpG);
                       break;
                   case 2:
                       changeColor(redButton, simonRed, simonLightRed, start, end, mpR);
                       break;
                   case 3:
                       changeColor(yellowButton, simonYellow, simonLightYellow, start, end, mpY);
                       break;
                   case 4:
                       changeColor(blueButton, simonBlue, simonLightBlue, start, end, mpB);
                       break;
               }

           }
       }
       if(pastelColor == true) {
           for (int i = 0; i < simonArray.size(); i++) {
               int start = (i) * 1000;
               int end = ((i + 1) * 1000) - 200;
               switch (simonArray.get(i)) {
                   case 1:
                       changeColor(greenButton, pastelDarkGreen, pastelGreen, start, end, mpG);
                       break;
                   case 2:
                       changeColor(redButton, pastelDarkRed, pastelRed, start, end, mpR);
                       break;
                   case 3:
                       changeColor(yellowButton, pastelDarkYellow, pastelyellow, start, end, mpY);
                       break;
                   case 4:
                       changeColor(blueButton, pastelDarkBlue, pastelBlue, start, end, mpB);
                       break;
               }

           }
       }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                greenButton.setClickable(true);
                redButton.setClickable(true);
                yellowButton.setClickable(true);
                blueButton.setClickable(true);
            }
        }, ((simonArray.size() ) * 1000) - 200);
    }

    public void nextRound() {
        Log.d(TAG, "nextRound()");
        buttonPress = -1;
        playerArray.clear();
        round++;
        String roundMessage = "Round: " + round;
        roundTextView.setText(roundMessage);
        if (round > highScore) {
            highScore = round;
        }
        String highScoreString = "High Score: " + highScore;
        HiScoreTextView.setText(highScoreString);
        simonArrayAdder();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Match the sequence!", Toast.LENGTH_SHORT).show();
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simonArrayDisplay();
            }
        }, 6000);

    }

    public void resumeRound(){
        Log.d(TAG, "resumeRound()");
        playerArray.clear();
        String roundMessage = "Round: " + round;
        roundTextView.setText(roundMessage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Match the sequence!", Toast.LENGTH_SHORT).show();
            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                simonArrayDisplay();
            }
        }, 6000);
    }




    public void winOrLose() {
        if (!playerArray.get(buttonPress).equals(simonArray.get(buttonPress))){
            Toast.makeText(getApplicationContext(), "Fail!", Toast.LENGTH_SHORT).show();
            restartGame();
        }

        if (playerArray.size() == simonArray.size()) {
            if (arrayChecker()) {
                nextRound();
            }
            else{
                restartGame();
            }
        }

    }


}








