package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends Controller{

    @FXML
    TextField toolNameTextField;
    @FXML
    TextField priceTextField;
    @FXML
    Text statusText;

    private int index;
    private OwnedUserTools tools;
    private OwnedTool tool;
    private int tid;

    @FXML
    public void initialize(OwnedTool theTool, OwnedUserTools tools, int index, int tid) {
        this.tools = tools;
        this.index = index;
        this.tool = theTool;
        this.tid = tid;

        toolNameTextField.setText(tool.getName());
        super.setCategories(parseCategories(tools.getCategories().get(index)));
    }

    @FXML
    private void changePrice(ActionEvent event) {
        String incrementText = ((((Button)event.getSource()).getText()));
        int initialPrice = Integer.parseInt(priceTextField.getText());
        if (initialPrice >= 0){
            switch (incrementText) {
                case "+10":
                    priceTextField.setText(String.valueOf(initialPrice + 10));
                    break;
                case "+5":
                    priceTextField.setText(String.valueOf(initialPrice + 5));
                    break;
                case "+1":
                    priceTextField.setText(String.valueOf(initialPrice + 1));
                    break;
                case "-10":
                    priceTextField.setText(String.valueOf(initialPrice - 10));
                    break;
                case "-5":
                    priceTextField.setText(String.valueOf(initialPrice - 5));
                    break;
                case "-1":
                    priceTextField.setText(String.valueOf(initialPrice - 1));
                    break;
            }
        }
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
        int newPrice = Integer.parseInt(priceTextField.getText());
        if (!newName.equals("")) {

            SQLController.updateTool(tid, newName, newPrice, super.getCategories());
        } else {
            statusText.setVisible(true);
        }
        gotoViewTools(event);
    }
}
