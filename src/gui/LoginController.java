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
    PasswordField passwordPasswordField;
    @FXML
    Text loginStatusText;



    @FXML
    public void login(ActionEvent event) {
        String user = usernameField.getText().trim();
        String pass = passwordPasswordField.getText().trim();

        if (!user.isEmpty() && !pass.isEmpty()) {
            int uid = SQLController.verifyLoginCredentials(user, pass);
            if (uid != -1) {
                loginStatusText.setVisible(false);
                Main.setUID(uid);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                changeScene("FXML/home.fxml", 400, 500, "Home");
            } else {
                loginStatusText.setVisible(true);
            }
        } else {
            loginStatusText.setVisible(true);
        }
    }

    @FXML
    public void gotoCreateNewAccount(ActionEvent event) {
        ((Stage)(((Hyperlink)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/newAccount.fxml", 400, 500, "Create New Account");
    }



}
