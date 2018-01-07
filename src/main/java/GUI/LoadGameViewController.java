package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import layers.DomainCtrl;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;


public class LoadGameViewController {

    private DomainCtrl domainCtrl;

    public VBox mainVBox;
    public GridPane savedGamesGridPane;

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        buildSavedGamesTable();
    }

    private void buildSavedGamesTable() {

        Vector ids = domainCtrl.getIdSavedGames();
        int i = 0;
        int errorLoading = 0;
        for (Object id1 : ids) {
            int id = (int) id1;
            ArrayList<String> infoGame = null;
            try {
                infoGame = domainCtrl.getInfoGame(id);
            } catch (FileNotFoundException e) {
                String s = "No se pudo cargar la partida con id: " + id + " comprueve que el archivo exista";
                ViewController.showErrorMessage(s);
                ++errorLoading;
                continue;
            }
            addSavedGame(infoGame, i);
            i++;
        }
    }

    private void addSavedGame(ArrayList<String> infoGame, int i) {
        i++;
        Label label = new Label(infoGame.get(2));
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.CENTER);
        savedGamesGridPane.add(label, 0, i);
        label = new Label(infoGame.get(1));
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.CENTER);
        savedGamesGridPane.add(label, 1, i);
        Button button = new Button("Cargar");
        button.setId(infoGame.get(0));
        button.setOnAction(loadButtonAction);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(button, VPos.CENTER);
        savedGamesGridPane.add(button, 2, i);
    }

    private EventHandler<ActionEvent> loadButtonAction = event -> {
        Object source = event.getTarget();
        if (source instanceof Button) {
            int gameId = Integer.parseInt(((Button)source).getId());
            try {
                ViewController.loadBoardView(domainCtrl, gameId);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    };


    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
