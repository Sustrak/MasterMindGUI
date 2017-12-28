package view;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import layers.DomainCtrl;

import java.util.Optional;


public class SignUpViewController {

    private DomainCtrl domainCtrl;

    public Button signUpButton;
    public TextField nameTextField;
    public TextField surnameTextField;
    public TextField nickNameTextField;
    public TextField birthDateTextField;
    public TextField password1TextField;
    public TextField password2TextField;
    public Label errorLabel;

    public void setDomainCtrl(DomainCtrl dCtrl) {
        this.domainCtrl = dCtrl;
    }

    public void signUpButtonAction(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String nickname = nickNameTextField.getText();
        String birthDate = birthDateTextField.getText();
        String password1 = password1TextField.getText();
        String password2 = password2TextField.getText();
        if (name.equals("")) errorLabel.setText("Debes introducir un nombre!");
        else if (surname.equals("")) errorLabel.setText("Debes introducir un apellido!");
        else if (nickname.equals("")) errorLabel.setText("Debes introducir un nickname!");
        else if (birthDate.equals("")) errorLabel.setText("Debes introducir una fecha de nacimiento!");
        else if (password1.equals("")) errorLabel.setText("Debes introducir una contraseña!");
        else if (password2.equals("")) errorLabel.setText("Debes verificar la contraseña!");
        else if (domainCtrl.usedNickname(nickNameTextField.getText())) errorLabel.setText("El nickname introducido ya existe, elija otro.");
        else if (!password1.equals(password2)) errorLabel.setText("Las contraseñas introducidas no coinciden.");
        else {
            switch (domainCtrl.createUser(name, surname, nickname, birthDate, password1)) {
                case 0:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Alta efectuada correctamente.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        System.out.print("OK");
                        ViewController.loginView(domainCtrl);
                    }
                    break;
                case 1:
                    errorLabel.setText("El usuario ya existe");
                    break;
                case 2:
                    errorLabel.setText("La fecha de nacimiento no es correcta");
                    break;
                default:
                    errorLabel.setText("Error no soportado");
                    break;
            }
        }
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        ViewController.loginView(domainCtrl);
    }
}
