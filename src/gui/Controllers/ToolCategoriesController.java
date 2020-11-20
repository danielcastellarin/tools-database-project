package gui.Controllers;

import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ToolCategoriesController extends Controller {

    @FXML
    private VBox categoryVBox;
    @FXML
    private TextField newCategoryTextField;
    @FXML
    private Text statusText;

    /**
     * Populates the scene with a list of categories from the database
     *
     * @param toolCategories
     */
    @FXML
    public void initialize(List<String> toolCategories) {
        List<String> categories = SQLController.getAllCategories();
        for (String category : categories) {
            RadioButton button = new RadioButton();
            button.setPrefWidth(200);
            button.setText(category);
            if (toolCategories != null) {
                if (toolCategories.contains(category)) {
                    button.setSelected(true);
                }
            }
            categoryVBox.getChildren().add(button);
        }
    }

    /**
     * Allows the user to create and insert an entirely new category into the
     * database
     */
    @FXML
    public void addNewCategory() {
        String category_name = newCategoryTextField.getText().toUpperCase();
        if (category_name.equals("")) {
            statusText.setVisible(true);
        } else {
            boolean status = SQLController.insertCategory(category_name);
            if (status) {
                statusText.setVisible(false);
                RadioButton button = new RadioButton();
                button.setPrefWidth(200);
                button.setText(category_name);
                categoryVBox.getChildren().add(button);
            } else {
                statusText.setVisible(true);
            }
        }
    }

    /**
     * Sets the categories list to the valued selected from the scene and
     * closes the scene.
     *
     * @param event A button click
     */
    @FXML
    public void submitCategories(ActionEvent event) {
        List<String> categories = new ArrayList<>();
        for (Node node : categoryVBox.getChildren()) {
            if (node instanceof RadioButton) {
                if (((RadioButton) node).isSelected()) {
                    categories.add(((RadioButton) node).getText());
                }
            }
        }
        super.setCategories(categories);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
