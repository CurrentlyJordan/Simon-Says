package nyc.c4q.jordansmith.simonsays;

import java.util.Random;

/**
 * Created by jordansmith on 9/26/16.
 */

public class GameRound {

    public void playGame(int round){
        MainActivity main = new MainActivity();
        int [] simonSequence = new int[round];
        Random rand = new Random();
        for (int i = 0; i < round ; i++) {
            int randNumb = rand.nextInt(4) + 1;
            simonSequence[i] = randNumb;
        }
        for (int j = 0; j < simonSequence.length; j++) {

            switch(simonSequence[j]){
                case 1:
                    main.changeGreen();
                    break;
                case 2:
                    main.changeRed();
                    break;
                case 3:
                    main.changeYellow();
                    break;
                case 4:
                    main.changeBlue();
                    break;
            }
        }
    }
}
