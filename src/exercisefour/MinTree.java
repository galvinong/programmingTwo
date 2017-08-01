package exercisefour;

/**
 * Created by Galvin on 3/13/2015.
 */
public class MinTree {

    public static void main(String[] args) {
        Tree tree = new Tree(24, new Tree(45, null, new Tree(8, null, null)), new Tree(17, new Tree(74, null, null), null));
        MinTree mt = new MinTree();
        System.out.println("Minimum is :" + mt.findMin(tree));
    }

    //This gets the value of either right or left, reducing the amount of messy code
    public int findMin(Tree t) {
        return findMin(t, t.getVal());
    }

    public int findMin(Tree t, int min) {
        //First node prints out
        System.out.println(min);
        int currentMin = min;

        //Checks for left and right node for null
        if (t.left() != null && t.right() != null) {
            System.out.println("Type 1");
            //Assigns the tree left and right to values
            final int left = findMin(t.left);
            final int right = findMin(t.right);

            //Compares the left or right, which is smaller and then assigns
            if (left < right) {
                currentMin = left;
            } else {
                currentMin = right;
            }
        } else if (t.left() != null) {  //If left is not null, use that node num
            System.out.println("Type 2");
            currentMin = findMin(t.left);
        } else if (t.right() != null) {  //If right is not null, use that node num
            System.out.println("Type 3");
            currentMin = findMin(t.right);
        }

        //Choose which to return
        if (currentMin < min) {
            return currentMin;
        } else {
            return min;
        }
    }
}

class Tree {
    private int val;
    Tree left;
    Tree right;

    public Tree(int val, Tree left, Tree right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public Tree left() {
        return left;
    }

    public Tree right() {
        return right;
    }
}


//    public int findMin() {
//        if (iterations+1 == arr.length) {
//            return arr[iterations];
//        }
//        if (arr[iterations] < arr[iterations + 1] && iterations < arr.length) {
//            arr[iterations + 1] = arr[iterations];
//            iterations++;
//            findMin();
//        } else if (arr[iterations] > arr[iterations + 1]) {
//            iterations++;
//            findMin();
//        }
//        return arr[iterations];
//    }