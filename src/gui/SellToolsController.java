package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class SellToolsController extends Controller {

    @FXML
    ComboBox<String> toolComboBox;
    @FXML
    ComboBox<String> userComboBox;
    @FXML
    Text statusText;

    private List<Integer> tids;
    private List<String> toolNames;
    private Set<String> usernames;
    private Set<Integer> uids;

    @FXML
    public void initialize() {

        tids = new ArrayList<>();
        toolNames = new ArrayList<>();
        usernames = new HashSet<>();
        uids = new HashSet<>();
        SQLController.getSellableUserTools(Main.getUID(), tids, toolNames, uids,
                usernames);


        toolComboBox.getItems().addAll(toolNames);
        userComboBox.getItems().addAll(usernames);
    }

    public void sell(ActionEvent event) {
        String username = userComboBox.getSelectionModel().getSelectedItem();
        String toolName = toolComboBox.getSelectionModel().getSelectedItem();
        if (usernames.contains(username) && toolNames.contains(toolName)) {
            statusText.setVisible(false);

            // tids & toolNames have equal indexes
            int tid = tids.get(toolNames.indexOf(toolName));

            boolean success = SQLController.sellTool(username, Main.getUID(), tid);
            if (!success) {
                statusText.setText("User does not have enough money");
                statusText.setVisible(true);
            } else {
                gotoHome(event);
            }

        } else {
            statusText.setText("Fill Out All Fields");
            statusText.setVisible(true);
        }
    }


}
