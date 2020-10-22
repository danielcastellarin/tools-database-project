package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.xml.soap.Text;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class BuyToolsController extends Controller {

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
        //TODO: Double click on row to purchase
    }
}
