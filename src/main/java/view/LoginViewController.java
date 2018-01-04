package view;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import layers.DomainCtrl;

import java.net.MalformedURLException;
import javafx.application.Platform;

public class LoginViewController {

    public TextField usernameTextField;
    public TextField passwordTextField;

    private DomainCtrl domainCtrl = new DomainCtrl();

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.domainCtrl = dCtrl;
    }
    
    public void closeButton(ActionEvent actionEvent) {
        Platform.exit();
    }
    
    public void loginButton(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int result = domainCtrl.logIn(username, password);
        if (result == 0) {
            System.out.print("LOGIN OK");
            try {
                //Starts the music
                domainCtrl.playBackgroundMusic();
                ViewController.mainMenuView(domainCtrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.print("BAD LOGIN");
        }
    }

    public void registerButton(ActionEvent actionEvent) {
        try {
            ViewController.signUpView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void resetPasswordButtonAction(ActionEvent actionEvent) {
        try {
            ViewController.resetPasswordView(domainCtrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
