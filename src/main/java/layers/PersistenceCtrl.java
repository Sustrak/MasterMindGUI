package layers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceCtrl {
    private static final String USER_FILE_PATH = "src/main/resources/Data/users.xml";
    private static final String RR_FILE_PATH = "src/main/resources/Data/rr.xml";
    private static final String GAME_FILE_PATH = "src/main/resources/Data/game.xml";

    public PersistenceCtrl() {
    }

    /**
     * @return returns a map with all the user attributes if the users.xml file exists otherwise returns an empty HashMap
     *         and creates the file
     */
    public HashMap<String, ArrayList<String>> getOrCreateUsers(){
        HashMap<String, ArrayList<String>> users = new HashMap<>();
        Document xml = null;
        File file = new File(USER_FILE_PATH);
        if (!file.isFile()) {
            try {
                file.createNewFile();
                return users;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert xml != null;
        NodeList nodeList = xml.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList childs = nodeList.item(i).getChildNodes();
            for (int j = 1; j < childs.getLength(); j += 2) {
                ArrayList<String> data = new ArrayList<>();
                Node n = childs.item(j);
                //TODO: All nodes had childNodes so I had to do this shit may be there is a better way
                if (n.getChildNodes().item(1) != null){
                    NodeList subChilds = n.getChildNodes();
                    for (int k = 1; k < subChilds.getLength(); k+=2) {
                        Node nn = subChilds.item(k);
                        data.add(nn.getTextContent());
                    }
                }
                else data.add(n.getTextContent());
                users.put(n.getNodeName(), data);
            }
        }
        return users;
    }

    public static void main(String[] args) {
        PersistenceCtrl p = new PersistenceCtrl();
        System.out.println(p.getOrCreateUsers());
    }
}
