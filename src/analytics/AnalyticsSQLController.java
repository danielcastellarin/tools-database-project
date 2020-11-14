package analytics;

import analytics.AnalyticsCSVWriter;
import gui.SQLController;

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

    public static ResultSet getAverageTimeLentByCategory() {
        String query = "SELECT c.tool_category, AVG(due_date - lend_date) AS " +
                "time_interval FROM \"Borrows\" AS b INNER JOIN \"Has\" AS h " +
                "ON b.tid = h.tid INNER JOIN \"Category\" AS c ON h.cid = c" +
                ".cid WHERE return_date IS NOT NULL GROUP BY c.tool_category" +
                " ORDER BY time_interval;";
        return getResultSet(query);
    }

    public static ResultSet getAverageSalePriceByCategory() {
        String query = "SELECT c.tool_category, AVG(o.sale_price) as " +
                "avg_price FROM \"Has\" AS h INNER JOIN \"Category\" c ON c" +
                ".cid = h.cid INNER JOIN \"Owns\" AS o ON h.tid = o.tid " +
                "GROUP BY c.tool_category ORDER BY avg_price DESC;";
        return getResultSet(query);
    }

    public static ResultSet getUserToolReturnTime() {
        String query = "SELECT u.username, " +
                "SUM(CASE WHEN return_date > due_date THEN return_date - due_date ELSE 0 END) AS late_differential, " +
                "SUM(CASE WHEN return_date <= due_date THEN due_date - return_date ELSE 0 END) AS early_differential, " +
                "COUNT(*) AS total_borrowed\nFROM \"User\" AS u, \"Borrows\" AS b\n" +
                "WHERE u.uid != 49 AND u.uid = b.bid AND return_date IS NOT NULL\n" +
                "GROUP BY u.username\nORDER BY late_differential DESC, early_differential, total_borrowed";
        return getResultSet(query);
    }



}
