package view;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import layers.DomainCtrl;

import java.net.MalformedURLException;

public class LoginViewController {

    public TextField usernameTextField;
    public TextField passwordTextField;

    private DomainCtrl domainCtrl = new DomainCtrl();

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.domainCtrl = dCtrl;
    }

    public void loginButton(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int result = domainCtrl.logIn(username, password);
        if (result == 0) {
            System.out.print("LOGIN OK");
            try {
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
