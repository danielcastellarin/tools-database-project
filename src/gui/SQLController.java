package gui;
/**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import java.sql.*;
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
     * Checks the credentials of the user to see if they match the
     * credentials of a user in the database.
     *
     * @param user     the username
     * @param password the password
     * @return positive number if valid, negative if not
     */
    public static int verifyLoginCredentials(String user, String password) {
        String query =
                "SELECT uid, username, password FROM \"User\" WHERE username " +
                        "='" + user + "' AND password='" + password + "'";
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Queries the database for UID upon logging into the app
     *
     * @param query SQL to retrieve UID using username and password
     * @return the UID of the user with the specified username and password
     */
    public static int readUID(String query) {
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Creates a new user in the database.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param username  the username of the user
     * @param password  the password of the user
     * @return true if the user is able to be created
     */
    public static boolean createNewUser(String firstName, String lastName,
                                        String username, String password) {
        String query =
                "INSERT INTO \"User\" (username, user_first_name, " +
                        "user_last_name, password)" +
                        " " + "VALUES('" + username + "', '" + firstName +
                        "', '" + lastName + "', '" + password + "')";
        return performUpdate(query);
    }

    /**
     * Gets the balance of a particular user.
     *
     * @param uid the uid of the user to get the balance of
     * @return the balance of the user, 0 if query doesn't work
     */
    public static int getBalance(int uid) {
        String query = "SELECT balance FROM \"User\" WHERE uid = " + uid;
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    // TODO Find way to combine this with readUID, because its basically the same

    /**
     * Fetches a user's balance from the database
     *
     * @param query SQL to retrieve balance using a user's uid
     * @return the user's balance
     */
    public static int readBalance(String query) {
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    // SQL Function is transfermoney(buyer, seller, price)

    /**
     * Increases the balance of a user by a specific amount
     *
     * @param uid       the user id
     * @param increment the value to increment by
     * @return the new balance after incrementing
     */
    public static int incrementBalance(int uid, int increment) {
        String q = "SELECT balance FROM \"User\" WHERE uid = " + uid;
//        int balance = getBalance(uid) + increment;
        int balance = readBalance(q) + increment;
        String query =
                "UPDATE \"User\" SET balance = " + balance + " WHERE " +
                        "uid = " + uid;
        performUpdate(query);
        return balance;
    }

    /**
     * Gets the all the categories from the database.
     *
     * @return a list of all the categories
     */
    public static List<String> getAllCategories() {
        String query = "SELECT tool_category FROM \"Category\"";
        performQuery(query);
        List<String> categories = new ArrayList<>();
//        while (true) {
//            try {
//                if (!resultSet.next()) break;
//                categories.add(resultSet.getString(1));
//            } catch (SQLException e) {
//                System.err.println(e.getMessage());
//            }
//        }
        try {
            while (resultSet.next()) {
                categories.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return categories;
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

    // TODO: delete this method once addNewCategory in ToolCategoriesController is removed

    /**
     * Adds a category to the database.
     *
     * @param category_name the category's name
     * @return true if the query was executed successfully
     */
    public static boolean insertCategory(String category_name) {
        String query =
                "INSERT INTO \"Category\" (tool_category)" +
                        " " + "VALUES('" + category_name + "')";
        return performUpdate(query);
    }

    /**
     * Get the cids of all the categories in a list
     *
     * @param categories the list of category names
     * @return the list of cids
     */
    private static List<Integer> getCategoryIDs(List<String> categories) {
        List<Integer> cids = new ArrayList<>();
        for (String categoryName : categories) {
            String query =
                    "SELECT cid FROM \"Category\" WHERE tool_category= '" +
                            categoryName + "'";
            performQuery(query);
            try {
                if (!resultSet.next()) break;
                cids.add(resultSet.getInt(1));
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return cids;
    }

    /**
     * Retrieves the cids of all the categories in a given list
     *
     * @param query SQL to retrieve the cids from the database
     * @param cids  the list to store the cids
     */
    private static void readCategoriesForCIDs(String query, List<Integer> cids) {
        performQuery(query);
        try {
            while (resultSet.next()) {
                cids.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // TODO Skipping for now, but might move performQuery call elsewhere later
    //  >>>> also really similar to readBalance and readUID

    /**
     * Finds the next possible TID when adding a new tool to the database.
     *
     * @return the value of the available tid
     */
    public static int getNextAvailableTID() {
        String query = "SELECT MAX(tid) FROM \"Tool\"";
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1) + 1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Clears the categories for a specific tool so that they can be reset.
     *
     * @param tid the tid of the tool
     */
    private static void deleteCategoriesFromHas(int tid) {
        String query = "DELETE FROM \"Has\" WHERE tid= " + tid;
        performUpdate(query);
    }

    /**
     * Creates new relationship between a tool and a category in the database.
     *
     * @param tid        the tid of the tool
     * @param categories the categories to rea
     */
    protected static void insertCategoriesToHas(int tid,
                                                List<String> categories) {
        List<Integer> cids = new ArrayList<>();
        String queryCats = "";
        for (int i = 0; i < categories.size(); i++) {
            queryCats += "'" + categories.get(i) + (i + 1 < categories.size() ? "', " : "'");
        }
        String q = "SELECT cid FROM \"Category\" WHERE tool_category IN (" + queryCats + ")";
        SQLController.readCategoriesForCIDs(q, cids);
//        List<Integer> cids = getCategoryIDs(categories);

        for (int cid : cids) {
            String query = "INSERT INTO \"Has\" (tid, cid)" +
                    " " + "VALUES(" + tid + "," + cid + ")";
            performUpdate(query);
        }
    }

    // TODO: will probably combine with others in the future

    /**
     * Creates new relations between a tool and a list of categories.
     *
     * @param tid        the tool's unique id
     * @param categories the list of categories to be applied to the tool
     */
    protected static void insertNewHasRelations(int tid, List<String> categories) {
        String queryCats = "";
        for (int i = 0; i < categories.size(); i++) {
            queryCats += "'" + categories.get(i) + (i + 1 < categories.size() ? "', " : "'");
        }
        performQuery("SELECT addCategories(" + tid + ", VARIADIC ARRAY[" + queryCats + "])");
    }

    // TODO: I believe this is a duplicate function. Whenever a tool is sold, this query is also run.
    //  Only replacing actual calls for now (this is also pasted where this function was originally called)

    /**
     * Inserts tool into Owns table.
     *
     * @param uid           the uid of the user
     * @param tid           the tid of the tool
     * @param datePurchased the date purchased
     * @param salePrice     the sale price
     */
    protected static void insertToolToOwns(int uid, int tid,
                                           String datePurchased,
                                           int salePrice) {
        String query = "INSERT INTO \"Owns\" (uid, tid, date_purchased, " +
                "date_sold, sale_price)" +
                " " + "VALUES(" + uid + ", " + tid + ", '" + datePurchased +
                "', NULL, " + salePrice + ")";
        performUpdate(query);
    }

    /**
     * Inserts new tool into tool table
     *
     * @param tid      the tid of the tool
     * @param toolName the name of the tool
     */
    protected static void insertNewToolToTool(int tid, String toolName) {
        String query = "INSERT INTO \"Tool\" (tid, tool_name, lendable)" +
                " " + "VALUES(" + tid + ", '" + toolName + "', true)";
        performUpdate(query);
    }

    // TODO merge these methods with the ones in DataGenerationSQLController

    /**
     * Adds new tool
     *
     * @param uid          the user id
     * @param toolName     tool name
     * @param purchaseDate date of purchase
     * @param sale_price   sale price
     * @param categories   categories list
     */
    public static void addNewTool(int uid, String toolName,
                                  String purchaseDate, int sale_price,
                                  List<String> categories) {
        int tid = getNextAvailableTID();
//        insertNewToolToTool(tid, toolName);
        performUpdate("INSERT INTO \"Tool\" (tid, tool_name, lendable)" +
                " VALUES(" + tid + ", '" + toolName + "', true)");
//        insertToolToOwns(uid, tid, purchaseDate, sale_price);
        // TODO: I believe this is a duplicate function. Whenever a tool is sold, this query is also run.
        //  Only replacing actual calls for now
        performUpdate("INSERT INTO \"Owns\" (uid, tid, date_purchased, " +
                "date_sold, sale_price) VALUES(" + uid + ", " + tid + ", '" +
                purchaseDate + "', NULL, " + sale_price + ")");
//        insertCategoriesToHas(tid, categories);
        insertNewHasRelations(tid, categories);
    }

    /**
     * Adds a new tool to the database using the stored function in the database.
     *
     * @param query The addTool function call for the database
     */
    public static void addTool(String query) {
        performQuery(query);
    }

    /**
     * Gets tool info from owns table
     *
     * @param uid        the user id
     * @param salePrices sale prices
     * @param tids       tool ids
     */
    private static void getToolsFromOwns(int uid, List<Integer> salePrices,
                                         List<Integer> tids) {
        String query =
                "SELECT tid, sale_price FROM \"Owns\" WHERE uid=" + uid + " " +
                        "AND date_sold IS NULL";
        performQuery(query);
        try {
            while (resultSet.next()) {
                tids.add(resultSet.getInt(1));
                salePrices.add(resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets tool info from tool table
     *
     * @param tids      list of tool ids
     * @param toolNames list of tool names
     * @param lendable  is lendable
     */
    private static void getToolInfoFromTool(List<Integer> tids,
                                            List<String> toolNames,
                                            List<Boolean> lendable) {
        for (int tid : tids) {
            String query =
                    "SELECT tool_name, lendable FROM \"Tool\" " +
                            "WHERE tid=" + tid;
            performQuery(query);
            try {
                while (resultSet.next()) {
                    toolNames.add(resultSet.getString(1));
                    lendable.add(resultSet.getBoolean(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get user tools
     *
     * @param uid        user id
     * @param tids       list of tool ids
     * @param salePrices list of sale price
     * @param toolNames  list of tool names
     * @param lendable   is lendable
     * @param categories list of categories
     */
    public static void getUserTools(int uid, List<Integer> tids,
                                    List<Integer> salePrices,
                                    List<String> toolNames,
                                    List<Boolean> lendable,
                                    List<String> categories) {
//        getToolsFromOwns(uid, salePrices, tids);
//        getToolInfoFromHas(tids, categories);
//        getToolInfoFromTool(tids, toolNames, lendable);
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
    public static void getGoodUserTools(int uid, List<Integer> tids,
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
     * Gets tool info from has table
     *
     * @param tids       tool ids
     * @param categories list of categories
     */
    private static void getToolInfoFromHas(List<Integer> tids,
                                           List<String> categories) {
        for (int tid : tids) {
            String query = "SELECT tool_category FROM \"Category\" WHERE " +
                    "cid IN (SELECT cid FROM \"Has\" WHERE tid=" + tid + ")";
            performQuery(query);
            try {
                StringBuilder categoryString = new StringBuilder();
                while (resultSet.next()) {
                    categoryString.append(resultSet.getString(1)).append(", ");
                }
                // Remove ending ', '
                String category = categoryString.toString().substring(0,
                        categoryString.length() - 2);
                categories.add(category);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets tool info from borrows table
     *
     * @param uid       the user id
     * @param tids      list of tool ids
     * @param toolNames list of tool names
     * @param lendDates list of lend dates
     * @param dueDates  list of due dates
     * @param owners    list of owners
     * @param usernames list of owners
     */
    private static void getToolInfoFromBorrows(int uid, List<Integer> tids, List<String> toolNames,
                                               List<String> lendDates, List<String> dueDates, List<Integer> owners, List<String> usernames) {
//        String query = "SELECT b.tid, t.tool_name, b.lend_date, b.due_date, o.uid, u.username FROM " +
//                "\"Borrows\" AS b, \"Tool\" AS t, \"Owns\" AS o, \"User\" AS u WHERE " +
//                "b.uid = " + uid + " AND b.return_date IS NULL AND b.tid " +
//                "= t.tid AND t.tid = o.tid AND o.date_sold IS NULL AND o.uid = u.uid";
//        performQuery(query);
//        while (true) {
//            try {
//                if (!resultSet.next()) break;
//                tids.add(resultSet.getInt(1));
//                toolNames.add(resultSet.getString(2));
//                lendDates.add(resultSet.getString(3));
//                dueDates.add(resultSet.getString(4));
//                owners.add(resultSet.getInt(5));
//                usernames.add(resultSet.getString(6));
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
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
     * Retrieves information necessary for lending a tool
     *
     * @param uid the lender's identification
     * @param tids a list to store the lendable tool tids
     * @param toolNames a list to store the lendable tool names
     */
    private static void getLendableToolInfo(int uid, List<Integer> tids, List<String> toolNames) {
//        String query = "SELECT t.tid, t.tool_name FROM \"Owns\" o, \"Tool\" t WHERE " +
//                "o.tid = t.tid AND o.uid = " + uid + " AND t.lendable = true " +
//                "AND date_sold IS NULL";
//        performQuery(query);
//        try {
//            while (resultSet.next()) {
//                tids.add(resultSet.getInt(1));
//                toolNames.add(resultSet.getString(2));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Obtain the ids and names of the users that can be lent to
     *
     * @param uid       the lender's identification
     * @param uids      a list to store user ids
     * @param usernames a list to store usernames
     */
    private static void getAllOtherUsers(int uid, Set<Integer> uids,
                                         Set<String> usernames) {
        String query = "SELECT uid, username FROM \"User\" WHERE uid != " + uid;
        performQuery(query);
        try {
            while (resultSet.next()) {
                uids.add(resultSet.getInt(1));
                usernames.add(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves information necessary for lending a tool
     *
     * @param uid       the lender's identification
     * @param tids      a list to store the lendable tool tids
     * @param toolNames a list to store the lendable tool names
     * @param uids      a set to store user ids
     * @param usernames a set to store usernames
     */
    public static void getLendableUserTools(int uid, List<Integer> tids, List<String> toolNames,
                                            Set<Integer> uids, Set<String> usernames) {
//        getLendableToolInfo(uid, tids, toolNames);
//        getAllOtherUsers(uid, uids, usernames);
    }

    /**
     * Retrieves information necessary for lending a tool
     *
     * @param uid       the lender's identification
     * @param tids      a list to store the lendable tool tids
     * @param toolNames a list to store the lendable tool names
     * @param users     a map to store user names and ids
     */
    public static void getLendableUserTools(int uid, List<Integer> tids, List<String> toolNames,
                         Map<String, Integer> users) {
        // Gather Tool information
        String toolQuery = "SELECT t.tid, t.tool_name FROM \"Owns\" o, \"Tool\" t WHERE " +
                "o.tid = t.tid AND o.uid = " + uid + " AND t.lendable = true " +
                "AND date_sold IS NULL";
        performQuery(toolQuery);
        try {
            while (resultSet.next()) {
                tids.add(resultSet.getInt(1));
                toolNames.add(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Gather User (Borrower) information
        String borrowerQuery = "SELECT username, uid FROM \"User\" WHERE uid != " + uid;
        performQuery(borrowerQuery);
        try {
            while (resultSet.next()) {
                users.put(resultSet.getString(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves information necessary for selling a tool
     *
     * @param query      the SQL query for obtaining the data
     * @param tids       a list to store tids of tools that can be sold
     * @param toolNames  a list to store names of tools that can be sold
     * @param toolPrices a list to store prices of tools that can be sold
     */
    public static void getSellableToolInfo(String query, List<Integer> tids,
                                           List<String> toolNames, List<Integer> toolPrices) {
        performQuery(query);
        try {
            while (resultSet.next()) {
                tids.add(resultSet.getInt(1));
                toolNames.add(resultSet.getString(2));
                toolPrices.add(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the users who have enough money to buy a specific tool.
     *
     * @param query     the SQL query for the database
     * @param users     a map to store user names and ids
     */
    public static void getUsersWithEnoughBank(String query, Map<String, Integer> users) {
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
     * Get user id from username
     *
     * @param username username
     */
    private static int getUIDFromUsername(String username) {
        String query = "SELECT uid FROM \"User\" WHERE username = '" + username + "'";
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Update tool in tool and owns tables
     *
     * @param tid        tool id
     * @param name       tool name
     * @param price      tool price
     * @param categories list of categories
     */
    public static void updateTool(int tid, String name, int price, List<String> categories) {
        String query = "UPDATE \"Tool\" SET tool_name = '" + name + "' WHERE tid = " + tid;
        performUpdate(query);
        query = "UPDATE \"Owns\" SET sale_price = " + price + " WHERE " +
                "date_sold IS NULL AND tid = " + tid;
        performUpdate(query);
//        deleteCategoriesFromHas(tid);
        performUpdate("DELETE FROM \"Has\" WHERE tid = " + tid);
//        insertCategoriesToHas(tid, categories);
        insertNewHasRelations(tid, categories);
    }

    // TODO: Merge with addfunction because it does the same thing.
    /**
     * Use a stored function to execute an (action) update on a tool in the database.
     *
     * @param query the SQL query used to call to stored function
     */
    public static void updateToolFunc(String query) {
        performQuery(query);
    }

    /**
     * This is called when a user lends a tool to someone else.
     * It creates a new Borrow record and sets the tool to not lendable.
     *
     * @param uid      the borrower's identification
     * @param tid      the borrowed tool's id
     * @param dueDate  the date tool should be returned to the owner
     */
    public static void lendTool(int uid, int tid, String dueDate) {
        String query1 = "INSERT INTO \"Borrows\" (uid, tid, due_date, lend_date) " +
                "VALUES(" + uid + ", " + tid + ", '" + dueDate + "', CURRENT_DATE)";
        performUpdate(query1);
        String query2 = "UPDATE \"Tool\" SET lendable = false WHERE tid = " + tid;
        performUpdate(query2);
    }

    /**
     * Gets sale price of tool from id
     *
     * @param tid tool id
     * @return sale price
     */
    private static int getSalePrice(int tid) {
        String query =
                "SELECT sale_price FROM \"Owns\" WHERE tid= " + tid + " AND " +
                        "date_sold IS NULL";
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Sell tool
     *
     * @param toUID   the buyer's user id
     * @param fromUID the seller's user id
     * @param tid     the id of the tool being sold
     * @return true if tool was successfully sold
     */
    public static boolean sellTool(int toUID, int fromUID,
                                   int tid) {
        int salePrice = getSalePrice(tid);
//        int toUID = getUIDFromUsername(toUsername);
//        int toBalance = getBalance(toUID);
        String q = "SELECT balance FROM \"User\" WHERE uid = " + toUID;
        int toBalance = readBalance(q);

        if (toBalance < salePrice) {
            return false;
        } else {
            // Exchange currency
//            incrementBalance(toUID, -salePrice);
//            incrementBalance(fromUID, salePrice);
            performQuery("SELECT transfermoney(" + toUID + ", " + fromUID + ", " + salePrice + ")");

            System.out.println("FROM ID: " + fromUID);
            System.out.println("TO ID: " + toUID);
            String query = "UPDATE \"Owns\" SET date_sold = CURRENT_DATE " +
                    "WHERE uid = " + fromUID + " AND tid = " + tid + " AND " +
                    "date_sold IS NULL";

            performUpdate(query);

            String query2 = "INSERT INTO \"Owns\" (uid, tid, date_purchased," +
                    " " +
                    "date_sold, sale_price) " + "VALUES(" + toUID + ", " + tid + ", CURRENT_DATE, NULL, " + salePrice + ")";
            performUpdate(query2);
        }
        return true;
    }

    /**
     * Update tool date and lendable status
     *
     * @param tid tool id
     */
    public static void returnTool(int tid) {
        String query1 = "UPDATE \"Borrows\" SET return_date = CURRENT_DATE WHERE tid = " + tid + " AND return_date IS NULL";
        performUpdate(query1);
        String query2 = "UPDATE \"Tool\" SET lendable = true WHERE tid = " + tid;
        performUpdate(query2);
    }
}
