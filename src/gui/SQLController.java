package gui; /**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SQLController {

    private static ResultSet resultSet = null;
    private static Connection connection = null;

    public static void openConnection(String url, String username,
                                      String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

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

    private static void performQuery(String query) {
        System.out.println("Submitting Query: " + query);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean performUpdate(String query) {
        System.out.println("Submitting Query: " + query);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

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

    public static boolean createNewUser(String firstName, String lastName,
                                        String username, String password) {
        String query =
                "INSERT INTO \"User\" (username, user_first_name, " +
                        "user_last_name, password)" +
                        " " + "VALUES('" + username + "', '" + firstName + "', '" + lastName + "', '" + password + "')";
        return performUpdate(query);
    }

    public static int getBalance(int uid) {
        String query =
                "SELECT balance FROM \"User\" WHERE uid = " + uid;
        Statement statement = null;
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public static int incrementBalance(int uid, int increment) {
        int balance = getBalance(uid) + increment;
        String query =
                "UPDATE \"User\" SET balance = " + balance + " WHERE " +
                        "uid = " + uid;
        performUpdate(query);
        return balance;
    }

    public static List<String> getAllCategories() {
        String query = "SELECT tool_category FROM \"Category\"";
        performQuery(query);
        List<String> categories = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                categories.add(resultSet.getString(1));
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }
        return categories;
    }

    public static boolean insertCategory(String category_name) {
        String query =
                "INSERT INTO \"Category\" (tool_category)" +
                        " " + "VALUES('" + category_name + "')";
        return performUpdate(query);
    }

    private static List<Integer> getCategoryIDs(List<String> categories) {
        List<Integer> cids = new ArrayList<>();
        for (String categoryName : categories) {
            String query =
                    "SELECT cid FROM \"Category\" WHERE tool_category= '" + categoryName + "'";
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

    private static void deleteCategoriesFromHas(int tid) {
        String query = "DELETE FROM \"Has\" WHERE tid= " + tid;
        performUpdate(query);
    }

    private static void insertCategoriesToHas(int tid, List<String> categories) {
        List<Integer> cids = getCategoryIDs(categories);
        for (int cid : cids) {
            String query = "INSERT INTO \"Has\" (tid, cid)" +
                    " " + "VALUES(" + tid + "," + cid + ")";
            performUpdate(query);
        }
    }

    private static void insertToolToOwns(int uid, int tid,
                                         String datePurchased,
                                         int salePrice) {
        String query = "INSERT INTO \"Owns\" (uid, tid, date_purchased, " +
                "date_sold, sale_price)" +
                " " + "VALUES(" + uid + ", " + tid + ", '" + datePurchased +
                "', NULL, " + salePrice + ")";
        performUpdate(query);
    }

    private static void insertNewToolToTool(int tid, String toolName) {
        String query = "INSERT INTO \"Tool\" (tid, tool_name, lendable, " +
                "purchasable)" +
                " " + "VALUES(" + tid + ", '" + toolName + "', true, true)";
        performUpdate(query);
    }

    public static void addNewTool(int uid, String toolName,
                                  String purchaseDate, int sale_price,
                                  List<String> categories) {
        int tid = getNextAvailableTID();
        insertNewToolToTool(tid, toolName);
        insertToolToOwns(uid, tid, purchaseDate, sale_price);
        insertCategoriesToHas(tid, categories);
    }

    private static void getToolInfoFromOwns(int uid, List<Integer> salePrices,
                                            List<Integer> tids) {
        String query =
                "SELECT tid, sale_price FROM \"Owns\" WHERE uid=" + uid + " " +
                        "AND date_sold IS NULL";
        performQuery(query);
        while (true) {
            try {
                if (!resultSet.next()) break;
                salePrices.add(resultSet.getInt(2));
                tids.add(resultSet.getInt(1));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getToolInfoFromTool(List<Integer> tids,
                                            List<String> toolNames,
                                            List<Boolean> lendable,
                                            List<Boolean> purchasable) {
        for (int tid : tids) {
            String query =
                    "SELECT tool_name, lendable, purchasable FROM \"Tool\" " +
                            "WHERE tid=" + tid;
            performQuery(query);
            while (true) {
                try {
                    if (!resultSet.next()) break;
                    toolNames.add(resultSet.getString(1));
                    lendable.add(resultSet.getBoolean(2));
                    purchasable.add(resultSet.getBoolean(2));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

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

    private static void getLendableToolInfo(int uid, List<Integer> tids, List<String> toolNames) {
        String query = "SELECT t.tid, t.tool_name FROM \"Owns\" o, \"Tool\" t WHERE " +
                "o.tid = t.tid AND o.uid = " + uid + " AND t.lendable = true";
        performQuery(query);
        while (true) {
            try {
                if (!resultSet.next()) break;
                tids.add(resultSet.getInt(1));
                toolNames.add(resultSet.getString(2));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getUserTools(int uid, List<Integer> tids,
                                    List<Integer> salePrices,
                                    List<String> toolNames,
                                    List<Boolean> lendable,
                                    List<Boolean> purchasable,
                                    List<String> categories) {
        getToolInfoFromOwns(uid, salePrices, tids);
        getToolInfoFromHas(tids, categories);
        getToolInfoFromTool(tids, toolNames, lendable, purchasable);
    }

    public static void getLendableUserTools(int uid, List<Integer> tids, List<String> toolNames, Set<Integer> uids,
                                            Set<String> usernames) {
        getLendableToolInfo(uid, tids, toolNames);
        getAllOtherUsers(uid, uids, usernames);
    }

    public static void getAllOtherUsers(int uid, Set<Integer> uids,
                                        Set<String> usernames) {
        String query = "SELECT uid, username FROM \"User\" WHERE uid != " + uid;
        performQuery(query);
        while (true) {
            try {
                if (!resultSet.next()) break;
                uids.add(resultSet.getInt(1));
                usernames.add(resultSet.getString(2));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

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

    public static void updateTool(int tid, String name, int price, List<String> categories) {
        String query = "UPDATE \"Tool\" SET tool_name = '" + name + "' WHERE tid = " + tid;
        performUpdate(query);
        query = "UPDATE \"Owns\" SET sale_price = " + price + " WHERE tid = " + tid;
        performUpdate(query);
        deleteCategoriesFromHas(tid);
        insertCategoriesToHas(tid, categories);
    }

    public static void insertNewBorrowRecord(String username, int tid, String dueDate) {
        int uid = getUIDFromUsername(username);
        String query1 = "INSERT INTO \"Borrows\" (uid, tid, due_date, lend_date) " +
                "VALUES(" + uid + ", " + tid + ", '" + dueDate + "', CURRENT_DATE)";
        performUpdate(query1);
        String query2 = "UPDATE \"Tool\" SET lendable = false, purchasable = false WHERE tid = " + tid;
        performUpdate(query2);
    }

    public static void sellTool(String username, int tid) {
        String query = "INSERT INTO \"Owns\" (uid, tid, date_purchased, " +
                "date_sold, sale_price)" +
//                " " + "VALUES(" + uid + ", " + tid + ", '" + datePurchased +
//                "', NULL, " + salePrice + ")" +
                "UPDATE \"Owns\" SET date_sold = CURRENT_DATE WHERE tid = " + tid + " AND date_sold = NULL";

        int uid = getUIDFromUsername(username);
        String query2 = "UPDATE Owns SET date_sold = CURRENT_DATE WHERE tid = " + tid + " AND date_sold = NULL" +
                "INSERT INTO \"Owns\" (uid, tid, date_purchased, date_sold, sale_price)" +
                "VALUES(" + uid + ", " + tid + ", CURRENT_DATE, NULL, (" +
                "SELECT sale_price FROM \"Owns\" WHERE tid = " + tid + " AND date_sold = CURRENT_DATE ))";
        performUpdate(query2);
    }



    public static void main(String[] args) {
        openConnection(Credentials.getUrl(), Credentials.getUsername(),
                Credentials.getPassword());

        closeConnection();
    }
}
