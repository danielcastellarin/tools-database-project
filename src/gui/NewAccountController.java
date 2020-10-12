package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class NewAccountController extends Controller{

    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    TextField usernameTextField;
    @FXML
    TextField emailTextField;

    @FXML
    public void createNewUser(ActionEvent event) {
        //TODO: Insert New User Data into SQL Server
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        System.out.println(username + " " + password + " " + firstName + " " + lastName + " " + email);
        returnToLogin(event);
    }


}
