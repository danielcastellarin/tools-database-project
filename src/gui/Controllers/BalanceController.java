package gui.Controllers;

import gui.Main;
import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class BalanceController extends Controller {

    @FXML
    private TextField balanceTextField;

    private int increment;
    private int initBalance;

    /**
     * Gets the users current balance from the database and sets the balance
     * text field to it
     */
    @FXML
    public void initialize() {
        String query = "SELECT balance FROM \"User\" WHERE uid = " + Main.getUID();
//        initBalance = SQLController.readBalance(query);
        initBalance = SQLController.readInt(query);
        increment = 0;
        balanceTextField.setText(Integer.toString(initBalance));
//        balanceTextField.setText(Integer.toString(SQLController.getBalance(Main.getUID())));
    }

    /**
     * Updates the balance of the user in both the database and UI based on the
     * button selected
     *
     * @param event A button click
     */
    @FXML
    public void updateBalance(ActionEvent event) {
        String id = (((Button) event.getSource()).getText());
        System.out.println(id);
//        int increment = 0;
//        switch (id) {
//            case "Add $1":
//                increment = 1;
//                break;
//            case "Add $5":
//                increment = 5;
//                break;
//            case "Add $10":
//                increment = 10;
//                break;
//            case "Add $20":
//                increment = 20;
//            default:
//                break;
//        }
//        balanceTextField.setText(Integer.toString(SQLController.incrementBalance(Main.getUID(), increment)));

        switch (id) {
            case "Add $1":
                increment += 1;
                break;
            case "Add $5":
                increment += 5;
                break;
            case "Add $10":
                increment += 10;
                break;
            case "Add $20":
                increment += 20;
            default:
                break;
        }

        balanceTextField.setText(Integer.toString(initBalance + increment));
    }

    @FXML
    public void submitIncrement(ActionEvent event) {
        SQLController.performUpdate("UPDATE \"User\" SET balance = balance + " + increment + " WHERE uid = " + Main.getUID());
        changeScene("FXML/home.fxml", "Home");
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
