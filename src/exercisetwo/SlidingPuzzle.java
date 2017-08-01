package exercisetwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Galvin on 4/30/2015.
 */
public class SlidingPuzzle {
    public static void main(String[] args) {
        PuzzleFrame puzzleFrame = new PuzzleFrame();
        puzzleFrame.init();
    }
}

class PuzzleFrame extends JFrame {
    void init() {
        PuzzlePanel puzzlePanel = new PuzzlePanel();
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(puzzlePanel);
        pack();
        setVisible(true);
        setTitle("Sliding Puzzle");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

final class PuzzlePanel extends JPanel {
    int row = 3;
    int col = 3;
    private final JButton[][] btn = new JButton[row][col];
    Integer nameBtn = 0;
    //Suggestion to make a font chooser for the game
    Font f = new Font("Garamond", Font.PLAIN, 40);

    //Things to do:
    //1. How are the numbers going to move?? getters and setters??, do a nested for loop to search for the empty button
    //2. Jumble the numbers up, num.Random(0-9)?, or store into a list shuffling numbers
    //3. Decide when the game is finished?, iterate through the numbers???
    //Universal mouse click event

    public PuzzlePanel() {

        setLayout(new GridLayout(row, col));
        //Execute outer once, and inner 3 times, continue on
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                nameBtn++;
                btn[r][c] = new JButton();
                btn[r][c].addActionListener(new TileActionListener());
                add(btn[r][c]);
                btn[r][c].setFont(f);
            }
        }
        rebuildTiles();
    }


    //Searches the tile with what string, used for searching the empty button
    private int[] findTile(String string) {
        int[] coordRC = new int[2];

        outerloop:
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (btn[r][c].getText().equals(string)) {
                    coordRC[0] = r;
                    coordRC[1] = c;
                    break outerloop;
                    //once the string has been found, assign it to coordinates and break out, return coord
                }
            }
        }
        return coordRC;
    }


    public int tileNumber(int x, int y) {
        return y + 1 + x * row;
    }

    //This rebuilds the tiles and disable one tile for clicking for the game
    public void rebuildTiles() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                btn[r][c].setText(Integer.toString(tileNumber(r, c)));
            }
        }

        // sets last tile to null
        btn[row - 1][col - 1].setEnabled(false);
        btn[row - 1][col - 1].setText("");
    }


    public class TileActionListener implements ActionListener {
        int[] coord = new int[2];

        @Override
        public void actionPerformed(ActionEvent e) {
            outerloop:
            //This finds the reference to the buttons, way to detect which button clicked
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (e.getSource().equals(btn[r][c])) {
                        coord[0] = r;
                        coord[1] = c;
                        System.out.println("if statement : coordinates: " + coord[0] + ", " + coord[1]);
                        // once found, breaks to outerloop above
                        break outerloop;
                    }
                }
            }
            moveLogic(coord);
            System.out.println("After movelogic coordinates: " + coord[0] + ", " + coord[1]);
        }
    }

    private void moveLogic(int[] coord) {
        // get the empty tile
        int[] emptyCoord = findTile("");

        System.out.println("The empty tile is in " + emptyCoord[0] + " , " + emptyCoord[1]);
        System.out.println("During move logic coordinates: " + coord[0] + ", " + coord[1]);

        // move is valid only if empty cell is on the same row or column as clicked cell
        if (coord[0] == emptyCoord[0] || coord[1] == emptyCoord[1]) {
            int sign = 1;

            //For being in the same columns
            if (coord[0] == emptyCoord[0]) {
                if (coord[1] - emptyCoord[1] < 0)
                    sign = -1;

                // loop through columns from empty cell and work towards clicked
                for (int y = 0; y < Math.abs(coord[1] - emptyCoord[1]); y++) {
                    btn[emptyCoord[0]][emptyCoord[1] + sign * y].setText(btn[emptyCoord[0]][emptyCoord[1] + sign * (y + 1)].getText());
                }
            }

            // same row
            if (coord[1] == emptyCoord[1]) {
                if (coord[0] - emptyCoord[0] < 0)
                    sign = -1;

                // loop through rows from empty cell and work towards
                // clicked
                for (int x = 0; x < Math.abs(coord[0] - emptyCoord[0]); x++) {
                    btn[emptyCoord[0] + sign * x][emptyCoord[1]].setText(btn[emptyCoord[0] + sign * (x + 1)][emptyCoord[1]].getText());
                }
            }

            // set previous null to enabled
            btn[emptyCoord[0]][emptyCoord[1]].setEnabled(true);

            // set clicked cell to null and disabled
            btn[coord[0]][coord[1]].setText("");
            btn[coord[0]][coord[1]].setEnabled(false);
        }
    }
}





