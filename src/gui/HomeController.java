package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class HomeController extends Controller {

    @FXML
    public void changeSceneFromGrid(ActionEvent event) {
        String buttonLabel = ((Button) event.getSource()).getText();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        String fxml = null; //TODO: Remove Null initialization
        int width = 450;
        int height = 500;
        switch (buttonLabel) {
            case "Add Tool":
                fxml = "FXML/addTool.fxml";
                break;
            case "View Tools":
                width = 600;
                fxml = "FXML/viewTools.fxml";
                break;
            case "Lend Tools":
                fxml = "FXML/lendTools.fxml";
                break;
            case "Get Lendable Tools":
                fxml = "FXML/getLendableTools.fxml";
                break;
            case "Change Balance":
                fxml = "FXML/balance.fxml";
                break;
            case "Buy Tools":
                fxml = "FXML/buyTools.fxml";
                break;
            case "Analytics":
                fxml = "FXML/analytics.fxml";
                break;
            default:
                fxml = "FXML/home.fxml";
                break;
        }
        changeScene(fxml, width, height, buttonLabel);
    }

}
