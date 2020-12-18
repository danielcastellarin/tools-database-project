package gui.Controllers;

import gui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class Controller {

    /**
     * Changes the scene to the desired scene
     *
     * @param fxml  The scene to be displayed
     * @param title The title of the scene
     */
    void changeScene(String fxml, String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(title);

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Goes to the login scene, either when logging out or creating a new user
     *
     * @param event A button click
     */
    @FXML
    public void gotoLogin(ActionEvent event) {
        Main.setUID(-1);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/login.fxml", "Login");
    }

    /**
     * Goes to the home scene
     *
     * @param event A button click
     */
    @FXML
    public void gotoHome(ActionEvent event) {
        changeScene("FXML/home.fxml", "Home");
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
