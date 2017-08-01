package exercisethree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import static java.awt.Color.getHSBColor;

/**
 * Created by Galvin on 4/30/2015.
 */
public class CircleColour {
    //draws a circle in its main window
    //Colour the circle in some random colour
    //Add a listener so that whenever the user clicks within the circle, changes to random color

    public static void main(String[] args) {
        CircleFrame frame = new CircleFrame();
        frame.init();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
}

class CircleFrame extends Frame {
    ShapePanel circleOne, circleTwo, squareOne, squareTwo;

    public CircleFrame() {
        //Constructor, create shapes here
        //one value for circle, two values for rect/square
        circleOne = new ShapePanel(50);
        circleTwo = new ShapePanel(100);
        squareOne = new ShapePanel(50, 50);
        squareTwo = new ShapePanel(100, 100);
    }

    public void init() {
        //Used boxlayout to easily change on how they're arranged
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setSize(640, 480);
        setVisible(true);
        setTitle("Circle Colour");
        //After instantiating, add this to the panel here
        add(circleOne);
        add(circleTwo);
        add(squareOne);
        add(squareTwo);
    }

    //PREVIOUS CODE, VERY INEFFICIENT
//    @Override
//    public void update(Graphics g){
//        System.out.println("Update!");
//        g2d = (Graphics2D)g;
//        color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
//        g2d.setColor(color);
//        g2d.fillOval((int)circle.getX(), (int)circle.getY(), 100, 100);
//        g2d.fillOval((int)circleTwo.getX(), (int)circleTwo.getY(), 100, 100);
//        g2d.fillRect(rect.x, rect.y, 100, 100);
//        g2d.fillRect(rectTwo.x, rectTwo.y, 100, 100);
//    }
//
//    //Do a seperate method for this
//    @Override
//    public void paint(Graphics g){
//        System.out.println("Paint!");
//        super.paint(g);
//            g2d = (Graphics2D)g;
//            //Create a arraylist of circles? what is the object above circle and rectangle, enhanced for loop??
//            g2d.fillOval((int)circle.getX(), (int)circle.getY(), 100, 100);
//            g2d.fillOval((int)circleTwo.getX(), (int)circleTwo.getY(), 100, 100);
//            g2d.fillRect(rect.x, rect.y, 100, 100);
//            g2d.fillRect(rectTwo.x, rectTwo.y, 100, 100);
//            g2d.setColor(color);
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e){
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e){
//    }
//
//    public void mouseEntered(MouseEvent e){
//    }
//
//    public void mouseExited(MouseEvent e){
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e){
//        if((e.getButton() == 1) && circle.contains(e.getX(), e.getY())){
//            repaint((int)circle.getX(), (int)circle.getY(),100, 100);
//        }else if ((e.getButton() == 1) && circleTwo.contains(e.getX(), e.getY())){
//            repaint((int)circleTwo.getX(), (int)circleTwo.getY(),100,100);
//        }else if((e.getButton() == 1) && rect.contains(e.getX(), e.getY())){
//            repaint((int)rect.getX(), (int)rect.getY(),100,100);
//        }else if((e.getButton() == 1) && rectTwo.contains(e.getX(), e.getY())){
//            repaint((int)rectTwo.getX(), (int)rectTwo.getY(),100,100);
//        }
//    }

}

class ShapePanel extends JPanel implements MouseListener {
    int length, width;
    Shape shape;
    Color color;
    Graphics2D g2d;

    //Because shapes are in a panel that only contains this shape, no coordinates are required here

    public ShapePanel(int length) {
        //only length is needed here, because for a circle
        this.length = length;
        addMouseListener(this);
        shape = new Ellipse2D.Double(0, 0, length, length);
    }

    public ShapePanel(int length, int width) {
        //length and width for either rectangle or square
        this.length = length;
        this.width = width;
        addMouseListener(this);
        shape = new Rectangle2D.Double(0, 0, width, length);
    }

    //Methods here to override mouse listener
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Color is refreshed here only when clicked
        //HSB color is used because of greater range, hue, saturation, brightness
        //System.out.println("CLICKED");
        //System.out.println(e.getPoint());
        if (shape.contains(e.getPoint())) {
            color = getHSBColor((float) Math.random(), 1, 1);
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //If color is inputted, here, panel will change color when resizing
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fill(shape);
    }
}

