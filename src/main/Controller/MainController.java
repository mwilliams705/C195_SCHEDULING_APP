package main.Controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Controller.Util.GeneralController;
import main.DAO.AppointmentDAO;
import main.DAO.ContactDAO;
import main.DAO.CustomerDAO;
import main.Model.Appointment;
import main.Model.Contact;
import main.Model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Michael Williams - 001221520
 *
 * This class controls and handles all processes related to the 'Main.fxml' page.
 */
public class MainController implements Initializable {

    public TabPane mainTabPane;


    //    Customers Table
    public TableView<Customer> customers_table;
    public TableColumn<Customer,Integer> customer_id;
    public TableColumn<Customer,String> customer_name;
    public TableColumn<Customer,String> customer_address;
    public TableColumn<Customer,String> customer_zipcode;
    public TableColumn<Customer,String> customer_phone;
    public TableColumn<Customer,String> customer_division;

//    Appointments This Week Table
    public TableView<Appointment> appt_this_week_table;
    public TableColumn<Appointment,Integer> week_appt_id;
    public TableColumn<Appointment,String> week_title;
    public TableColumn<Appointment,String> week_desc;
    public TableColumn<Appointment,String> week_location;
    public TableColumn<Appointment,Integer> week_contact;
    public TableColumn<Appointment,String> week_type;
    public TableColumn<Appointment,String> week_start;
    public TableColumn<Appointment,String> week_end;
    public TableColumn<Appointment,Integer> week_customer_id;

//    Appointments This Month Table
    public TableView<Appointment> appt_this_month_table;
    public TableColumn<Appointment,Integer> month_appt_id;
    public TableColumn<Appointment,String> month_title;
    public TableColumn<Appointment,String> month_desc;
    public TableColumn<Appointment,String> month_location;
    public TableColumn<Appointment,Integer> month_contact;
    public TableColumn<Appointment,String> month_type;
    public TableColumn<Appointment,String> month_start;
    public TableColumn<Appointment,String> month_end;
    public TableColumn<Appointment,Integer> month_customer_id;

    public TableView<Appointment> appt_all_table;
    public TableColumn<Appointment,Integer> all_appt_id;
    public TableColumn<Appointment,String> all_title;
    public TableColumn<Appointment,String> all_desc;
    public TableColumn<Appointment,String> all_location;
    public TableColumn<Appointment,Integer> all_contact;
    public TableColumn<Appointment,String> all_type;
    public TableColumn<Appointment,String> all_start;
    public TableColumn<Appointment,String> all_end;
    public TableColumn<Appointment,Integer> all_customer_id;


    public Label currentUserLbl;

    public TableView<Contact> contacts_table;
    public TableColumn<Contact,Integer> contact_id;
    public TableColumn<Contact,String> contact_name;
    public TableColumn<Contact,String> contact_email;

    private static Customer modifyCustomer;
    private static Appointment modifyAppointment;


    /**
     * This method initializes the Main view and sets the Tab closing policy for the 4 main tabs.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentUserLbl.setText(LoginController.getGlobalUser().getUserName());
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        buildTables();

    }

    /**
     * This method builds all the tables found in the Main view of the application
     */
    public void buildTables(){
        FilteredList<Customer> filteredCustomerList = new FilteredList<>(Objects.requireNonNull(CustomerDAO.getAllCustomersWithDivisionAndCountries()));
        customers_table.setItems(filteredCustomerList);
        customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customer_address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customer_zipcode.setCellValueFactory(new PropertyValueFactory<>("customerZipcode"));
        customer_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customer_division.setCellValueFactory(new PropertyValueFactory<>("customerDivisionText"));


        FilteredList<Appointment> filteredWeeklyAppointmentsList = new FilteredList<>(Objects.requireNonNull(AppointmentDAO.getAllAppointmentsThisWeek()));
        appt_this_week_table.setItems(filteredWeeklyAppointmentsList);
        week_appt_id.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        week_title.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        week_desc.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        week_location.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        week_contact.setCellValueFactory(new PropertyValueFactory<>("apptContact"));
        week_type.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        week_start.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        week_end.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        week_customer_id.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));


        FilteredList<Appointment> filteredMonthlyAppointmentList = new FilteredList<>(Objects.requireNonNull(AppointmentDAO.getAllAppointmentsThisMonth()));
        appt_this_month_table.setItems(filteredMonthlyAppointmentList);
        month_appt_id.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        month_title.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        month_desc.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        month_location.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        month_contact.setCellValueFactory(new PropertyValueFactory<>("apptContact"));
        month_type.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        month_start.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        month_end.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        month_customer_id.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));


        FilteredList<Appointment> filteredAllAppointmentList = new FilteredList<>(Objects.requireNonNull(AppointmentDAO.getAllAppointments()));
        appt_all_table.setItems(filteredAllAppointmentList);
        all_appt_id.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        all_title.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        all_desc.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
        all_location.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        all_contact.setCellValueFactory(new PropertyValueFactory<>("apptContact"));
        all_type.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        all_start.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        all_end.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        all_customer_id.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));


        FilteredList<Contact> filteredContactList = new FilteredList<>(Objects.requireNonNull(ContactDAO.getAllContacts()));
        contacts_table.setItems(filteredContactList);
        contact_id.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contact_name.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contact_email.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
    }


