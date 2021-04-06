package main.Controller.Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Controller.MainController;
import main.DAO.CustomerDAO;
import main.Model.Customer;
import main.Util.DBConnector;


import java.io.IOException;

/**
 * @author Michael Williams - 001221520
 *<br>
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
        DBConnector.startConnection();
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

    /**
     * This method creates a tab in the MainTabPane which moves the user back to the customer tab on closing the created tab
     * @param tabPane
     * @param tabName
     * @param pageName
     * @throws IOException
     */
    public static void addCloseableTabWithCustomerFormViewAndMoveTo(TabPane tabPane, String tabName, String pageName) throws IOException {
        DBConnector.startConnection();
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        Parent root = FXMLLoader.load(GeneralController.class.getResource("/main/View/"+pageName+".fxml"));
        Tab tab = new Tab(tabName,root);
        tab.setTooltip(new Tooltip(tabName));

        tabPane.getTabs().add(tab);
        selectionModel.select(tab);
        tabPane.setSelectionModel(selectionModel);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tab.setOnClosed(e -> tabPane.getSelectionModel().select(tabPane.getTabs().get(0)));

    }

    /**
     *This method creates a tab in the MainTabPane which moves the user back to the appointment tab on closing the created tab
     * @param tabPane
     * @param tabName
     * @param pageName
     * @throws IOException
     */
    public static void addCloseableTabWithAppointmentFormViewAndMoveTo(TabPane tabPane,String tabName, String pageName) throws IOException {
        DBConnector.startConnection();
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        Parent root = FXMLLoader.load(GeneralController.class.getResource("/main/View/"+pageName+".fxml"));
        Tab tab = new Tab(tabName,root);
        tab.setTooltip(new Tooltip(tabName));

        tabPane.getTabs().add(tab);
        selectionModel.select(tab);
        tabPane.setSelectionModel(selectionModel);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tab.setOnClosed(e -> tabPane.getSelectionModel().select(tabPane.getTabs().get(1)));

    }

    /**
     * This method creates a tab in the MainTabPane which moves the user back to the report tab on closing the created tab
     * @param tabPane
     * @param tabName
     * @param pageName
     * @throws IOException
     */
    public static void addCloseableTabWithReportFormViewAndMoveTo(TabPane tabPane,String tabName, String pageName) throws IOException {
        DBConnector.startConnection();
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        Parent root = FXMLLoader.load(GeneralController.class.getResource("/main/View/"+pageName+".fxml"));
        Tab tab = new Tab(tabName,root);
        tab.setTooltip(new Tooltip(tabName));

        tabPane.getTabs().add(tab);
        selectionModel.select(tab);
        tabPane.setSelectionModel(selectionModel);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tab.setOnClosed(e -> tabPane.getSelectionModel().select(tabPane.getTabs().get(3)));

    }
}