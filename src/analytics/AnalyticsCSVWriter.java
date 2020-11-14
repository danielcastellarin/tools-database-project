package analytics;

import com.opencsv.CSVWriter;
import gui.AnalyticsSQLController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AnalyticsCSVWriter{

    public static void write(File filePath,
                             ResultSet resultSet, String fileName) {
        try {
            FileWriter output = new FileWriter(filePath + "\\" + fileName);
            CSVWriter writer = new CSVWriter(output);
            writer.writeAll(resultSet, true);
            writer.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        String st = System.getProperty("user.home") + "\\Desktop";

        ResultSet resultSet = AnalyticsSQLController.getTopTenCategories();
        File file = new File(st);
        String name = "Test.csv";
        write(file, resultSet, name);
    }

}
