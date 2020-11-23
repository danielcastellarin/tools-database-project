package gui.Controllers;

import gui.Main;
import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AddToolController extends ToolController {

    // TODO migrate FXML calls to gotoCategories directly in ToolController if possible
    /***
     * Opens a window to select category tags for the tool
     */
    @FXML
    public void addCategories() {
        gotoCategories(new ArrayList<>(), "Add Tool");
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
                    purchaseDate, salePrice, getCategories());

            gotoHome(event);
        } else {
            statusText.setVisible(true);
        }
    }
}
