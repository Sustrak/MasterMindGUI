package game;

import java.util.ArrayList;
import java.util.Arrays;

public class CodeMaker extends Game {

    private Guess guess;

    public CodeMaker() {

        currentBoard = new Board(4, 12);
        colorsAvailable = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        guess = new Guess(CodeMaker.this);
    }

    @Override
    public int getElementsComb() {
        return 4;
    }

    @Override
    public double calculateScore() {
        return 0;
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    public DiffEnum getDificulty() {
        return null;
    }

    public ArrayList<Integer> getNewCombination() {

        return guess.getNewCombination();
    }

}
