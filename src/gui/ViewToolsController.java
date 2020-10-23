package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

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



    private OwnedUserTools tools;
    private ArrayList<OwnedTool> toolList;
    private int selectedTid;

    @FXML
    public void initialize() {
        //Owned Tools Table
        ownsNameColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, String>("Name"));
        ownsPriceColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, Integer>("Price"));
        ownsLendableColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, Boolean>("Lendable"));
        ownsPurchasableColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, Boolean>("Purchasable"));
        ownsCategoriesColumn.setCellValueFactory(new PropertyValueFactory<OwnedTool, String>("Categories"));
        tools = new OwnedUserTools(Main.getUID());
        toolList = new ArrayList<>(tools.getTids().size());
        for (int i = 0; i < tools.getTids().size(); i++) {
            toolList.add(new OwnedTool(tools.getToolNames().get(i),
                    tools.getSalePrices().get(i), tools.getLendable().get(i),
                    tools.getPurchasable().get(i),
                    tools.getCategories().get(i)));
        }
        ownsTable.setItems(FXCollections.observableList(toolList));


        //Borrowed Tools Table
        borrowsNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Name"));
        borrowsOwnerColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Owner"));
        borrowsLendDateColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Lend Date"));
        borrowsDueDateColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Due Date"));
        borrowsCategoriesColumn.setCellValueFactory(new PropertyValueFactory<BorrowedTool, String>("Categories"));

        //TODO: Repeat process above but with something like BorrowedUserTools



    }

    @FXML
    public void gotoModifyTool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = ((TableView)event.getSource()).getSelectionModel().getFocusedIndex();
            selectedTid = tools.getTids().get(index);
            ((Stage)(((TableView)event.getSource()).getScene().getWindow())).close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML" +
                        "/modifyTool.fxml"));
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Modify Tool");

                Scene scene = new Scene(loader.load());
                ModifyToolController controller = loader.getController();
                controller.initialize(toolList.get(index), tools, index, selectedTid);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }

}
