package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Util.DBConnector;

/**
 * Main thread of the Application<br>
 *     <br>
 * For the Performance Assessor:<br>
 *     The lambda expressions can be found on the 'main.Controller.AppointmentFormController' class
 *     & 'main.Controller.CustomerFormController' class
 */
public class Main extends Application {

    public static void main(String[] args) {

        DBConnector.startConnection();


        launch(args);
        DBConnector.closeConnection();
    }

    /**
     * Starts the JavaFX user interface portion of the application
     * @param primaryStage Primary Stage of the application.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginForm.fxml"));
        primaryStage.setTitle("C195 - Scheduling Service");
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
