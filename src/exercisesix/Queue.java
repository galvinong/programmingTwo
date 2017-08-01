package exercisesix;

/**
 * Created by Galvin on 4/21/2015.
 */
public class Queue {

    private Process head, tail;

    public synchronized void add(Object o) {
        Process c = new Process(o);
        if (tail == null) {
            head = c;
        } else {
            tail.next = c;
        }
        c.next = null;
        tail = c;
        notifyAll();
    }

    public synchronized Object remove()
            throws InterruptedException {
        while (head == null) {
            wait();
        }
        Process c = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        ;
        return c.contents;
    }

    public static void main(String[] args) {

    }

}

class Process {
    Process next;
    Object contents;

    public Process(Object o) {
        contents = o;
    }
}