package treegraph;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;


/**
 * Created by Galvin on 5/3/2015.
 */
public class BinarySearchTreeTest<T> extends TestCase {
    Random rand;

    //Check whether returns an iterator
    @Test
    public void testIterator() throws Exception {
        BinarySearchTree<T> binarySearchTree = new BinarySearchTree<T>();
        assertNotNull(binarySearchTree.iterator());
    }

    //Test adding by calculating the size after adding
    @Test
    public void testAdd() throws Exception {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        for (int i = 0; i < 50; i++) {
            binarySearchTree.add(i);
            assertEquals(i + 1, binarySearchTree.size());
        }
    }

    //Check whether remove works, by removing after adding
    //Although it is zero, and may fall through if the code doesnt work at all, the testAdd will catch fail
    @Test
    public void testRemove() throws Exception {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        for (int i = 0; i < 50; i++) {
            binarySearchTree.add(i);
            binarySearchTree.remove(i);
        }
        assertEquals("Remove not working", 0, binarySearchTree.size());
    }

    //Check whether that specific position contains that element
    @Test
    public void testContains() throws Exception {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        for (int i = 0; i < 50; i++) {
            binarySearchTree.add(i);
            assertEquals("contains not working", true, binarySearchTree.contains(i));
        }
    }

    //Test depth
    public void testDepth() throws Exception {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();

        for (int i = 0; i < 70; i++) {
            binarySearchTree.add(randInt(1,1000000));
        }
        binarySearchTree.getAvgDepth();

    }

    public int randInt(int min, int max) {
        rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}