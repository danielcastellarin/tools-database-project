package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends Controller {

    @FXML
    private TextField toolNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Text statusText;

    private int index;
    private OwnedUserTools tools;
    private OwnedTool tool;
    private int tid;

    /**
     * Creates the necessary data for getting the current information about
     * an owned tool and updates the UI accordingly
     *
     * @param theTool An OwnedTool object containing information on the tool
     * @param tools   An OwnedUserTools object containing information on all
     *                owned tools
     * @param index   The index of the selected tool
     * @param tid     The tool id of the tool
     */
    @FXML
    public void initialize(OwnedTool theTool, OwnedUserTools tools, int index, int tid) {
        this.tools = tools;
        this.index = index;
        this.tool = theTool;
        this.tid = tid;

        toolNameTextField.setText(tool.getName());
        priceTextField.setText(String.valueOf(tool.getPrice()));
        super.setCategories(parseCategories(tools.getCategories().get(index)));
    }

    /**
     * Updates the sale price of the tool based on which button is clicked
     *
     * @param event A button click
     */
    @FXML
    private void changePrice(ActionEvent event) {
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
     * Parses the given String category into individual Strings
     *
     * @param category A String of comma separated categories
     * @return
     */
    private List<String> parseCategories(String category) {
        List<String> categories = Arrays.asList(category.split(", "));
        System.out.println(categories);
        return categories;
    }

    /**
     * Changes the scene to the view tools scene
     *
     * @param event A button click
     */
    @FXML
    public void gotoViewTools(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/viewTools.fxml", "View Tools");
    }

    /**
     * Opens a scene containing a list of all available categories
     */
    @FXML
    public void modifyCategories() {
        super.gotoCategories(super.getCategories(), "Modify Tool");
    }

    /**
     * Submits the modified values to the database and returns to the view
     * tools scene
     *
     * @param event A button click
     */
    @FXML
    public void modifyTool(ActionEvent event) {

        String newName = toolNameTextField.getText();
        statusText.setVisible(false);
        int newPrice = Integer.parseInt(priceTextField.getText());
        if (!newName.equals("")) {

            SQLController.updateTool(tid, newName, newPrice, super.getCategories());
        } else {
            statusText.setVisible(true);
        }
        gotoViewTools(event);
    }
}
