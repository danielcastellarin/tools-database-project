package gui;
/**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class SQLController {

    protected static ResultSet resultSet = null;
    private static Connection connection = null;

    /**
     * Opens the connection with the PostgresSQL database.
     *
     * @param url      the url of the database
     * @param username the username to use when connecting
     * @param password the password to use when connecting
     */
    public static void openConnection(String url, String username,
                                      String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database");
            System.out.println(LocalDate.now());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Closes the connection with the database.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from Database");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Generic function for sending SELECT queries to the database.
     *
     * @param query the query to be executed
     */
    protected static void performQuery(String query) {
        System.out.println("Submitting Query: " + query);
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Generic function for sending INSERT, UPDATE, and DELETE queries
     * to the database.
     *
     * @param query the query to be executed
     * @return true if the query successfully updates the database
     */
    public static boolean performUpdate(String query) {
        System.out.println("Submitting Update: " + query);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    // TODO first implementing where queries are performed here, but will probably have those done by the classes as well.
    // proposed generic format for classes doing SQL queries
    // first gather any info needed for the queryString
    // then initialize the queryString and call performQuery/Update with the queryString as the only parameter
    // in the class, immediately call a function that reads from the resultSet that is needed
//    ...
//    SQLController.performQuery(queryString);
//    infoNeeded = SQLController.readInfoFromQuery;
//    ...

    /**
     * Performs a query on the database that returns an integer.
     * Used when retrieving a user's uid and balance.
     *
     * @param query the SQL query for the database
     * @return an integer
     */
    public static int readInt(String query) {
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Fetches all tool categories from the database
     *
     * @param query      SQL to retrieve the tool categories from the database
     * @param categories the list to store the categories
     */
    public static void readCategories(String query, List<String> categories) {
        performQuery(query);
        try {
            while (resultSet.next()) {
                categories.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // TODO: decide whether performQuery should be public, because this is pretty stupid

    /**
     * Used when adding or modifying tools by calling stored
     * functions in the database.
     *
     * @param query The SQL query calling the stored function
     */
    public static void performStoredFunc(String query) {
        performQuery(query);
    }

    /**
     * Retrieves the information about a user's owned tools
     *
     * @param uid        the user's identification
     * @param tids       a list to store the tids of tools
     * @param toolNames  a list to store the tool names
     * @param salePrices a list to store the prices of tools
     * @param lendable   a list to store whether tools are lendable
     * @param categories a list to store tool categories
     */
    public static void getOwnedTools(int uid, List<Integer> tids,
                                     List<String> toolNames,
                                     List<Integer> salePrices,
                                     List<Boolean> lendable,
                                     List<String> categories) {
        performQuery("SELECT * FROM getOwnedTools(" + uid + ")");
        try {
            while (resultSet.next()) {
                tids.add(resultSet.getInt(1));
                toolNames.add(resultSet.getString(2));
                salePrices.add(resultSet.getInt(3));
                lendable.add(resultSet.getBoolean(4));
                categories.add(resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the information about a user's borrowed tools
     *
     * @param uid        the user's identification
     * @param tids       a list to store the tids of tools
     * @param toolNames  a list to store the tool names
     * @param usernames  a list to store the tools' owner's usernames
     * @param lendDates  a list to store the dates tools were lent
     * @param dueDates   a list to store the dates tools should be returned
     * @param categories a list to store tool categories
     */
    public static void getBorrowedTools(int uid, List<Integer> tids,
                                        List<String> toolNames,
                                        List<String> usernames,
                                        List<String> lendDates,
                                        List<String> dueDates,
                                        List<String> categories) {
        String query = "SELECT b.tid, t.tool_name, u.username, b.lend_date, b.due_date, array_agg(tool_category::CHAR(1)) " +
                "FROM \"Borrows\" AS b, \"Tool\" AS t, \"Owns\" AS o, \"User\" AS u, \"Has\" AS h, \"Category\" AS c " +
                "WHERE b.uid = " + uid + " AND b.return_date IS NULL AND b.tid = t.tid AND t.tid = o.tid AND o.date_sold IS NULL AND o.uid = u.uid " +
                "AND t.tid = h.tid AND h.cid = c.cid " +
                "GROUP BY b.tid, t.tool_name, b.lend_date, b.due_date, u.username;";
        performQuery(query);
        try {
            while (resultSet.next()) {
                tids.add(resultSet.getInt(1));
                toolNames.add(resultSet.getString(2));
                usernames.add(resultSet.getString(3));
                lendDates.add(resultSet.getString(4));
                dueDates.add(resultSet.getString(5));
                categories.add(resultSet.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves information necessary for lending or selling a tool
     *
     * @param query the SQL query for obtaining the data
     * @param tids a list to store tids of tools that can be lent/sold
     * @param toolNames a list to store names of tools that can be lent/sold
     * @param prices a list to store prices of tools that can be sold if applicable
     */
    public static void getToolInfo(String query, List<Integer> tids, List<String> toolNames, List<Integer> prices) {
        performQuery(query);
        try {
            while (resultSet.next()) {
                tids.add(resultSet.getInt(1));
                toolNames.add(resultSet.getString(2));
                if (prices != null) {
                    prices.add(resultSet.getInt(3));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve users who can borrow/buy a certain tool.
     *
     * @param query     the SQL query for the database
     * @param users     a map to store user names and ids
     */
    public static void getUsers(String query, Map<String, Integer> users) {
        performQuery(query);
        try {
            while (resultSet.next()) {
                users.put(resultSet.getString(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs the action of selling a tool to another user in the database
     * using a stored function.
     *
     * @param query the SQL query to for the database
     * @return true if tool was successfully sold
     */
    public static boolean sellToolFunc(String query) {
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This is called when a user lends a tool to someone else.
     * It creates a new Borrow record and sets the tool to not lendable.
     *
     * @param uid      the borrower's identification
     * @param tid      the borrowed tool's id
     * @param dueDate  the date tool should be returned to the owner
     */
    public static void lendTool(int uid, int tid, LocalDate dueDate, LocalDate lendDate) {
//        String query1 = "INSERT INTO \"Borrows\" (uid, tid, due_date, lend_date) " +
//                "VALUES(" + uid + ", " + tid + ", '" + dueDate + "', CURRENT_DATE)";
        String query1 = "INSERT INTO \"Borrows\" (uid, tid, due_date, lend_date) " +
                "VALUES(" + uid + ", " + tid + ", '" + dueDate + "', '" + lendDate + "')";
        performUpdate(query1);
        String query2 = "UPDATE \"Tool\" SET lendable = false WHERE tid = " + tid;
        performUpdate(query2);
    }

    // TODO: Consider merging these functions into two updates and pass in the two queries

    /**
     * Update tool date and lendable status
     *
     * @param tid tool id
     */
    public static void returnTool(int tid, LocalDate currentDate) {
        String query1 = "UPDATE \"Borrows\" SET return_date = '" + currentDate + "' WHERE tid = " + tid + " AND return_date IS NULL";
        performUpdate(query1);
        String query2 = "UPDATE \"Tool\" SET lendable = true WHERE tid = " + tid;
        performUpdate(query2);
    }
}
