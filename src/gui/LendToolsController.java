package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.*;

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
    Text statusText;

    private Set<String> usernames;
    private Set<Integer> uids;
    private UserTools tools;

    @FXML
    public void initialize() {
        // Disables past dates
        dueDateDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        tools = new UserTools(Main.getUID());
        toolComboBox.getItems().addAll(tools.getToolNames());

        usernames = new HashSet<>();
        uids = new HashSet<>();
        SQLController.getAllOtherUsers(Main.getUID(), uids, usernames);
        userComboBox.getItems().addAll(usernames);
    }

    @FXML
    public void lendTool(ActionEvent event) {

        String username = userComboBox.getSelectionModel().getSelectedItem();
        String toolName = toolComboBox.getSelectionModel().getSelectedItem();
        LocalDate dueDate = dueDateDatePicker.getValue();
        int tid = toolComboBox.getSelectionModel().getSelectedItem();
        tools.getTids();
        System.out.println(username);
        System.out.println(toolName);
        System.out.println(dueDate);
        //TODO: Update Server when a tool has been lent to someone else
        SQLController.addNewTool(Main.getUID(), toolName, lendable,
                purchasable, purchaseDate, salePrice, categories);
        gotoHome(event);
    }
}
