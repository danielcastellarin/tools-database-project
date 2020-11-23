package gui.Controllers;

import gui.OwnedTool;
import gui.OwnedUserTools;
import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends ToolController {

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
        this.tid = tid;

        toolNameTextField.setText(theTool.getName());
        priceTextField.setText(String.valueOf(theTool.getPrice()));
        setCategories(parseCategories(tools.getCategories().get(index)));
    }

    /**
     * Parses the given String category into individual Strings
     *
     * @param category A String of comma separated categories
     * @return a list of Categories as a comma separated list
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

    // TODO migrate FXML calls to gotoCategories directly in ToolController if possible
    /**
     * Opens a scene containing a list of all available categories
     */
    @FXML
    public void modifyCategories() {
        gotoCategories(getCategories(), "Modify Tool");
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
        if (!(newName.equals("") || getCategories().isEmpty())) {
            SQLController.updateTool(tid, newName, newPrice, getCategories());
            gotoViewTools(event);
        } else {
            statusText.setVisible(true);
        }
    }
}
