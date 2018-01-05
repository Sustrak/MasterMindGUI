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
        try {
            ViewController.codeMakerView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        try {
            ViewController.recordsView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void playerButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.userView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void settingsButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.settingsView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonAction(ActionEvent actionEvent) {
        domainCtrl.logOut();
        try {
            ViewController.loginView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
