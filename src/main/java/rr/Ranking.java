package rr;

import game.DiffEnum;

import java.util.Arrays;

public class Ranking {
    private RREntry maxPuntuationEasy[];
    private RREntry maxPuntuationOriginal[];
    private RREntry maxPuntuationHard[];

    public static final int NUM_ENTRYS_RANKING = 10;

    public Ranking() {
        this.maxPuntuationEasy = new RREntry[NUM_ENTRYS_RANKING];
        this.maxPuntuationOriginal = new RREntry[NUM_ENTRYS_RANKING];
        this.maxPuntuationHard = new RREntry[NUM_ENTRYS_RANKING];

        for (int i = 0; i < maxPuntuationEasy.length; i++) {
            maxPuntuationEasy[i] = new RREntry("pepito", 10.0 * i);
            maxPuntuationOriginal[i] = new RREntry("pepito", 10.0 * i);
            maxPuntuationHard[i] = new RREntry("pepito", 10.0 * i);
        }

        Arrays.sort(maxPuntuationEasy, (a, b) -> Double.compare(b.getScore(), a.getScore()));
        Arrays.sort(maxPuntuationOriginal, (a, b) -> Double.compare(b.getScore(), a.getScore()));
        Arrays.sort(maxPuntuationHard, (a, b) -> Double.compare(b.getScore(), a.getScore()));
    }

    public RREntry[] getMaxPuntuationEasy() {
        return maxPuntuationEasy;
    }

    public RREntry[] getMaxPuntuationOriginal() {
        return maxPuntuationOriginal;
    }

    public RREntry[] getMaxPuntuationHard() {
        return maxPuntuationHard;
    }

    public boolean evalNewRR(RREntry rrEntry, DiffEnum diff) {
        RREntry vec[] = new RREntry[0];
        if (diff == DiffEnum.EASY) vec = this.maxPuntuationEasy;
        else if (diff == DiffEnum.ORIGINAL) vec = this.maxPuntuationOriginal;
        else if (diff == DiffEnum.HARD) vec = this.maxPuntuationHard;

        if (rrEntry.isBigger(vec[9])){
            vec[9] = rrEntry;
            Arrays.sort(vec, (a, b) -> Double.compare(b.getScore(), a.getScore()));
            return true;
        }

        return false;
    }

    //For serialization

    public void setMaxPuntuationEasy(RREntry[] maxPuntuationEasy) {
        this.maxPuntuationEasy = maxPuntuationEasy;
    }

    public void setMaxPuntuationOriginal(RREntry[] maxPuntuationOriginal) {
        this.maxPuntuationOriginal = maxPuntuationOriginal;
    }

    public void setMaxPuntuationHard(RREntry[] maxPuntuationHard) {
        this.maxPuntuationHard = maxPuntuationHard;
    }
}
