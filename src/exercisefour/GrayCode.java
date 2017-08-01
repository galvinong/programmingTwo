package exercisefour;

import java.util.Scanner;

/**
 * Created by Galvin on 4/4/2015.
 */
public class GrayCode {

    public static void main(String[] main) {
        System.out.println("Enter number of bits");
        Scanner input = new Scanner(System.in);
        int integerInput = input.nextInt();
        gray("", integerInput);
    }

    //append gray code to the end of prefix
    //pfix is the string that prints, n is the number of digits
    public static void gray(String pFix, int n) {
        if (n == 0) {
            System.out.println(pFix);
        } else {
            //example 2bit gray code, 00 01
            gray(pFix + "0", n - 1);
            reverse(pFix + "1", n - 1);
        }
    }

    //append reverse of gray order code to prefix
    public static void reverse(String pFix, int n) {
        if (n == 0) {
            System.out.println(pFix);
        } else {
            //example 2 bit gray code, 11 10
            gray(pFix + "1", n - 1);
            reverse(pFix + "0", n - 1);
        }
    }
}
