package rr;

import game.DiffEnum;

import java.util.Scanner;

public class RankingTest {
    private static Ranking r = null;
    public static void main(String[] args) {
        System.out.println("Test of class: Ranking\n");
        Scanner s = new Scanner(System.in);
        int n = 6;
        boolean loop = true;
        r = new Ranking();

        while(loop){

            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: getMaxPuntuationEasy");
            System.out.println("Test 3: getMaxPuntuationOriginal");
            System.out.println("Test 4: getMaxPuntuationHard");
            System.out.println("Test 5: evalNewRR");
            System.out.println("Option 6: Exit");

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
                    testGetMaxPuntuationEasy();
                    break;
                case 3:
                    testGetMaxPuntuationOriginal();
                    break;
                case 4:
                    testGetMaxPuntuationHard();
                    break;
                case 5:
                    System.out.println("Pick Difficulty");
                    System.out.println("1: EASY");
                    System.out.println("2: ORIGINAL");
                    System.out.println("3: HARD");
                    int diff = s.nextInt();
                    if(diff < 0 || diff > 3){
                        System.out.println("ERROR");
                        break;
                    }
                    System.out.println("Introduzca un valor para la entrada del ranking");
                    double val = s.nextDouble();
                    RREntry rrEntry = new RREntry("TEST", val);
                    testEvalNewRR(rrEntry, DiffEnum.values()[diff-1]);
                    break;
                case 6:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void testConstructor() {
        r = new Ranking();
    }

    private static void testGetMaxPuntuationEasy() {
        RREntry rrEntries[] = r.getMaxPuntuationEasy();
        printRREntries(rrEntries);
    }

    private static void testGetMaxPuntuationOriginal() {
        RREntry rrEntries[] = r.getMaxPuntuationOriginal();
        printRREntries(rrEntries);
    }

    private static void testGetMaxPuntuationHard() {
        RREntry rrEntries[] = r.getMaxPuntuationHard();
        printRREntries(rrEntries);
    }

    private static void testEvalNewRR(RREntry rrEntry, DiffEnum diff) {
        boolean res = r.evalNewRR(rrEntry, diff);
        if (res) System.out.println("Entrada añadida");
        else System.out.println("Entrada no añadida");
    }

    private static void printRREntries(RREntry[] rrEntries) {
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");
        for (RREntry rrEntry: rrEntries) System.out.println(rrEntry);
    }

}
