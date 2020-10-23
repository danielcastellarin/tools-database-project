package gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ViewToolsController extends Controller{

    //TODO: Add borrowed tools to collection

    @FXML
    TableView ownsTable;

    @FXML
    TableColumn ownsNameColumn;

    @FXML
    TableColumn ownsPriceColumn;

    @FXML
    TableColumn ownsLendableColumn;

    @FXML
    TableColumn ownsPurchasableColumn;

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
    private ArrayList<BorrowedTool> borrowedToolList;
    private ArrayList<OwnedTool> ownedToolList;
    private int selectedTid;

    @FXML
    public void initialize() {
        //Owned Tools Table
        ownsNameColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, String>("Name"));
        ownsPriceColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, Integer>("Price"));
        ownsLendableColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, Boolean>("Lendable"));
        ownsPurchasableColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, Boolean>("Purchasable"));
        ownsCategoriesColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, String>("Categories"));
        ownedTools = new OwnedUserTools(Main.getUID());
        ownedToolList = new ArrayList<>(ownedTools.getTids().size());
        for (int i = 0; i < ownedTools.getTids().size(); i++) {
            ownedToolList.add(new OwnedTool(ownedTools.getToolNames().get(i),
                    ownedTools.getSalePrices().get(i), ownedTools.getLendable().get(i),
                    ownedTools.getPurchasable().get(i),
                    ownedTools.getCategories().get(i)));
        }
        ownsTable.setItems(FXCollections.observableList(ownedToolList));

        //Borrowed Tools Table
        borrowsNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Name"));
        borrowsOwnerColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Owner"));
        borrowsLendDateColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("LendDate"));
        borrowsDueDateColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("DueDate"));
        borrowsCategoriesColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Categories"));
        borrowedTools = new BorrowedUserTools(Main.getUID());
        borrowedToolList = new ArrayList<>(borrowedTools.getTids().size());
        for (int i = 0; i < borrowedTools.getTids().size(); i++) {
            borrowedToolList.add(new BorrowedTool(borrowedTools.getToolNames().get(i),
                    borrowedTools.getOwnerNames().get(i), borrowedTools.getLendDates().get(i),
                    borrowedTools.getDueDates().get(i),
                    borrowedTools.getCategories().get(i)));
        }
        borrowsTable.setItems(FXCollections.observableList(borrowedToolList));
        borrowedToolTab.setOnSelectionChanged(event -> clickInstructionLabel.setText("Double click on a tool to modify"));
        ownedToolTab.setOnSelectionChanged(event -> clickInstructionLabel.setText("Double click on a tool to return it"));

    }


    @FXML
    public void gotoModifyTool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = ((TableView)event.getSource()).getSelectionModel().getFocusedIndex();
            selectedTid = ownedTools.getTids().get(index);
            ((Stage)(((TableView)event.getSource()).getScene().getWindow())).close();
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

    @FXML
    public void returnTool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = ((TableView)event.getSource()).getSelectionModel().getFocusedIndex();
            selectedTid = borrowedTools.getTids().get(index);
            SQLController.returnTool(selectedTid);
            ((Stage)(((TableView)event.getSource()).getScene().getWindow())).close();
            changeScene("FXML/viewTools.fxml", "View Tools");
        }
    }

}
