package view;

import game.DiffEnum;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import layers.DomainCtrl;

import java.util.Optional;

public class MainMenuViewController {

    private DomainCtrl dCtrl;
    private ViewController vCtrl = new ViewController();

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.dCtrl = dCtrl;
    }

    public void codeBreakerButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Seleccion de nivel");
        alert.setHeaderText("Fácil. 4 espacios. 6 colores. Sin repetidos\n" +
                "Original. 4 espacios. 6 colores. Con repetidos\n" +
                "Dificil. 6 espacios. 8 colores. Con repetidos");
        alert.setContentText(null);

        ButtonType easy = new ButtonType("Fácil");
        ButtonType original = new ButtonType("Original");
        ButtonType hard = new ButtonType("Dificil");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(easy, original, hard, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == easy){
            dCtrl.startNewCodeBreaker(DiffEnum.EASY);
            vCtrl.boardView(dCtrl, DiffEnum.EASY);
        } else if (result.get() == original) {
            dCtrl.startNewCodeBreaker(DiffEnum.ORIGINAL);
            vCtrl.boardView(dCtrl, DiffEnum.ORIGINAL);
        } else if (result.get() == hard) {
            dCtrl.startNewCodeBreaker(DiffEnum.HARD);
            vCtrl.boardView(dCtrl, DiffEnum.HARD);
        }
    }

    public void codeMakerButtonAction(ActionEvent actionEvent) {
    }

    public void loadGameButtonAction(ActionEvent actionEvent) {
    }

    public void rankButtonAction(ActionEvent actionEvent) {
    }

    public void recordsButtonAction(ActionEvent actionEvent) {
    }

    public void playerButtonAction(ActionEvent actionEvent) {
    }

    public void logoutButtonAction(ActionEvent actionEvent) {
    }
}
