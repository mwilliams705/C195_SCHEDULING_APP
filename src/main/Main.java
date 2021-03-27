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
//        TODO: Change this back to 'LoginForm.fxml' before submitting
        Parent root = FXMLLoader.load(getClass().getResource("View/Main.fxml"));
        primaryStage.setTitle("C195 - Scheduling Service");
//        TODO: Change back to login form width and height
//        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.setScene(new Scene(root,1100,600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
