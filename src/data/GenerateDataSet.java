package data;

import gui.Credentials;
import gui.SQLController;

public class GenerateDataSet extends SQLController{

    private static void generate() {
        for (int i = 0; i < 150; i++) {
            new CreateTool();
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
            generate();
        }

        SQLController.closeConnection();
    }
}
