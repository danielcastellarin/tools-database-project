package gui.Controllers;

import gui.*;
import gui.data.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ViewToolsController extends Controller {

    @FXML
    TableView ownsTable;

    @FXML
    TableColumn ownsNameColumn;

    @FXML
    TableColumn ownsPriceColumn;

    @FXML
    TableColumn ownsLendableColumn;

    @FXML
    TableColumn ownsCategoriesColumn;

    @FXML
    TableView borrowsTable;
    @FXML
    TableColumn borrowsNameColumn;
    @FXML
    TableColumn borrowsOwnerColumn;
    @FXML
    TableColumn borrowsLendDateColumn;
    @FXML
    TableColumn borrowsDueDateColumn;
    @FXML
    TableColumn borrowsCategoriesColumn;
    @FXML
    Label clickInstructionLabel;
    @FXML
    Tab ownedToolTab;
    @FXML
    Tab borrowedToolTab;

    private GroupToolData tools;
    private ArrayList<IndividualToolData> borrowedToolList;
    private ArrayList<IndividualToolData> ownedToolList;
    private int selectedTid;

    /**
     * Fills in the tables of the user's Owned and Borrowed tools
     */
    @FXML
    public void initialize() {
        tools = new GroupToolData();
        updateOwnedTable();
        updateBorrowedTable();

        borrowedToolTab.setOnSelectionChanged(event -> clickInstructionLabel.setText("Double click on a tool to return it"));
        ownedToolTab.setOnSelectionChanged(event -> clickInstructionLabel.setText("Double click on a tool to modify"));
    }

    /**
     * Updates the table of owned tools
     */
    private void updateOwnedTable() {
        ownsNameColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData, String>("Name"));
        ownsPriceColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData, Integer>("Price"));
        ownsLendableColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData, Boolean>("Lendable"));
        ownsCategoriesColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData, String>("Categories"));
        tools.updateOwnedTools();
        ownedToolList = new ArrayList<>(tools.getOwnedTids().size());
        for (int i = 0; i < tools.getOwnedTids().size(); i++) {
            ownedToolList.add(new IndividualToolData(tools.getOwnedToolNames().get(i),
                    tools.getSalePrices().get(i),
                    tools.getLendable().get(i),
                    tools.getOwnedCategories().get(i)));
        }
        ownsTable.setItems(FXCollections.observableList(ownedToolList));
    }

    /**
     * Updates the table of borrowed tools
     */
    private void updateBorrowedTable() {
        borrowsNameColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData,
                String>("Name"));
        borrowsOwnerColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData,
                String>("Owner"));
        borrowsLendDateColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData,
                String>("LendDate"));
        borrowsDueDateColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData,
                String>("DueDate"));
        borrowsCategoriesColumn.setCellValueFactory(new PropertyValueFactory<IndividualToolData, String>("Categories"));
        tools.updateBorrowedTools();
        borrowedToolList = new ArrayList<>(tools.getBorrowedTids().size());
        for (int i = 0; i < tools.getBorrowedTids().size(); i++) {
            borrowedToolList.add(new IndividualToolData(tools.getBorrowedToolNames().get(i), tools.getOwnerNames().get(i), tools.getLendDates().get(i), tools.getDueDates().get(i), tools.getBorrowedCategories().get(i)));
        }
        borrowsTable.setItems(FXCollections.observableList(borrowedToolList));
    }

    /**
     * Opens the modify tools scene
     *
     * @param event A mouse double click
     */
    @FXML
    public void gotoModifyTool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = ((TableView) event.getSource()).getSelectionModel().getFocusedIndex();
            selectedTid = tools.getOwnedTids().get(index);
            ((Stage) (((TableView) event.getSource()).getScene().getWindow())).close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML" +
                        "/modifyTool.fxml"));
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Modify Tool");

                Scene scene = new Scene(loader.load());
                ModifyToolController controller = loader.getController();
                controller.initialize(ownedToolList.get(index), tools,
                        index, selectedTid);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Returns a tool to the original user
     *
     * @param event A mouse double click
     */
    @FXML
    public void returnTool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = ((TableView) event.getSource()).getSelectionModel().getFocusedIndex();
            selectedTid = tools.getBorrowedTids().get(index);
            SQLController.returnTool(selectedTid, LocalDate.now());
            updateBorrowedTable();
        }
    }
}
