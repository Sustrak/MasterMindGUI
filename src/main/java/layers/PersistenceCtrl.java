package layers;

import game.CodeBreaker;
import game.DiffEnum;
import rr.Records;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;


public class PersistenceCtrl {
    public static final String USERS_FILE_PATH = "src/main/resources/Data/users.xml";
    public static final String RANKINGS_FILE_PATH = "src/main/resources/Data/rankings.xml";
    public static final String RECORDS_FILE_PATH = "src/main/resources/Data/records.xml";
    public static final String META_FILE_PATH = "src/main/resources/Data/meta.xml";
    public static final String GAMES_DIR_PATH = "src/main/resources/Data/Games/";

    public PersistenceCtrl() {}

    public static void saveObject(Object o, String path) {
        XMLEncoder encoder =
                null;
        try {
            encoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert encoder != null;
        encoder.writeObject(o);
        encoder.close();
    }

    public static Object loadObject(String path) throws FileNotFoundException {
        XMLDecoder decoder =
                new XMLDecoder(
                        new BufferedInputStream(
                                new FileInputStream(path)));
        return decoder.readObject();
    }

    public static void saveGame(Object o, int gameId, String nickname){
        File userDir = new File(GAMES_DIR_PATH, nickname);
        if (!userDir.isDirectory()) userDir.mkdirs();
        String path = String.format("%s%s%s.xml", userDir.getPath(), File.separator, String.valueOf(gameId));
        System.out.println(path);
        System.out.println(userDir.isDirectory());
        saveObject(o, path);
    }

    public static Object loadGame(int gameId, String nickname) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        CodeBreaker cb = new CodeBreaker(DiffEnum.EASY, 4);
        saveGame(cb, 2, "Manolo");
    }
}
