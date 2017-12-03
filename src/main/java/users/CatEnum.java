package users;

public enum CatEnum {
    PADAWAN,
    FRIKI,
    FIBER,
    CRAZY,
    MASTERJEDI;

    private static final int FRIKI_POINTS = 25000;
    private static final int FIBER_POINTS = 45000;
    private static final int CRAZY_POINTS = 60000;
    private static final int MASTER_JEDI_POINTS = 100000;


    public static CatEnum getCategory(double experience) {
        if (experience >= MASTER_JEDI_POINTS) return MASTERJEDI;
        else if (experience >= CRAZY_POINTS) return CRAZY;
        else if (experience >= FIBER_POINTS) return FIBER;
        else if (experience >= FRIKI_POINTS) return FRIKI;
        else return PADAWAN;
    }
}
