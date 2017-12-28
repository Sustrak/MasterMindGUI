package view;

import javafx.event.ActionEvent;
import layers.DomainCtrl;

public class MainMenuViewController {

    private DomainCtrl domainCtrl;

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.domainCtrl = dCtrl;
    }

    public void codeBreakerButtonAction(ActionEvent actionEvent) {
        ViewController.boardView(domainCtrl);
    }

    public void codeMakerButtonAction(ActionEvent actionEvent) {
    }

    public void loadGameButtonAction(ActionEvent actionEvent) {
    }

    public void rankButtonAction(ActionEvent actionEvent) {
      ViewController.rankingsView(domainCtrl);
    }

    public void recordsButtonAction(ActionEvent actionEvent) {
    }

    public void playerButtonAction(ActionEvent actionEvent) {
    }

    public void logoutButtonAction(ActionEvent actionEvent) {
    }
}
