package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

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

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, String>("Name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Integer>("Price"));
        lendableColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Boolean>("Lendable"));
        purchasableColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, Boolean>("Purchasable"));
        categoriesColumn.setCellValueFactory(new PropertyValueFactory<ViewATool, String>("Categories"));


        HashMap<String, List> map = new HashMap();
        List<Integer> tids = new ArrayList<>();
        List<Integer> salePrices = new ArrayList<>();
        List<String> toolNames = new ArrayList<>();
        List<Boolean> lendable = new ArrayList<>();
        List<Boolean> purchasable = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        SQLController.getUserTools(Main.getUID(), tids, salePrices, toolNames, lendable, purchasable, categories);
//        map.put("tids", tids);
        map.put("toolnames", toolNames);
        map.put("salePrices", salePrices);
        map.put("lendable", tids);
        map.put("purchasable", tids);
        map.put("categories", categories);

        ArrayList<ViewATool> toolList = new ArrayList<>(tids.size());
        for (int i = 0; i < tids.size(); i++) {
            toolList.add(new ViewATool(toolNames.get(i), salePrices.get(i), lendable.get(i), purchasable.get(i), categories.get(i)));
        }
        table.setItems(FXCollections.observableList(toolList));
    }

    //PLACEHOLDER METHOD -- WILL BE REMOVED
    @FXML
    public void gotoModifyTool(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/modifyTool.fxml", 400, 500, "Modify Tool");
    }
}
