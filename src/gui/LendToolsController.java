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
//    private UserTools tools;

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

//        tools = new UserTools(Main.getUID());
//        toolComboBox.getItems().addAll(tools.getToolNames());

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
        int tid = tids.indexOf(toolName);
        String dDate = dueDate.toString();
        System.out.println(username);
        System.out.println(toolName);
        System.out.println(dueDate);
        //TODO: Update Server when a tool has been lent to someone else
//        SQLController.sellTool(username, tid);
        SQLController.insertNewBorrowRecord(username, tid, dDate);
        gotoHome(event);
    }
}
