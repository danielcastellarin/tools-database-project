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
    //TODO: Add Functionality to populate list of tools from database
    //TODO: Add Functionality to click on tool and be able to modify it

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

    UserTools tools;

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, String>("Name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Integer>("Price"));
        lendableColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Boolean>("Lendable"));
        purchasableColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Boolean>("Purchasable"));
        categoriesColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, String>("Categories"));


        HashMap<String, List> map = new HashMap();
        tools = new UserTools(Main.getUID());
//        map.put("tids", tids);
        map.put("toolnames", tools.getToolNames());
        map.put("salePrices", tools.getSalePrices());
        map.put("lendable", tools.getTids());
        map.put("purchasable", tools.getTids());
        map.put("categories", tools.getCategories());

        ArrayList<ViewATool> toolList = new ArrayList<>(tools.getTids().size());
        for (int i = 0; i < tools.getTids().size(); i++) {
            toolList.add(new ViewATool(tools.getToolNames().get(i),
                    tools.getSalePrices().get(i), tools.getLendable().get(i),
                    tools.getPurchasable().get(i),
                    tools.getCategories().get(i)));
        }
        table.setItems(FXCollections.observableList(toolList));
    }



    //PLACEHOLDER METHOD -- WILL BE REMOVED
    @FXML
    public void gotoModifyTool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            int index = ((TableView)event.getSource()).getSelectionModel().getFocusedIndex();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML" +
                        "/modifyTool.fxml"));
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Modify Tool");

                Scene scene = new Scene(loader.load());
                ModifyToolController controller = loader.getController();
                controller.initialize(tools, index);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }


//        changeScene("FXML/modifyTool.fxml", 400, 500, "Modify Tool");
    }

}
