package users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class UserSetTest {

    private static UserSet us = null;
    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Test of class: UserSet\n");
        int n = 8;
        boolean loop = true;
        String nickname;
        Date dB;
        String password;
        String date;
        us = new UserSet();

        while(loop){

            System.out.println("\nTest 1: Constructor");
            System.out.println("Test 2: newUser");
            System.out.println("Test 3: login");
            System.out.println("Test 4: resetPassword");
            System.out.println("Test 5: getNUsers");
            System.out.println("Test 6: getUserInfo");
            System.out.println("Test 7: existsUser");
            System.out.println("Option 8: Exit");

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
                    String nname = getName();
                    String ssurname = getSurname();
                    nickname = getNickname();
                    date = getDate();
                    try {
                        dB = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    } catch (ParseException ex) {
                        System.out.println("ERROR");
                        break;
                    }
                    password = getPassword();
                    testNewUser(nname, ssurname, nickname, dB, password);
                    break;
                case 3:
                    s.nextLine();
                    nickname = getNickname();
                    password = getPassword();
                    testLogIn(nickname, password);
                    break;
                case 4:
                    s.nextLine();
                    nickname = getNickname();
                    date = getDate();
                    try {
                        dB = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    } catch (ParseException ex) {
                        System.out.println("ERROR");
                        break;
                    }
                    password = getPassword();
                    testResetPassword(nickname, dB, password);
                    break;
                case 5:
                    testGetNUsers();
                    break;
                case 6:
                    s.nextLine();
                    nickname = getNickname();
                    testGetUserInfo(nickname);
                    break;
                case 7:
                    s.nextLine();
                    nickname = getNickname();
                    testExistsUser(nickname);
                    break;
                case 8:
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }

    private static void testConstructor() {
        us = new UserSet();
    }

    private static void testNewUser(String nname, String ssurname, String nickname, Date dB, String password) {
        int i = us.newUser(nname, ssurname, nickname, dB, password);
        if (i == 1) System.out.println("El user ya esta registrado");
        else System.out.println("User registrado");
    }

    private static void testLogIn(String nick, String pass) {
        User u = null;
        try {
            u = us.logIn(nick, pass);
        } catch (UserNotFoundException e) {
            System.out.println("User no encontrado");
        } catch (WrongPasswordException e) {
            System.out.println("Password incorrecto");
        }
        System.out.println(u);
    }

    private static void testResetPassword(String nickname, Date bD, String npass) {
        try {
            us.resetPassword(nickname, bD, npass);
        } catch (UserNotFoundException e) {
            System.out.println("User no encontrado");
            return;
        } catch (WrongDateException e) {
            System.out.println("BirthDate incorrecta");
            return;
        }
        System.out.println("Password canviado");
    }

    private static void testGetNUsers() {
        System.out.println(us.getNUsers());
    }

    private static void testGetUserInfo(String nickname) {
//        Map<String, String> info = null;
//        try {
//            info = us.getUserInfo(nickname);
//        } catch (UserNotFoundException e) {
//            System.out.println("User no encontrado");
//        }
//        System.out.println(System.lineSeparator() + nickname + System.lineSeparator());
//        System.out.println("Name:          " + info.get("name"));
//        System.out.println("Category:      " + info.get("category"));
//        System.out.println("Experience:    " + info.get("experience"));
//
//        System.out.println("               EASY   ORIGINAL    HARD");
//
//        System.out.println("Max Score:     " + info.get("maxScore"));
//        System.out.println("Played Games:  " + info.get("playedGames"));
//        System.out.println("Winned Games:  " + info.get("winnedGames"));
//        System.out.println("Winning Spree: " + info.get("winningSpree"));
    }

    private static void testExistsUser(String nickname) {
        System.out.println(nickname);
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
