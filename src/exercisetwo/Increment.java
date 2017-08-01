package exercisetwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Galvin on 4/30/2015.
 */
public class Increment {

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.init();
    }
}

class MainFrame extends JFrame {
    private JButton btnIncrement, btnReset;
    private JTextField textField;
    private Integer i = 0;

    public void init() {
        //Creation of container and set to flow layout without panel
        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        //Creation of buttons and text field, set textfiled edit to false
        btnIncrement = new JButton("Increment");
        btnReset = new JButton("Reset");
        textField = new JTextField(5);
        textField.setEditable(false);

        //Adding buttons and field to contrainer
        add(btnIncrement);
        add(btnReset);
        add(textField);

        //Adding Action listeners
        //Increment by one, when button is pressed, set Integer to string
        btnIncrement.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               i++;
                                               textField.setText(i.toString());
                                           }
                                       }
        );
        //Reset to zero, when this reset button is pressed
        btnReset.addActionListener(new ActionListener() {
                                       @Override
                                       public void actionPerformed(ActionEvent e) {
                                           i = 0;
                                           textField.setText("0");
                                       }
                                   }
        );

        //Misc stuff to setup container
        setTitle("Increment and Reset");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setVisible(true);
    }
}

