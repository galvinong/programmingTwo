package exercisefour;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Galvin on 3/17/2015.
 */
public class Circles {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Circles");
                frame.setContentPane(new CirclePanel(1));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 500);
                frame.setVisible(true);
            }
        });
    }
}

class CirclePanel extends JPanel {

    //Arraylist to keep references of all the circles drawn
    ArrayList<Circle> circleArrayList = new ArrayList<Circle>();
    int iterations = 1;
    int numCircles;

    //Depth of green colour, changes when number of iterations goes up
    int green = 255;
    Color nGreen = Color.GREEN;

    //
    public CirclePanel(int n) {
        numCircles = n;
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        //Start the recursion
        drawCircle(1);

        //Draw everything out using arraylist after recursion
        for (Circle output : circleArrayList) {
            g.setColor(output.getColor());
            g.drawOval(output.getPointX(), output.getPointY(), output.getDiameter(), output.getDiameter());
            g.fillOval(output.getPointX(), output.getPointY(), output.getDiameter(), output.getDiameter());
        }
    }

    //1st iteration 1 circle with width /3
    //2nd iteration 3 circles with width /9
    //3rd iteration 9 circles with width /27
    public void drawCircle(int numCircles) {
        //Powers of 3, recursion settles this by dividing by 3 each recurse
        double diameter = (getWidth() / Math.pow(3, iterations++));

        //reduces the green value whenever new circle appears
        nGreen = nGreen.darker();

        //This draws the circle in the middle, always taking the middle point
        //Condition for exiting the recurse, *width of circle <=1
        if (diameter > 1) {
            for (int i = 0; i < numCircles; i++) {
                int x = (int) (((i * 3 * diameter) + diameter));
                int y = (int) (getHeight() - diameter) / 2;
                //Creates a circle object and add it into a array list for reference to draw later
                Circle circle = new Circle(x, y, (int) diameter, nGreen);
                circleArrayList.add(circle);
            }
            //Number of circles increases in 3
            numCircles *= 3;
            //Green goes darker
            green -= 50;
            //recurse at the end
            drawCircle(numCircles);
        } else {
            return;
        }
    }
}

class Circle {
    private int pointX, pointY, diameter;
    private Color color;

    public Circle(int x, int y, int diameter, Color color) {
        this.pointX = x;
        this.pointY = y;
        this.diameter = diameter;
        this.color = color;
    }

    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public int getDiameter() {
        return diameter;
    }

    public Color getColor() {
        return color;
    }
}







