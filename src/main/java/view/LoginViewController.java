package view;

import game.DiffEnum;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import layers.DomainCtrl;

public class LoginViewController {

    public TextField usernameTextField;
    public TextField passwordTextField;

    private DomainCtrl dCtrl = new DomainCtrl();
    private ViewController vCtrl = new ViewController();

    public void setDctrl(DomainCtrl dCtrl) {
        this.dCtrl = dCtrl;
    }

    public void loginButton(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int result = dCtrl.logIn(username, password);
        if (result == 0) {
            System.out.print("LOGIN OK");
            dCtrl.startNewCodeBreaker(DiffEnum.EASY);
            vCtrl.boardView(dCtrl);
        }
        else {
            System.out.print("BAD LOGIN");
        }
    }

    public void registerButton(ActionEvent actionEvent) {
        vCtrl.signUpView(dCtrl);
    }

    public void resetPasswordButtonAction(ActionEvent actionEvent) {
        vCtrl.resetPasswordView(dCtrl);
    }
}
