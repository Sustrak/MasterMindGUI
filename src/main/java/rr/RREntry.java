package rr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(date) + "    " + username + "        " + (int)score;
    }

    //For serialization

    public RREntry() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
