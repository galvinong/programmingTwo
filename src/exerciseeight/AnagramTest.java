package exerciseeight;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Galvin on 5/6/2015.
 */
public class AnagramTest {

    @Test
    public void testGenerate() throws Exception {
        String test = "Galvin";
        Anagram anagram = new Anagram(test);

        //Test for input and output length
        //Test whether input is an actually a String
        for (Object output : anagram.generate()) {
            assertNotNull("Output object returns null", output);
            assertTrue("Output is not a string", output instanceof String);
            assertEquals("Output is not of the same length as input", test.length(), output.toString().length());

            //list out all anagrams

        }
        System.out.println(anagram.generate());
    }
}