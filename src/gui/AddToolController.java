package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AddToolController extends Controller{
    //TODO: Populate Tool Category ChoiceBox with categories from database

    private static List<String> categories;

    @FXML
    RadioButton yesLendableRadioButton;
    @FXML
    ComboBox<Integer> priceComboBox;
    @FXML
    TextField toolNameTextField;
    @FXML
    Text statusText;

    @FXML
    public void initialize() {
        priceComboBox.getItems().addAll(5, 10, 15, 20, 25, 30, 35, 40, 45, 50);
    }

    @FXML
    public void addCategories() {
        changeScene("FXML/ToolCategories.fxml", "Add Categories");
    }

    @FXML
    public void addTool(ActionEvent event){
        String toolName = toolNameTextField.getText();
        if (!toolName.equals("")) {
            statusText.setVisible(false);
            int salePrice = priceComboBox.getSelectionModel().getSelectedItem();
            boolean lendable = yesLendableRadioButton.isSelected();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String purchaseDate = dateFormat.format(date);

            SQLController.addNewTool(Main.getUID(), toolName, lendable,
                    purchaseDate, salePrice, super.getCategories());

            gotoHome(event);
        } else {
            statusText.setVisible(true);
        }
    }
}
