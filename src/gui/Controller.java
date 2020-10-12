package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class Controller {

    public void changeScene(String fxml, int width, int height, String title) {
        try {
            Parent part = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);
            Scene scene = new Scene(part, width, height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void returnToLogin(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/login.fxml", 400, 500, "Login");
    }
}
