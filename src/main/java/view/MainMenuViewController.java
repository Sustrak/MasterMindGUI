package view;

import javafx.event.ActionEvent;
import layers.DomainCtrl;

import java.net.MalformedURLException;

public class MainMenuViewController {

    private DomainCtrl domainCtrl;

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.domainCtrl = dCtrl;
    }

    public void codeBreakerButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.boardView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void codeMakerButtonAction(ActionEvent actionEvent) {
    }

    public void loadGameButtonAction(ActionEvent actionEvent) {
    }

    public void rankButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.rankingsView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void recordsButtonAction(ActionEvent actionEvent) {
    }

    public void playerButtonAction(ActionEvent actionEvent) {
    }

    public void logoutButtonAction(ActionEvent actionEvent) {
    }
}
