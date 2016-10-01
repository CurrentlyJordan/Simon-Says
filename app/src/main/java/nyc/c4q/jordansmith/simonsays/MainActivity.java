package nyc.c4q.jordansmith.simonsays;

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
    private MediaPlayer mpR;
    private MediaPlayer mpG;
    private MediaPlayer mpY;
    private MediaPlayer mpB;
    private MediaPlayer mpL;
    ArrayList<Integer> playerArray = new ArrayList<>();
    ArrayList<Integer> simonArray = new ArrayList<>();
    int round = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);
        roundTextView = (TextView) findViewById(R.id.score_board);
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
                changeColor(greenButton,simonGreen,simonLightGreen,0,300, mpG);
                winOrLose();

            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(2);
                mpR.start();
                changeColor(redButton,simonRed,simonLightRed,0,300, mpR);
                winOrLose();

            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(3);
                mpY.start();
                changeColor(yellowButton,simonYellow,simonLightYellow,0,300, mpY);
                winOrLose();

            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(4);
                mpB.start();
                changeColor(blueButton,simonBlue,simonLightBlue,0,300, mpB);
                winOrLose();
            }
        });

        if(savedInstanceState == null) {
            nextRound();
        }


//        setClickForButton(greenButton, 1, simonGreen, simonLightGreen);
//        setClickForButton(redButton, 2, simonRed, simonLightRed);
//        setClickForButton(yellowButton, 3, simonYellow, simonLightYellow);
//        setClickForButton(blueButton, 4, simonBlue, simonLightBlue);
    }

//    public void setClickForButton( final Button b, final int val, final int oldColor, final int newColor){
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                playerArray.add(val);
//                changeColor(b, oldColor, newColor, 0, 300);
//                winOrLose();
//            }
//        });
//    }


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





    public boolean arrayChecker() {
        boolean doesArrayMatch = true;
        for (int i = 0; i < simonArray.size(); i++) {
            if (!playerArray.get(i).equals(simonArray.get(i))) {
                doesArrayMatch = false;
            }
        }
        if (!doesArrayMatch) {
            Toast.makeText(getApplicationContext(), "Fail!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Restart using menu", Toast.LENGTH_LONG).show();
            mpL.start();
        } else {
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
            case R.id.score_board:
                Toast.makeText(this, "High Score is ", Toast.LENGTH_LONG).show();
            break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void restartGame(){
        round = 0;
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

//    public void computerSound(final MediaPlayer b, int start){
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                b.start();
//            }
//        }, start);
//    }


//    public ArrayList<Integer> simonArrayBuilder(int round) {
//        ArrayList<Integer> simonSequence = new ArrayList<Integer>();
//        Random rand = new Random();
//        for (int i = 0; i < round; i++) {
//            int randNumb = rand.nextInt(4) + 1;
//            simonSequence.add(randNumb);
//        }
//        for (int j = 0; j < simonSequence.size(); j++) {
//            Log.d("JORDAN", String.format("%s, %s, %s", j, j * 1000, simonSequence.get(j)));
//            int start = (j) * 1000;
//            int end = ((j + 1) * 1000) - 200;
//            switch (simonSequence.get(j)) {
//                case 1:
//                    changeColor(greenButton, simonGreen, simonLightGreen, start, end, mpG);
//                   // computerSound(mpG, end);
//                    break;
//                case 2:
//                    changeColor(redButton, simonRed, simonLightRed, start, end, mpR);
//                   // computerSound(mpR, end);
//                    break;
//                case 3:
//                    changeColor(yellowButton, simonYellow, simonLightYellow, start, end, mpY);
//                    //computerSound(mpY, end);
//                    break;
//                case 4:
//                    changeColor(blueButton, simonBlue, simonLightBlue, start, end, mpB);
//                    //computerSound(mpB, end);
//                    break;
//            }
//        }
//        return simonSequence;
//    }


    public void simonArrayAdder() {
        Random rand = new Random();
        int newNumber = rand.nextInt(4) + 1;
        simonArray.add(newNumber);
    }

    public void simonArrayDisplay() {
        for (int i = 0; i < simonArray.size(); i++) {
            int start = (i) * 1000;
            int end = ((i + 1) * 1000) - 200;
            switch (simonArray.get(i)) {
                case 1:
                    changeColor(greenButton, simonGreen, simonLightGreen, start, end, mpG);
                    //mpG.start();
                    break;
                case 2:
                    changeColor(redButton, simonRed, simonLightRed, start, end, mpR);
                    //mpR.start();
                    break;
                case 3:
                    changeColor(yellowButton, simonYellow, simonLightYellow, start, end, mpY);
                    //mpY.start();
                    break;
                case 4:
                    changeColor(blueButton, simonBlue, simonLightBlue, start, end, mpB);
                    //mpB.start();
                    break;
            }
//            mpG.release();
//            mpB.release();
//            mpY.release();
//            mpR.release();

        }
    }

    public void nextRound() {
        Log.d(TAG, "nextRound()");
        playerArray.clear();
        round++;
        String roundMessage = "Round: " + round;
        roundTextView.setText(roundMessage);
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
        if (playerArray.size() == simonArray.size()) {
            if (arrayChecker()) {
                nextRound();
            }
        }

    }


}








