package test;

/**
 * Created by Galvin on 5/2/2015.
 */
public class Test {
    public static void main(String[] args) {
        double x = 1.6;
        for (int i = 0; i < 50; i++) {
            x = Math.sqrt(x);
            System.out.println("SQRT "+ x);
        }

        for (int j = 0; j < 50; j++) {
            x = x * x;
            System.out.println("MULTIPLY " + x);
        }
    }
}
