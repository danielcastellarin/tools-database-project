package data;

import gui.Credentials;
import gui.SQLController;

public class GenerateDataSet extends SQLController{

    public void generate() {
        for (int i = 0; i < 100; i++) {
            new CreateTool();
        }
    }

    public static void the_purge() {
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
        GenerateDataSet g = new GenerateDataSet();
        SQLController.openConnection(Credentials.getUrl(),
                Credentials.getUsername(), Credentials.getPassword());

        boolean isPurge = false;
        if (isPurge) {
            the_purge();
        } else {
            g.generate();
        }

        SQLController.closeConnection();
    }
}
