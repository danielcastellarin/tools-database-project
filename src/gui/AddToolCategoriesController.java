package gui;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AddToolCategoriesController extends Controller{

    @FXML
    VBox categoryVBox;
    @FXML
    TextField newCategoryTextField;
    @FXML
    Text statusText;

    @FXML
    public void initialize() {
        List<String> categories = SQLController.getCategories();
        for (String category : categories) {
            RadioButton button = new RadioButton();
            button.setText(category);
            categoryVBox.getChildren().add(button);
        }
    }


    public void addNewCategory() {
        String category_name = newCategoryTextField.getText().toUpperCase();
        boolean status = SQLController.insertCategory(category_name);
        if (status) {
            statusText.setVisible(false);
            RadioButton button = new RadioButton();
            button.setText(category_name);
            categoryVBox.getChildren().add(button);
        } else {
            statusText.setVisible(true);
        }
    }



}
