package game;

import java.util.ArrayList;

public abstract class Game {

    protected int id;
    protected Board currentBoard;
    protected ArrayList<Integer> colorsAvailable;


    public Game(int id){
        this.id = id;
    }

    public abstract int getElementsComb();

    public boolean boardIsFull() {
        return currentBoard.boardIsFull();
    }

    public int getWhitePegs(int nWP) {
        return currentBoard.getWhitePegs(nWP);
    }

    public boolean setNewCombination(ArrayList<Integer> newCombination) {
        return currentBoard.setNewCombination(newCombination);
    }

    public int getBlackPegs(int nBP) {
        return currentBoard.getBlackPegs(nBP);
    }

    public int getNPieces() {
        return currentBoard.getNPieces();
    }

    public ArrayList<Integer> getColorsAvailable() {
        return colorsAvailable;
    }

    public ArrayList<Integer> getCombination(int i) {
        return currentBoard.getCombination(i);
    }

    public int getNCombinations() {
        return currentBoard.getNCombinations();
    }

    public abstract double calculateScore();

    public abstract double getScore();

    public abstract DiffEnum getDificulty();

    public Combination getWinnerCombination() {
        return currentBoard.getWinnerCombination();
    }

    public void setWinnerCombination(ArrayList<Integer> winnerComb) {
        currentBoard.setWinnerCombination(winnerComb);
    }

    public void setLastCorrection(int bP, int wP) {
        currentBoard.setLastCorrection(bP, wP);
    }

    public boolean isGoodCorrection(int bP, int wP) {
        return currentBoard.isGoodCorrection(bP, wP);
    }

    //Methods for serialization


    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public void setColorsAvailable(ArrayList<Integer> colorsAvailable) {
        this.colorsAvailable = colorsAvailable;
    }
}