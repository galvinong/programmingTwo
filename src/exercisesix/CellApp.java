package exercisesix;

/**
 * Created by Galvin on 4/21/2015.
 */

public class CellApp {

    public static void main(String[] args) {

        Cell c1 = new Cell(15);
        Cell c2 = new Cell(23);

        Thread t1 = new swapThread(c1, c2);
        Thread t2 = new swapThread(c2, c1);

        t1.start();
        t2.start();
    }
}

class Cell {
    private static int counter = 0;
    private int value, id;

    public Cell(int v) {
        value = v;
        id = counter++;
    }

    synchronized int getValue() {
        return value;
    }

    synchronized void setValue(int v) {
        value = v;
    }

    //Add both setId and getId
    synchronized void setId(int num) {
        this.id = num;
    }

    synchronized int getId() {
        return id;
    }

    //Value is swapped however Cell id is still the same
    synchronized void swapValue(Cell otherCell, int idNum) {
        int t = getValue();
        int v = otherCell.getValue();

        //Set ID number and value
        setValue(v);
        setId(idNum);

        //Set the current ID and value to the other cell
        otherCell.setValue(t);
        otherCell.setId(id);


    }
}

class swapThread extends Thread {

    Cell cell, othercell;

    public swapThread(Cell c, Cell oc) {
        cell = c;
        othercell = oc;
    }

    public void run() {
        cell.swapValue(othercell, othercell.getId());
    }
}
