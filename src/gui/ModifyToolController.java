package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends Controller{

    @FXML
    TextField toolNameTextField;
    @FXML
    ComboBox<Integer> priceComboBox;
    @FXML
    Text statusText;

    private int index;
    private UserTools tools;
    private ViewATool tool;
    private int tid;

    @FXML
    public void initialize(ViewATool theTool, UserTools tools, int index, int tid) {
        this.tools = tools;
        this.index = index;
        this.tool = theTool;
        this.tid = tid;

        priceComboBox.getItems().addAll(5, 10, 15, 20, 25, 30, 35, 40, 45, 50);

        toolNameTextField.setText(tool.getName());
        priceComboBox.getSelectionModel().select(tools.getSalePrices().get(index));

        super.setCategories(parseCategories(tools.getCategories().get(index)));
    }

    private List<String> parseCategories(String category) {
        List<String> categories = Arrays.asList(category.split(", "));
        System.out.println(categories);
        return categories;
    }

    @FXML
    public void gotoViewTools(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/viewTools.fxml",  "View Tools");
    }

    @FXML
    public void modifyCategories() {
        super.gotoCategories(super.getCategories(), "Modify Tool");
    }

    @FXML
    public void modifyTool(ActionEvent event){

        String newName = toolNameTextField.getText();
        statusText.setVisible(false);
        int newPrice = priceComboBox.getSelectionModel().getSelectedItem();
        if (!newName.equals("")) {

            SQLController.updateTool(tid, newName, newPrice, super.getCategories());
        } else {
            statusText.setVisible(true);
        }
        gotoViewTools(event);
    }
}
