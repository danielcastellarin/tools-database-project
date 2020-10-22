package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends Controller{
    //TODO: Populate data from selected tool using database info

    @FXML
    TextField toolNameTextField;
    @FXML
    RadioButton yesLendableRadioButton;
    @FXML
    ComboBox<Integer> priceComboBox;

    int index;
    UserTools tools;
    ViewATool tool;
    int tid;

    @FXML
    public void initialize(ViewATool theTool, UserTools tools, int index, int tid) {
        this.tools = tools;
        this.index = index;
        this.tool = theTool;
        this.tid = tid;

        priceComboBox.getItems().addAll(5, 10, 15, 20, 25, 30, 35, 40, 45, 50);

//        if (tools.getLendable().get(index)) {
//            yesLendableRadioButton.setSelected(true);
//        }
        if(tool.isLendable()) {
            yesLendableRadioButton.setSelected(true);
        }

//        toolNameTextField.setText(tools.getToolNames().get(index));
        toolNameTextField.setText(tool.getName());
//        priceComboBox.getSelectionModel().select(tools.getSalePrices().get(index));
        priceComboBox.getSelectionModel().select(tool.getPrice());
    }

    private List<String> parseCategories(String category) {
        List<String> categories = Arrays.asList(category.split(", "));
        System.out.println(categories);
        return categories;
    }

    @FXML
    public void back(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


    @FXML
    public void gotoViewTools(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/viewTools.fxml",  "View Tools");
    }

    @FXML
    public void addCategories() {
        List<String> categories = parseCategories(tools.getCategories().get(index));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML" +
                    "/ToolCategories.fxml"));
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Modify Tool");

            Scene scene = new Scene(loader.load());
            ToolCategoriesController controller = loader.getController();
            controller.initialize(categories);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void modifyTool(ActionEvent event){
        StringBuilder categoryString = new StringBuilder();
        for (int i = 0; i < super.getCategories().size(); i++) {
            categoryString.append(super.getCategories().get(i)).append(", ");
        }
        // Remove ending ', '
        String category = categoryString.toString().substring(0,
                categoryString.length() - 2);
        ViewATool newTool = new ViewATool(toolNameTextField.getText(), priceComboBox.getSelectionModel().getSelectedItem(),
                (tool.isPurchasable() ? yesLendableRadioButton.isSelected() : tool.isPurchasable()), tool.isPurchasable(), category);
        //TODO: Update Name, Lendable, Categories, Sale Price
        SQLController.updateTool(tid, newTool.getName(), newTool.getPrice(), newTool.isLendable(), newTool.getCategories());
        gotoViewTools(event);
    }
}
