package game;

import java.util.ArrayList;
import java.util.Scanner;

public class CombinationTest {

    /**
     * @param args the command line arguments
     */
    private static Combination c;
    private static Scanner s;
    
    public static void main(String[] args) {
        
        System.out.println("Test of class: Combination");
        s = new Scanner(System.in);
        
        int n = 8;
        ArrayList<Integer> comb = new ArrayList<>(4);
        comb.add(1); comb.add(2); comb.add(3); comb.add(4); 
        c = new Combination(comb);
        
        while(true){
            
            System.out.println();
            System.out.println("Test 1: constructor");
            System.out.println("Test 2: getCombination");
            System.out.println("Test 3: getNElements");
            System.out.println("Test 4: getRandomPosition");
            System.out.println("Test 5: getColor");
            System.out.println("Test 6: hashCode");
            System.out.println("Test 7: equals");
            System.out.println("Opción 8: Salir");
            
            System.out.println("\nSeleccione un test");
            int option = s.nextInt();
            while(option < 0 || option > n){
                System.out.println("Test no válido");
                option = s.nextInt();
            }
            
            switch(option){
                
                case 1:
                    testCombination();
                    break;
                case 2:
                    testgetCombination();
                    break;
                case 3:
                    testgetNElements();
                    break;
                case 4:
                    testgetRandomPosition();
                    break;
                case 5:
                    testgetColor();
                    break;
                case 6:
                    testhashCode();
                    break;
                case 7:
                    testequals();
                    break;
                case 8:
                    return;
                default:
                    break;
            }
        }
    }
    
    private static void testCombination(){
    
        System.out.println("Test Constructor.");
        System.out.println("Introduce 4 enteros separados por un espacio:");
        ArrayList<Integer> newComb = new ArrayList<>(4);
        for(int i = 0; i < 4; ++i) newComb.add(s.nextInt());
        c = new Combination(newComb);
        System.out.println("Has creado la combinación: " + c.toString());  
        
    }
    private static void testgetCombination(){
    
        System.out.println("Test getCombination.");
        System.out.println("La combinación es " + c.getCombination());
    }
    private static void testgetNElements(){
        
        System.out.println("Test getNElements.");
        System.out.println("La combinación tiene " + c.getNElements() + " elementos");
    }
    private static void testgetRandomPosition(){
        
        System.out.println("Test getRandomPosition.");
        System.out.println("Posición generada " + c.getRandomPosition());
    }
    private static void testgetColor(){
        
        System.out.println("Test getColor.");
        System.out.println("Introduce un índice entre 0 y 3:" );
        System.out.println("Color " + c.getColor(s.nextInt()));
    }
    private static void testhashCode(){
    
        System.out.println("Test hashCode.");
        
        System.out.print("La combinación " + c.toString() + " tiene hashCode: ");
        System.out.println(c.hashCode());
    }
    private static void testequals(){
    
        System.out.println("Test equals.");
        System.out.println("Combinación 1: Introduce 4 enteros separados por un espacio:");
        ArrayList<Integer> newComb1 = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            newComb1.add(s.nextInt());
        }
        
        System.out.println("Combinación 2: Introduce 4 enteros separados por un espacio:");
        ArrayList<Integer> newComb2 = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            newComb2.add(s.nextInt());
        }
        Combination c1 = new Combination(newComb1);
        Combination c2 = new Combination(newComb2);
        
        if(c1.equals(c2)) System.out.println("Las combinaciones son iguales");
        else System.out.println("Las combinaciones son diferentes");
    }
    
}
