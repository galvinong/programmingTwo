package exercisefive;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Galvin on 4/10/2015.
 */
public class RollDie implements Runnable {
    int timeMultiplier = 1;
    int diceNum;
    int prevNum = 0, currentNum = 0;

    @Override
    public void run() {

        //For loop for the number of times the dice rolls, default: 20
        for (int i = 0; i < 20; i++) {

            //Random number dice face 1-6
            diceNum = randInt(1, 6);

            try {
                //timeMultiplier increases after iteration, making it slower after each roll
                Thread.sleep(45 * timeMultiplier);
                System.out.print(diceNum + ",");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Iteration increases
            timeMultiplier++;
        }
        //Print out the last number of rolled dice
        System.out.println("\n" + diceNum);
    }

    public static void main(String[] args) {
        //Main to start the roll die code
        RollDie rolldie = new RollDie();
        rolldie.run();
    }

    public int randInt(int min, int max) {
        //Creates an arraylist for shuffling the numbers
        ArrayList<Integer> numArray = new ArrayList<Integer>();
        for (int i = min; i <= max; i++) {
            numArray.add(new Integer(i));
        }

        //Shuffle the numbers if the currentNum and prevNum are the same
        do {
            Collections.shuffle(numArray);
            currentNum = numArray.get(0);
        } while (currentNum == prevNum);

        //Sets the prevNum for checking
        prevNum = currentNum;
        //Return
        return currentNum;
    }
}



