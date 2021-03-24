package main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    public Label greetingLbl;
    public Label usernameLbl;
    public Label passwordLbl;
    public TextField usernameTextfield;
    public PasswordField passwordTextfield;
    public Button loginBtn;
    public Label localeLbl;
    public Label currentLocaleLbl;

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

            Alert alert = GeneralController.alertUser(Alert.AlertType.WARNING,"Error", "Locale Not Supported","Reverting locale to en_us");
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


    public void login() {
        ResourceBundle rb  = ResourceBundle.getBundle("main/Nat",Locale.getDefault());


        Connection connection = DBConnector.getConnection();
        String loginInput = "SELECT * FROM users where User_Name = ? AND Password = ?";
        try {
            DBQuery.setPreparedStatement(connection, loginInput);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            if (usernameTextfield.getText().isEmpty()){
                Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,"Error","Username field empty", "Username required");
                alert.showAndWait();
            }
            if (passwordTextfield.getText().isEmpty()){
                Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,"Error","Password field empty", "Password required");
                alert.showAndWait();
            }

            ps.setString(1, usernameTextfield.getText());
            ps.setString(2, passwordTextfield.getText());

            ps.execute();

            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                Alert success =  GeneralController.alertUser(Alert.AlertType.INFORMATION,"Success","User Found","Logged in.");
                success.showAndWait();
            }
            else {
                Alert fail =  GeneralController.alertUser(Alert.AlertType.INFORMATION,"Failure","User Not Found","Try Again!");
                fail.showAndWait();
            }

        }catch (SQLException sqlException){
            Alert loginError = GeneralController.alertUser(Alert.AlertType.ERROR, rb.getString("loginErrorTitle"), rb.getString("loginErrorHeader"),rb.getString("loginErrorContent"));
            loginError.showAndWait();
        }

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
