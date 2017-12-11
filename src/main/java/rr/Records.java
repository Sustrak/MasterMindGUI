package rr;

public class Records {
    private RREntry maxExp;
    private RREntry maxWinningSpree;
    private RREntry maxPlayedGames;
    private RREntry maxWinnedGames;

    public static final int NUM_RECORDS = 4;

    public Records() {
        this.maxExp = new RREntry("Pepito", 50);
        this.maxWinningSpree = new RREntry("Pepito", 2);
        this.maxPlayedGames = new RREntry("Pepito", 1000);
        this.maxWinnedGames = new RREntry("Pepito", 50);

        // TODO: Load Records from DataBase
    }

    public RREntry getMaxExp() {
        return maxExp;
    }

    public RREntry getMaxWinningSpree() {
        return maxWinningSpree;
    }

    public RREntry getMaxPlayedGames() {
        return maxPlayedGames;
    }

    public RREntry getMaxWinnedGames() {
        return maxWinnedGames;
    }

    public void evalMaxExp(RREntry rrEntry) {
        if (rrEntry.isBigger(this.maxExp)) this.maxExp = rrEntry;
    }

    public void evalMaxWinningSpree(RREntry rrEntry) {
        if (rrEntry.isBigger(this.maxWinningSpree)) this.maxWinningSpree = rrEntry;
    }

    public void evalmaxPlayedGames(RREntry rrEntry) {
        if (rrEntry.isBigger(this.maxPlayedGames)) this.maxPlayedGames = rrEntry;
    }

    public void evalmaxWinnedGames(RREntry rrEntry) {
        if (rrEntry.isBigger(this.maxWinnedGames)) this.maxWinnedGames = rrEntry;
    }

    //For serialization

    public void setMaxExp(RREntry maxExp) {
        this.maxExp = maxExp;
    }

    public void setMaxWinningSpree(RREntry maxWinningSpree) {
        this.maxWinningSpree = maxWinningSpree;
    }

    public void setMaxPlayedGames(RREntry maxPlayedGames) {
        this.maxPlayedGames = maxPlayedGames;
    }

    public void setMaxWinnedGames(RREntry maxWinnedGames) {
        this.maxWinnedGames = maxWinnedGames;
    }
}
