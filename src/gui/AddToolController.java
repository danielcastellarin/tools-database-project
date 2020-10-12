package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AddToolController extends Controller{

    //TODO: Populate Tool Category ChoiceBox with categories from database

    @FXML
    RadioButton yesLendableRadioButton;
    @FXML
    TextField priceTextField;
    @FXML
    TextField toolNameTextField;
    @FXML
    ChoiceBox<String> toolCategoryChoiceBox;

    @FXML
    public void addTool(ActionEvent event){
        //TODO: Add Tool to Database
        gotoHome(event);
    }
}
