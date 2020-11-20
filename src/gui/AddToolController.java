package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AddToolController extends Controller {

    private static List<String> categories;

    @FXML
    private TextField priceTextField;
    @FXML
    private TextField toolNameTextField;
    @FXML
    private Text statusText;

    /***
     * Opens a window to select category tags for the tool
     */
    @FXML
    public void addCategories() {
        super.gotoCategories(null, "Add Tool");
    }

    /**
     * Updates the price of the tool with the use of a series of buttons
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
     * Gets the selected parameters for the tool and adds the tool to the
     * appropriate tables in the database
     *
     * @param event A button click
     */
    @FXML
    public void addTool(ActionEvent event) {
        String toolName = toolNameTextField.getText();

        if (!toolName.equals("")) {
            statusText.setVisible(false);
            int salePrice = Integer.parseInt(priceTextField.getText());

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
