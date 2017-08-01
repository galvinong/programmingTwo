package exerciseseven;

import java.io.*;
import java.util.Random;

/**
 * Created by Galvin on 4/21/2015.
 */
public class LocalFile {
    static Random rand;

    public static void main(String[] args) throws IOException {
        //Create a file on local file system
        File outputFile = new File("Char.txt");
        File outputFileTwo = new File("Byte.txt");

        //First using byte stream, byte uses 8 bits
        FileOutputStream fileOutputStream = null;
        //Char stream // char uses 16 bits, therefore using more space
        FileWriter fileWriter = null;
        try {
            fileOutputStream = new FileOutputStream(outputFile);
            fileWriter = new FileWriter(outputFileTwo);
            Writer out = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

            //contains 10,000 random generated integer between 0 and 100,000
            for (int i = 0; i < 10000; i++) {
                Integer numberToWrite = randInt(0, 1000);
                out.write(numberToWrite);
                out.flush();
                fileWriter.write(numberToWrite);
                fileWriter.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {

            fileOutputStream.close();
            fileWriter.close();
        }
    }

    public static int randInt(int min, int max) {
        rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
