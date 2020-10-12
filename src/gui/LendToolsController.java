package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class LendToolsController extends Controller{

    @FXML
    ComboBox<String> toolComboBox;
    @FXML
    ComboBox<String> userComboBox;
    @FXML
    DatePicker dueDateDatePicker;

    @FXML
    public void lendTool(ActionEvent event) {
        //TODO: Update Server when a tool has been lent to someone else
        gotoHome(event);
    }
}
