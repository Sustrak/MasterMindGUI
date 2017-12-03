package rr;

import java.util.Scanner;

public class RREntryTest {
    
    private static RREntry r = null;
    
    public static void main(String[] args) {
        
        System.out.println("Test of class: RREntry\n");
        Scanner s = new Scanner(System.in);
        
        int n = 5;
        double score = 0.;
        r = new RREntry("NULL", 0.0);

        while(true){

            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: testIsBigger");
            System.out.println("Test 3: testGetScore");
            System.out.println("Test 4: printRREntry");
            System.out.println("Option 5: Exit");

            System.out.println("\nSeleccione test");
            int test = s.nextInt();
            while(test < 0 || test > n){
                System.out.println("Test no valido");
                test = s.nextInt();
            }

            switch(test){
                case 1:
                    System.out.println("Introduzca una score");
                    score = s.nextDouble();
                    System.out.println("Introduzca un username");
                    String username = s.next();
                    testConstructor(username, score);
                    break;
                case 2:
                    System.out.println("Introduzca una score para la RREntry a comparar");
                    score = s.nextDouble();
                    s.nextLine();
                    System.out.println("Introduzca un username para la RREntry a comparar");
                    String name = s.nextLine();
                    RREntry rrEntry = new RREntry(name, score);
                    testIsBigger(rrEntry);
                    break;
                case 3:
                    testGetScore();
                    break;
                case 4:
                    printRREntry();
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        }
    }

    private static void testConstructor(String username, double score) {
        r = new RREntry(username, score);
        printRREntry();
    }

    private static void testIsBigger(RREntry rrEntry) {
        boolean b = rrEntry.isBigger(r);
        System.out.println(b);
    }

    private static void testGetScore() {
        double score = r.getScore();
        System.out.println(score);
    }

    private static void printRREntry() {
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");
        System.out.println(r);
    }
}
