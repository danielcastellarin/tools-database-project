package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class BalanceController extends Controller{

    @FXML
    TextField balanceTextField;
    @FXML
    Button addOneButton;
    @FXML
    Button addFiveButton;
    @FXML
    Button addTenButton;
    @FXML
    Button addTwentyButton;

    @FXML
    public void initialize() {
        balanceTextField.setText(Integer.toString(SQLController.getBalance(Main.username)));
    }

    @FXML
    public void updateBalance(ActionEvent event) {
        String id = (((Button)event.getSource()).getId());
        System.out.println(id);
        int increment = 0;
        switch (id) {
            case "addOneButton":
                increment = 1;
                break;
            case "addFiveButton":
                increment = 5;
                break;
            case "addTenButton":
                increment = 10;
                break;
            case "addTwentyButton":
                increment = 20;
            default:
                break;
        }

        balanceTextField.setText(Integer.toString(SQLController.incrementBalance(Main.username, increment)));
    }


}
