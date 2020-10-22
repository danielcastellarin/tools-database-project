package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class GetLendableToolsController extends Controller {

    @FXML
    TextField toolNameTextField;


    @FXML
    public void selectSearchCategories() {
        super.gotoCategories(null, "Select Search Categories");
    }

    @FXML
    public void search() {
        System.out.println(toolNameTextField.getText());
        System.out.println(super.getCategories());
        //TODO: Call SQL Method to populate tables
    }

    public void selectTool() {
        //TODO: Double click on row to borrow
    }



}
