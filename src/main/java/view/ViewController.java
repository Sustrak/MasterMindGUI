package view;

import javafx.fxml.FXMLLoader;
import layers.DomainCtrl;

import java.io.IOException;

public class ViewController {

    public void boardView(DomainCtrl dCtrl) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("../../resources/GUI/FXML/BoardView.fxml"));
        try {
            Loader.load();
            BoardViewController boardViewController = Loader.getController();
            boardViewController.setDctrl(dCtrl);
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
            signUpViewController.setDctrl(dCtrl);
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
            loginViewController.setDctrl(dCtrl);
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
            resetPasswordViewController.setDctrl(dCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
