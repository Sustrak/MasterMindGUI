package game;

import java.util.*;

public class Guess {

    private CodeMaker cM;
    private static Set<Combination> combSet;
    private static ArrayList<Combination> nonUsedComb;
    private static HashMap<Correction, Integer > corrections;
    private Combination lastComb;

    public Guess(CodeMaker game){

        cM = game;

        combSet = new HashSet<>();
        for(int i = 0; i <= 5; ++i)
            for(int j = 0; j <= 5; ++j)
                for(int k = 0; k <= 5; ++k)
                    for(int l = 0; l <= 5; ++l)
                        combSet.add(new Combination(new ArrayList<>(Arrays.asList(i,j,k,l))));

        nonUsedComb = new ArrayList<>(combSet);

        corrections = new HashMap<>(15);
        // Corrections contains a map of all possible corrections, e.g. (0,0) --> 0, (0,1) --> 1, ..., (4,0) --> 14
        int k = 0;
        for(int i = 0; i <= 4; i++)
            for(int j = 0; i+j <= 4; j++)
                corrections.put(new Correction(i,j), k++);

    }

    public ArrayList<Integer> getNewCombination() {

        Combination newComb = null;

        if(combSet.size() == 1296){
            newComb = new Combination(new ArrayList<>(Arrays.asList(1,1,2,2)));
            lastComb = newComb;
        }
        else {
            removeCombFromS();
            getNewGuess();
        }
        boolean remove = nonUsedComb.remove(lastComb);
        boolean remove1 = combSet.remove(lastComb);
        return lastComb.getCombination();
    }

    private void removeCombFromS() {

        Iterator<Combination> it = combSet.iterator();
        Correction currentCorrection = new Correction(cM.getWinnerCombination(), lastComb);
        while(it.hasNext()){
            Correction c = new Correction(it.next(), lastComb);
            if(!c.equals(currentCorrection))it.remove();

        }
    }

    private void getNewGuess() {

        // Create a counter for each possible correction initialized to 0
        ArrayList<Integer> initialValues = new ArrayList(corrections.size());
        for(int i = 0; i < corrections.size(); ++i) initialValues.add(0);

        // Set one counter for each element in nonUsedComb and initialize track of the maximum
        ArrayList<Integer> indexMax = new ArrayList<>(nonUsedComb.size());
        ArrayList<ArrayList<Integer> > scores = new ArrayList<>(nonUsedComb.size());
        for(int i = 0; i < nonUsedComb.size(); ++i){
            scores.add(new ArrayList<>(initialValues));
            // add(0) because all counters start at 0. It could be whatever
            indexMax.add(0);
        }


        for (int i = 0; i < nonUsedComb.size(); ++i){
            Combination n = nonUsedComb.get(i);
            ArrayList<Integer> aux = scores.get(i);
            for (Combination s : combSet) {
                // Correction guessing that s is the winnerCombination and n is the guess
                Correction c = new Correction(s, n);
                // Get the index of a specific correction
                int indexScore = corrections.get(c);
                // Set an increment of 1 to the correction obtaines. Correction with lower increments will be the minimum
                aux.set(indexScore, aux.get(indexScore) + 1);
                // Keep track of the maximum
                if(aux.get(indexScore) > aux.get(indexMax.get(i))) indexMax.set(i, indexScore);
            }
        }

        // Find the minimum value of the maximums and get the set of possible new Combinations
        int minScore = scores.get(0).get(indexMax.get(0));
        ArrayList<Combination> candidates = new ArrayList<>(nonUsedComb.size());
        candidates.add(new Combination(nonUsedComb.get(0).getCombination()));

        for(int i = 1; i < nonUsedComb.size(); ++i){
            int auxScore = scores.get(i).get(indexMax.get(i));
            if(auxScore < minScore){
                minScore = auxScore;
                candidates = new ArrayList<>(nonUsedComb.size()-i);
                candidates.add(new Combination(nonUsedComb.get(i).getCombination()));
            }
            else if(auxScore == minScore)
                candidates.add(new Combination(nonUsedComb.get(i).getCombination()));
        }

        boolean assigned = false;
        for(int i = 0; i < candidates.size() && !assigned; ++i){
            if(combSet.contains(candidates.get(i))){
                assigned = true;
                lastComb = candidates.get(i);
            }
        }
        if(!assigned) lastComb = nonUsedComb.get(0);
    }
}
