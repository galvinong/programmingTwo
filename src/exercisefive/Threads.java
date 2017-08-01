package exercisefive;


/**
 * Created by Galvin on 4/7/2015.
 */
public class Threads {
    public static void main(String[] args) {
        //Create threads
        Thread t0 = new MyThread();
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        //Start the threads, run method is set to interrupt when run immediately
        t0.start();
        t1.start();
        t2.start();
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(this.getName() + " started");

        //Stops the thread
        if (!this.isInterrupted()) {
            this.interrupt();
        }

        //If interrupted print out into console
        if (this.isInterrupted()) {
            System.out.println(this.getName() + " terminated");
            return;
        }
    }


}


