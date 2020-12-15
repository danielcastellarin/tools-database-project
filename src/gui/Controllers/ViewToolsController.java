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


    private BorrowedUserTools borrowedTools;
    private OwnedUserTools ownedTools;
    private ArrayList<IndividualToolData> borrowedToolList;
    private ArrayList<IndividualToolData> ownedToolList;
    private int selectedTid;

    /**
     * Fills in the tables of the user's Owned and Borrowed tools
     */
    @FXML
    public void initialize() {
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
        ownedTools = new OwnedUserTools(Main.getUID());
        ownedToolList = new ArrayList<>(ownedTools.getTids().size());
        for (int i = 0; i < ownedTools.getTids().size(); i++) {
            ownedToolList.add(new IndividualToolData(ownedTools.getToolNames().get(i),
                    ownedTools.getSalePrices().get(i), ownedTools.getLendable().get(i),
                    ownedTools.getCategories().get(i)));
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
        borrowedTools = new BorrowedUserTools(Main.getUID());
        borrowedToolList = new ArrayList<>(borrowedTools.getTids().size());
        for (int i = 0; i < borrowedTools.getTids().size(); i++) {
            borrowedToolList.add(new IndividualToolData(borrowedTools.getToolNames().get(i),
                    borrowedTools.getOwnerNames().get(i), borrowedTools.getLendDates().get(i),
                    borrowedTools.getDueDates().get(i),
                    borrowedTools.getCategories().get(i)));
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
            selectedTid = ownedTools.getTids().get(index);
            ((Stage) (((TableView) event.getSource()).getScene().getWindow())).close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML" +
                        "/modifyTool.fxml"));
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Modify Tool");

                Scene scene = new Scene(loader.load());
                ModifyToolController controller = loader.getController();
                controller.initialize(ownedToolList.get(index), ownedTools, index, selectedTid);
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
            selectedTid = borrowedTools.getTids().get(index);
            SQLController.returnTool(selectedTid, LocalDate.now());
            updateBorrowedTable();
        }
    }
}
