package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Util.DBConnector;
import main.Util.LocaleTester;

import java.util.Calendar;
import java.util.Locale;

public class Main extends Application {
    public static void main(String[] args) {

//        LocaleTester.setLocaleAndTest(new Locale("fr"));
        DBConnector.startConnection();
        System.out.println(Calendar.getInstance().getTimeZone().getID());
        launch(args);
        DBConnector.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginForm.fxml"));
        primaryStage.setTitle("C195 - Scheduling Service");
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
