package layers;

/**
 * Class for storing info about the programm
 */
public class Meta {
    private int lastId;

    public Meta() {

    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public int getIncLastId() {
        return lastId++;
    }
}