package main.Controller.Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * @author Michael Williams - 001221520
 *
 * This class is used to handle actions across all controllers, minimizing the amount of code to implement certain
 * repetitive actions done in each controller.
 */
public class GeneralController {

    /**
     * This static method minimizes the code needed to transition from any given view in the
     * application to another in each controller
     * @param actionEvent
     * @param pageName
     * @throws IOException
     */
    public static void changePage(ActionEvent actionEvent, String pageName) throws IOException {
        Parent root = FXMLLoader.load(GeneralController.class.getResource("/main/View/"+pageName+".fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This static method minimized the code needed to alert the user on any given view in the application.
     * @param alertType
     * @param titleText
     * @param headerText
     * @param contentText
     * @return
     */
    public static Alert alertUser(Alert.AlertType alertType,String titleText, String headerText, String contentText ){
        Alert alert = new Alert(alertType);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

}