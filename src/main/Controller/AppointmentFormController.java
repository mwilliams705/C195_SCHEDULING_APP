package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import main.Controller.Util.GeneralController;
import main.DAO.AppointmentDAO;
import main.DAO.ContactDAO;
import main.DAO.CustomerDAO;
import main.Exception.BusinessHoursException;
import main.Exception.ValidationException;
import main.Model.Appointment;
import main.Model.Contact;
import main.Model.Customer;
import main.Util.DBConnector;
import main.Util.DBQuery;
import main.Util.TimeConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Michael Williams - 001221520
 *<br>
 * This class controls and handles all processes related to the 'AppointmentForm.fxml' page.
 *<br><br>
 * LAMBDA EXPRESSIONS in this controller simplify creating and updating appointments without having to write repetitive
 * code to create the new object for any given scenario in the process. This makes it possible to have the objects available
 * and predetermined, only leaving the Appointment Type, Start time and End Time to be handled during object creation.
 */
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

    public Label customerLbl;

    private final ObservableList<LocalTime> appointmentTimes = FXCollections.observableArrayList();
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private final ObservableList<Contact> contactList = FXCollections.observableArrayList();

    AppointmentInterface updateAppointment = (type,start, end) -> new Appointment(
            Integer.parseInt(id_textfield.getText()),
            title_textfield.getText(),
            desc_textarea.getText(),
            location_textfield.getText(),
            contact_choicebox.getValue().getContactId(),
            type,
            Timestamp.valueOf(start),
            Timestamp.valueOf(end),
            customer_choicebox.getValue().getCustomerId()

    );
    AppointmentInterface addAppointment = ((type,start, end) -> new Appointment(
            title_textfield.getText(),
            desc_textarea.getText(),
            location_textfield.getText(),
            contact_choicebox.getValue().getContactId(),
            type,
            Timestamp.valueOf(start),
            Timestamp.valueOf(end),
            customer_choicebox.getValue().getCustomerId()

    ));



    public static Appointment appointmentToModify;

    /**
     * Initializes the login form.<br>
     *     The form will switch between either English or French based on the locale of the system. <br>
     *     If the locale of the operating system falls outside of the defined locales, the application will
     *     default to English.

     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList.addAll(Objects.requireNonNull(CustomerDAO.getAllCustomers()));
        contactList.addAll(Objects.requireNonNull(ContactDAO.getAllContacts()));

        for (int i = 0; i < 24; i++) {
            appointmentTimes.add(LocalTime.of(i,0));
            appointmentTimes.add(LocalTime.of(i,15));
            appointmentTimes.add(LocalTime.of(i,30));
            appointmentTimes.add(LocalTime.of(i,45));

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

    /**
     * Checks that all form fields are filled, valid and no appointments fall outside of business hours or overlap.
     * If all criteria are met, the appointment is stored in the database and the user is taken back to the Main view.
     * <br><br>
     *     Appointment objects created or updated use the LAMBDA EXPRESSIONS defined above to create the Appointment
     *     objects, minimizing code and making it easier to read and use.
     */
    public void save(ActionEvent actionEvent) throws IOException {

        try {
            try{
                if (isFormComplete()){

                LocalDateTime start = LocalDateTime.of(start_datepicker.getValue(),start_time_combobox.getValue());
                LocalDateTime end = LocalDateTime.of(end_datepicker.getValue(),end_time_combobox.getValue());

                    if (appointmentToModify != null) {

                        if (physical_radio.isSelected()) {

//                                Uses LAMBDA EXPRESSION
                            try {
                                Appointment appointment = updateAppointment.newAppointment("Physical",start,end);


                                if (appointment.isValid() && appointment.isValidTime()) {
                                    if (isUpdatedAppointmentOverlapping(appointment.getApptStart().toLocalDateTime().plusSeconds(1),appointment.getApptEnd().toLocalDateTime().minusSeconds(1),appointment.getApptId())){
                                        AppointmentDAO.updateAppointment(appointment);
                                        GeneralController.changePage(actionEvent, "Main");
                                    }
                                }
                            }catch (BusinessHoursException b){
                                Alert a = GeneralController.alertUser(Alert.AlertType.ERROR,"Error","Overlapping appointments",b.getMessage());
                                a.showAndWait();
                            }
                        }
                        if (bloodwork_radio.isSelected()) {

//                                Uses LAMBDA EXPRESSION
                            try {
                                Appointment appointment = updateAppointment.newAppointment("Bloodwork",start,end);


                                if (appointment.isValid() && appointment.isValidTime()) {
                                    if (isUpdatedAppointmentOverlapping(appointment.getApptStart().toLocalDateTime().plusSeconds(1),appointment.getApptEnd().toLocalDateTime().minusSeconds(1),appointment.getApptId())){
                                        AppointmentDAO.updateAppointment(appointment);
                                        GeneralController.changePage(actionEvent, "Main");
                                    }
                                }
                            }catch (BusinessHoursException b){
                                Alert a = GeneralController.alertUser(Alert.AlertType.ERROR,"Error","Overlapping appointments",b.getMessage());
                                a.showAndWait();
                            }
                        }
                    } else {

                        if (physical_radio.isSelected()) {

//                                Uses LAMBDA EXPRESSION
                            try {
                                Appointment appointment = addAppointment.newAppointment("Physical",start,end);


                                if (appointment.isValid() && appointment.isValidTime()) {
                                    if (isAppointmentOverlapping(appointment.getApptStart().toLocalDateTime().plusSeconds(1),appointment.getApptEnd().toLocalDateTime().minusSeconds(1))){
                                        AppointmentDAO.addAppointment(appointment);
                                        GeneralController.changePage(actionEvent, "Main");
                                    }
                                }
                            }catch (BusinessHoursException b){
                                Alert a = GeneralController.alertUser(Alert.AlertType.ERROR,"Error","Overlapping appointments",b.getMessage());
                                a.showAndWait();
                            }
                        }
                        if (bloodwork_radio.isSelected()) {

//                                Uses LAMBDA EXPRESSION
                            try {
                                Appointment appointment = addAppointment.newAppointment("Bloodwork",start,end);


                                if (appointment.isValid() && appointment.isValidTime()) {
                                    if (isAppointmentOverlapping(appointment.getApptStart().toLocalDateTime().plusSeconds(1),appointment.getApptEnd().toLocalDateTime().minusSeconds(1))){
                                        AppointmentDAO.addAppointment(appointment);
                                        GeneralController.changePage(actionEvent, "Main");
                                    }
                                }
                            }catch (BusinessHoursException b){
                                Alert a = GeneralController.alertUser(Alert.AlertType.ERROR,"Error","Overlapping appointments",b.getMessage());
                                a.showAndWait();
                            }
                        }
                    }
                }
            }catch (NullPointerException n){
                n.printStackTrace();

                Alert alert = GeneralController.alertUser(Alert.AlertType.WARNING,"Empty Form Field","There is an error in the form:",n.getMessage());
                alert.showAndWait();
            }
        }catch (ValidationException validationException){
            Alert alert = GeneralController.alertUser(Alert.AlertType.WARNING,"Validation Exception","There is an error in the form:",validationException.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * This method cancels the current add/update form and moves the user to the Main view.
     *
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        GeneralController.changePage(actionEvent,"Main");
    }


    /**
     * Getter for appointmentToModify variable.
     *
     */
    public static Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    /**
     * Setter for appointmentToModify variable.
     *
     */
    public static void setAppointmentToModify(Appointment appointmentToModify) {
        AppointmentFormController.appointmentToModify = appointmentToModify;
    }

    /**
     * Validates that form fields are not left empty.
     *
     */
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
        if (start_datepicker.getValue()==null){
            throw new NullPointerException("Start date field cannot be empty");
        }
        if (start_time_combobox.getValue().toString().equals("")){
            throw new NullPointerException("Start time field cannot be empty");
        }
        if (end_datepicker.getValue()==null){
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

    /**
     * Validates that the appointment being updated is not at the same time as another appointment in the database.
     * @param start Start time
     * @param end End Time
     * @return true if appointment doesn't not overlap another appointment.
     * @throws BusinessHoursException
     */
    public boolean isAppointmentOverlapping(LocalDateTime start,LocalDateTime end) throws BusinessHoursException {

        if (AppointmentDAO.isOverlapping(start,end).size()>=1){
            throw new BusinessHoursException("An appointment cannot be scheduled at the same time as another appointment.");
        }
        return true;
    }

    /**
     * Validates that the appointment being updated is not at the same time as another appointment in the database.
     * @param start Start time
     * @param end End Time
     * @return true if appointment doesn't not overlap another appointment.
     * @throws BusinessHoursException
     */
    public boolean isUpdatedAppointmentOverlapping(LocalDateTime start,LocalDateTime end, int id) throws BusinessHoursException {

        if (AppointmentDAO.isOverlapping(start,end).size()>=1){
            if (AppointmentDAO.isOverlapping(start,end).get(0).getApptId() != id)
                throw new BusinessHoursException("An appointment cannot be scheduled at the same time as another appointment.");
        }
        return true;
    }

    /**
     * Filters through the contactList ObservableList and returns the contact with the provided Contact_ID.
     * @param id ID of requested contact.
     * @return Contact with provided ID.
     */
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

    /**
     * Filters through the customerList ObservableList and returns the customer with the provided Customer_ID.
     * @param id ID of requested customer.
     * @return Customer with provided ID.
     */
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
