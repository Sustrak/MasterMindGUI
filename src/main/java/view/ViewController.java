package view;

import game.DiffEnum;
import javafx.fxml.FXMLLoader;
import layers.DomainCtrl;

import java.io.IOException;

public class ViewController {

    public void boardView(DomainCtrl dCtrl, DiffEnum difficulty) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../../resources/GUI/FXML/BoardView.fxml"));
        try {
            Loader.load();
            BoardViewController boardViewController = Loader.getController();
            boardViewController.setDifficulty(difficulty);
            boardViewController.setDomainCtrl(dCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUpView(DomainCtrl dCtrl) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../../resources/GUI/FXML/SignUpView.fxml"));
        try {
            Loader.load();
            SignUpViewController signUpViewController = Loader.getController();
            signUpViewController.setDomainCtrl(dCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginView(DomainCtrl dCtrl) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../../resources/GUI/FXML/LoginView.fxml"));
        try {
            Loader.load();
            LoginViewController loginViewController = Loader.getController();
            loginViewController.setDomainCtrl(dCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetPasswordView(DomainCtrl dCtrl) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../../resources/GUI/FXML/ResetPasswordView.fxml"));
        try {
            Loader.load();
            ResetPasswordViewController resetPasswordViewController = Loader.getController();
            resetPasswordViewController.setDomainCtrl(dCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void MainMenuView(DomainCtrl dCtrl) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../../resources/GUI/FXML/MainMenuView.fxml"));
        try {
            Loader.load();
            MainMenuViewController mainMenuViewController = Loader.getController();
            mainMenuViewController.setDomainCtrl(dCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
