package game;

import java.util.ArrayList;
import java.util.Scanner;

public class CorrectionTest {

    private static Correction c;
    private static Scanner s;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Test of class: Correction");
        s = new Scanner(System.in);
        int n = 9;
        c = new Correction(2,1);
        
        while(true){
            
            System.out.println();
            System.out.println("Test 1: Constructor 1");
            System.out.println("Test 2: Constructor 2");
            System.out.println("Test 3: testgetWhitePegs");
            System.out.println("Test 4: testgetBlackPegs");
            System.out.println("Test 5: testsetWhitePegs");
            System.out.println("Test 6: testsetBlackPegs");
            System.out.println("Test 7: testhashCode");
            System.out.println("Test 8: testequals");
            System.out.println("Opción 9: Salir");
            
            System.out.println("\nSeleccione opción");
            int option = s.nextInt();
            while(option < 0 || option > n){
                System.out.println("Test no válida");
                option = s.nextInt();
            }
            
            switch(option){
                
                case 1:
                    testCorrection1();
                    break;
                case 2:
                    testCorrection2();
                    break;
                case 3:
                    testgetWhitePegs();
                    break;
                case 4:
                    testgetBlackPegs();
                    break;    
                case 5:
                    testsetWhitePegs();
                    break;
                case 6:
                    testsetBlackPegs();
                    break;
                case 7:
                    testhashCode();
                    break;
                case 8:
                    testequals();
                    break;
                case 9:
                    return;
                default:
                    break;
            }
        }
        
    }
    
    private static void testCorrection1(){
    
        System.out.println("Test constructor 1");
        System.out.println("Introduce 4 enteros separados por un espacio para generar la combinación ganadora:");
        ArrayList<Integer> newComb1 = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            newComb1.add(s.nextInt());
        }
        
        System.out.println("Introduce 4 enteros separados por un espacio para generar la combinación del jugador:");
        ArrayList<Integer> newComb2 = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            newComb2.add(s.nextInt());
        }
        Combination c1 = new Combination(newComb1);
        Combination c2 = new Combination(newComb2);
        c = new Correction(c1, c2);
        System.out.println("Fichas negras: " + c.getBlackPegs());
        System.out.println("Fichas blancas: " + c.getWhitePegs());
        
    }
    private static void testCorrection2(){
    
        System.out.println("Test constructor 2");
        System.out.println("Introduce el numero de fichas negras");
        int bp = s.nextInt();
        System.out.println("Introduce el numero de fichas negras");
        int wp = s.nextInt();
        
        c = new Correction(bp, wp);
        System.out.println("Fichas negras: " + c.getBlackPegs());
        System.out.println("Fichas blancas: " + c.getWhitePegs());
        
    }
    private static void testgetWhitePegs(){
        
        System.out.println("Test getWhitePegs");
        System.out.println("Fichas blancas: " + c.getWhitePegs());
    }
    private static void testgetBlackPegs(){
        
        System.out.println("Test getBlackPegs");
        System.out.println("Fichas negras: " + c.getBlackPegs());
    }
    private static void testsetWhitePegs(){
    
        System.out.println("Test setWhitePegs");
        System.out.println("Introduce la cantidad de fichas blancas: ");
        c.setWhitePegs(s.nextInt());
        System.out.println("Fichas blancas: " + c.getWhitePegs());
    }
    private static void testsetBlackPegs(){
        
        System.out.println("Test setBlackPegs");
        System.out.println("Introduce la cantidad de fichas negras: ");
        c.setBlackPegs(s.nextInt());
        System.out.println("Fichas negras: " + c.getBlackPegs());
    }
    private static void testhashCode(){
        System.out.println("Test hashCode.");
        
        System.out.print("La corrección " + c.toString() + " tiene hashCode: ");
        System.out.println(c.hashCode());
    }
    private static void testequals(){
    
        System.out.println("Test equals.");
        System.out.println("Corrección 1");
        System.out.println("Introduce el numero de fichas negras");
        int bp1 = s.nextInt();
        System.out.println("Introduce el numero de fichas blancas");
        int wp1 = s.nextInt();
        
        System.out.println();
        System.out.println("Corrección 2");
        System.out.println("Introduce el numero de fichas negras");
        int bp2 = s.nextInt();
        System.out.println("Introduce el numero de fichas blancas");
        int wp2 = s.nextInt();
        
        Correction c1 = new Correction(bp1, wp1);
        Correction c2 = new Correction(bp2, wp2);
        
        if(c1.equals(c2)) System.out.println("Las correcciones son iguales");
        else System.out.println("Las correcciones son diferentes");
        
    }
}
