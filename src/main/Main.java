package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Util.LocaleTester;

import java.util.Calendar;
import java.util.Locale;

public class Main extends Application {
    public static void main(String[] args) {

        LocaleTester.setLocaleAndTest(new Locale("fr"));


        System.out.println(Calendar.getInstance().getTimeZone().getID());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginForm.fxml"));
        primaryStage.setTitle("C195 - Scheduling Service");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
