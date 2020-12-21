package gui.Controllers;

import gui.AnalyticsSQLController;
import gui.Main;
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
     *
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
            case "Return Time Differentials (All Users)":
                csvName = "Return Time Differentials (All Users).csv";
                resultSet =
                        AnalyticsSQLController.getUserToolReturnTimeDifferential();
                break;
            case "Top 10 Most Active Users":
                csvName = "Top 10 Most Active Users.csv";
                resultSet = AnalyticsSQLController.getTopTenMostActiveUsers();
                break;
            case "Average Borrow Time (All Users)":
                csvName = "Average Borrow Time (All Users).csv";
                resultSet = AnalyticsSQLController.getAverageBorrowTimeOfAllUsers();
                break;
            case "Tally of Each Category You Have Owned":
                csvName = "Tally of Each Category You Have Owned.csv";
                resultSet = AnalyticsSQLController.getTallyOfEachCategoryType();
        }
        AnalyticsSQLController.write(file, resultSet, csvName);
        statusText.setText("CSV written to: " + file + "\\" + csvName);
        statusText.setVisible(true);
    }

    //TODO: Remove once finished
    @FXML
    public void testing(ActionEvent event) {
        changeScene("FXML/analyticsGraphs.fxml", "Analytics");
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void gotoHome(ActionEvent event) {
        changeScene("FXML/home.fxml", "Home");
        statusText.setVisible(false);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
