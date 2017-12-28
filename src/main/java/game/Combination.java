package game;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Combination {

    private ArrayList<Integer> combination;

    public Combination(ArrayList<Integer> comb){

        combination = new ArrayList<>(comb);
    }

    public ArrayList<Integer> getCombination(){

        return new ArrayList<>(combination);
    }

    public int getNElements() {

        return combination.size();
    }

    public int getRandomPosition() {

        Random r = new Random();
        return r.nextInt(combination.size());
    }

    public int getColor(int i) {

        return combination.get(i);
    }
    
    public void setColor(int p, int c){
        
        combination.set(p, c);
    }

    @Override
    public int hashCode(){
        int result = 1;
        for (int n : combination) {
            result *= n;
        }
        return result;
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
        final Combination other = (Combination) obj;
        if (!Objects.equals(this.combination, other.combination)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return combination.toString();
    }
}
