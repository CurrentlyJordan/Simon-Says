package nyc.c4q.jordansmith.simonsays;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
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
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button yellowButton;
    TextView roundTextView;
    ArrayList<Integer> playerArray = new ArrayList<>();
    List<Integer> simonArray = new ArrayList<>();
    int round = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);
        roundTextView = (TextView) findViewById(R.id.score_board);


        nextRound();


        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(1);
                winOrLose();

            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(2);
                winOrLose();

            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(3);
                winOrLose();

            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerArray.add(4);
                winOrLose();
            }
        });

//        setClickForButton(greenButton, 1, simonGreen, simonLightGreen);
//        setClickForButton(redButton, 2, simonRed, simonLightRed);
//        setClickForButton(yellowButton, 3, simonYellow, simonLightYellow);
//        setClickForButton(blueButton, 4, simonBlue, simonLightBlue);
    }

    public void setClickForButton( final Button b, final int val, final int oldColor, final int newColor){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                playerArray.add(val);
                changeColor(b, oldColor, newColor, 0, 500);
                winOrLose();
            }
        });
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
        } else {
            Toast.makeText(getApplicationContext(), "You matched the sequence!", Toast.LENGTH_SHORT).show();
        }
        return doesArrayMatch;

    }


    public void changeColor(final Button b, int oldColor, int newColor, int start, int end) {
        // http://stackoverflow.com/a/33674062
        final int oldColorRGB = ContextCompat.getColor(getBaseContext(), oldColor);
        final int newColorRGB = ContextCompat.getColor(getBaseContext(), newColor);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setBackgroundColor(newColorRGB);
            }
        }, start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setBackgroundColor(oldColorRGB);
            }
        }, end);
    }


    public ArrayList<Integer> simonArrayBuilder(int round) {
        ArrayList<Integer> simonSequence = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < round; i++) {
            int randNumb = rand.nextInt(4) + 1;
            simonSequence.add(randNumb);
        }
        for (int j = 0; j < simonSequence.size(); j++) {
            Log.d("JORDAN", String.format("%s, %s, %s", j, j * 1000, simonSequence.get(j)));
            int start = (j) * 1000;
            int end = ((j + 1) * 1000) - 200;
            switch (simonSequence.get(j)) {
                case 1:
                    changeColor(greenButton, simonGreen, simonLightGreen, start, end);
                    break;
                case 2:
                    changeColor(redButton, simonRed, simonLightRed, start, end);
                    break;
                case 3:
                    changeColor(yellowButton, simonYellow, simonLightYellow, start, end);
                    break;
                case 4:
                    changeColor(blueButton, simonBlue, simonLightBlue, start, end);
                    break;
            }
        }
        return simonSequence;
    }


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
                    changeColor(greenButton, simonGreen, simonLightGreen, start, end);
                    break;
                case 2:
                    changeColor(redButton, simonRed, simonLightRed, start, end);
                    break;
                case 3:
                    changeColor(yellowButton, simonYellow, simonLightYellow, start, end);
                    break;
                case 4:
                    changeColor(blueButton, simonBlue, simonLightBlue, start, end);
                    break;
            }

        }
    }

    public void nextRound() {
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

    public void winOrLose() {
        if (playerArray.size() == simonArray.size()) {
            Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();
            if (arrayChecker()) {
                nextRound();
            }
        }

    }


}








