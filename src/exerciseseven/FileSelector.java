package exerciseseven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by Galvin on 5/5/2015.
 */
public class FileSelector {
    public static void main(String[] args) {
        new FileDisplayFrame();
    }
}

class FileDisplayFrame extends JFrame{
    public FileDisplayFrame() {
        super("File Selector");
        FileDisplayPanel fileDisplayPanel = new FileDisplayPanel();

        setLayout(new BorderLayout());
        add(new ButtonPanel(fileDisplayPanel), BorderLayout.NORTH);
        add(fileDisplayPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);
    }
}
class ButtonPanel extends JPanel{
    JButton selectFileBtn;
    JFileChooser fileChooser;

    public ButtonPanel(final FileDisplayPanel fileDisplayPanel){
        setLayout(new FlowLayout());
        fileChooser = new JFileChooser();
        selectFileBtn = new JButton("Select a file");

        add(selectFileBtn);

        selectFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader bufferedReader = null;
                String line;
                if (e.getSource() == selectFileBtn) {
                    int returnVal = fileChooser.showOpenDialog(fileDisplayPanel);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        //Store file when the file gets selected in the dialog
                        File file = fileChooser.getSelectedFile();
                        try {
                            bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));

                            fileDisplayPanel.fileContentsArea.append("Opening " + file.getName() + ". \n");

                            //while loop to get the contents of the file
                            do {
                                line = bufferedReader.readLine();
                                fileDisplayPanel.fileContentsArea.append(line + "\n");
                            } while (line != null);

                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                bufferedReader.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        fileDisplayPanel.fileContentsArea.append("Open command failed \n");
                    }
                }
            }
        });
    }
}


class FileDisplayPanel extends JPanel{
    //Panel to select a file from local file system

    //Create a file chooser

    public JTextArea fileContentsArea;
    JScrollPane scrollPane;

    public FileDisplayPanel() {
        setLayout(new FlowLayout());
        //Setup the textArea
        //Display the content with jtextarea with scrollbars

        fileContentsArea = new JTextArea(20, 50);
        fileContentsArea.setEditable(false);
        fileContentsArea.setLineWrap(true);
        fileContentsArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(fileContentsArea);
        add(scrollPane);

    }
}
