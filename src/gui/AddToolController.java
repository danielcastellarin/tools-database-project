package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AddToolController extends Controller{

    private static List<String> categories;

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
        super.gotoCategories(null, "Add Tool");
    }

    @FXML
    public void addTool(ActionEvent event){
        String toolName = toolNameTextField.getText();

        if (!toolName.equals("") && !priceComboBox.getSelectionModel().isEmpty()) {
            statusText.setVisible(false);
            int salePrice = priceComboBox.getSelectionModel().getSelectedItem();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String purchaseDate = dateFormat.format(date);

            SQLController.addNewTool(Main.getUID(), toolName,
                    purchaseDate, salePrice, super.getCategories());

            gotoHome(event);
        } else {
            statusText.setVisible(true);
        }
    }
}
