package exercisesix;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Galvin on 4/15/2015.
 */
//Using synchronized method defaults to "this"
public class SynchRollDie extends RollDie {
    //Synchronised, only allowed to retrieve when the last roll is finished

    public synchronized int getVal() {
        //Method should not return value until the die has finished rolling
        //Any thread that calls this should block until method returns .wait??
        while (!ready) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ready = false;
        this.notify();
        return finalNum;
    }

    public static void main(String[] args) {
        int finalNum = 0;

        //Use an array list, so that references are kept
        ArrayList<SynchRollDie> threads = new ArrayList<SynchRollDie>();
        for (int i = 0; i < 5; i++) {
            SynchRollDie rolldie = new SynchRollDie();
            new Thread(rolldie).start();
            threads.add(rolldie);
        }

        //Multi threaded here
        for (SynchRollDie output : threads) {
            finalNum += output.getVal();
        }
        System.out.println("\nFinal number " + finalNum);
    }
}

class RollDie implements Runnable {

    int diceNum, finalNum;
    int prevNum = 0, currentNum = 0;
    boolean ready = false;
    int timeMultiplier = 1;

    @Override
    public void run() {
        //Have to adjust synchronized range, as it affects on the output of code
        synchronized (this) {
            //For loop for the number of times the dice rolls, default: 20
            for (int i = 0; i < 20; i++) {
                //Random number dice face 1-6
                diceNum = randInt(1, 6);
                ready = true;
                this.notify();
                finalNum = diceNum;
                try {
                    //timeMultiplier increases after iteration, making it slower after each roll
                    Thread.sleep(45 * timeMultiplier);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.print(diceNum + ",");
            }
        }
        //Iteration increases
        timeMultiplier++;
        System.out.println("\n " + diceNum);
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