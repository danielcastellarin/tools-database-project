package gui;

import analytics.AnalyticsCSVWriter;
import analytics.AnalyticsSQLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.sql.ResultSet;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AnalyticsMenuController extends Controller {

    @FXML
    private Text statusText;

    /**
     * Downloads an appropriate CSV in accordance with the button clicked
     * @param event A button click
     */
    @FXML
    public void downloadCSV(ActionEvent event) {
        String buttonLabel = ((Button) event.getSource()).getText();
        File file = Main.openDialog();
        ResultSet resultSet;
        switch (buttonLabel) {
            case "Top 10 Tool Categories":
                String csvName = "Top Ten Categories.csv";
                resultSet = AnalyticsSQLController.getTopTenCategories();
                AnalyticsCSVWriter.write(file, resultSet, csvName);
                statusText.setText("CSV written to: " + file + "\\" +csvName);
                statusText.setVisible(true);
                break;
        }
    }

    @FXML
    public void gotoHome(ActionEvent event) {
        changeScene("FXML/home.fxml", "Home");
        statusText.setVisible(false);
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
