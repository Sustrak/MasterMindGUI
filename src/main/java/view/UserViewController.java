package view;

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
import java.util.Map;
import java.util.ResourceBundle;

public class UserViewController implements Initializable{

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

    private DomainCtrl domainCtrl;

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

    private void fillGridPanes(String nickName, Map<String, String> info) {
        nameLabel.setText(info.get("name"));
        categoryLabel.setText(info.get("category"));
        experienceLabel.setText(info.get("experience"));

    }

    public void checkButtonAction(ActionEvent actionEvent) {
        Map<String, String> info;
        String nickName = nickNameTextField.getText();
        try {
            info = domainCtrl.getPlayerInfo(nickName);
            userNotFoundLabel.setVisible(false);
            fillGridPanes(nickName, info);
            userInfoGridPane.setVisible(true);
            userInfoGridPane.setVisible(true);
            //mainVBox.getChildren().add(1, userGridPane);

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            userInfoGridPane.setVisible(false);
            userInfoGridPane.setVisible(false);
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
}
