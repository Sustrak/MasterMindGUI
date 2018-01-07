package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CodeBreaker extends Game {

    private DiffEnum difficulty;
    private double score;
    private boolean firstClue;
    private boolean secondClue;
    private int positionClue;
    private int colorClue;
    private long colorRemoved;

    private long elapsedTime;
    private long initialTime;
    private Date lastSaveDate;

    /**
     *
     * @param difficulty
     */
    public CodeBreaker(DiffEnum difficulty, int id) {

        this.id = id;
        this.difficulty = difficulty;
        score = 0;
        firstClue = secondClue = false;
        colorClue = (int) -1;
        colorRemoved = (int) -1;
        elapsedTime = 0;
        initialTime = System.currentTimeMillis();


        if(difficulty == DiffEnum.EASY || difficulty == DiffEnum.ORIGINAL) {
            currentBoard = new Board(4, 10);
            colorsAvailable = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        }
        else {
            currentBoard = new Board(6, 10);
            colorsAvailable = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
        }

        generateWinnerCombination();
    }

    // Es genera la combinació inicial. Si tinguessim subclases no faria falta switch
    private void generateWinnerCombination() {

        Random r = new Random();
        ArrayList<Integer> combination = new ArrayList<>();

        switch(difficulty){

            case EASY:
                // Easy mode. 4 pieces, 6 possible colours without repetitions
                combination = new ArrayList<>(4);
                ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));

                for(int i = 0; i < 4; ++i){
                    int color = r.nextInt(colors.size());
                    combination.add(colors.remove((int)color));
                } break;

            case ORIGINAL:
                // Original mode. 4 pieces, 6 possible colours with repetitions
                combination = new ArrayList<>(4);

                for(int i = 0; i < 4; ++i){
                    int color = r.nextInt(6);
                    combination.add(color);
                } break;

            case HARD:
                // Hard mode. 6 pieces, 8 possible colours with repetitions
                combination = new ArrayList<>(6);

                for(int i = 0; i < 6; ++i){
                    int color = r.nextInt(8);
                    combination.add(color);
                } break;

            default: break;
        }
        currentBoard.setWinnerCombination(combination);
    }

    @Override
    public int getElementsComb(){
        
        if(difficulty == DiffEnum.EASY || difficulty == DiffEnum.ORIGINAL) return 4;
        else return 6;
    }

    public boolean secondClueUsed() {
        return secondClue;
    }

    public boolean firstClueUsed() {
        return firstClue;
    }

    public int getPositionClue() {
        return positionClue;
    }

    public int getColorClue() {
        return colorClue;
    }

    public int getColorRemoved(){
        return (int) colorRemoved;
    }

    public int useFirstClue(){

        firstClue = true;
        ArrayList<Integer> colorsCopy = new ArrayList(colorsAvailable);
        colorsCopy.removeAll(currentBoard.getWinnerCombination().getCombination());

        Random r = new Random();
        colorRemoved = colorsCopy.get(r.nextInt(colorsCopy.size()));
        return colorsAvailable.remove((int)colorRemoved);
    }

    public void useSecondClue() {

        Combination winnerComb = currentBoard.getWinnerCombination();
        positionClue = winnerComb.getRandomPosition();
        colorClue = winnerComb.getColor(positionClue);
        secondClue = true;
    }

    public long setTimeElapsed() {
        elapsedTime += System.currentTimeMillis() - initialTime;
        return elapsedTime;
    }

    @Override
    public double calculateScore(){
        if(score == 0){
            setTimeElapsed();
            System.out.println(elapsedTime);
            int nCombUsed = currentBoard.getNCombinations();
            switch(nCombUsed){
                case 1: score = 3000; break;
                case 2: score = 2000; break;
                case 3: score = 1500; break;
                case 4: score = 1200; break;
                case 5: score = 1000; break;
                case 6: score = 900; break;
                case 7: score = 800; break;
                case 8: score = 500; break;
                case 9: score = 400; break;
                case 10: score = 300; break;
                default: break;
            }
        
            double seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
            score = score * (1./seconds) * 100;
        }

        return score;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public DiffEnum getDificulty() {
        return difficulty;
    }

    public void setLSDNow(){
        this.lastSaveDate = new Date();
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    //Methods for serialize the class

    public CodeBreaker() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DiffEnum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DiffEnum difficulty) {
        this.difficulty = difficulty;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isFirstClue() {
        return firstClue;
    }

    public void setFirstClue(boolean firstClue) {
        this.firstClue = firstClue;
    }

    public boolean isSecondClue() {
        return secondClue;
    }

    public void setSecondClue(boolean secondClue) {
        this.secondClue = secondClue;
    }

    public void setPositionClue(int positionClue) {
        this.positionClue = positionClue;
    }

    public void setColorClue(int colorClue) {
        this.colorClue = colorClue;
    }

    public void setColorRemoved(long colorRemoved) {
        this.colorRemoved = colorRemoved;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(long initialTime) {
        this.initialTime = initialTime;
    }

    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }
}
