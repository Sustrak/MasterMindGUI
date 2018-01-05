package layers;

import game.CodeBreaker;
import game.DiffEnum;
import utils.OnlineRR;

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

    private static String getFileName(String path) {
        switch (path) {
            case USERS_FILE_PATH:
                return "users.xml";
            case RANKINGS_FILE_PATH:
                return "rankings.xml";
            case RECORDS_FILE_PATH:
                return "records.xml";
            default:
                return null;
        }
    }

    /**
     * Method to serialize and save all the MasterMind data in XML
     * @param o object to save
     * @param path path to save the object
     */
    public static void saveObject(Object o, String path) {
        XMLEncoder encoder =  null;
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

        //Upload file to server
        String fileName = getFileName(path);
        if (fileName != null) try {
            OnlineRR.HttpPOSTFile(fileName);
        } catch (IOException e) {
            System.out.println("Error guardando " + fileName + " en la nube");
        }
    }

    /**
     * Method to load the MasterMind data. The object needs to be casted to its correct Object (Records, UaerSet, ...)
     * @param path of the object to load
     * @return returns the Object
     * @throws FileNotFoundException
     */
    public static Object loadObject(String path) throws FileNotFoundException {
        //Download file from server
        String fileName = getFileName(path);
        if (fileName != null) try {
            OnlineRR.HttpGETFile(fileName);
        } catch (IOException e) {
            System.out.println("Error cargando " + fileName + " de la nube");
            throw new FileNotFoundException();
        }
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
        saveObject(o, path);
    }

    public static Object loadGame(int gameId, String nickname) throws FileNotFoundException {
        String path = String.format("%s%s%s.xml", GAMES_DIR_PATH + nickname, File.separator, String.valueOf(gameId));
        return loadObject(path);
    }

    public static boolean removeGame(int gameId, String nickname) {
        String path = String.format("%s%s%s.xml", GAMES_DIR_PATH + nickname, File.separator, String.valueOf(gameId));
        File f = new File(path);
        return f.delete();
    }
}
