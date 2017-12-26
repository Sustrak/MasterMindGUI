package game;


import java.util.Scanner;

public class CodeBreakerTest {
    private static CodeBreaker c = null;
    public static void main(String[] args) {
        System.out.println("Test of class: CodeBreakerTest2\n");
        Scanner s = new Scanner(System.in);
        int n = 14;
        boolean loop = true;
        c = new CodeBreaker(DiffEnum.EASY);

        while(loop){

            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: getElementsComb");
            System.out.println("Test 3: secondClueUsed");
            System.out.println("Test 4: firstClueUsed");
            System.out.println("Test 5: getPositionClue");
            System.out.println("Test 6: getColorClue");
            System.out.println("Test 7: getColorRemoved");
            System.out.println("Test 8: useFirstClue");
            System.out.println("Test 9: useSecondClue");
            System.out.println("Test 10: setTimeElapsed");
            System.out.println("Test 11: calculateScore");
            System.out.println("Test 12: getScore");
            System.out.println("Test 13: getDificulty");
            System.out.println("Option 14: Exit");


            System.out.println("\nSeleccione test");
            int test = s.nextInt();
            while(test < 0 || test > n){
                System.out.println("Test no valido");
                test = s.nextInt();
            }

            switch(test){

                case 1:
                    System.out.println("Pick Difficulty");
                    System.out.println("1: EASY");
                    System.out.println("2: ORIGINAL");
                    System.out.println("3: HARD");
                    int diff = s.nextInt();
                    if(diff < 0 || diff > 3){
                        System.out.println("ERROR");
                        break;
                    }
                    testConstructor(DiffEnum.values()[diff-1]);
                    break;
                case 2:
                    testGetElementsComb();
                    break;
                case 3:
                    testSecondClueUsed();
                    break;
                case 4:
                    testFirstClueUsed();
                    break;
                case 5:
                    testGetPositionClue();
                    break;
                case 6:
                    testGetColorClue();
                    break;
                case 7:
                    testGetColorRemoved();
                    break;
                case 8:
                    testUseFirstClue();
                    break;
                case 9:
                    testUseSecondClue();
                    break;
                case 10:
                    testSetTimeElapsed();
                    break;
                case 11:
                    testCalculateScore();
                    break;
                case 12:
                    testGetScore();
                    break;
                case 13:
                    testGetDificulty();
                    break;
                case 14:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }


    private static void testConstructor(DiffEnum diffEnum) {
        c = new CodeBreaker(diffEnum);
    }

    private static void testGetElementsComb() {
        System.out.println(c.getElementsComb());
    }

    private static void testSecondClueUsed() {
        System.out.println(c.secondClueUsed());
    }

    private static void testFirstClueUsed() {
        System.out.println(c.firstClueUsed());
    }

    private static void testGetPositionClue() {
        System.out.println(c.getPositionClue());
    }

    private static void testGetColorClue() {
        System.out.println(c.getColorClue());
    }

    private static void testGetColorRemoved() {
        System.out.println(c.getColorRemoved());
    }

    private static void testUseFirstClue() {
        System.out.println(c.useFirstClue());
    }

    private static void testUseSecondClue() {
        c.useSecondClue();
        testSecondClueUsed();
    }

    private static void testSetTimeElapsed() {
        System.out.println(c.setTimeElapsed());
    }

    private static void testCalculateScore() {
        System.out.println(c.calculateScore());
    }

    private static void testGetScore() {
        System.out.println(c.getScore());
    }

    private static void testGetDificulty() {
        System.out.println(c.getDificulty());
    }
}
