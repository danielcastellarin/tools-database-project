package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    RadioButton yesPurchasableRadioButton;
    @FXML
    RadioButton yesLendableRadioButton;
    @FXML
    TextField priceTextField;
    @FXML
    TextField toolNameTextField;

    public static void setCategoriesList(List<String> categoriesList) {
        categories = categoriesList;
    }


    @FXML
    public void addCategories() {
        changeScene("FXML/addToolCategories.fxml", 400, 500, "Add Categories");
    }

    @FXML
    public void addTool(ActionEvent event){

        System.out.println("Tool Name: " + toolNameTextField.getText());
        System.out.println("Sale Price: " + priceTextField.getText());
        System.out.println("Lendable: " + yesLendableRadioButton.isSelected());
        System.out.println("Purchasable: " + yesPurchasableRadioButton.isSelected());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println("Purchase Date:" + dateFormat.format(date));

        //TODO: Create Insert Query into various needed tables
        //TODO: Figure out how to find auto incremented TID

        System.out.print("Categories: ");
        for (String s: categories) {
            System.out.print(s + " ");
        }
        System.out.println();
        gotoHome(event);
    }
}
