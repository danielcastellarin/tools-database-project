package data;

import gui.Credentials;
import gui.SQLController;

public class GenerateDataSet {

    public void generate() {
        SQLController.openConnection(Credentials.getUrl(),
                Credentials.getUsername(), Credentials.getPassword());
        for (int i = 0; i < 100; i++) {
            new CreateTool();
        }
        SQLController.closeConnection();
    }

    public static void main(String[] args) {
        GenerateDataSet g = new GenerateDataSet();
        g.generate();
    }
}
