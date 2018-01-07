package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import layers.DomainCtrl;
import users.UserNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class UserViewController implements Initializable{

    private DomainCtrl domainCtrl;

    public Label nameLabel;
    public Label categoryLabel;
    public Label experienceLabel;
    public Label originalMaxScoreLabel;
    public Label easyPlayedGamesLabel;
    public Label originalPlayedGamesLabel;
    public Label easyWinnedGamesLabel;
    public Label easyMaxScoreLabel;
    public Label originalWinnedGamesLabel;
    public Label hardWinnedGamesLabel;
    public Label easyWinningSpreeLabel;
    public Label hardWinningSpreeLabel;
    public Label hardMaxScoreLabel;
    public Label hardPlayedGamesLabel;
    public Label originalWinningSpreeLabel;

    public VBox mainVBox;
    public Label userNotFoundLabel;
    public TextField nickNameTextField;
    public GridPane userInfoGridPane;
    public GridPane userStaticsGridPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfoGridPane.setVisible(false);
        userStaticsGridPane.setVisible(false);
    }

    public void setDomainCtrl(DomainCtrl domainCtrl) {
        this.domainCtrl = domainCtrl;
    }

    public void checkButtonAction(ActionEvent actionEvent) {
        Map<String, ArrayList<String>> info;
        String nickName = nickNameTextField.getText();
        try {
            info = domainCtrl.getPlayerInfo(nickName);
            userNotFoundLabel.setVisible(false);
            fillGridPanes(nickName, info);
            userInfoGridPane.setVisible(true);
            userStaticsGridPane.setVisible(true);
            //mainVBox.getChildren().add(1, userGridPane);

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            userInfoGridPane.setVisible(false);
            userStaticsGridPane.setVisible(false);
            userNotFoundLabel.setVisible(true);
        }

    }


    public void exitButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.mainMenuView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void fillGridPanes(String nickName, Map<String, ArrayList<String>> info) {
        nameLabel.setText(info.get("name").get(0));
        categoryLabel.setText(info.get("category").get(0));
        experienceLabel.setText(info.get("experience").get(0));
        // Max Score
        easyMaxScoreLabel.setText(info.get("maxScore").get(0));
        originalMaxScoreLabel.setText(info.get("maxScore").get(1));
        hardMaxScoreLabel.setText(info.get("maxScore").get(2));
        // Played Games
        easyPlayedGamesLabel.setText(info.get("playedGames").get(0));
        originalPlayedGamesLabel.setText(info.get("playedGames").get(1));
        hardPlayedGamesLabel.setText(info.get("playedGames").get(2));
        // Winned Games
        easyWinnedGamesLabel.setText(info.get("winnedGames").get(0));
        originalWinnedGamesLabel.setText(info.get("winnedGames").get(1));
        hardWinnedGamesLabel.setText(info.get("winnedGames").get(2));
        // Winning Spree
        easyWinningSpreeLabel.setText(info.get("winningSpree").get(0));
        originalWinningSpreeLabel.setText(info.get("winningSpree").get(1));
        hardWinningSpreeLabel.setText(info.get("winningSpree").get(2));

    }

}
