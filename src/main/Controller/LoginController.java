package main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.io.IOException;
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

    public static String globalUsername;



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


    public void login(ActionEvent actionEvent) {
        ResourceBundle rb  = ResourceBundle.getBundle("main/Nat",Locale.getDefault());


        Connection connection = DBConnector.getConnection();
        String loginInput = "SELECT * FROM users where User_Name = ? AND Password = ?";
        try {
            DBQuery.setPreparedStatement(connection, loginInput);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            if (usernameTextfield.getText().isEmpty()){
                Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,rb.getString("loginErrorTitle"),rb.getString("usernameEmpty"), rb.getString("usernameRequired"));
                alert.showAndWait();
            }
            if (passwordTextfield.getText().isEmpty()){
                Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,rb.getString("loginErrorTitle"),rb.getString("passwordEmpty"), rb.getString("passwordRequired"));
                alert.showAndWait();
            }

            ps.setString(1, usernameTextfield.getText());
            ps.setString(2, passwordTextfield.getText());
            globalUsername = usernameTextfield.getText();

            ps.execute();

            ResultSet rs = ps.getResultSet();
            if (rs.next()){

//                Move to main application from here!!!!!!!!
                GeneralController.changePage(actionEvent ,"Main");

//                Alert success =  GeneralController.alertUser(Alert.AlertType.INFORMATION,"Success","User Found","Logged in. (This is a test and doesnt need to be in french)");
//                success.showAndWait();
            }
            else {
                Alert fail =  GeneralController.alertUser(Alert.AlertType.INFORMATION,rb.getString("loginErrorTitle"),rb.getString("loginErrorHeader"),rb.getString("loginErrorContent"));
                fail.showAndWait();
            }

        }catch (SQLException | IOException sqlException){
            System.out.println("SQL Exception:");
            System.out.println(sqlException.getMessage());
            sqlException.printStackTrace();
        }

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public static String getGlobalUsername() {
        return globalUsername;
    }

    public static void setGlobalUsername(String globalUsername) {
        LoginController.globalUsername = globalUsername;
    }
}
