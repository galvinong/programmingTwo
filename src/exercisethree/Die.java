package exercisethree;

/**
 * Created by Galvin on 4/14/2015.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;


public class Die extends JFrame {
    //List<DiePanel> shapeList;
    DiePanel[] die = new DiePanel[6];
    int dieNum;
    Color color;
    int i = 0;

    public Die(int dieNum) {
        this.dieNum = dieNum;
        //Instantiate the die shape panels
        //Add them to the frame containing them
        for (i = 0; i < dieNum; i++) {
            die[i] = new DiePanel(50);
            add(die[i]);
        }
    }

    public void init() {
        //This readjusts the layout, if die is 1, just leave it
        if (dieNum == 1 || dieNum == 0) {
            setLayout(new GridLayout(1, 1));
        } else {
            //This readjusts based on the number of dies you have, eg 3 rows 2 col for 4 die
            setLayout(new GridLayout(dieNum - 1, dieNum - 2));
        }

        color = new Color(0, 0, 0);
        setVisible(true);
        setTitle("Dice");
        setSize(350, 350);
        this.getContentPane().setBackground(color);
        //APPARENTLY PUTTING MOUSE LISTENER HERE MESS UP CODE, because of init being called many times
    }

    public void updateVal(int i) {
        //Logic for graphics to add/remove/leave it alone
        //Checks for how much has already been drawn, and minus that off to draw the rest
        if (i > dieNum) {
            //i is remainder to how much to add
            int num = i - dieNum;
            for (int j = 0; j < num; j++) {
                dieNum++;
                i--;
                die[i] = new DiePanel(50);
                add(die[i]);
            }
        } else if (i < dieNum) { //Checks
            //if i is lesser, run it for the amount to remove
            i = dieNum - i;
            //System.out.println("i is " + i + ", dieNum is " + dieNum);
            for (int k = 0; k < i; k++) {
                dieNum--;
                remove(die[dieNum]);
                //System.out.println("i is " + i + ", dieNum is " + dieNum);
            }
        }
        repaint();
    }

    public static void main(String[] args) {

        RollDie rolldie = new RollDie(1);
        rolldie.init();
        rolldie.run();
        rolldie.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }
}

class DiePanel extends JPanel {
    int length;
    Shape shape;
    Color white = new Color(255, 251, 247);
    Color black = new Color(0, 0, 0);
    Graphics2D g2d;

    public DiePanel(int length) {
        this.length = length;
        shape = new Ellipse2D.Double(0, 0, length, length);
        this.setBackground(black);
        //setLayout(new BorderLayout());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        //System.out.println("Paint");
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Sets the size of each panel
        setSize(100, 100);

        //int w = (int) ((getWidth() - insets.left - insets.right) / 2);
        //int h = (int) ((getHeight() - insets.top - insets.bottom) / 2);
        //Get w and h based on the panel sizes, therefore any resizing wont affect the box itself

        int w = getWidth() / 2;
        int h = getHeight() / 2;
//        System.out.println("Width :" + w);
//        System.out.println("Height :" + h);
        //This centers the graphics within the box
        g2d.translate(w, h);

        g2d.setColor(white);
        g2d.fill(shape);
    }
}

class RollDie extends Die implements Runnable {
    int timeMultiplier = 1;
    int diceNum;
    int prevNum = 0, currentNum = 0;

    public RollDie(int dieNum) {
        super(dieNum);
    }

    @Override
    public void run() {
        timeInt();
    }

    public void timeInt() {
        for (int i = 0; i < 20; i++) {
            //Random number dice face 1-6
            diceNum = randInt(1, 6);
            try {
                //timeMultiplier increases after iteration, making it slower after each roll
                Thread.sleep(45 * timeMultiplier);
                //Have to updateVal here, do the animation start here
                super.updateVal(diceNum);
                super.init();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Iteration increases
            timeMultiplier++;
        }
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
