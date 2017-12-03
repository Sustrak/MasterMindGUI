package game;

import java.util.ArrayList;

public class Correction {

    private int blackPegs;
    private int whitePegs;


    /**
     *
     * @param winnerComb
     * @param newComb
     */
    public Correction(Combination winnerComb, Combination newComb){

        int bP = 0, wP = 0;

        ArrayList<Boolean> checkWinner = new ArrayList<>(winnerComb.getNElements());
        ArrayList<Boolean> checkNew = new ArrayList<>(newComb.getNElements());
        for(int i = 0; i < newComb.getNElements(); ++i){
            checkWinner.add(true);
            checkNew.add(true);
        }

        for(int i = 0; i < newComb.getNElements(); ++i)
            if(newComb.getColor(i) == winnerComb.getColor(i)){
                checkWinner.set(i, false);
                checkNew.set(i, false);
                ++bP;
            }

        for(int i = 0; i < newComb.getNElements(); ++i)
            for(int j = 0; j < winnerComb.getNElements() && checkNew.get(i); ++j)
                if(checkWinner.get(j) && i != j && newComb.getColor(i) == winnerComb.getColor(j)){
                    checkWinner.set(j, false);
                    checkNew.set(i, false);
                    ++wP;
                }

        blackPegs = bP;
        whitePegs = wP;
    }

    public Correction(int bP, int wP){
        blackPegs = bP;
        whitePegs = wP;
    }

    public boolean equals(Correction c){
        return this.blackPegs == c.blackPegs && this.whitePegs == c.whitePegs;
    }

    public int getWhitePegs(){

        return whitePegs;
    }

    public int getBlackPegs(){

        return blackPegs;
    }

    public void setWhitePegs(int wP){
        whitePegs = wP;
    }

    public void setBlackPegs(int bP){
        blackPegs = bP;
    }

    @Override
    public int hashCode(){
        return whitePegs * blackPegs;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Correction other = (Correction) obj;
        if (this.blackPegs != other.blackPegs) {
            return false;
        }
        if (this.whitePegs != other.whitePegs) {
            return false;
        }
        return true;
    }

    public String toString(){

        return "Fichas negras: " + blackPegs + " Fichas blancas: " + whitePegs;
    }
}
