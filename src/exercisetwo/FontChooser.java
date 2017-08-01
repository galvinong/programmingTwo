package exercisetwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Galvin on 4/30/2015.
 */
public class FontChooser {
    public static void main(String[] args) {
        FontFrame mf = new FontFrame();
        mf.init();
    }
}

class FontFrame extends JFrame {
    void init() {
        BoldItalicPanel boldItalicPanel = new BoldItalicPanel();
        BoxListenerPanel boxListenerPanel = new BoxListenerPanel(boldItalicPanel);
        ComboBoxPanel comboBoxPanel = new ComboBoxPanel(boldItalicPanel);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        //GUI is split into 3 panels
        //boxListenerPanel contains checkbox for bold and italic
        //comboBoxPanel contains font combobox
        //boldItalicPanel contains textField and ok button

        container.add(boxListenerPanel);
        container.add(comboBoxPanel);
        container.add(boldItalicPanel);

        pack();
        setVisible(true);
        setTitle("Font Chooser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class BoxListenerPanel extends JPanel {
    private final JCheckBox boldBox, italicBox;

    public BoxListenerPanel(final BoldItalicPanel tpThree) {
        setLayout(new GridLayout(2, 1));

        boldBox = new JCheckBox("Bold");
        italicBox = new JCheckBox("Italic");

        add(boldBox);
        add(italicBox);

        boldBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //Boolean to determine whether italic to remember previous state
                boolean italic = tpThree.textField.getFont().isItalic();

                //If bold box is selected, checks for italic, if true set both BOLD and ITALIC
                //if italic is not sure, set it to just BOLD
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (italic == true) {
                        tpThree.setBoldandItalicFont();
                    } else {
                        tpThree.setBoldFont();
                    }
                    //If bold is not deselected, check for italic font previously, if not set it back to plain font
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    if (italic == true) {
                        tpThree.setItalicFont();
                    } else {
                        tpThree.setPlainFont();
                    }
                }
            }
        });

        //ITALIC CHECKBOX LISTENER
        italicBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //Boolean to determine whether bold font is already BOLD
                boolean bold = tpThree.textField.getFont().isBold();

                //If the font is already BOLD, then set both ITALIC AND BOLD, if not just ITALIC
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (bold == true) {
                        tpThree.setBoldandItalicFont();
                    } else {
                        tpThree.setItalicFont();
                    }
                    //If the font was previously ITALIC, set it back to italic, if not, set it back to PLAIN
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    if (bold == true) {
                        tpThree.setBoldFont();
                    } else {
                        tpThree.setPlainFont();
                    }
                }

            }
        });
    }
}

class BoldItalicPanel extends JPanel {

    JTextField textField;
    JButton okBtn;

    public BoldItalicPanel() {
        setLayout(new FlowLayout());
        okBtn = new JButton("OK");
        textField = new JTextField("Test", 30);
        textField.setEditable(false);
        //Add buttons to panel
        add(textField);
        add(okBtn);
    }

    public void setTextArea(String input) {
        textField.setText(input);
    }

    public void setBoldFont() {
        textField.setFont(textField.getFont().deriveFont(Font.BOLD));
    }

    public void setBoldandItalicFont() {
        textField.setFont(textField.getFont().deriveFont(Font.ITALIC + Font.BOLD));
    }

    public void setItalicFont() {
        textField.setFont(textField.getFont().deriveFont(Font.ITALIC));
    }

    public void setPlainFont() {
        textField.setFont(textField.getFont().deriveFont(Font.PLAIN));
    }
}

class ComboBoxPanel extends JPanel {
    public final JComboBox comboBox;

    GraphicsEnvironment graphicsEnviro;

    //Panel coding for font comboBox
    //Take tpThree to modify text area
    public ComboBoxPanel(final BoldItalicPanel tpThree) {

        setLayout(new FlowLayout());

        //Gets the avaliable font family names and assign it into string array
        graphicsEnviro = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final String[] fontString = graphicsEnviro.getAvailableFontFamilyNames();

        //Add the font string array to the combobox
        comboBox = new JComboBox(fontString);
        add(comboBox);

        //Add action listener to combobox
        //final BoldItalicPanel tpThree = new BoldItalicPanel();
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    String fontSelected = (String) e.getItem();
                    //Set the font text to the text area
                    tpThree.setTextArea(fontSelected);

                    //Creates the font for the text to be displayed
                    //Style is dervived from getStyle
                    String fontName = (fontSelected);
                    System.out.println(fontName);
                    Font f = new Font(fontName, tpThree.textField.getFont().getStyle(), 18);
                    //Finally sets the font
                    tpThree.textField.setFont(f);
                }
            }
        });
    }
}
