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
                        " " + "VALUES('" + username +"', '"+ firstName + "', '" + lastName + "', '" + password + "')";
        return performUpdate(query);
    }

    public static int getBalance(int uid) {
        String query =
                "SELECT balance FROM \"User\" WHERE uid = " + uid ;
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
                        "uid = " + uid ;
        performUpdate(query);
        return balance;
    }

    public static List<String> getAllCategories() {
        String query = "SELECT tool_category FROM \"Category\"";
        performQuery(query);
        List<String> categories = new ArrayList<>();
        while(true) {
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
                        " " + "VALUES('" + category_name +"')";
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

    public static int getNextAvailableTID(){
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

    private static void insertCategoriesToHas(int tid, List<String> categories) {
        List<Integer> cids = getCategoryIDs(categories);
        for (int cid : cids) {
            String query = "INSERT INTO \"Has\" (tid, cid)" +
                    " " + "VALUES(" + tid + "," + cid +")";
            performUpdate(query);
        }
    }

    private static void insertNewToolToOwns(int uid, int tid,
                                         String datePurchased,
                                         int salePrice) {
        String query = "INSERT INTO \"Owns\" (uid, tid, date_purchased, " +
                "date_sold, sale_price)" +
                " " + "VALUES(" + uid + ", " + tid + ", '" + datePurchased +
                "', NULL, " + salePrice + ")";
        performUpdate(query);
    }

    private static void insertNewToolToTool(int tid, String toolName,
                                      boolean lendable) {
        String query = "INSERT INTO \"Tool\" (tid, tool_name, lendable, " +
                "purchasable)" +
                " " + "VALUES(" + tid + ", '" + toolName +"', " + lendable +
                ", purchasable)";
        performUpdate(query);
    }

    public static void addNewTool(int uid, String toolName,
                                  boolean lendable,
                                  String purchaseDate, int sale_price,
                                  List<String> categories) {
        int tid = getNextAvailableTID();
        insertNewToolToTool(tid, toolName, lendable);
        insertNewToolToOwns(uid, tid, purchaseDate, sale_price);
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
        for (int tid: tids) {
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
        for (int tid: tids) {
            String query = "SELECT tool_category FROM \"Category\" WHERE " +
                    "cid IN (SELECT cid FROM \"Has\" WHERE tid=" + tid + ")";
            performQuery(query);
            try {
                StringBuilder categoryString = new StringBuilder("( ");
                while (resultSet.next()) {
                    categoryString.append(resultSet.getString(1)).append(" ");
                }
                categoryString.append(")");
                categories.add(categoryString.toString());

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

    public static void insertNewBorrowRecord(int uid, int tid, String dueDate, String dateLent) {
        String query = "INSERT INTO \"Borrows\" (uid, tid, due_date, lend_date) " +
                "VALUES(" + uid + ", " + tid + ", '" + dueDate + "', '" + dateLent + "')" +
                "UPDATE \"Tool\" SET lendable = FALSE AND purchasable = FALSE WHERE tid = " + tid;
        performUpdate(query);
    }


    public static void main(String[] args) {
        openConnection(Credentials.getUrl(), Credentials.getUsername(),
                Credentials.getPassword());

        closeConnection();
    }
}
