package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Controller.Util.GeneralController;
import main.DAO.AppointmentDAO;
import main.DAO.ContactDAO;
import main.DAO.CustomerDAO;
import main.Exception.ValidationException;
import main.Model.Appointment;
import main.Model.Contact;
import main.Model.Customer;
import main.Util.TimeConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


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
    public Label contactLbl;
    public RadioButton startAM_radio;
    public RadioButton startPM_radio;
    public RadioButton endAM_radio;
    public RadioButton endPM_radio;
    public Label customerLbl;

    private final ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private final ObservableList<Contact> contactList = FXCollections.observableArrayList();

    public static Appointment appointmentToModify;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList.addAll(Objects.requireNonNull(CustomerDAO.getAllCustomers()));
        contactList.addAll(Objects.requireNonNull(ContactDAO.getAllContacts()));

        for (int i = 0; i < 12; i++) {
            appointmentTimes.add(LocalTime.of(i+1,0));
            appointmentTimes.add(LocalTime.of(i+1,15));
            appointmentTimes.add(LocalTime.of(i+1,30));
            appointmentTimes.add(LocalTime.of(i+1,45));

        }

        setAppointmentToModify(MainController.getModifyAppointment());

        contact_choicebox.setItems(contactList);
        customer_choicebox.setItems(customerList);

        start_time_combobox.setItems(appointmentTimes);
        start_time_combobox.setMaxWidth(75.0);
        end_time_combobox.setItems(appointmentTimes);
        end_time_combobox.setMaxWidth(75.0);


        if (appointmentToModify != null){
            LocalDateTime startDateTime = appointmentToModify.getApptStart().toLocalDateTime();
            LocalDateTime endDateTime = appointmentToModify.getApptEnd().toLocalDateTime();
            LocalDate startDate = startDateTime.toLocalDate();
            LocalDate endDate = endDateTime.toLocalDate();
            LocalTime startTime = startDateTime.toLocalTime();
            LocalTime endTime = endDateTime.toLocalTime();

            headerLbl.setText("Update Appointment");
            id_textfield.setText(String.valueOf(appointmentToModify.getApptId()));
            title_textfield.setText(appointmentToModify.getApptTitle());
            desc_textarea.setText(appointmentToModify.getApptDesc());
            location_textfield.setText(appointmentToModify.getApptLocation());

            contact_choicebox.setValue(getContactById(appointmentToModify.getApptContact()));
            customer_choicebox.setValue(getCustomerById(appointmentToModify.getApptCustomerId()));


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



        try {
            try{
                if (isFormComplete()){
                LocalDateTime start = LocalDateTime.of(start_datepicker.getValue(),start_time_combobox.getValue());
                LocalDateTime end = LocalDateTime.of(end_datepicker.getValue(),end_time_combobox.getValue());

                if (appointmentToModify != null){

                    if (physical_radio.isSelected()){
                        Appointment appointment = new Appointment(
                                Integer.parseInt(id_textfield.getText()),
                                title_textfield.getText(),
                                desc_textarea.getText(),
                                location_textfield.getText(),
                                contact_choicebox.getValue().getContactId(),
                                "Physical",
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(start))),
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(end))),
                                customer_choicebox.getValue().getCustomerId()

                        );
                        if (appointment.isValid()){
                            AppointmentDAO.updateAppointment(appointment);
                            System.out.println(appointment.toString());
                            GeneralController.changePageFromAppointment(actionEvent,"Main");
                        }

                    }
                    if  (bloodwork_radio.isSelected()){
                        Appointment appointment = new Appointment(
                                Integer.parseInt(id_textfield.getText()),
                                title_textfield.getText(),
                                desc_textarea.getText(),
                                location_textfield.getText(),
                                contact_choicebox.getValue().getContactId(),
                                "Bloodwork",
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(start))),
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(end))),
                                customer_choicebox.getValue().getCustomerId()

                        );
                        try{
                            appointment.isValid();
                            AppointmentDAO.updateAppointment(appointment);
                            System.out.println(appointment.toString());
                            GeneralController.changePageFromAppointment(actionEvent,"Main");
                        }catch (ValidationException v){

                        }
                    }
                }
                else {
                    if (physical_radio.isSelected()){
                        Appointment appointment = new Appointment(
                                title_textfield.getText(),
                                desc_textarea.getText(),
                                location_textfield.getText(),
                                contact_choicebox.getValue().getContactId(),
                                "Physical",
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(start))),
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(end))),
                                customer_choicebox.getValue().getCustomerId()

                        );
                        if (appointment.isValid()){
                            AppointmentDAO.addAppointment(appointment);
                            System.out.println(appointment.toString());
                            GeneralController.changePageFromAppointment(actionEvent,"Main");
                        }
                    }
                    if  (bloodwork_radio.isSelected()){
                        Appointment appointment = new Appointment(
                                title_textfield.getText(),
                                desc_textarea.getText(),
                                location_textfield.getText(),
                                contact_choicebox.getValue().getContactId(),
                                "Bloodwork",
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(start))),
                                Timestamp.valueOf(TimeConverter.localToUTC(Timestamp.valueOf(end))),
                                customer_choicebox.getValue().getCustomerId()

                        );
                        if (appointment.isValid()){
                            AppointmentDAO.addAppointment(appointment);
                            System.out.println(appointment.toString());
                            GeneralController.changePageFromAppointment(actionEvent,"Main");
                        }
                    }
                }
                }
            }catch (NullPointerException n){
                n.printStackTrace();

                Alert alert = GeneralController.alertUser(Alert.AlertType.WARNING,"Empty Form Field","There is an error in the form:",n.getMessage());
                alert.showAndWait();
                System.out.println(n.getMessage());
            }
        }catch (ValidationException validationException){
            validationException.getLocalizedMessage();
        }

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

    public boolean isFormComplete() throws NullPointerException{

        if (title_textfield.getText().equals("")){
            throw new NullPointerException("Title field cannot be empty");
        }
        if (desc_textarea.getText().equals("")){
            throw new NullPointerException("Description field cannot be empty");
        }
        if (location_textfield.getText().equals("")){
            throw new NullPointerException("Location field cannot be empty");
        }
        if (contact_choicebox.getValue().toString().equals("")){
            throw new NullPointerException("Contact choice cannot be empty");
        }
        if (start_datepicker.getValue().toString().equals("")){
            throw new NullPointerException("Start date field cannot be empty");
        }
        if (start_time_combobox.getValue().toString().equals("")){
            throw new NullPointerException("Start time field cannot be empty");
        }
        if (end_datepicker.getValue().toString().equals("")){
            throw new NullPointerException("End date field cannot be empty");
        }
        if (end_time_combobox.getValue().toString().equals("")){
            throw new NullPointerException("End time field cannot be empty");
        }
        if (customer_choicebox.getValue().toString().equals("")){
            throw new NullPointerException("Customer choice cannot be empty");
        }

        return true;
    }

    private Contact getContactById(int id){
            Contact con = null;

        for (Contact c: contactList
             ) {
            if (c.getContactId() != id){
                continue;
            }
            else {
                con = c;
                break;
            }
        }
        return con;
    }

    private Customer getCustomerById(int id){
            Customer cust = null;

        for (Customer c: customerList
             ) {
            if (c.getCustomerId() != id){
                continue;
            }
            else {
                cust = c;
            }
        }
        return cust;
    }

}
