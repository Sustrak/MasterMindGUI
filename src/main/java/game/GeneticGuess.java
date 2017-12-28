/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.max;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author pererumbo
 */
public class GeneticGuess extends CodeMaker {
    
    private final int maxGen;
    private final int maxSize;
    private final int a;
    private final int b;
    private final double crossProb;
    private final double mutProb;
    private final double permProb;
    private final double invProb;
    
    private int nGames;
    private int h;
    private HashSet<Combination> eligible;
    private HashSet<Combination> population;
    private ArrayList<Pair<Integer, Combination> > combScores;
    private int maxScore;
    private Comparator comp1, comp2;
    private Random r;
    
    public GeneticGuess(){
        
        currentBoard = new Board(6, 10);
        colorsAvailable = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
        
        maxGen = 150;
        maxSize = 200;
        a = 1;
        b = 2;
        crossProb = 0.5;
        mutProb = 0.03;
        permProb = 0.03;
        invProb = 0.02;
        
        nGames = 1;
        h = 1;
        
        r = new Random();
        comp1 = (Comparator<Pair<Integer, Integer>>) new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {
                if(o1.getKey() < o2.getKey()) return -1;
                else if(Objects.equals(o1.getKey(), o2.getKey())) return 0;
                else return 1;
            }
        };
    }
    
    @Override
    public int getElementsComb() {
        return 6;
    }

    @Override
    public ArrayList<Integer> getNewCombination() {
        
        if(nGames == 1){
            nGames++;
            return new ArrayList<>(Arrays.asList(1,1,2,3,4,5));
        }
        
        eligible = new HashSet();
        h = 1;
        initializePopulation();
        getScores();
        
        while(h <= maxGen && eligible.size() <= maxSize){

            evolvePop();
            getScores();
            addEligible();
            //selectComb();
            ++h;
        }
        return pickGuess();
    }

    private void initializePopulation() {
        
        population = new HashSet<>();
        int nPieces = super.currentBoard.getNPieces();
        while(population.size() < maxSize)
            population.add(generateRandomComb(nPieces));
    }
    
    private Combination generateRandomComb(int nPieces){
        
        ArrayList<Integer> newComb = new ArrayList<>();
        for(int j = 0; j < nPieces; ++j)
            newComb.add(r.nextInt(8));
        return new Combination(newComb);
    }

    private void getScores() {
        
        int nComb = super.currentBoard.getNCombinations();
        int nPieces = super.currentBoard.getNPieces();
        combScores = new ArrayList<>();
        maxScore = 0;
        
        for(Combination c : population){
            int blacks = 0;
            int whites = 0;
            
            for(int i = 0; i < nComb; ++i){
                //Get info about ith guess made
                Combination iComb = new Combination(currentBoard.getCombination(i));
                int iBlack = currentBoard.getBlackPegs(i);
                int iWhite = currentBoard.getWhitePegs(i);
                
                //Correct every code as the ith guess is the winner comb
                Correction corr = new Correction(c, iComb);
                
                //Compute difference of pegs
                blacks += abs(corr.getBlackPegs() - iBlack);
                whites += abs(corr.getWhitePegs() - iWhite);
            }
        
            int score = a*blacks + whites; // + b*nPieces*nComb;
            combScores.add(new Pair(score, c));
            if(score > maxScore) maxScore = score;
        }
        
        
        
        combScores.sort(comp1);
    }

    private Pair<Combination, Combination> crossComb(Combination c1, Combination c2) {
        
        if(crossProb >= r.nextDouble()){
            int cross = c1.getRandomPosition();
            while(cross == 0 || cross == 7) cross = c1.getRandomPosition();
            ArrayList<Integer> nC1 = new ArrayList<>();
            ArrayList<Integer> nC2 = new ArrayList<>();
            
            for(int i = 0; i < c1.getNElements(); ++i){
                if(i < cross){
                    nC1.add(c1.getColor(i));
                    nC2.add(c2.getColor(i));
                }
                else{
                    nC1.add(c2.getColor(i));
                    nC2.add(c1.getColor(i));
                }
            }
            return new Pair(new Combination(nC1), new Combination(nC2));
        }
        return new Pair(c1, c2);
    }
    
    private Combination mutComb(Combination c){
        
        if(mutProb >= r.nextDouble()){
            int rP = c.getRandomPosition();
            int rC = r.nextInt(8);
            c.setColor(rP, rC);
        }
        return c;
    }
    
    private Combination permComb(Combination c){
        
        if(permProb >= r.nextDouble()){
            
            int rP1 = c.getRandomPosition();
            int rP2 = c.getRandomPosition();
            while(rP1 == rP2) rP2 = c.getRandomPosition();
            
            int auxCol = c.getColor(rP1);
            c.setColor(rP1, c.getColor(rP2));
            c.setColor(rP2, auxCol);
        }
        return c;
    }
    
    private Combination invComb(Combination c){
        
        if(invProb >= r.nextDouble()){
            
            int rP1 = c.getRandomPosition();
            int rP2 = c.getRandomPosition();
            while(rP1 == rP2) rP2 = c.getRandomPosition();
            
            if(rP1 > rP2){
                int aux = rP1;
                rP1 = rP2;
                rP2 = aux;
            }
            
            ArrayList<Integer> colors = new ArrayList<>();
            for(int i = 0; i <= rP1 - rP2; ++i) colors.add(rP1 + i);
            for(int i = 0; i <= rP1 - rP2; ++i) c.setColor(rP1 + i, colors.get(rP2 - i));
        }
        return c;
    }

    private void addEligible() {
        
        for(int i = 0; i < combScores.size(); ++i) 
            if(combScores.get(i).getKey() == 0) 
                eligible.add(combScores.get(i).getValue());
    }

    private ArrayList<Integer> pickGuess() {
        
        ArrayList<Combination> eC = new ArrayList<>(eligible);
        ArrayList<Integer> sP = new ArrayList<>(eC.size());
        
        for(int i = 0; i < eC.size(); ++i){
            int s = 0;
            for(int j = 0; j < eC.size(); ++j){
                if(i != j){
                    Correction c = new Correction(eC.get(j), eC.get(i));
                    s += c.getBlackPegs();
                    s += c.getWhitePegs();
                }
            }
            sP.add(s);
        }
        
        int ind = -1;
        if(!sP.isEmpty()) ind = sP.indexOf(max(sP));
        
        if(ind != -1) return eC.get(ind).getCombination();
        return generateRandomComb(getElementsComb()).getCombination();
    }

    private void evolvePop() {
        
        population = new HashSet<>();
        for(int i = 0; i < combScores.size()-1; i += 2){

            Combination c1 = combScores.get(i).getValue();
            Combination c2 = combScores.get(i+1).getValue();

            Pair<Combination, Combination> auxC = crossComb(c1,c2);
            c1 = auxC.getKey();
            c2 = auxC.getValue();
            c1 = mutComb(c1); c2 = mutComb(c2);
            c1 = permComb(c1); c2 = permComb(c2);
            c1 = invComb(c1); c2 = invComb(c2);

            while(!population.add(c1)) c1 = generateRandomComb(getElementsComb());
            while(!population.add(c2)) c2 = generateRandomComb(getElementsComb());
        }
    }
}
