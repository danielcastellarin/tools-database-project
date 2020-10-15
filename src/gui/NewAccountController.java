package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    public void createNewUser(ActionEvent event) {
        //TODO: Insert New User Data into SQL Server
        gotoLogin(event);
    }


}
