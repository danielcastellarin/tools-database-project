package data;

import gui.Credentials;
import gui.SQLController;

import java.util.HashMap;

/**
 * Class used for automatically creating or removing data from the database.
 */
public class GenerateDataSet extends SQLController {

    /**
     * Helper method to create new tools for the database. Uses the
     * randomly-generated preferences of the existing users to generate
     * unique data that best simulates activity if real people used the app.
     */
    private static void generateTools() {
        HashMap<Integer, User> users = new HashMap<>();
        for(int i = 1; i < 55; i++) {
            users.put(i, new User());
        }
        for (int i = 0; i < 150; i++) {
             new CreateTool(users);
        }
    }

    /**
     * Helper method for deleting rows from the Has, Owns, Borrows, and Tool
     * tables in our database. Primarily used when resetting our dataset.
     */
    private static void the_purge() {
        String purge = "DELETE FROM \"Has\";";
        performUpdate(purge);
        purge = "DELETE FROM \"Owns\";";
        performUpdate(purge);
        purge = "DELETE FROM \"Borrows\";";
        performUpdate(purge);
        purge = "DELETE FROM \"Tool\";";
        performUpdate(purge);
    }

    /**
     * Runnable method for when data needs to be reset or generated.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        SQLController.openConnection(Credentials.getUrl(),
                Credentials.getUsername(), Credentials.getPassword());

        boolean isPurge = false;
        if (isPurge) {
            the_purge();
        } else {
            generateTools();
        }

        SQLController.closeConnection();
    }
}
