package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {


    private static int UID = -1;
    private static Stage stage;

    /**
     * Opens a connection to the databse and displays the login scene
     * @param primaryStage The Stage of the scene
     * @throws Exception if anything goes wrong on startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = stage;
        SQLController.openConnection(Credentials.getUrl(),
                Credentials.getUsername(), Credentials.getPassword());

        Parent root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    public static File openDialog() {
        DirectoryChooser chooser = new DirectoryChooser();
        File file = chooser.showDialog(stage);
        return file;
    }

    /**
     * Closes the database connection when the application closes
     */
    @Override
    public void stop(){
        SQLController.closeConnection();
    }

    /**
     * Sets the UID of the user for the current session
     * @param uid The integer UID of the user
     */
    public static void setUID(int uid) {
        UID = uid;
    }

    /**
     * Gets the UID of the user for the current session
     * @return The integer UID of the user
     */
    public static int getUID() {
        return UID;
    }

    /**
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
