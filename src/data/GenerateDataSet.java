package data;

import gui.Credentials;
import gui.SQLController;

import java.util.HashMap;

public class GenerateDataSet extends SQLController {

    private static HashMap<Integer, User> users;

    private static void generateTools() {
        users = new HashMap<>();
        for(int i = 1; i < 55; i++) {
            users.put(i, new User(i));
        }
        for (int i = 0; i < 150; i++) {
//            new CreateTool();
             new CreateTool(users);
        }
    }

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
