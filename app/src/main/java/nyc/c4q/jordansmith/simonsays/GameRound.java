package nyc.c4q.jordansmith.simonsays;

/**
 * Created by jordansmith on 9/26/16.
 */

public class GameRound {
    MainActivity main = new MainActivity();

    boolean isgameGoing = true;
    int round = 1;

    public void game(){
        while(isgameGoing){
            main.simonArrayAdder();
            main.simonArrayDisplay();



        }

    }









}