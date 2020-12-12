package gui.Controllers;

import gui.Main;
import gui.SQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.util.*;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class SellToolsController extends Controller {

    @FXML
    private ComboBox<String> toolComboBox;
    @FXML
    private ComboBox<String> userComboBox;
    @FXML
    private Text statusText;

    private List<Integer> tids;
    private List<String> toolNames;
    private List<Integer> toolPrices;
    private Map<String, Integer> users;

    /**
     * Gets information on sellable tools and updates to tool combo box to
     * display those tools.
     */
    @FXML
    public void initialize() {
        tids = new ArrayList<>();
        toolNames = new ArrayList<>();
        users = new HashMap<>();
        toolPrices = new ArrayList<>();

        String query = "SELECT t.tid, t.tool_name, o.sale_price FROM \"Owns\" o, \"Tool\" t WHERE " +
                "o.tid = t.tid AND o.uid = " + Main.getUID() + " AND t.lendable = " +
                "true AND date_sold IS NULL";
        SQLController.getSellableToolInfo(query, tids, toolNames, toolPrices);
        toolComboBox.getItems().addAll(toolNames);
    }

    /**
     * Enables and populates the list of users who have enough in their balance
     * based on the selected tool
     */
    @FXML
    public void selectTool() {
        userComboBox.getItems().clear();
        users.clear();
        int toolPrice;

        if (toolComboBox.getSelectionModel().getSelectedItem() != null) {
            toolPrice = toolPrices.get(toolComboBox.getSelectionModel().getSelectedIndex());
            userComboBox.setDisable(false);
            String query = "SELECT username, uid FROM \"User\" WHERE " +
                    "uid != " + Main.getUID() + " AND balance > " + toolPrice;
            SQLController.getUsers(query, users);
            userComboBox.getItems().addAll(users.keySet());
        }
    }

    // TODO: balance and toolprice check done twice (above and in sell func) remove one (preferably above)

    /**
     * Sells a tool to the selected user
     *
     * @param event A button click
     */
    public void sell(ActionEvent event) {
        String username = userComboBox.getSelectionModel().getSelectedItem();
        String toolName = toolComboBox.getSelectionModel().getSelectedItem();
        if (users.containsKey(username) && toolNames.contains(toolName)) {
            statusText.setVisible(false);

            // tids & toolNames have equal indexes
            int tid = tids.get(toolNames.indexOf(toolName));

//            boolean success = SQLController.sellTool(users.get(username), Main.getUID(), tid);
            String query = "SELECT sellTool(" + tid + ", " + users.get(username) + ", " + Main.getUID() + ")";
            boolean success = SQLController.sellToolFunc(query);
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
