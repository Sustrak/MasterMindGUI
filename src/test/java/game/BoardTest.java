package game;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardTest {

    private static Board b;
    private static Scanner s;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Test of class: Board");
        s = new Scanner(System.in);
        int n = 13;
        b = new Board(4, 12);
        ArrayList<Integer> wC = new ArrayList<>(4);
        wC.add(1); wC.add(2);wC.add(3);wC.add(4);
        b.setWinnerCombination(wC);
        for(int i = 0; i < 6; ++i)b.setNewCombination(wC);
        
        while(true){
            
            System.out.println();
            System.out.println("Test 1: Constructor");
            System.out.println("Test 2: getWinnerCombination");
            System.out.println("Test 3: getCombination");
            System.out.println("Test 4: getWhitePegs");
            System.out.println("Test 5: getBlackPegs");
            System.out.println("Test 6: getNPieces");
            System.out.println("Test 7: getNCombinations");
            System.out.println("Test 8: setNewCombination");
            System.out.println("Test 9: setWinnerCombination");
            System.out.println("Test 10: setLastCorrection");
            System.out.println("Test 11: isGoodCorrection");
            System.out.println("Test 12: boardIsFull");
            System.out.println("Opción 13: Salir");
            
            System.out.println("\nSeleccione opción");
            int option = s.nextInt();
            while(option < 0 || option > n){
                System.out.println("Test no válida");
                option = s.nextInt();
            }
            
            switch(option){
                
                case 1:
                    testBoard();
                    break;
                case 2:
                    testgetWinnerCombination();
                    break;
                case 3:
                    testgetCombination();
                    break;
                case 4:
                    testgetWhitePegs();
                    break;    
                case 5:
                    testgetBlackPegs();
                    break;
                case 6:
                    testgetNPieces();
                    break;
                case 7:
                    testgetNCombinations();
                    break;
                case 8:
                    testsetNewCombination();
                    break;
                case 9:
                    testsetWinnerCombination();
                    break;
                case 10:
                  testsetLastCorrection();
                  break;
                case 11:
                    testisGoodCorrection();
                    break;
                case 12:
                    testboardIsFull();
                    break;
                case 13:
                    return;
                default:
                    break;
            }
        }
    
}

    private static void testBoard() {
        
        System.out.println("Test constructor Board(int, int)");
        System.out.println("Introduce el número de fichas de una combinación:");
        int n = s.nextInt();
        System.out.println("Introduce el número de combinaciones del tablero:");
        int nc = s.nextInt();
        Board b1 = new Board(4,12);
        b1 = new Board(n, nc);
        
        System.out.println("El tablero tiene " + b1.getNPieces() + " fichas.");
        System.out.println("El tablero tiene " + nc + " combinaciones.");
    }

    private static void testgetWinnerCombination() {
        
        Combination c = b.getWinnerCombination();
        System.out.println("La combinación ganadora es: " + c._toString());
    }
    
    private static void testgetCombination() {
        
        System.out.println("Test getCombination(int i)");
        System.out.println("Introduce el índice que quiere consultar.");
        int i = s.nextInt();
        if(i >= b.getNCombinations()) System.out.println("El índice no es válido.");
        else System.out.println(b.getCombination(i).toString());
    }

    private static void testgetWhitePegs() {
        
        System.out.println("Test getWhitePegs");
        System.out.println("Introduzca el índice de la combinación que quiere consultar:");
        int i = s.nextInt();
        if(i >= b.getNCombinations()) System.out.println("El índice no es válido.");
        else System.out.println("Fichas blancas: " + b.getWhitePegs(i));
    }
    
    private static void testgetBlackPegs() {
        
        System.out.println("Test getBlackPegs");
        System.out.println("Introduzca el índice de la combinación que quiere consultar:");
        int i = s.nextInt();
        if(i >= b.getNCombinations()) System.out.println("El índice no es válido.");
        else System.out.println("Fichas negras: " + b.getBlackPegs(i));
    }

    private static void testgetNPieces() {
        
        System.out.println("Test getNPieces");
        System.out.println("El tablero es de " + b.getNPieces() + " piezas.");
    }

    private static void testgetNCombinations() {
        
        System.out.println("Test getNCombinations");
        System.out.println("El tablero tiene " + b.getNCombinations() + " combinaciones.");
    }
    
    private static void testsetWinnerCombination() {
        
        System.out.println("Test setWinnerCombination(ArrayList<Integer>)");
        System.out.println("Introduce 4 enteros separados por un espacio para generar la combinación ganadora:");
        ArrayList<Integer> newComb = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            newComb.add(s.nextInt());
        }
        Board b1 = new Board(4,12);
        b1.setWinnerCombination(newComb);
        System.out.println("La combinación ganadora es: " + b1.getWinnerCombination()._toString());
    }

    private static void testsetNewCombination() {
        
        System.out.println("Test setNewCombination(ArrayList<Integer>)");
        System.out.println("Introduce 4 enteros separados por un espacio para generar la combinación a introducir:");
        ArrayList<Integer> newComb = new ArrayList<>(4);
        for (int i = 0; i < 4; ++i) {
            newComb.add(s.nextInt());
        }        
        b.setNewCombination(newComb);
        System.out.println("La última combinación introducida es: " + b.getCombination(-1));
    }

    private static void testsetLastCorrection() {
        
        System.out.println("Test setLastCorrection(int,int)");
        System.out.println("Introduce el número de fichas negras: ");
        int bp = s.nextInt();
        System.out.println("Introduce el número de fichas blancas: ");
        int wp = s.nextInt();
        
        b.setLastCorrection(bp, wp);
        System.out.print("La última corrección tiene " + b.getBlackPegs(-1) + " fichas negras ");
        System.out.println("y " + b.getBlackPegs(-1) + " fichas blancas.");
    }
    
    private static void testboardIsFull() {
        
        System.out.println("Test boardIsFull");
        
        if(b.boardIsFull()) System.out.println("El tablero ya está completo.");
        else System.out.println("El tablero no está completo.");
    }

    private static void testisGoodCorrection() {
        
        System.out.println("Test isGoodCorrection");
        System.out.println("Introduce el número de fichas negras: ");
        int bp = s.nextInt();
        System.out.println("Introduce el número de fichas blancas: ");
        int wp = s.nextInt();
        if(b.isGoodCorrection(bp,wp)) System.out.println("La corrección es correcta.");
        else System.out.println("La corrección no es correcta.");
    }
}