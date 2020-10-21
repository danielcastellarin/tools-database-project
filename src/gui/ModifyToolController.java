package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ModifyToolController extends Controller{
    //TODO: Populate data from selected tool using database info


    @FXML
    public void initialize(UserTools tools, int index) {
        System.out.println(tools.getTids() + " " + index);
    }


    @FXML
    public void gotoViewTools(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/viewTools.fxml",  "View Tools");
    }

    @FXML
    public void modifyTool(ActionEvent event){
        //TODO: Update selected tool in Database
        gotoViewTools(event);
    }
}
