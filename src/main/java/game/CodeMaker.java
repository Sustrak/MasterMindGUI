package game;

import java.util.ArrayList;

public abstract class CodeMaker extends Game {

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

    public abstract ArrayList<Integer> getNewCombination();

}