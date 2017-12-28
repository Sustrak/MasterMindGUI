package view;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import layers.DomainCtrl;

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
            ViewController.mainMenuView(domainCtrl);
        }
        else {
            System.out.print("BAD LOGIN");
        }
    }

    public void registerButton(ActionEvent actionEvent) {
        ViewController.signUpView(domainCtrl);
    }

    public void resetPasswordButtonAction(ActionEvent actionEvent) {
        ViewController.resetPasswordView(domainCtrl);
    }
}