//    ==================================================================================================================
//    ==================Customers=======================================================================================
//    ==================================================================================================================

    /**
     * This method moves the user to the Customer Form and clears the modifyCustomer variable as to not transfer any data
     * over the the form
     * @param actionEvent
     * @throws IOException
     */
    public void addCustomer(ActionEvent actionEvent) throws IOException {
        modifyCustomer = null;
        GeneralController.addCloseableTabWithCustomerFormViewAndMoveTo(mainTabPane,"Add Customer", "CustomerForm");

    }

    /**
     * This method moves the user to the Customer Form and stores a selected customer in the modifyCustomer variable so to
     * transfer data over the the form.
     * @param actionEvent
     * @throws IOException
     */
    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        modifyCustomer = customers_table.getSelectionModel().getSelectedItem();
        GeneralController.addCloseableTabWithCustomerFormViewAndMoveTo(mainTabPane,modifyCustomer.getCustomerId()+" | "+modifyCustomer.getCustomerName(), "CustomerForm");

    }

    /**
     * This method takes a selected customer from the table and removes them after confirming via alert.
     * If there is any appointments associated to the customer, an alert is shown. If confirmed, the
     * appointments associated to the customer are deleted.
     * @param actionEvent
     * @throws IOException
     */
    public void deleteCustomer(ActionEvent actionEvent) throws IOException {
        modifyCustomer = customers_table.getSelectionModel().getSelectedItem();

        Alert confirmDelete = GeneralController.alertUser(Alert.AlertType.CONFIRMATION,"Delete Customer","Continue Deleting Customer?", modifyCustomer.getCustomerId()+" | "+modifyCustomer.getCustomerName());
        Optional<ButtonType> confirm = confirmDelete.showAndWait();
        if (confirm.isPresent()&&confirm.get()==ButtonType.OK){

            if (AppointmentDAO.customerHasAppointments(modifyCustomer.getCustomerId())){

                Alert confirmDeleteAppointments = GeneralController.alertUser(Alert.AlertType.CONFIRMATION,"Delete Customer","Customer Has Appointments", "Would you like to delete the associated appointments?");
                Optional<ButtonType> confirmAppts = confirmDeleteAppointments.showAndWait();

                if (confirmAppts.isPresent()&&confirmAppts.get()==ButtonType.OK){
                    AppointmentDAO.deleteAllAppointmentsByCustomerID(modifyCustomer.getCustomerId());
                    CustomerDAO.deleteCustomer(modifyCustomer.getCustomerId());

                    customers_table.setItems(CustomerDAO.getAllCustomersWithDivisionAndCountries());
                    appt_all_table.setItems(AppointmentDAO.getAllAppointments());
                    appt_this_week_table.setItems(AppointmentDAO.getAllAppointmentsThisWeek());
                    appt_this_month_table.setItems(AppointmentDAO.getAllAppointmentsThisMonth());

                }


            }

        }

    }
