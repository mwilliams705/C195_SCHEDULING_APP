package main.Controller;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.DAO.AppointmentDAO;
import main.DAO.CustomerDAO;
import main.Model.Appointment;
import main.Model.Customer;

import java.awt.image.FilteredImageSource;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

//    Customers Table
    public TableView<Customer> customers_table;
    public TableColumn<Customer,Integer> customer_id;
    public TableColumn<Customer,String> customer_name;
    public TableColumn<Customer,String> customer_address;
    public TableColumn<Customer,String> customer_zipcode;
    public TableColumn<Customer,String> customer_phone;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        FilteredList<Customer> filteredCustomerList = new FilteredList<>(Objects.requireNonNull(CustomerDAO.getAllCustomers()));
        customers_table.setItems(filteredCustomerList);
        customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customer_address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customer_zipcode.setCellValueFactory(new PropertyValueFactory<>("customerZipcode"));
        customer_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));


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

        if (!Objects.requireNonNull(AppointmentDAO.isAppointmentInNext15Minutes()).isEmpty()){
            System.out.println("Appointment within the next 15 minutes!");
        }
    }



//    ==================================================================================================================
//    ==================Customers=======================================================================================
//    ==================================================================================================================
    public void addCustomer(ActionEvent actionEvent) {
    }

    public void updateCustomer(ActionEvent actionEvent) {
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }

//    ==================================================================================================================
//    ==================Appointments====================================================================================
//    ==================================================================================================================


    public void addAppointment(ActionEvent actionEvent) {
    }

    public void updateAppointment(ActionEvent actionEvent) {
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }
}
