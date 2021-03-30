package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.DAO.ContactDAO;
import main.DAO.CustomerDAO;
import main.Model.Appointment;
import main.Model.Contact;
import main.Model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;



public class AppointmentFormController implements Initializable {
    public Label headerLbl;
    public TextField id_textfield;
    public TextField title_textfield;
    public TextArea desc_textarea;
    public TextField location_textfield;
    public ChoiceBox<Contact> contact_choicebox;
    public DatePicker start_datepicker;
    public ChoiceBox<LocalTime> start_time_combobox;
    public DatePicker end_datepicker;
    public ChoiceBox<LocalTime> end_time_combobox;
    public ChoiceBox<Customer> customer_choicebox;
    public RadioButton physical_radio;
    public RadioButton bloodwork_radio;


    private final ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();



    public static Appointment appointmentToModify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i = 0; i < 12; i++) {
            appointmentTimes.add(LocalTime.of(i+1,0));
            appointmentTimes.add(LocalTime.of(i+1,15));
            appointmentTimes.add(LocalTime.of(i+1,30));
            appointmentTimes.add(LocalTime.of(i+1,45));

        }

        setAppointmentToModify(MainController.getModifyAppointment());

        LocalDateTime startDateTime = appointmentToModify.getApptStart().toLocalDateTime();
        LocalDateTime endDateTime = appointmentToModify.getApptEnd().toLocalDateTime();
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate =endDateTime.toLocalDate();
        LocalTime startTime =startDateTime.toLocalTime();
        LocalTime endTime =endDateTime.toLocalTime();

        contact_choicebox.setItems(ContactDAO.getAllContacts());
        customer_choicebox.setItems(CustomerDAO.getAllCustomers());
        start_time_combobox.setItems(appointmentTimes);
        start_time_combobox.setMaxWidth(75.0);
        end_time_combobox.setItems(appointmentTimes);
        end_time_combobox.setMaxWidth(75.0);


        if (appointmentToModify != null){
            headerLbl.setText("Update Appointment");
            id_textfield.setText(String.valueOf(appointmentToModify.getApptId()));
            title_textfield.setText(appointmentToModify.getApptTitle());
            desc_textarea.setText(appointmentToModify.getApptDesc());
            location_textfield.setText(appointmentToModify.getApptLocation());
            start_datepicker.setValue(LocalDate.of(startDate.getYear(),startDate.getMonth(),startDate.getDayOfMonth()));
            end_datepicker.setValue(LocalDate.of(endDate.getYear(),endDate.getMonth(),endDate.getDayOfMonth()));
            start_time_combobox.setValue(LocalTime.of(startTime.getHour(),startTime.getMinute()));
            end_time_combobox.setValue(LocalTime.of(endTime.getHour(),endTime.getMinute()));

        }
        else{
            headerLbl.setText("Add Appointment");
        }

    }

    public void save(ActionEvent actionEvent) throws IOException {
//        TODO: create to appointment object (convert dates to UTC)
//         and use update method on AppointmentsDAO

//        LocalDateTime start = LocalDateTime.of(start_datepicker.getValue(),start_time_combobox.getValue());
//        LocalDateTime end = LocalDateTime.of(end_datepicker.getValue(),end_time_combobox.getValue());
//
//        if (physical_radio.isSelected()){
//            Appointment appointment = new Appointment(
//                    id_textfield.getId(),
//                    desc_textarea.getText(),
//                    location_textfield.getText(),
//                    contact_choicebox.getValue().getContactId(),
//                    "Physical",
//                    start,
//                    end,
//
//
//                    );
//        }
//        if  (bloodwork_radio.isSelected()){
//            Appointment appointment = new Appointment(
//                    id_textfield.getId(),
//                    desc_textarea.getText(),
//                    location_textfield.getText(),
//                    contact_choicebox.getValue().getContactId(),
//                    "Bloodwork",
//                    );
//        }

        GeneralController.changePageFromAppointment(actionEvent,"Main");
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        GeneralController.changePage(actionEvent,"Main");
    }

    public void physical_selected(ActionEvent actionEvent) {
    }

    public void bloodwork_selected(ActionEvent actionEvent) {
    }

    public static Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    public static void setAppointmentToModify(Appointment appointmentToModify) {
        AppointmentFormController.appointmentToModify = appointmentToModify;
    }
}
