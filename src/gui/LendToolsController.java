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

    private List<Integer> tids;
    private List<String> toolNames;
    private Set<String> usernames;
    private Set<Integer> uids;

    @FXML
    public void initialize() {
        // Disables past dates
        dueDateDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) <= 0 );
            }
        });

        tids = new ArrayList<>();
        toolNames = new ArrayList<>();
        usernames = new HashSet<>();
        uids = new HashSet<>();
        SQLController.getLendableUserTools(Main.getUID(), tids, toolNames, uids, usernames);
        toolComboBox.getItems().addAll(toolNames);
        userComboBox.getItems().addAll(usernames);
    }

    @FXML
    public void lendTool(ActionEvent event) {
        String username = userComboBox.getSelectionModel().getSelectedItem();
        String toolName = toolComboBox.getSelectionModel().getSelectedItem();
        LocalDate dueDate = dueDateDatePicker.getValue();
        if (usernames.contains(username) && toolNames.contains(toolName) && dueDate != null) {
            statusText.setVisible(false);
            String dateString = dueDate.toString();
            // tids & toolNames have equal indexes
            int tid = tids.get(toolNames.indexOf(toolName));
            SQLController.insertNewBorrowRecord(username, tid, dateString);
            gotoHome(event);
        } else {
            statusText.setVisible(true);
        }


    }
}
