package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.DAO.AppointmentDAO;
import main.DAO.UserDAO;
import main.Exception.BusinessHoursException;
import main.Model.Appointment;
import main.Model.User;
import main.Util.DBConnector;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Michael Williams - 001221520
 *
 * This class controls and handles all processes related to the 'LoginForm.fxml' page.
 */
public class LoginController implements Initializable {


    public Label greetingLbl;
    public Label usernameLbl;
    public Label passwordLbl;
    public TextField usernameTextfield;
    public PasswordField passwordTextfield;
    public Button loginBtn;
    public Label localeLbl;
    public Label currentLocaleLbl;

    public static User globalUser;

    /**
     * Initializes the Login Form and retrieves the configured ResourceBundle objects
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.getDefault());
            greetingLbl.setText(rb.getString("greetingLbl"));
            usernameLbl.setText(rb.getString("usernameLbl"));
            passwordLbl.setText(rb.getString("passwordLbl"));
            loginBtn.setText(rb.getString("loginBtn"));
            currentLocaleLbl.setText(rb.getString("currentLocaleLbl"));
            localeLbl.setText(Calendar.getInstance().getTimeZone().getID());

        }catch (MissingResourceException missingResourceException){

            Alert alert = GeneralController.alertUser(Alert.AlertType.WARNING,"Error", "Only English and French are supported.","Reverting locale to en_us");
            alert.showAndWait();

            ResourceBundle rb = ResourceBundle.getBundle("main/Nat",Locale.US);
            greetingLbl.setText(rb.getString("greetingLbl"));
            usernameLbl.setText(rb.getString("usernameLbl"));
            passwordLbl.setText(rb.getString("passwordLbl"));
            loginBtn.setText(rb.getString("loginBtn"));
            currentLocaleLbl.setText(rb.getString("currentLocaleLbl"));
            localeLbl.setText(Calendar.getInstance().getTimeZone().getID());
        }
    }

    /**
     * Checks the database for a user with the username and password given. If on is found, the user is stored in a
     * static variable so it can be used across the application.
     * @param actionEvent
     * @throws IOException
     */
    public void login(ActionEvent actionEvent) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.getDefault());

        try{

        if (usernameTextfield.getText().isEmpty()) {
            Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR, rb.getString("loginErrorTitle"), rb.getString("usernameEmpty"), rb.getString("usernameRequired"));
            alert.showAndWait();
        }
        if (passwordTextfield.getText().isEmpty()) {
            Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR, rb.getString("loginErrorTitle"), rb.getString("passwordEmpty"), rb.getString("passwordRequired"));
            alert.showAndWait();
        }
        User user = new User(usernameTextfield.getText(),passwordTextfield.getText());
        setGlobalUser(UserDAO.getUser(user));
            if (globalUser != null){
                writeLoginSuccessToFile(globalUser.getUserName());
                GeneralController.changePage(actionEvent, "Main");



                    if (isUpcomingAppointments().size()>=1){
                        for (Appointment a:
                             isUpcomingAppointments()) {
                            Alert alert = GeneralController.alertUser(Alert.AlertType.INFORMATION,"Welcome","Upcoming Appointment",a.getApptId()+" | "+ a.getApptStart());
                            alert.showAndWait();
                        }
                    }else {
                        Alert alert = GeneralController.alertUser(Alert.AlertType.INFORMATION,"Welcome","Upcoming Appointment","There are no upcoming appointments");
                        alert.showAndWait();
                    }



            } else {
                    writeLoginFailureToFile(usernameTextfield.getText());
                    Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,rb.getString("loginErrorTitle"),rb.getString("loginErrorHeader"),rb.getString("loginErrorContent"));
                    alert.showAndWait();
            }

    }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }

    }


    /**
     * This method searches all appointments and adds ones that fit the 15 minute criteria are added to a list and returned
     * @return List of upcoming appointments
     */
    public ObservableList<Appointment> isUpcomingAppointments() {

        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        if (allAppointments!= null){

            for (Appointment a : allAppointments){
                LocalDateTime start = a.getApptStart().toLocalDateTime();
                LocalDateTime now = Timestamp.from(Instant.now()).toLocalDateTime();

                if (start.isBefore(now.plusMinutes(15))) {
                    if (!start.isAfter(now)){
                        upcomingAppointments.add(a);
                    }
                }



            }

        }
        return upcomingAppointments;

    }

    /**
     * Closes the database and the application.
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        DBConnector.closeConnection();
        System.exit(0);
    }

    /**
     * Getter for the globalUser variable
     * @return
     */
    public static User getGlobalUser() {
        return globalUser;
    }

    /**
     * Setter for the globalUser variable
     * @param user
     */
    public static void setGlobalUser(User user) {
        LoginController.globalUser = user;
    }

    /**
     * Writes a successful login to the 'login_activity.txt' file
     * @param username Username data from username_textfield
     * @throws IOException
     */
    public void writeLoginSuccessToFile(String username) throws IOException{
        String logString = "Login Attempt [User: "+ username +" | Date: "+ LocalDate.now() +" | Timestamp: "+ Timestamp.valueOf(LocalDateTime.now()) +" | Login Success]";

        FileWriter fw = new FileWriter("login_activity.txt",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(logString);
        pw.close();

    }

    /**
     * Writes an unsuccessful login to the 'login_activity.txt' file
     * @param username Username data from username_textfield
     * @throws IOException
     */
    public void writeLoginFailureToFile(String username) throws IOException{
        String logString = "Login Attempt [User: "+ username +" | Date: "+ LocalDate.now() +" | Timestamp: "+ Timestamp.valueOf(LocalDateTime.now()) +" | Login Failed]";

        FileWriter fw = new FileWriter("login_activity.txt",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(logString);
        pw.close();

    }
}
