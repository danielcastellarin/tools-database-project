package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ViewToolsController extends Controller{

    //TODO: Add borrowed tools to collection

    @FXML
    TableView table;

    @FXML
    TableColumn nameColumn;

    @FXML
    TableColumn priceColumn;

    @FXML
    TableColumn lendableColumn;

    @FXML
    TableColumn purchasableColumn;

    @FXML
    TableColumn categoriesColumn;

    private UserTools tools;
    private ArrayList<ViewATool> toolList;
    private int selectedTid;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, String>("Name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Integer>("Price"));
        lendableColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Boolean>("Lendable"));
        purchasableColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Boolean>("Purchasable"));
        categoriesColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, String>("Categories"));

        tools = new UserTools(Main.getUID());

        toolList = new ArrayList<>(tools.getTids().size());
        for (int i = 0; i < tools.getTids().size(); i++) {
            toolList.add(new ViewATool(tools.getToolNames().get(i),
                    tools.getSalePrices().get(i), tools.getLendable().get(i),
                    tools.getPurchasable().get(i),
                    tools.getCategories().get(i)));
        }
        table.setItems(FXCollections.observableList(toolList));
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
