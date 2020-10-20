package gui; /**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getCategories() {
        String query = "SELECT tool_category FROM \"Category\"";
        performQuery(query);
        List<String> categories = new ArrayList<>();
        while(true) {
            try {
                if (!resultSet.next()) break;
                categories.add(resultSet.getString(1));
            } catch (SQLException e) {
                e.printStackTrace();
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

    public static void main(String[] args) {
        openConnection(Credentials.getUrl(), Credentials.getUsername(),
                Credentials.getPassword());
        closeConnection();
    }
}
