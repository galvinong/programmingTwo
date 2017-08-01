package exerciseeight;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Galvin on 5/7/2015.
 */
public class FileWriteTest {
    Random rand;

    //Test whether its a integer, by using inserting non integer?
    // and sees what exception it returns
    //same goes for the file name


    @Test
    public void testWriteByte() throws Exception {
        int[] intCheck;
        FileWrite fileWrite = new FileWrite();
        intCheck = fileWrite.getIntByte();

        for (int i = 0; i < 1000; i++) {
            Integer num = randInt(1, 1000);
            fileWrite.writeByte(num, "Byte.txt");
            intCheck[i] = num;
        }

        for (int i : intCheck) {
            assertTrue(i<=100000);
            assertTrue(i>=0);
        }

        assertEquals("Byte.txt",fileWrite.getFileName());
    }

    @Test
    public void testWriteChar() throws Exception {
        int[] intCheck;
        FileWrite fileWrite = new FileWrite();
        intCheck = fileWrite.getIntChar();
        for (int i = 0; i < 1000; i++) {
            Integer num = randInt(1, 1000);
            fileWrite.writeChar(num, "Char.txt");
        }

        for (int i : intCheck) {
            assertTrue(i<=100000);
            assertTrue(i>=0);
        }
        assertEquals("Char.txt",fileWrite.getFileName());
    }

    public Integer randInt(int min, int max) {
        rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}