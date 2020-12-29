package gui.Controllers;

import gui.AnalyticsSQLController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AnalyticsGraphsController extends Controller {

    //TODO: Implement analytics for display

    @FXML
    PieChart categoryDistributionChart;
    @FXML
    BarChart<?, ?> topTenCategoriesChart;
    @FXML
    Label borrowedToolCount;

    private void fillCategoryDistribution() {
        ResultSet resultSet =  AnalyticsSQLController.getCategoryCount();
        while (true) {
            try {
                if (!resultSet.next()) break;
                String categoryName = resultSet.getString(1);
                int appearanceCount = resultSet.getInt(2);
                categoryDistributionChart.getData().add(new PieChart.Data(categoryName,
                        appearanceCount));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void fillTopTenCategories() {
        ResultSet resultSet = AnalyticsSQLController.getTopTenCategories();
        while (true) {
            try {
                if (!resultSet.next()) break;
                String categoryName = resultSet.getString(1);
                int count = resultSet.getInt(2);
                XYChart.Series series = new XYChart.Series();
                series.getData().add(new XYChart.Data(categoryName, count));
                topTenCategoriesChart.getData().add(series);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }



    private void fillNumberBorrows() {
        try {
            ResultSet resultSet =
                    AnalyticsSQLController.getNumberOfBorrowedTools();
            resultSet.next();
            borrowedToolCount.setText(String.valueOf(resultSet.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initialize() {
        fillCategoryDistribution();
        fillNumberBorrows();
        fillTopTenCategories();
    }


}
