package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    /**
     * Changes the scene to the desired scene
     *
     * @param fxml  The scene to be displayed
     * @param title The title of the scene
     */
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

    /**
     * Goes to the login scene
     *
     * @param event A button click
     */
    @FXML
    public void gotoLogin(ActionEvent event) {
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

    /**
     * Gets a list of categories
     *
     * @return A list of strings of categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Sets the static list of categories to a given list of categories
     *
     * @param categories A list of strings of categories
     */
    public void setCategories(List<String> categories) {
        Controller.categories = categories;
    }

    /**
     * Changes the scene to the tool category selection scene
     *
     * @param categories The list of strings of categories
     * @param title      The title of the scene
     */
    public void gotoCategories(List<String> categories, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML" +
                    "/ToolCategories.fxml"));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);

            Scene scene = new Scene(loader.load());
            ToolCategoriesController controller = loader.getController();
            controller.initialize(categories);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
