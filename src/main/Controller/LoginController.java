package main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;

import java.net.URL;
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
    public Button exitBtn;
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


    public void login(ActionEvent actionEvent) {
        ResourceBundle rb  = ResourceBundle.getBundle("main/Nat",Locale.getDefault());
        Alert loginError = GeneralController.alertUser(Alert.AlertType.ERROR, rb.getString("loginErrorTitle"), rb.getString("loginErrorHeader"),rb.getString("loginErrorContent"));
        loginError.showAndWait();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
