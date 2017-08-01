package exercisetwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by Galvin on 4/30/2015.
 */
public class MouseSpeed {
    public static void main(String[] args) {
        MouseFrame mf = new MouseFrame();
        mf.init();
    }
}

class MouseFrame extends JFrame {

    void init() {

        ButtonPanel tpOne = new ButtonPanel();
        ListenerPanel tpTwo = new ListenerPanel(tpOne);

        Container container = getContentPane();
        //container.setLayout(new GridLayout(2,1));

        container.add(tpOne, BorderLayout.NORTH);
        container.add(tpTwo, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setTitle("Mouse Listener");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}


class ButtonPanel extends JPanel {
    JTextField posField;
    JButton btnReset;

    public ButtonPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //Box to show coordinates
        posField = new JTextField("Test", 10);
        btnReset = new JButton("Reset");
        posField.setEditable(false);
        add(posField);
        add(btnReset);
    }
}


class ListenerPanel extends JPanel {
    private final JTextArea emptyArea;
    private int xPos, yPos, numRecorded, speed;

    public ListenerPanel(final ButtonPanel tpOne) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setSize(20, 30);

        //Empty area that detects mouse movements
        emptyArea = new JTextArea(60, 80);
        emptyArea.setEditable(false);
        add(emptyArea);

        emptyArea.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {

                //Record of how many counts it has done
                numRecorded++;

                System.out.println(speed);

                //"average speed" means the distance travelled between mouse coordinates per number of mouse moved events received.
                //xPos and yPos is the previous position
                //e.getX and e.getY is the current position

                //Two algorithms to the distance between two points
                speed += Math.sqrt(Math.pow(xPos - e.getX(), 2) + Math.pow(yPos - e.getY(), 2));
                //speed += Math.max(Math.abs(xPos-e.getX()), Math.abs(yPos-e.getY()));

                xPos = e.getX();
                yPos = e.getY();

                //Divide by the number of times recorded
                String output = "X: " + e.getX() + " Y: " + e.getY() + " Speed:" + (speed / numRecorded) + " Number of Times: " + numRecorded;

                tpOne.posField.setText(output);
            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });

        //Resets everything back to zero
        tpOne.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speed = 0;
                numRecorded = 0;
            }
        });

    }
}
