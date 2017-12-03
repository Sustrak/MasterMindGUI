package users;

import game.DiffEnum;

import java.util.Scanner;

public class GameTripleTest {
    private static GameTriple g = null;
    public static void main(String[] args) {
        System.out.println("Test of class: GameTriple\n");
        Scanner s = new Scanner(System.in);
        int n = 10;
        boolean loop = true;
        double val = 0.;
        g = new GameTriple();

        while(loop){

            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: setEasy");
            System.out.println("Test 3: setOriginal");
            System.out.println("Test 4: setHard");
            System.out.println("Test 5: incEasy");
            System.out.println("Test 6: incOriginal");
            System.out.println("Test 7: incHard");
            System.out.println("Test 8: printGameTriple");
            System.out.println("Test 9: getScore");
            System.out.println("Option 10: Exit");


            System.out.println("\nSeleccione test");
            int test = s.nextInt();
            while(test < 0 || test > n){
                System.out.println("Test no valido");
                test = s.nextInt();
            }

            switch(test){

                case 1:
                    testConstructor();
                    break;
                case 2:
                    System.out.println("Introduzca un valor");
                    val = s.nextDouble();
                    testSetEasy(val);
                    break;
                case 3:
                    System.out.println("Introduzca un valor");
                    val = s.nextDouble();
                    testSetOriginal(val);
                    break;
                case 4:
                    System.out.println("Introduzca un valor");
                    val = s.nextDouble();
                    testSetHard(val);
                    break;
                case 5:
                    testIncEasy();
                    break;
                case 6:
                    testIncOriginal();
                    break;
                case 7:
                    testIncHard();
                    break;
                case 8:
                    printGameTriple();
                    break;
                case 9:
                    testGetScore();
                    break;
                case 10:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
    private static void testConstructor() {
        g = new GameTriple();
        printGameTriple();
    }
    private static void testSetEasy(double val) {
        g.setEasy(val);
        printGameTriple();
    }
    private static void testSetOriginal(double val) {
        g.setOriginal(val);
        printGameTriple();
    }
    private static void testSetHard(double val) {
        g.setHard(val);
        printGameTriple();
    }
    private static void testIncEasy() {
        g.incEasy();
        printGameTriple();
    }
    private static void testIncOriginal() {
        g.incOriginal();
        printGameTriple();
    }
    private static void testIncHard() {
        g.incHard();
        printGameTriple();
    }
    private static void printGameTriple() {
        System.out.println("EASY   ORIGINAL    HARD");
        System.out.println(g);
    }
    private static void testGetScore() {
        System.out.println("EASY     " + g.getScore(DiffEnum.EASY));
        System.out.println("ORIGINAL " + g.getScore(DiffEnum.ORIGINAL));
        System.out.println("HARD     " + g.getScore(DiffEnum.HARD));
    }

}
