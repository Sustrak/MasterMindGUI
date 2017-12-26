package layers;


import rr.Records;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class PersistenceCtrl {
    public static final String USERS_FILE_PATH = "src/main/resources/Data/users.xml";
    public static final String RANKINGS_FILE_PATH = "src/main/resources/Data/rankings.xml";
    public static final String RECORDS_FILE_PATH = "src/main/resources/Data/records.xml";
    public static final String GAMES_FILE_PATH = "src/main/resources/Data/games.xml";

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

    public static void main(String[] args) throws Exception {
        Records rr = (Records) loadObject(RECORDS_FILE_PATH);
        System.out.println(rr.getMaxExp());
    }
}
