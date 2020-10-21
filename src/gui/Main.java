package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {


    private static int UID = -1;


    @Override
    public void start(Stage primaryStage) throws Exception{
        SQLController.openConnection(Credentials.getUrl(),
                Credentials.getUsername(), Credentials.getPassword());

        Parent root = FXMLLoader.load(getClass().getResource("FXML/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

    @Override
    public void stop(){
        SQLController.closeConnection();
    }

    public static void setUID(int uid) {
        UID = uid;
    }

    public static int getUID() {
        return UID;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
