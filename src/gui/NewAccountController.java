package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    Text creationStatusText;

    @FXML
    public void createNewUser(ActionEvent event) {
        boolean success = SQLController.createNewUser(Main.connection,
                firstNameTextField.getText(), lastNameTextField.getText(),
                usernameTextField.getText(), passwordPasswordField.getText());
        if (success) {
            creationStatusText.setVisible(false);
            gotoLogin(event);
        } else {
            creationStatusText.setVisible(true);
        }

    }


}
