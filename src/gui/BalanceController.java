package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class BalanceController extends Controller{

    @FXML
    TextField balanceTextField;

    @FXML
    public void updateBalance(ActionEvent event) {
        //TODO: Update balance in database
        gotoHome(event);
    }


}
