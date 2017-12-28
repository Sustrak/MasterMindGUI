package view;

import game.DiffEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import layers.DomainCtrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

public class ViewController {

    public static void boardView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/BoardView.fxml").toUri().toString()));
        try {
            Loader.load();
            BoardViewController boardViewController = Loader.getController();
            boardViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUpView(DomainCtrl domainCtrl) throws MalformedURLException, MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/SignUpView.fxml").toUri().toString()));
        try {
            Loader.load();
            SignUpViewController signUpViewController = Loader.getController();
            signUpViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loginView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/LoginView.fxml").toUri().toString()));
        try {
            Loader.load();
            LoginViewController loginViewController = Loader.getController();
            loginViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetPasswordView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/ResetPasswordView.fxml").toUri().toString()));
        try {
            Loader.load();
            ResetPasswordViewController resetPasswordViewController = Loader.getController();
            resetPasswordViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mainMenuView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/MainMenuView.fxml").toUri().toString()));
        try {
            Loader.load();
            MainMenuViewController mainMenuViewController = Loader.getController();
            mainMenuViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rankingsView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/RankingView.fxml").toUri().toString()));
        try {
            Loader.load();
            RankingViewController rankingViewController = Loader.getController();
            rankingViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiffEnum askDifficulty() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleccion de nivel");
        alert.setHeaderText(null);
        alert.setContentText("Selecciona un nivel");

        ButtonType easy = new ButtonType("Fácil");
        ButtonType original = new ButtonType("Original");
        ButtonType hard = new ButtonType("Dificil");

        alert.getButtonTypes().setAll(easy, original, hard);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == easy) return DiffEnum.EASY;
        else if (result.get() == original) return DiffEnum.ORIGINAL;
        else return DiffEnum.HARD;
    }
}
