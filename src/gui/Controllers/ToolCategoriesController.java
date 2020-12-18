package gui.Controllers;

import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ToolCategoriesController extends ToolController {

    @FXML
    private VBox categoryVBox;

    /**
     * Populates the scene with a list of categories from the database
     *
     * @param toolCategories the tool categories from the database
     */
    @FXML
    public void initialize(List<String> toolCategories) {
        String query = "SELECT tool_category FROM \"Category\"";
        List<String> allCategories = new ArrayList<>();
        SQLController.readCategories(query, allCategories);
//        List<String> allCategories = SQLController.getAllCategories();

        for (String cat : allCategories) {
            RadioButton button = new RadioButton();
            button.setPrefWidth(200);
            button.setText(cat);
            if (!toolCategories.isEmpty() && toolCategories.contains(cat)) {
                button.setSelected(true);
            }
            // TODO if we below version of the if, we need to change the categories
            //  variable in AddToolCategory call of gotoCategories
//            try {
//                if (toolCategories.contains(cat)) {
//                    button.setSelected(true);
//                }
//            } catch (NullPointerException ignored) {}
            categoryVBox.getChildren().add(button);
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
            if (((RadioButton) node).isSelected()) {
                categories.add(((RadioButton) node).getText());
            }
        }
        setCategories(categories);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
