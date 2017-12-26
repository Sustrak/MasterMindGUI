package layers;

import game.*;
import rr.*;
import users.*;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DomainCtrl {

    private UserSet uSet;
    private User currentUser;
    private Game currentGame;
    private Ranking ranking;
    private Records records;

    public DomainCtrl(){

        try {
            uSet = (UserSet) PersistenceCtrl.loadObject(PersistenceCtrl.USERS_FILE_PATH);
        } catch (FileNotFoundException e) {
            uSet = new UserSet();
        }
        currentUser = null;
        currentGame = null;
        try {
            ranking = (Ranking) PersistenceCtrl.loadObject(PersistenceCtrl.RANKINGS_FILE_PATH);
        } catch (FileNotFoundException e) {
            ranking = new Ranking();
        }
        try {
            records = (Records) PersistenceCtrl.loadObject(PersistenceCtrl.RECORDS_FILE_PATH);
        } catch (FileNotFoundException e) {
            records = new Records();
        }
    }

    // Initial Options

    /**
     *
     * @param name
     * @param surname
     * @param nickname
     * @param birthDate
     * @param password
     * @return
     */
    public int createUser(String name, String surname, String nickname, String birthDate, String password){

        //Check if birthDate is a wellformed Date
        Date bD;
        try {
            bD = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        } catch (ParseException ex) {
            return 2;
        }
        int res = uSet.newUser(name, surname, nickname, bD, password);
        if (res == 0) PersistenceCtrl.saveObject(uSet, PersistenceCtrl.USERS_FILE_PATH);
        return res;
    }

    /**
     *
     * @param nickname
     * @param password
     * @return
     */
    public int logIn(String nickname, String password) {

        try {
            currentUser = uSet.logIn(nickname, password);
        } catch (WrongPasswordException ex) {
            return 1;
        } catch (UserNotFoundException ex) {
            return 2;
        }
        return 0;
    }

    /**
     *
     * @param nickname
     * @param birthDate
     * @param newPassword
     * @return
     */
    public int resetPassword(String nickname, String birthDate, String newPassword) {

        //Check if birthDate is a wellformed Date
        Date bD;
        try {
            bD = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        } catch (ParseException ex) {
            return 1;
        }

        try {
            uSet.resetPassword(nickname, bD, newPassword);
        } catch (UserNotFoundException ex) {
            Logger.getLogger(DomainCtrl.class.getName()).log(Level.SEVERE, null, ex);
            return 2;
        } catch (WrongDateException ex) {
            Logger.getLogger(DomainCtrl.class.getName()).log(Level.SEVERE, null, ex);
            return 3;
        }

        return 0;
    }

    public void logOut() {

        currentUser = null;
        currentGame = null;
    }

    // Game Options

    /**
     *
     * @param difficulty
     */
    public void startNewCodeBreaker(DiffEnum difficulty) {

        currentGame = new CodeBreaker(difficulty);
    }

    public void startNewCodeMaker() {

        currentGame = new CodeMaker();
    }

    public String[] getRanking(DiffEnum diff) {

        RREntry rrEntry[] = new RREntry[0];
        switch (diff) {
            case EASY:
                rrEntry =  ranking.getMaxPuntuationEasy();
                break;
            case ORIGINAL:
                rrEntry =  ranking.getMaxPuntuationOriginal();
                break;
            case HARD:
                rrEntry =  ranking.getMaxPuntuationHard();
                break;
        }

        String s[] = new String[Ranking.NUM_ENTRYS_RANKING];
        for (int i = 0; i < s.length; i++) {
            s[i] = rrEntry[i].toString();
        }

        return s;
    }

    public String[] getRecords() {
        RREntry rrEntrys[] = new RREntry[Records.NUM_RECORDS];
        rrEntrys[0] = records.getMaxExp();
        rrEntrys[1] = records.getMaxWinningSpree();
        rrEntrys[2] = records.getMaxPlayedGames();
        rrEntrys[3] = records.getMaxWinnedGames();

        String s[] = new String[Records.NUM_RECORDS];
        for (int i = 0; i < s.length; i++) {
            s[i] = rrEntrys[i].toString();
        }

        return s;
    }

    public Map<String,String> getPlayerInfo(String nickname) throws UserNotFoundException {
        return uSet.getUserInfo(nickname);
    }

    public void loadGame(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Integer> getNewCodeMakerComb() {

        return ((CodeMaker)currentGame).getNewCombination();
    }

    public int useFirstClue() {
        return ((CodeBreaker)currentGame).useFirstClue();
    }

    public void useSecondClue() {
        ((CodeBreaker)currentGame).useSecondClue();
    }




    // Getters

    public int getWhitePegs(int nWP) {
        return currentGame.getWhitePegs(nWP);
    }

    public int getBlackPegs(int nBP) {
        return currentGame.getBlackPegs(nBP);
    }

    public int getNPieces() {
        return currentGame.getNPieces();
    }

    public int getPositionClue() {
        return ((CodeBreaker)currentGame).getPositionClue();
    }

    public int getColorClue() {
        return ((CodeBreaker)currentGame).getColorClue();
    }

    public int getColorRemoved() {
        return ((CodeBreaker)currentGame).getColorRemoved();
    }

    public int getNCombinations() {

        return currentGame.getNCombinations();
    }

    public ArrayList<Integer> getCombination(int i) {
        return currentGame.getCombination(i);
    }

    public int getNUsers() {
        return uSet.getNUsers();
    }

    public double getScore() {
        return currentGame.getScore();
    }

    public Combination getWinnerCombination() {
        return currentGame.getWinnerCombination();
    }

    public ArrayList<Integer> getWinnerCombinationArray() {
        return currentGame.getWinnerCombination().getCombination();
    }

    public ArrayList<Integer> getColorsAvailable(){
        return currentGame.getColorsAvailable();
    }

    String getInfoGame(String get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ArrayList<String> getIdSavedGames() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DiffEnum getDifficulty() {
        return currentGame.getDificulty();
    }


    // Setters

    public boolean setNewCombination(ArrayList<Integer> newCombination) {
        return currentGame.setNewCombination(newCombination);
    }

    public void setWinnerCombination(ArrayList<Integer> winnerComb) {
        currentGame.setWinnerCombination(winnerComb);
    }

    public long setTimeElapsed() {
        return ((CodeBreaker)currentGame).setTimeElapsed();
    }

    public void setLastCorrection(int bP, int wP) {
        currentGame.setLastCorrection(bP, wP);
    }


    // Auxiliary

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public double calculateScore() {
        return currentGame.calculateScore();
    }

    public boolean firstClueUsed(){
        return ((CodeBreaker)currentGame).firstClueUsed();
    }

    public boolean secondClueUsed(){
        return ((CodeBreaker)currentGame).secondClueUsed();
    }

    public boolean boardIsFull() {
        return currentGame.boardIsFull();
    }

    public boolean isGoodCorrection(int bP, int wP) {
        return currentGame.isGoodCorrection(bP, wP);
    }

    /**
     * Updates all the info after a game is finished. User, Records and Rankings
     * @param winnedGame is true if the player winned the game otherwise is false
     */
    //TODO: Return info if a record or ranking has been updated
    public void updatePlayerOnFinishGame(boolean winnedGame) {
        if (!winnedGame){
            currentUser.resetWinningSpree(currentGame.getDificulty());
        }
        double score = currentGame.getScore();
        String nickname = currentUser.getNickname();
        DiffEnum diff = currentGame.getDificulty();
        RREntry scoreEntry = new RREntry(nickname, score);

        //Update & Save ranking
        ranking.evalNewRR(scoreEntry, diff);
        PersistenceCtrl.saveObject(ranking, PersistenceCtrl.RANKINGS_FILE_PATH);

        double exp = 0;
        switch (diff) {
            case EASY:
                exp = score;
                break;
            case ORIGINAL:
                exp = 2*score;
                break;
            case HARD:
                exp = 3*score;
                break;
            default:
                break;
        }

        //Update & Save user
        currentUser.addExperience(exp);
        currentUser.addToPlayedGames(diff);
        currentUser.addToWinnedGames(diff);
        currentUser.addToWinningSpree(diff);
        if (currentUser.getMaxScore().getScore(diff) < score) currentUser.setMaxScore(score, diff);
        PersistenceCtrl.saveObject(uSet, PersistenceCtrl.USERS_FILE_PATH);

        //Update & Save records
        RREntry maxExp = new RREntry(nickname, currentUser.getExperience());
        RREntry maxPlayedGames = new RREntry(nickname, currentUser.getPlayedGames().getScore(diff));
        RREntry maxWinnedGames = new RREntry(nickname, currentUser.getWinnedGames().getScore(diff));
        RREntry maxWinningSpree = new RREntry(nickname, currentUser.getWinningSpree().getScore(diff));

        records.evalMaxExp(maxExp);
        records.evalmaxPlayedGames(maxPlayedGames);
        records.evalmaxWinnedGames(maxWinnedGames);
        records.evalMaxWinningSpree(maxWinningSpree);
        PersistenceCtrl.saveObject(records, PersistenceCtrl.RECORDS_FILE_PATH);
    }

    public boolean usedNickname(String nickname) {
        return uSet.existsUser(nickname);
    }

}
