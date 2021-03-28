package main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.Model.Appointment;
import main.Model.Contact;
import main.Model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class AppointmentFormController implements Initializable {
    public Label headerLbl;
    public TextField id_textfield;
    public TextField title_textfield;
    public TextArea desc_textarea;
    public TextField location_textfield;
    public ChoiceBox<String> contact_choicebox;
    public DatePicker start_datepicker;
    public ChoiceBox start_time_combobox;
    public DatePicker end_datpicker;
    public ChoiceBox end_time_combobox;
    public ChoiceBox<Customer> customer_combobox;
    public RadioButton physical_radio;
    public RadioButton bloodwork_radio;

    public Appointment appointmentToModify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setAppointmentToModify(MainController.getModifyAppointment());

        if (appointmentToModify != null){
            headerLbl.setText("Update Appointment");
            id_textfield.setText(String.valueOf(appointmentToModify.getApptId()));
            title_textfield.setText(appointmentToModify.getApptTitle());
            desc_textarea.setText(appointmentToModify.getApptDesc());
            location_textfield.setText(appointmentToModify.getApptLocation());
//            contact_choicebox.setValue(appointmentToModify.getApptContact());

        }
        else{
            headerLbl.setText("Add Appointment");
        }

    }

    public void save(ActionEvent actionEvent) throws IOException {
        GeneralController.changePage(actionEvent,"Main");
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        GeneralController.changePage(actionEvent,"Main");
    }

    public void physical_selected(ActionEvent actionEvent) {
    }

    public void bloodwork_selected(ActionEvent actionEvent) {
    }

    public Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    public void setAppointmentToModify(Appointment appointmentToModify) {
        this.appointmentToModify = appointmentToModify;
    }
}
