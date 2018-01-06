package layers;

import game.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import rr.*;
import users.*;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DomainCtrl {

    private UserSet uSet;
    private User currentUser;
    private Game currentGame;
    private Ranking ranking;
    private Records records;
    private Meta meta;
    // Music
    private MediaPlayer backgroundMusic;

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
        try {
            meta = (Meta) PersistenceCtrl.loadObject(PersistenceCtrl.META_FILE_PATH);
        } catch (FileNotFoundException e) {
            //TODO: Informar a l'usuari que no s'ha trobat el meta i pertant podria haver-hi fallos al guardar una partida per problema del id
            meta = new Meta();
            meta.setLastId(0);
            meta.setBackgroundMusicVolume(0.25);
        }
        //Load Music
        Media media = new Media (Paths.get("src/main/resources/GUI/Music/MM_music.mp4").toUri().toString());
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setCycleCount(99999);
        backgroundMusic.setVolume(meta.getBackgroundMusicVolume());
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

        currentGame = new CodeBreaker(difficulty, meta.getIncLastId());
        //Save meta because we have updated lastId
        PersistenceCtrl.saveObject(meta, PersistenceCtrl.META_FILE_PATH);
    }

    public void startNewCodeMaker(DiffEnum difficulty) {

        if(difficulty == DiffEnum.ORIGINAL) currentGame = new FiveGuess();
        else currentGame = new GeneticGuess();
    }

    public String[] getRanking(DiffEnum diff) {

        RREntry rrEntry[] = getRankingRREntryes(diff);

        String s[] = new String[Ranking.NUM_ENTRYS_RANKING];
        for (int i = 0; i < s.length; i++) {
            s[i] = rrEntry[i].toString();
        }

        return s;
    }

    public RREntry[] getRankingRREntryes(DiffEnum diff) {
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
        return rrEntry;
    }

    public String[] getRecords() {
        RREntry rrEntrys[] = getRecordsRREntryes();

        String s[] = new String[Records.NUM_RECORDS];
        for (int i = 0; i < s.length; i++) {
            s[i] = rrEntrys[i].toString();
        }

        return s;
    }

    public RREntry[] getRecordsRREntryes() {
        RREntry rrEntrys[] = new RREntry[Records.NUM_RECORDS];
        rrEntrys[0] = records.getMaxExp();
        rrEntrys[1] = records.getMaxWinningSpree();
        rrEntrys[2] = records.getMaxPlayedGames();
        rrEntrys[3] = records.getMaxWinnedGames();

        return rrEntrys;
    }

    public Map<String, ArrayList<String>> getPlayerInfo(String nickname) throws UserNotFoundException {
        return uSet.getUserInfo(nickname);
    }

    public void loadGame(int id) throws FileNotFoundException {
        currentGame = (CodeBreaker) PersistenceCtrl.loadGame(id, currentUser.getNickname());
        ((CodeBreaker) currentGame).setInitialTime(System.currentTimeMillis());
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

    public void saveGame() {
        ((CodeBreaker) currentGame).setLSDNow();
        ((CodeBreaker) currentGame).setTimeElapsed();
        PersistenceCtrl.saveGame(currentGame, currentGame.getId(), currentUser.getNickname());
        currentUser.addToSavedGames(currentGame.getId());
        PersistenceCtrl.saveObject(uSet, PersistenceCtrl.USERS_FILE_PATH);
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


    /**
     * @param gameId
     * @return an ArrayList which contains the info of a game in this order:
     *         game.id, game.DiffEnum, game.lastSavedDate
     */
    public ArrayList<String> getInfoGame(int gameId) throws FileNotFoundException {
        CodeBreaker game = null;
        game = (CodeBreaker) PersistenceCtrl.loadGame(gameId, currentUser.getNickname());

        ArrayList<String> info = new ArrayList<>();
        assert game != null;
        info.add(String.valueOf(gameId));
        info.add(game.getDificulty().toString());
        info.add(game.getLastSaveDate().toString());
        return info;
    }

    public Vector<Integer> getIdSavedGames() {
        return currentUser.getSavedGames();
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
        if (currentUser.removeFromSavedGames(currentGame.getId()))
            PersistenceCtrl.removeGame(currentGame.getId(), currentUser.getNickname());
        if (!winnedGame){
            currentUser.resetWinningSpree(currentGame.getDificulty());
            currentUser.addToPlayedGames(currentGame.getDificulty());
            PersistenceCtrl.saveObject(uSet, PersistenceCtrl.USERS_FILE_PATH);
            RREntry maxPlayedGames = new RREntry(currentUser.getNickname(), currentUser.getPlayedGames().getScore(currentGame.getDificulty()));
            records.evalmaxPlayedGames(maxPlayedGames);
            PersistenceCtrl.saveObject(records, PersistenceCtrl.RECORDS_FILE_PATH);
            return;
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

    public void saveMeta() {
        PersistenceCtrl.saveObject(meta, PersistenceCtrl.META_FILE_PATH);
    }
    //Music options
    public void playBackgroundMusic() {
        backgroundMusic.play();
    }

    public void setVolumeBackgroundMusic(double newVol) {
        backgroundMusic.setVolume(newVol);
        meta.setBackgroundMusicVolume(newVol);
    }

    public double getVolumeBackgrounMusic() {
        return meta.getBackgroundMusicVolume();
    }

    public void muteBackgroundMusic(Boolean b) {
        backgroundMusic.setMute(b);
    }
}
