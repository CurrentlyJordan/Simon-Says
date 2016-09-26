package nyc.c4q.jordansmith.simonsays;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button yellowButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                redButton.setBackgroundColor(getResources().getColor(R.color.simonRed));}
//        }, 2000);

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRed();
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playGame(5);
            }
        });



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

        // no need to return anything
    }

    public String changeRed(){
        redButton.setBackgroundColor(getResources().getColor(R.color.simonLightRed));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redButton.setBackgroundColor(getResources().getColor(R.color.simonRed));}
        }, 1000);
        return "red";
    }

    public String changeGreen(){
        greenButton.setBackgroundColor(getResources().getColor(R.color.simonLightGreen));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                greenButton.setBackgroundColor(getResources().getColor(R.color.simonGreen));}
        }, 1000);
        return "green";
    }


    public String changeBlue(){
        blueButton.setBackgroundColor(getResources().getColor(R.color.simonLightBlue));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blueButton.setBackgroundColor(getResources().getColor(R.color.simonBlue));}
        }, 1000);
        return "Blue";
    }
    public String changeYellow(){
        yellowButton.setBackgroundColor(getResources().getColor(R.color.simonLightYellow));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                yellowButton.setBackgroundColor(getResources().getColor(R.color.simonYellow));}
        }, 1000);
        return "yellow";
    }



    public void playGame(int round) {
            int[] simonSequence = new int[round];
            Random rand = new Random();
            for (int i = 0; i < round; i++) {
                int randNumb = rand.nextInt(4) + 1;
                simonSequence[i] = randNumb;
            }
            for (int j = 0; j < simonSequence.length; j++) {
                Log.d("JORDAN", String.format("%s, %s, %s", j, j*1000, simonSequence[j]));
                int start = (j) * 1000;
                int end = (j+1) * 1000;
                switch (simonSequence[j]) {
                    case 1:
//                        changeGreen();
                        changeColor(greenButton, R.color.simonGreen, R.color.black, start, end);
                        break;
                    case 2:
//                        changeRed();
                        changeColor(redButton, R.color.simonRed, R.color.black, start, end);
                        break;
                    case 3:
//                        changeYellow();
                        changeColor(yellowButton, R.color.simonYellow, R.color.black, start, end);
                        break;
                    case 4:
//                        changeBlue();
                        changeColor(blueButton, R.color.simonBlue, R.color.black, start, end);
                        break;
                }
            }
        }
}


