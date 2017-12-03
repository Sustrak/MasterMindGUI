package rr;

import java.util.Scanner;

public class RecordsTest {
    private static Records r = null;
    public static void main(String[] args) {
        System.out.println("Test of class: Records\n");
        Scanner s = new Scanner(System.in);
        int n = 10;
        boolean loop = true;
        double score = 0.;
        RREntry rrEntry = null;
        r = new Records();

        while(loop){

            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: testGetMaxExp");
            System.out.println("Test 3: testGetMaxWinningSpree");
            System.out.println("Test 4: testGetMaxPlayedGames");
            System.out.println("Test 5: testGetMaxWinnedGames");
            System.out.println("Test 6: testEvalMaxExp");
            System.out.println("Test 7: testEvalMaxWinningSpree");
            System.out.println("Test 8: testEvalmaxPlayedGames");
            System.out.println("Test 9: testEvalmaxWinnedGames");
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
                    testGetMaxExp();
                    break;
                case 3:
                    testGetMaxWinningSpree();
                    break;
                case 4:
                    testGetMaxPlayedGames();
                    break;
                case 5:
                    testGetMaxWinnedGames();
                    break;
                case 6:
                    System.out.println("Introduzca un valor para la entrada del record");
                    score = s.nextDouble();
                    rrEntry = new RREntry("TEST", score);
                    testEvalMaxExp(rrEntry);
                    break;
                case 7:
                    System.out.println("Introduzca un valor para la entrada del record");
                    score = s.nextDouble();
                    rrEntry = new RREntry("TEST", score);
                    testEvalMaxWinningSpree(rrEntry);
                    break;
                case 8:
                    System.out.println("Introduzca un valor para la entrada del record");
                    score = s.nextDouble();
                    rrEntry = new RREntry("TEST", score);
                    testEvalmaxPlayedGames(rrEntry);
                    break;
                case 9:
                    System.out.println("Introduzca un valor para la entrada del record");
                    score = s.nextDouble();
                    rrEntry = new RREntry("TEST", score);
                    testEvalmaxWinnedGames(rrEntry);
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
        r = new Records();
        printRecords();
    }

    private static void testGetMaxExp() {
        RREntry r1 = r.getMaxExp();
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");
        System.out.println(r1);
    }

    private static void testGetMaxWinningSpree() {
        RREntry r1 = r.getMaxWinningSpree();
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");
        System.out.println(r1);
    }

    private static void testGetMaxPlayedGames() {
        RREntry r1 = r.getMaxPlayedGames();
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");
        System.out.println(r1);
    }

    private static void testGetMaxWinnedGames() {
        RREntry r1 = r.getMaxWinnedGames();
        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");
        System.out.println(r1);
    }

    private static void testEvalMaxExp(RREntry rrEntry) {
        r.evalMaxExp(rrEntry);
        printRecords();
    }

    private static void testEvalMaxWinningSpree(RREntry rrEntry) {
        r.evalMaxWinningSpree(rrEntry);
        printRecords();
    }

    private static void testEvalmaxPlayedGames(RREntry rrEntry) {
        r.evalmaxPlayedGames(rrEntry);
        printRecords();
    }

    private static void testEvalmaxWinnedGames(RREntry rrEntry) {
        r.evalmaxWinnedGames(rrEntry);
        printRecords();
    }

    private static void printRecords(){
        RREntry r1 = r.getMaxExp();
        RREntry r2 = r.getMaxPlayedGames();
        RREntry r3 = r.getMaxWinnedGames();
        RREntry r4 = r.getMaxWinningSpree();
        RREntry rrEntrys[] = new RREntry[4];
        rrEntrys[0] = r1; rrEntrys[1] = r2; rrEntrys[2] = r3; rrEntrys[3] = r4;

        System.out.println("Date" + "                            " + "NickName" + "    " + "Score");

        String nameRecords[] = {"Más Experiencia:                  ",
                                "Más partidos ganados sin perder:  ",
                                "Más partidas jugadas:             ",
                                "Más partidas ganadas:             "};
        for (int i = 0; i < rrEntrys.length; i++) {
            System.out.print(nameRecords[i]);
            System.out.println(rrEntrys[i]);
        }

    }
}
