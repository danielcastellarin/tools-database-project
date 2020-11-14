package gui;

import analytics.AnalyticsCSVWriter;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ryan LaRue, rml5169@rit.edu
 */
public class AnalyticsSQLController extends SQLController {

    private static ResultSet getResultSet(String query) {
        performQuery(query);
        return resultSet;
    }

    public static ResultSet getTopTenCategories() {
        String query = "SELECT category, count FROM (SELECT c.tool_category AS " +
                "category, COUNT(*) AS count FROM \"Category\" AS c INNER JOIN " +
                "\"Has\" as h ON c.cid = h.cid\n" +
                "GROUP BY c.tool_category ORDER BY count DESC) as cc LIMIT 10;";
        return getResultSet(query);

    }
}