//    ==================================================================================================================
//    ==================Appointments====================================================================================
//    ==================================================================================================================

    /**
     * This method moves the user to the Appointment form and clears the modifyAppintment variable as to not move any
     * stored information to the form.
     * @param actionEvent
     * @throws IOException
     */
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        modifyAppointment = null;
        GeneralController.addCloseableTabWithAppointmentFormViewAndMoveTo(mainTabPane,"New Appointment","AppointmentForm");
    }

    /**
     * This method moves the user to the Appointment form and stores a selected Appointment from the table views so to
     * store the appointment data in the Appointment form for editing.
     * @param actionEvent
     * @throws IOException
     */
    public void updateAppointment(ActionEvent actionEvent)throws IOException {
        try {
            modifyAppointment = appt_all_table.getSelectionModel().getSelectedItem();
            GeneralController.addCloseableTabWithAppointmentFormViewAndMoveTo(mainTabPane,"Appointment for " +modifyAppointment.getApptCustomerId(),"AppointmentForm");
        }catch (NullPointerException n1){
            try {
                modifyAppointment = appt_all_table.getSelectionModel().getSelectedItem();
                modifyAppointment = appt_this_month_table.getSelectionModel().getSelectedItem();
                System.out.println(modifyAppointment.getApptTitle());
            }catch (NullPointerException n2){
                try {
                    modifyAppointment = appt_all_table.getSelectionModel().getSelectedItem();
                    modifyAppointment = appt_this_week_table.getSelectionModel().getSelectedItem();
                    System.out.println(modifyAppointment.getApptTitle());
                }catch (NullPointerException n3){
                    n3.printStackTrace();
                }
            }
        }
    }

    /**
     * This method deletes an Appointment from the database after the user confirms via alert.
     * @param actionEvent
     * @throws IOException
     */
    public void deleteAppointment(ActionEvent actionEvent) throws IOException {

        try {
            modifyAppointment = appt_all_table.getSelectionModel().getSelectedItem();
            System.out.println(modifyAppointment.getApptTitle());

            Alert confirmDelete = GeneralController.alertUser(Alert.AlertType.CONFIRMATION,"Delete Appointment","Continue Deleting Appointment?:", modifyAppointment.getApptTitle()+" | "+modifyAppointment.getApptStart()+" - "+modifyAppointment.getApptEnd());
            Optional<ButtonType> confirm = confirmDelete.showAndWait();
            if (confirm.isPresent()&&confirm.get()==ButtonType.OK){
                AppointmentDAO.deleteAppointment(modifyAppointment.getApptId());
                appt_all_table.setItems(AppointmentDAO.getAllAppointments());

                Alert onDelete = GeneralController.alertUser(Alert.AlertType.INFORMATION,"Appointment Deleted", "Deletion Confirmed", modifyAppointment.getApptId() +" | "+modifyAppointment.getApptType());
                onDelete.showAndWait();
            }
        }catch (NullPointerException n1){
            try {
                modifyAppointment = appt_this_month_table.getSelectionModel().getSelectedItem();
                System.out.println(modifyAppointment.getApptTitle());

                Alert confirmDelete = GeneralController.alertUser(Alert.AlertType.CONFIRMATION,"Delete Appointment","Continue Deleting Appointment?:", modifyAppointment.getApptTitle()+" | "+modifyAppointment.getApptStart()+" - "+modifyAppointment.getApptEnd());
                Optional<ButtonType> confirm = confirmDelete.showAndWait();
                if (confirm.isPresent()&&confirm.get()==ButtonType.OK){
                    AppointmentDAO.deleteAppointment(modifyAppointment.getApptId());
                    appt_this_month_table.setItems(AppointmentDAO.getAllAppointmentsThisMonth());

                    Alert onDelete = GeneralController.alertUser(Alert.AlertType.INFORMATION,"Appointment Deleted", "Deletion Confirmed", modifyAppointment.getApptId() +" | "+modifyAppointment.getApptType());
                    onDelete.showAndWait();
                }
            }catch (NullPointerException n2){
                try {
                    modifyAppointment = appt_this_week_table.getSelectionModel().getSelectedItem();
                    System.out.println(modifyAppointment.getApptTitle());

                    Alert confirmDelete = GeneralController.alertUser(Alert.AlertType.CONFIRMATION,"Delete Appointment","Continue Deleting Appointment?:", modifyAppointment.getApptTitle()+" | "+modifyAppointment.getApptStart()+" - "+modifyAppointment.getApptEnd());
                    Optional<ButtonType> confirm = confirmDelete.showAndWait();
                    if (confirm.isPresent()&&confirm.get()==ButtonType.OK){
                        AppointmentDAO.deleteAppointment(modifyAppointment.getApptId());
                        appt_this_week_table.setItems(AppointmentDAO.getAllAppointmentsThisWeek());

                        Alert onDelete = GeneralController.alertUser(Alert.AlertType.INFORMATION,"Appointment Deleted", "Deletion Confirmed", modifyAppointment.getApptId() +" | "+modifyAppointment.getApptType());
                        onDelete.showAndWait();
                    }
                }catch (NullPointerException n3){
                    n3.printStackTrace();
                }
            }
        }



    }

//    ==================================================================================================================
//    ==================Getters & Setters===============================================================================
//    ==================================================================================================================

    /**
     * Getter for modifyCustomer variable.
     * @return
     */
    public static Customer getModifyCustomer() {
        return modifyCustomer;
    }

    /**
     * setter for modifyCustomer variable.
     * @param modifyCustomer
     */
    public static void setModifyCustomer(Customer modifyCustomer) {
        MainController.modifyCustomer = modifyCustomer;
    }

    /**
     * Getter for modifyAppointment variable.
     * @return
     */
    public static Appointment getModifyAppointment() {
        return modifyAppointment;
    }

    /**
     * Setter for modifyAppointment variable.
     * @param modifyAppointment
     */
    public static void setModifyAppointment(Appointment modifyAppointment) {
        MainController.modifyAppointment = modifyAppointment;
    }

    /**
     * Button action to open a new tab in the Main view tab pane with the 'Appointments by Type' report.
     * @param actionEvent
     * @throws IOException
     */
    public void openApptsByTypeReport(ActionEvent actionEvent) throws IOException {
        GeneralController.addCloseableTabWithReportFormViewAndMoveTo(mainTabPane,"Appointments By Type","ReportOne");
    }

    /**
     * Button action to open a new tab in the Main view tab pane with the 'Contact Schedule' report.
     * @param actionEvent
     * @throws IOException
     */
    public void openContactScheduleReport(ActionEvent actionEvent) throws IOException {
        GeneralController.addCloseableTabWithReportFormViewAndMoveTo(mainTabPane,"Contact Schedules","ReportTwo");
    }

    /**
     * Button action to open a new tab in the Main view tab pane with the 'Total Appointments by Type' report.
     * @param actionEvent
     * @throws IOException
     */
    public void OpenTotalApptsByType(ActionEvent actionEvent) throws IOException {
        GeneralController.addCloseableTabWithReportFormViewAndMoveTo(mainTabPane,"Total Appointments By Type","ReportThree");
    }
}
