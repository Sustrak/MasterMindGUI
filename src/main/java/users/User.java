package users;

import game.DiffEnum;

import java.util.Date;
import java.util.Vector;

public class User {
    private String name;
    private String surname;
    private String nickname;
    private Date birthDate;
    private String password;
    private CatEnum category;
    private Vector<Integer> savedGames;
    private double experience;

    private GameTriple maxScore;
    private GameTriple playedGames;
    private GameTriple winnedGames;
    private GameTriple winningSpree;

    /**
     * User Creator
     * @param name
     * @param surname
     * @param nickname
     * @param birthDate
     * @param password
     */
    public User(String name, String surname, String nickname, Date birthDate, String password){
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.password = password;
        this.category = CatEnum.PADAWAN;
        this.savedGames = new Vector<>();
        this.experience = 0.0;
        this.maxScore = new GameTriple();
        this.playedGames = new GameTriple();
        this.winnedGames = new GameTriple();
        this.winningSpree = new GameTriple();
    }

    public User(){}

    /* GETTERS */
    public String getName() {
        return this.name + " " + this.surname;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPassword() {
        return password;
    }

    public CatEnum getCategory() {
        return category;
    }

    public double getExperience() {
        return experience;
    }

    public Vector<Integer> getSavedGames() {
        return savedGames;
    }

    public GameTriple getMaxScore() {
        return maxScore;
    }

    public GameTriple getPlayedGames() {
        return playedGames;
    }

    public GameTriple getWinnedGames() {
        return winnedGames;
    }

    public GameTriple getWinningSpree() {
        return winningSpree;
    }

    /* SETTERS */

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean addToSavedGames(int id){
        return this.savedGames.add(id);
    }

    public boolean removeFromSavedGames(int id){
        Integer i = id;
        return this.savedGames.remove(i);
    }

    public void addExperience(double exp){
        this.experience += exp;
        this.category = CatEnum.getCategory(this.experience);
    }

    public void setMaxScore(double score, DiffEnum difficulty){
        switch (difficulty) {
            case EASY:
                maxScore.setEasy(score);
                break;
            case ORIGINAL:
                maxScore.setOriginal(score);
                break;
            case HARD:
                maxScore.setHard(score);
                break;
        }
    }

    @SuppressWarnings("Duplicates")
    public void addToPlayedGames(DiffEnum difficulty){
        switch (difficulty) {
            case EASY:
                playedGames.incEasy();
                break;
            case ORIGINAL:
                playedGames.incOriginal();
                break;
            case HARD:
                playedGames.incHard();
                break;
        }
    }

    @SuppressWarnings("Duplicates")
    public void addToWinnedGames(DiffEnum difficulty){
        switch (difficulty) {
            case EASY:
                winnedGames.incEasy();
                break;
            case ORIGINAL:
                winnedGames.incOriginal();
                break;
            case HARD:
                winnedGames.incHard();
                break;
        }
    }

    @SuppressWarnings("Duplicates")
    public void addToWinningSpree(DiffEnum difficulty){
        switch (difficulty) {
            case EASY:
                winningSpree.incEasy();
                break;
            case ORIGINAL:
                winningSpree.incOriginal();
                break;
            case HARD:
                winningSpree.incHard();
                break;
        }
    }

    public void resetWinningSpree(DiffEnum difficulty){
        switch (difficulty) {
            case EASY:
                winningSpree.setEasy(0);
                break;
            case ORIGINAL:
                winningSpree.setOriginal(0);
                break;
            case HARD:
                winningSpree.setHard(0);
                break;
        }
    }

    @Override
    public boolean equals(Object o){
        return ((User)o).nickname.equals(this.nickname);
    }

    @Override
    public int hashCode(){
        return this.nickname.hashCode();
    }

    public String toString(){
        String s = "";
        return s.concat(name + " "+ surname + " " + nickname);
    }



    //Getters and setters for serialization


    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCategory(CatEnum category) {
        this.category = category;
    }

    public void setSavedGames(Vector<Integer> savedGames) {
        this.savedGames = savedGames;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public void setMaxScore(GameTriple maxScore) {
        this.maxScore = maxScore;
    }

    public void setPlayedGames(GameTriple playedGames) {
        this.playedGames = playedGames;
    }

    public void setWinnedGames(GameTriple winnedGames) {
        this.winnedGames = winnedGames;
    }

    public void setWinningSpree(GameTriple winningSpree) {
        this.winningSpree = winningSpree;
    }
}
