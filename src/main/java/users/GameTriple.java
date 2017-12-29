package users;

import game.DiffEnum;


public class GameTriple {
    //TODO: Infinity problem at scores to high
    private double easy;
    private double original;
    private double hard;

    public GameTriple() {
    }

    public GameTriple(double easy, double original, double hard) {
        this.easy = easy;
        this.original = original;
        this.hard = hard;
    }

    public void setEasy(double easy) {
        this.easy = easy;
    }

    public void setOriginal(double original) {
        this.original = original;
    }

    public void setHard(double hard) {
        this.hard = hard;
    }

    public void incEasy() {
        this.easy++;
    }

    public void incOriginal() {
        this.original++;
    }

    public void incHard() {
        this.hard++;
    }

    @Override
    public String toString() {
        return easy + "    "  + original + "         " + hard;
    }

    public double getScore(DiffEnum diff) {
        if (diff == DiffEnum.EASY) return easy;
        if (diff == DiffEnum.ORIGINAL) return original;
        if (diff == DiffEnum.HARD) return hard;
        return 0.;
    }

    //Getters and setters for serialization

    public double getEasy() {
        return easy;
    }

    public double getOriginal() {
        return original;
    }

    public double getHard() {
        return hard;
    }
}