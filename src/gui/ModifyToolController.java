package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends Controller{
    //TODO: Populate data from selected tool using database info

    @FXML
    TextField toolNameTextField;
    @FXML
    RadioButton yesLendableRadioButton;
    @FXML
    ComboBox<Integer> priceComboBox;


    @FXML
    public void initialize(UserTools tools, int index) {

        priceComboBox.getItems().addAll(5, 10, 15, 20, 25, 30, 35, 40, 45, 50);

        if (tools.getLendable().get(index)) {
            yesLendableRadioButton.setSelected(true);
        }

        toolNameTextField.setText(tools.getToolNames().get(index));
        priceComboBox.getSelectionModel().select(tools.getSalePrices().get(index));

        //TODO: Populate categories
        parseCategories(tools.getCategories().get(index));
    }

    private List<String> parseCategories(String category) {
        List<String> categories = Arrays.asList(category.split(", "));
        System.out.println(categories);
        return categories;
    }


    @FXML
    public void gotoViewTools(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/viewTools.fxml",  "View Tools");
    }

    @FXML
    public void modifyTool(ActionEvent event){
        //TODO: Update selected tool in Database
        gotoViewTools(event);
    }
}
