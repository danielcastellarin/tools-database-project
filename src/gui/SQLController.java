package gui; /**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import java.sql.*;

public class SQLController {


    public static Connection openConnection(String url, String username,
                                  String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Database");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from Database");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static boolean verifyLoginCredentials(Connection connection,
                                              String user, String password) {
        String query =
                "SELECT usrnam, pwd FROM \"User\" WHERE usrnam ='" + user +
                        "' AND pwd='" + password + "'";
        System.out.println("Submitting Query: " + query);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createNewUser(Connection connection, String firstName,
                                     String lastName,
                                     String username, String password) {
        //Verify Username doesn't already exist
        String query =
                "SELECT usrnam FROM \"User\" WHERE usrnam = '" + username + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Submitting Query: " + query);
            if (resultSet.next()) {
                System.out.println("Username already in use.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Username available.");
        statement = null;
        //Create new User Entity
        query = "INSERT INTO \"User\" (usrnam, ufname, ulname, pwd) " + "VALUES('" + username +"', '"+ firstName + "', '" + lastName + "', '" + password + "')";
        System.out.println("Submitting Query: " + query);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("New User Successfully Created");
        return true;
    }

    public static int getBalance(Connection connection, String username) {
        String query =
                "SELECT usrnam, balance FROM \"User\" WHERE usrnam = '" + username + "'";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Submitting Query: " + query);
            resultSet.next();
            int balance = resultSet.getInt(2);
            System.out.println("Current balance: " + balance);
            return balance;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int incrementBalance(Connection connection,
                                        String username, int increment) {
        int balance = getBalance(connection, username) + increment;
        String query =
                "UPDATE \"User\" SET balance = " + balance + " WHERE " +
                        "usrnam = '" + username + "'";
        System.out.println("Submitting Query: " + query);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Balance Updated: " + balance);
        return balance;
    }

    public static void getCategories(Connection connection) {
        String query =
                "SELECT * FROM \"Category\"";
        System.out.println("Submitting Query: " + query);
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                System.out.println(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Connection connection = openConnection(Credentials.getUrl(),
                Credentials.getUsername(),
                Credentials.getPassword());
        getCategories(connection);
        closeConnection(connection);
    }
}
