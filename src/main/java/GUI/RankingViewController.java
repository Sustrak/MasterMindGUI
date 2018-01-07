package GUI;

import game.DiffEnum;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import layers.DomainCtrl;
import rr.RREntry;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;


public class RankingViewController {

    private DomainCtrl domainCtrl;

    public VBox mainVBox;
    public Label difficultyLabel;
    public GridPane rankingGridPane;

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
        buildRankingsGridPane();
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void buildRankingsGridPane() {
        
        ArrayList<ArrayList<String>> rankingList = new ArrayList<>();

        switch (ViewController.askCodeBreakerDifficulty()) {
            case EASY:
                rankingList = domainCtrl.getRankingTable(DiffEnum.EASY);
                difficultyLabel.setText("Ranking dificultad Fácil");
                break;
            case ORIGINAL:
                rankingList = domainCtrl.getRankingTable(DiffEnum.ORIGINAL);
                difficultyLabel.setText("Ranking dificultad Original");
                break;
            case HARD:
                rankingList = domainCtrl.getRankingTable(DiffEnum.HARD);
                difficultyLabel.setText("Ranking dificultad Difícil");
                break;
        }

        for (int i = 0; i < rankingList.size(); i++) {
            Label label = new Label(rankingList.get(i).get(0));
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            rankingGridPane.add(label, 0, i+1);
            label = new Label(rankingList.get(i).get(1));
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            rankingGridPane.add(label, 1, i+1);
            label = new Label(rankingList.get(i).get(2));
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.CENTER);
            rankingGridPane.add(label, 2, i+1);
        }

    }

}
