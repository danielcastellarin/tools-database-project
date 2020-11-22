package gui.Controllers;

import gui.Main;
import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Text loginStatusText;


    /**
     * Gets the username and password from the UI and queries the database to
     * make sure a valid username and password has been entered.
     *
     * @param event A button click
     */
    @FXML
    public void login(ActionEvent event) {
        String user = usernameField.getText().trim();
        String pass = passwordPasswordField.getText().trim();

        if(!(user.isEmpty() || pass.isEmpty())) {
//        if (!user.isEmpty() && !pass.isEmpty()) {
            //TODO: don't need query var, but leave for now
            String query = "SELECT uid FROM \"User\" WHERE username ='" +
                    user + "' AND password='" + pass + "'";
//            SQLController.performQuery(query);
            int uid = SQLController.readUID(query);
//            int uid = SQLController.verifyLoginCredentials(user, pass);
            if (uid != -1) {
                loginStatusText.setVisible(false);
                Main.setUID(uid);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                changeScene("FXML/home.fxml", "Home");
            } else {
                loginStatusText.setVisible(true);
            }
        } else {
            loginStatusText.setVisible(true);
        }
    }

    /**
     * Goes to the create new account scene
     *
     * @param event A button click
     */
    @FXML
    public void gotoCreateNewAccount(ActionEvent event) {
        ((Stage) (((Hyperlink) event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/newAccount.fxml", "Create New Account");
    }


}
