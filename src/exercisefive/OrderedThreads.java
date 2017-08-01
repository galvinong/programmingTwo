package exercisefive;


/**
 * Created by Galvin on 4/9/2015.
 */
public class OrderedThreads {
    public static void main(String[] args) {
        ControlThread threadOne = new ControlThread("ThreadOne", 10);
        threadOne.start();

        ControlThread threadTwo = new ControlThread("ThreadTwo", 5);
        threadTwo.start();

        ControlThread threadThree = new ControlThread("ThreadThree", 1);
        threadThree.start();
    }
}

class ControlThread implements Runnable {
    String threadName;
    Thread thread;
    int timeMultiplier;

    //Contains the name of thread, and sleep timer
    public ControlThread(String name, int num) {
        threadName = name;
        timeMultiplier = num;
    }

    //Sleeps according to the timer given, and exits afterwards
    @Override
    public void run() {
        System.out.println("Running thread " + threadName);
        try {
            Thread.sleep(300 * timeMultiplier);
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted");
        }
        System.out.println("***Thread" + threadName + " exiting");
        return;
    }

    public void start() {
        System.out.println("Starting thread " + threadName);

        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
}


