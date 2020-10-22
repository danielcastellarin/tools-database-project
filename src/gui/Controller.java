package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class Controller {

    private static List<String> categories;

    public void changeScene(String fxml, String title) {

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

    @FXML
    public void gotoLogin(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/login.fxml", "Login");
    }

    @FXML
    public void gotoHome(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/home.fxml", "Home");
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        Controller.categories = categories;
    }


}
