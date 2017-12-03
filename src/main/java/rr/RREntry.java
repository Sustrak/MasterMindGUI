package rr;

import java.util.Date;

public class RREntry {
    private String username;
    private double score;
    private Date date;

    public RREntry(String username, double score) {
        this.username = username;
        this.score = score;
        this.date = new Date();
    }

    public boolean isBigger(RREntry r) {
        return this.score > r.score;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return date.toString() + "    " + username + "        " + score;
    }
}
