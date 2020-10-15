package gui; /**
 * @author Ryan LaRue, rml5169@rit.edu
 */

import javax.swing.plaf.nimbus.State;
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

//    public static void main(String[] args) {
//        Connection connection = openConnection(Credentials.getUrl(),
//                Credentials.getUsername(),
//                Credentials.getPassword());
//        verifyLoginCredentials(connection, "adminiiii", "password");
//    }
}
