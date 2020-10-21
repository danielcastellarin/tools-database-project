package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class ViewToolsController extends Controller{
    //TODO: Add Functionality to populate list of tools from database
    //TODO: Add Functionality to click on tool and be able to modify it







    //PLACEHOLDER METHOD -- WILL BE REMOVED
    @FXML
    public void gotoModifyTool(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        changeScene("FXML/modifyTool.fxml", 400, 500, "Modify Tool");
    }
}
