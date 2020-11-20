package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class NewAccountController extends Controller {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Text creationStatusText;

    /**
     * Collects the input from the UI components, queries the database to see
     * if the username is taken, if not, updates the the database and returns
     * to the login scene
     *
     * @param event A button click
     */
    @FXML
    public void createNewUser(ActionEvent event) {
        creationStatusText.setText("Username Already In Use. Try Again");
        creationStatusText.setVisible(false);
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = passwordPasswordField.getText().trim();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            boolean success = SQLController.createNewUser(
                    firstName, lastName, username, password);
            if (success) {
                creationStatusText.setVisible(false);
                gotoLogin(event);
            } else {
                creationStatusText.setVisible(true);
            }
        } else {
            creationStatusText.setText("Fill out all Fields.");
            creationStatusText.setVisible(true);
        }
    }
}
