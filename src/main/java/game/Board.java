package game;

import java.util.ArrayList;

public class Board {

    private ArrayList<Combination> combinations;
    private ArrayList<Correction> corrections;
    private Combination winnerComb;
    private int nComb;
    private int nPieces;

    public Board(int nPieces, int nComb) {

        this.nPieces = nPieces;
        this.nComb = nComb;
        combinations = new ArrayList<>(nComb);
        corrections = new ArrayList<>(nComb);

    }

    // Getters

    public Combination getWinnerCombination() {
        return new Combination(winnerComb.getCombination());
    }

    public ArrayList<Integer> getCombination(int i) {
        if(i == -1) return combinations.get(combinations.size()-1).getCombination();
        else return combinations.get(i).getCombination();
    }

    public int getWhitePegs(int nWP) {
        if(nWP == -1) return corrections.get(corrections.size()-1).getWhitePegs();
        else return corrections.get(nWP).getWhitePegs();
    }

    public int getBlackPegs(int nBP) {
        if(nBP == -1) return corrections.get(corrections.size()-1).getBlackPegs();
        else return corrections.get(nBP).getBlackPegs();
    }

    public int getNPieces() {
        return nPieces;
    }

    public int getNCombinations() {
        return combinations.size();
    }

    // Setters

    public boolean setNewCombination(ArrayList<Integer> newCombination) {

        Combination newComb = new Combination(newCombination);
        combinations.add(newComb);
        corrections.add(new Correction(winnerComb, newComb));
        if(corrections.get(corrections.size() - 1).getBlackPegs() == nPieces) return true;
        return false;

    }

    public void setWinnerCombination(ArrayList<Integer> winnerCombination) {

        winnerComb = new Combination(winnerCombination);
    }

    public void setLastCorrection(int bP, int wP) {

        corrections.add(new Correction(bP, wP));
    }

    // Auxiliary

    public boolean isGoodCorrection(int bP, int wP) {

        Correction c = new Correction(bP, wP);
        return c.equals(corrections.get(corrections.size()-1));
    }

    public boolean boardIsFull() {
        return combinations.size() >= nComb;
    }

    //Methods for serialization

    public Board() {
    }

    public ArrayList<Combination> getCombinations() {
        return combinations;
    }

    public void setCombinations(ArrayList<Combination> combinations) {
        this.combinations = combinations;
    }

    public ArrayList<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(ArrayList<Correction> corrections) {
        this.corrections = corrections;
    }

    public Combination getWinnerComb() {
        return winnerComb;
    }

    public void setWinnerComb(Combination winnerComb) {
        this.winnerComb = winnerComb;
    }

    public int getnComb() {
        return nComb;
    }

    public void setnComb(int nComb) {
        this.nComb = nComb;
    }

    public int getnPieces() {
        return nPieces;
    }

    public void setnPieces(int nPieces) {
        this.nPieces = nPieces;
    }
}

