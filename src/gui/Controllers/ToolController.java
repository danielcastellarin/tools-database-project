package gui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ToolController extends Controller {

    @FXML
    protected TextField toolNameTextField;
    @FXML
    protected TextField priceTextField;
    @FXML
    protected Text statusText;

    /**
     * Updates the price of the tool with the use of a series of buttons
     *
     * @param event A button click
     */
    @FXML
    private void changePrice(ActionEvent event) {       // TODO: maybe use this in balanceController too?
        String incrementText = ((((Button) event.getSource()).getText()));
        int initialPrice = Integer.parseInt(priceTextField.getText());
        if (initialPrice >= 0) {
            switch (incrementText) {
                case "+10":
                    priceTextField.setText(String.valueOf(initialPrice + 10));
                    break;
                case "+5":
                    priceTextField.setText(String.valueOf(initialPrice + 5));
                    break;
                case "+1":
                    priceTextField.setText(String.valueOf(initialPrice + 1));
                    break;
                case "-10":
                    priceTextField.setText(String.valueOf(initialPrice - 10));
                    break;
                case "-5":
                    priceTextField.setText(String.valueOf(initialPrice - 5));
                    break;
                case "-1":
                    priceTextField.setText(String.valueOf(initialPrice - 1));
                    break;
            }
        }
    }

    /**
     * Changes the scene to the tool category selection scene
     *
     * @param categories The list of strings of categories
     * @param title      The title of the scene
     */
    void gotoCategories(List<String> categories, String title) {
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

    /**
     * Gets a list of categories
     *
     * @return A list of strings of categories
     */
    List<String> getCategories() {
        return categories;
    }
}
