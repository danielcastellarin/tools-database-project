package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends Controller{

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Text loginStatus;



    @FXML
    public void login(ActionEvent event) {
        boolean succcess = SQLController.verifyLoginCredentials(Main.connection,
                usernameField.getText(), passwordField.getText());
        if (succcess) {
            loginStatus.setVisible(false);
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            changeScene("FXML/home.fxml", 400, 500, "Home");
        } else {
            loginStatus.setVisible(true);
        }
    }

    @FXML
    public void gotoCreateNewAccount(ActionEvent event) {
        ((Stage)(((Hyperlink)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/newAccount.fxml", 400, 500, "Create New Account");
    }



}
