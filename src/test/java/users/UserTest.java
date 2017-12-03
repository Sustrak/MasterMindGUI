package users;

import game.DiffEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserTest {
    private static User u = null;
    private static Scanner s = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Test of class: GameTriple\n");
        int n = 25;
        boolean loop = true;
        double d;
        int i;
        DiffEnum diff;
        String str;
        u = new User("NULL", "NULL", "NULL", new Date(), "NULL");

        while (loop) {
            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: getName");
            System.out.println("Test 3: getNickname");
            System.out.println("Test 4: getBirthDate");
            System.out.println("Test 5: getPassword");
            System.out.println("Test 6: getCategory");
            System.out.println("Test 7: getExperience");
            System.out.println("Test 8: getSavedGames");
            System.out.println("Test 9: getMaxScore");
            System.out.println("Test 10: getPlayedGames");
            System.out.println("Test 11: getWinnedGames");
            System.out.println("Test 12: getWinningSpree");
            System.out.println("Test 13: setPassword");
            System.out.println("Test 14: addToSavedGames");
            System.out.println("Test 15: removeFromSavedGames");
            System.out.println("Test 16: addExperience");
            System.out.println("Test 17: setMaxScore");
            System.out.println("Test 18: addToPlayedGames");
            System.out.println("Test 19: addToWinnedGames");
            System.out.println("Test 20: addToWinningSpree");
            System.out.println("Test 21: resetWinningSpree");
            System.out.println("Test 22: equals");
            System.out.println("Test 23: hashCode");
            System.out.println("Test 24: toString");
            System.out.println("Option 25: Exit");


            System.out.println("\nSeleccione test");
            int test = s.nextInt();
            while (test < 0 || test > n) {
                System.out.println("Test no valido");
                test = s.nextInt();
            }

            switch (test) {
                case 1:
                    String name = getName();
                    String surname = getSurname();
                    String nickname = getNickname();
                    Date bD;
                    String date = getDate();
                    try {
                        bD = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    } catch (ParseException ex) {
                        System.out.println("ERROR");
                        break;
                    }
                    String password = getPassword();
                    testConstructor(name, surname, nickname, bD, password);
                    break;
                case 2:
                    testGetName();
                    break;
                case 3:
                    testGetNickname();
                    break;
                case 4:
                    testGetBirthDate();
                    break;
                case 5:
                    testGetPassword();
                    break;
                case 6:
                    testGetCategory();
                    break;
                case 7:
                    testGetExperience();
                    break;
                case 8:
                    testGetSavedGames();
                    break;
                case 9:
                    testGetMaxScore();
                    break;
                case 10:
                    testGetPlayedGames();
                    break;
                case 11:
                    testGetWinnedGames();
                    break;
                case 12:
                    testGetWinningSpree();
                    break;
                case 13:
                    s.nextLine();
                    str = getPassword();
                    testSetPassword(str);
                    break;
                case 14:
                    System.out.println("Introduce el id de la partida");
                    i = s.nextInt();
                    testAddToSavedGames(i);
                    break;
                case 15:
                    System.out.println("Introduce el id de la partida");
                    i = s.nextInt();
                    testRemoveFromSavedGames(i);
                    break;
                case 16:
                    System.out.println("Introduce la experiencia a añadir");
                    d = s.nextDouble();
                    testAddExperience(d);
                    break;
                case 17:
                    System.out.println("Introduce la max score");
                    d = s.nextDouble();
                    diff = getDiff();
                    testSetMaxScore(d, diff);
                    break;
                case 18:
                    diff = getDiff();
                    testAddToPlayedGames(diff);
                    break;
                case 19:
                    diff = getDiff();
                    testAddToWinnedGames(diff);
                    break;
                case 20:
                    diff = getDiff();
                    testAddToWinningSpree(diff);
                    break;
                case 21:
                    diff = getDiff();
                    testResetWinningSpree(diff);
                    break;
                case 22:
                    String nname = getName();
                    String ssurname = getSurname();
                    String nnickname = getNickname();
                    Date bbD;
                    String ddate = getDate();
                    try {
                        bbD = new SimpleDateFormat("dd/MM/yyyy").parse(ddate);
                    } catch (ParseException ex) {
                        System.out.println("ERROR");
                        break;
                    }
                    String ppassword = getPassword();
                    User uu = new User(nname, ssurname, nnickname, bbD, ppassword);
                    testEquals(uu);
                    break;
                case 23:
                    testHashCode();
                    break;
                case 24:
                    testPrintUser();
                    break;
                case 25:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void testGetCategory() {
        System.out.println(u.getCategory());
    }

    private static void testConstructor(String name, String surname, String nickname, Date bD, String password) {
        System.out.println(name);
        u = new User(name, surname, nickname, bD, password);
        testPrintUser();
    }

    private static void testGetName() {
        System.out.println(u.getName());
    }

    private static void testGetNickname() {
        System.out.println(u.getNickname());
    }

    private static void testGetBirthDate() {
        System.out.println(u.getBirthDate());
    }

    private static void testGetPassword() {
        System.out.println(u.getPassword());
    }

    private static void testGetExperience() {
        System.out.println(u.getExperience());
    }

    private static void testGetSavedGames() {
        for (Integer i : u.getSavedGames())
            System.out.println(i);
    }

    private static void testGetMaxScore() {
        System.out.println("EASY   ORIGINAL    HARD");
        System.out.println(u.getMaxScore());
    }

    private static void testGetPlayedGames() {
        System.out.println("EASY   ORIGINAL    HARD");
        System.out.println(u.getPlayedGames());

    }

    private static void testGetWinnedGames() {
        System.out.println("EASY   ORIGINAL    HARD");
        System.out.println(u.getWinnedGames());
    }

    private static void testGetWinningSpree() {
        System.out.println("EASY   ORIGINAL    HARD");
        System.out.println(u.getWinningSpree());
    }

    private static void testSetPassword(String str) {
        u.setPassword(str);
        testGetPassword();
    }

    private static void testAddToSavedGames(int i) {
        u.addToSavedGames(i);
        testGetSavedGames();
    }

    private static void testRemoveFromSavedGames(int i) {
        u.removeFromSavedGames(i);
        testGetSavedGames();
    }

    private static void testAddExperience(double d) {
        u.addExperience(d);
        testGetExperience();
    }

    private static void testSetMaxScore(double d, DiffEnum diff) {
        u.setMaxScore(d, diff);
        testGetMaxScore();
    }

    private static void testAddToPlayedGames(DiffEnum diff) {
        u.addToPlayedGames(diff);
        testGetPlayedGames();
    }

    private static void testAddToWinnedGames(DiffEnum diff) {
        u.addToWinnedGames(diff);
        testGetWinnedGames();
    }

    private static void testAddToWinningSpree(DiffEnum diff) {
        u.addToWinningSpree(diff);
        testGetWinningSpree();
    }

    private static void testResetWinningSpree(DiffEnum diff) {
        u.resetWinningSpree(diff);
        testGetWinningSpree();
    }

    private static void testEquals(User uu) {
        System.out.println(u.equals(uu));
    }

    private static void testHashCode() {
        System.out.println(u.hashCode());
    }

    private static void testPrintUser() {
        System.out.println("name" + " "+ "surname" + " " + "nickname");
        System.out.println(u);
    }

    private static DiffEnum getDiff(){
        Scanner s = new Scanner(System.in);
        int diff;
        System.out.println("Escoja Difficulty");
        System.out.println("1: EASY");
        System.out.println("2: ORIGINAL");
        System.out.println("3: HARD");
        diff = s.nextInt();
        if(diff < 0 || diff > 3){
            System.out.println("ERROR");
            return null;
        }
        return DiffEnum.values()[diff-1];
    }

    private static String getDate() {
        System.out.println("Introduzca la birthDate");
        return s.nextLine();
    }

    private static String getNickname() {
        System.out.println("Introduzca el nickname");
        return s.nextLine();
    }

    private static String getSurname() {
        System.out.println("Introduzca el surname");
        return s.nextLine();
    }

    private static String getName() {
        System.out.println("Introduzca el name");
        s.nextLine();
        return s.nextLine();
    }

    private static String getPassword() {
        System.out.println("Introduzca la password");
        return s.nextLine();
    }
}
