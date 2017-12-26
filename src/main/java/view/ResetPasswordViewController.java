package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import layers.DomainCtrl;

import java.util.Optional;

public class ResetPasswordViewController {

    public TextField nickNameTextField;
    public TextField birthDateTextField;
    public TextField password1TextField;
    public TextField password2TextField;
    public Label errorLabel;

    private ViewController vCtrl =  new ViewController();
    private DomainCtrl dCtrl;

    public void setDctrl(DomainCtrl dCtrl) {
        this.dCtrl = dCtrl;
    }

    public void cancelButonAction(ActionEvent actionEvent) {
        vCtrl.loginView(dCtrl);
    }

    public void resetPasswordButtonAction(ActionEvent actionEvent) {
        String nickName = nickNameTextField.getText();
        String birthDate = birthDateTextField.getText();
        String password1 = password1TextField.getText();
        String password2 = password2TextField.getText();
        if (nickName.equals("")) errorLabel.setText("Debes introducir un nickname!");
        else if (birthDate.equals("")) errorLabel.setText("Debes introducir una fecha de nacimiento!");
        else if (password1.equals("")) errorLabel.setText("Debes introducir una contraseña!");
        else if (password2.equals("")) errorLabel.setText("Debes verificar la contraseña!");
        else if (!password1.equals(password2)) errorLabel.setText("Las contraseñas introducidas no coinciden.");
        else {
            int result = dCtrl.resetPassword(nickName, birthDate, password1);
            switch (result) {
                case 0:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Password reseteado correctamente.");

                    Optional<ButtonType> r = alert.showAndWait();
                    if (r.get() == ButtonType.OK) vCtrl.loginView(dCtrl);
                    break;

                case 1:
                    errorLabel.setText("Introduzca la fecha siguiendo el siguiente formato dd/MM/yyyy");
                    break;
                case 2: errorLabel.setText("No se ha encontrado al usuario"); break;
                case 3: errorLabel.setText("La fecha no coincide con la fecha de nacimiento introducida al crear el usuario");
            }
        }
    }
}
