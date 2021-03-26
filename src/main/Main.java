package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Util.DBConnector;

public class Main extends Application {

    public static void main(String[] args) {

//        LocaleTester.setLocaleAndTest(new Locale("fr"));

        DBConnector.startConnection();

//        Date convertedDate = TimeConverter.localToUTC();
//        Date localizedDate = TimeConverter.utcToLocal(convertedDate);
//        System.out.println(new Date());
//        System.out.println(convertedDate);
//        System.out.println(localizedDate);

//        TimeConverter.fromSchoolMaterial();


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
