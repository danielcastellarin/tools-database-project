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
        ResultSet resultSet = null;
        String csvName = null;
        switch (buttonLabel) {
            case "Top 10 Tool Categories":
                csvName = "Top Ten Categories.csv";
                resultSet = AnalyticsSQLController.getTopTenCategories();
                break;
            case "Average Lend Time By Category":
                csvName = "Average Lend Time By Category.csv";
                resultSet = AnalyticsSQLController.getAverageTimeLentByCategory();
                break;
            case "Average Sale Price By Category":
                csvName = "Average Sale Price By Category.csv";
                resultSet = AnalyticsSQLController.getAverageSalePriceByCategory();
                break;
            case "User Tool Return Time":
                csvName = "User Tool Return Time.csv";
                resultSet = AnalyticsSQLController.getUserToolReturnTime();
                break;
        }
        AnalyticsCSVWriter.write(file, resultSet, csvName);
        statusText.setText("CSV written to: " + file + "\\" +csvName);
        statusText.setVisible(true);
    }

    @FXML
    public void gotoHome(ActionEvent event) {
        changeScene("FXML/home.fxml", "Home");
        statusText.setVisible(false);
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
