package analytics;

import gui.Main;
import gui.SQLController;
import java.sql.ResultSet;

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
                "\"Has\" as h ON c.cid = h.cid " +
                "GROUP BY c.tool_category ORDER BY count DESC) as cc LIMIT 10;";
        return getResultSet(query);

    }

    public static ResultSet getAverageTimeLentByCategory() {
        String query = "SELECT c.tool_category, AVG(due_date - lend_date) AS " +
                "time_interval FROM \"Borrows\" AS b INNER JOIN \"Has\" AS h " +
                "ON b.tid = h.tid INNER JOIN \"Category\" AS c ON h.cid = c" +
                ".cid GROUP BY c.tool_category ORDER BY time_interval;";
        return getResultSet(query);
    }

    public static ResultSet getAverageSalePriceByCategory() {
        String query = "SELECT c.tool_category, AVG(o.sale_price) as " +
                "avg_price FROM \"Has\" AS h INNER JOIN \"Category\" c ON c" +
                ".cid = h.cid INNER JOIN \"Owns\" AS o ON h.tid = o.tid " +
                "GROUP BY c.tool_category ORDER BY avg_price DESC;";
        return getResultSet(query);
    }

    public static ResultSet getUserToolReturnTimeDifferential() {
        String query = "SELECT uname, ((early_differential - late_differential)::FLOAT / total_borrowed::FLOAT) AS diff FROM (" +
                " SELECT username AS uname, SUM(CASE WHEN return_date > due_date THEN return_date - due_date ELSE 0 END) AS late_differential," +
                " SUM(CASE WHEN return_date <= due_date THEN due_date - return_date ELSE 0 END) AS early_differential," +
                " COUNT(*) AS total_borrowed" +
                " FROM \"User\" AS u, \"Borrows\" AS b" +
                " WHERE u.uid = b.uid AND return_date IS NOT NULL" +
                " GROUP BY u.username" +
                " ORDER BY late_differential DESC, early_differential, total_borrowed) AS sums" +
                " ORDER BY diff;";
        return getResultSet(query);
    }

    public static ResultSet getTopTenMostActiveUsers() {
        String query = "SELECT num_owns.username, own_count + borrow_count + " +
                "lend_count AS total " +
                "FROM (SELECT u.username, COUNT(*) AS own_count FROM \"User\" AS u, \"Owns\" AS o " +
                "WHERE u.uid = o.uid GROUP BY u.username) AS num_owns INNER JOIN ( " +
                "SELECT u.username, COUNT(*) AS borrow_count FROM \"User\" AS u, \"Borrows\" AS b WHERE u.uid = b.uid " +
                "GROUP BY u.username) AS num_borrows ON num_owns.username = num_borrows.username " +
                "INNER JOIN (SELECT u.username, COUNT(*) AS lend_count FROM \"User\" AS u, \"Owns\" AS o, \"Borrows\" AS b " +
                "WHERE u.uid = o.uid AND o.tid = b.tid AND ((o.date_sold IS NULL AND o.date_purchased < b.lend_date) " +
                "OR b.lend_date BETWEEN o.date_purchased AND o.date_sold) " +
                "GROUP BY u.username) AS num_lends ON num_borrows.username = num_lends.username " +
                "ORDER BY total DESC FETCH FIRST 10 ROWS ONLY;";
        return getResultSet(query);
    }

    /**
     * Get the average borrow time of all users
     *
     * @return average borrow time, -1 if error
     */
    public static ResultSet getAverageBorrowTimeOfAllUsers() {
        String query = "SELECT u.username, AVG(b.return_date - b.lend_date) " +
                "AS avg_time_lent FROM \"User\" AS u " +
                "INNER JOIN \"Borrows\" b ON u.uid = b.uid " +
                "WHERE b.return_date IS NOT NULL " +
                "GROUP BY u.username ORDER BY avg_time_lent DESC;";
        return getResultSet(query);

    }

    public static ResultSet getTallyOfEachCategoryType() {
        String query = "SELECT c.tool_category, COUNT(*) " +
                "FROM \"Category\" AS c INNER JOIN \"Has\" AS h ON c.cid = h" +
                ".cid INNER JOIN \"Owns\" AS o on h.tid = o.tid WHERE o.uid =" +
                " " + Main.getUID() + " GROUP BY c.tool_category " +
                "ORDER BY COUNT(*) DESC;";
        return getResultSet(query);
    }


}
