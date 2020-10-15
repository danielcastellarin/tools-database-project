package gui; /**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import java.sql.*;

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

    public static boolean verifyLoginCredentials(String user, String password) {
        String query =
                "SELECT username, password FROM \"User\" WHERE username ='" + user + "' AND password='" + password + "'";
        performQuery(query);
        try {
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static boolean createNewUser(String firstName, String lastName,
                                     String username, String password) {
        String query =
                "INSERT INTO \"User\" (username, user_first_name, " +
                        "user_last_name, password)" +
                        " " + "VALUES('" + username +"', '"+ firstName + "', '" + lastName + "', '" + password + "')";
        return performUpdate(query);
    }

    public static int getBalance(String username) {
        String query =
                "SELECT username, balance FROM \"User\" WHERE username = '" + username + "'";
        Statement statement = null;
        performQuery(query);
        try {
            resultSet.next();
            return resultSet.getInt(2);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public static int incrementBalance(String username, int increment) {
        int balance = getBalance(username) + increment;
        String query =
                "UPDATE \"User\" SET balance = " + balance + " WHERE " +
                        "username = '" + username + "'";
        System.out.println("Submitting Query: " + query);
        performUpdate(query);
        return balance;
    }
}
