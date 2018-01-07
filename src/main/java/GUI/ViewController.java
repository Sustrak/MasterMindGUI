package GUI;

import game.DiffEnum;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import layers.DomainCtrl;
import view.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ViewController {

    public static void newBoardView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/CodeBrakerView.fxml").toUri().toString()));
        try {
            Loader.load();
            CodeBreakerViewController codeBreakerViewController = Loader.getController();
            codeBreakerViewController.setDomainCtrl(domainCtrl);
            codeBreakerViewController.newGame();
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBoardView(DomainCtrl domainCtrl, int gameId) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/CodeBrakerView.fxml").toUri().toString()));
        try {
            Loader.load();
            CodeBreakerViewController codeBreakerViewController = Loader.getController();
            codeBreakerViewController.setDomainCtrl(domainCtrl);
            codeBreakerViewController.loadGame(gameId);
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

    public static void recordsView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/RecordView.fxml").toUri().toString()));
        try {
            Loader.load();
            RecordViewController recordViewController = Loader.getController();
            recordViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void userView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/UserView.fxml").toUri().toString()));
        try {
            Loader.load();
            UserViewController userViewController = Loader.getController();
            userViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void settingsView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/SettingsView.fxml").toUri().toString()));
        try {
            Loader.load();
            SettingsViewController settingsViewController = Loader.getController();
            settingsViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void codeMakerView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/CodeMakerView.fxml").toUri().toString()));
        try {
            Loader.load();
            CodeMakerViewController codeMakerViewController = Loader.getController();
            codeMakerViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGameView(DomainCtrl domainCtrl) throws MalformedURLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(new URL(Paths.get("src/main/resources/GUI/FXML/LoadGameView.fxml").toUri().toString()));
        try {
            Loader.load();
            LoadGameViewController loadGameViewController = Loader.getController();
            loadGameViewController.setDomainCtrl(domainCtrl);
            Main.changeScene(Loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiffEnum askCodeBreakerDifficulty() {
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

    public static DiffEnum askCodeMakerDifficulty() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleccion de nivel");
        alert.setHeaderText(null);
        alert.setContentText("Selecciona un nivel");

        ButtonType original = new ButtonType("Original");
        ButtonType hard = new ButtonType("Dificil");

        alert.getButtonTypes().setAll(original, hard);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == original) return DiffEnum.ORIGINAL;
        else return DiffEnum.HARD;
    }

    public static void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInformationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInformationMessageSeconds(String message, int seconds) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setContentText(message);
        dialog.show();
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }
}