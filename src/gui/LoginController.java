package gui;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLOutput;

public class LoginController {

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;


    @FXML
    public void getLoginInfo() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username + " " + password);
    }

}
