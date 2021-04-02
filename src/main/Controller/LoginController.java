package main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.DAO.UserDAO;
import main.Model.User;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;

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
            } else {
                    writeLoginFailureToFile(usernameTextfield.getText());
                    Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,rb.getString("loginErrorTitle"),rb.getString("loginErrorHeader"),rb.getString("loginErrorContent"));
                    alert.showAndWait();
            }

    }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }

    }

    public void exit(ActionEvent actionEvent) {
        DBConnector.closeConnection();
        System.exit(0);
    }

    public static User getGlobalUser() {
        return globalUser;
    }

    public static void setGlobalUser(User user) {
        LoginController.globalUser = user;
    }

    public void writeLoginSuccessToFile(String username) throws IOException{
        String logString = "Login Attempt [User: "+ username +" | Date: "+ LocalDate.now() +" | Timestamp: "+ Timestamp.valueOf(LocalDateTime.now()) +" | Login Success]";

        FileWriter fw = new FileWriter("login_activity.txt",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(logString);
        pw.close();

    }

    public void writeLoginFailureToFile(String username) throws IOException{
        String logString = "Login Attempt [User: "+ username +" | Date: "+ LocalDate.now() +" | Timestamp: "+ Timestamp.valueOf(LocalDateTime.now()) +" | Login Failed]";

        FileWriter fw = new FileWriter("login_activity.txt",true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(logString);
        pw.close();

    }
}
