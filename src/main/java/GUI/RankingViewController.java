package GUI;

import game.DiffEnum;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import layers.DomainCtrl;
import rr.RREntry;

import java.net.MalformedURLException;
import java.util.Date;


public class RankingViewController {

    private DomainCtrl domainCtrl;

    public VBox mainVBox;
    public Label difficultyLabel;
    public TableView<RREntry> rankingTable;
    public TableColumn<RREntry, Date> dateColumn;
    public TableColumn<RREntry, String> nickNameColumn;
    public TableColumn<RREntry, Double> scoreColumn;

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
        
        RREntry rankingList[] = new RREntry[0];

        switch (ViewController.askCodeBreakerDifficulty()) {
            case EASY:
                rankingList = domainCtrl.getRankingRREntryes(DiffEnum.EASY);
                difficultyLabel.setText("Ranking dificultad Fácil");
                break;
            case ORIGINAL:
                rankingList = domainCtrl.getRankingRREntryes(DiffEnum.ORIGINAL);
                difficultyLabel.setText("Ranking dificultad Original");
                break;
            case HARD:
                rankingList = domainCtrl.getRankingRREntryes(DiffEnum.HARD);
                difficultyLabel.setText("Ranking dificultad Difícil");
                break;
        }

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<RREntry, Date>("date"));

        nickNameColumn.setCellValueFactory(
                new PropertyValueFactory<RREntry, String>("username"));

        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<RREntry, Double>("score"));

        rankingTable.getItems().setAll(rankingList);
    }

}
