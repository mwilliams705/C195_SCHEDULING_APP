package main.Controller;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Controller.Util.GeneralController;
import main.DAO.AppointmentDAO;
import main.DAO.CustomerDAO;
import main.Model.Appointment;
import main.Model.Customer;

import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

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

    private static Customer modifyCustomer;
    private static Appointment modifyAppointment;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        FilteredList<Customer> filteredCustomerList = new FilteredList<>(Objects.requireNonNull(CustomerDAO.getAllCustomers()));
        customers_table.setItems(filteredCustomerList);
        customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customer_address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customer_zipcode.setCellValueFactory(new PropertyValueFactory<>("customerZipcode"));
        customer_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customer_division.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));



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


        if (!Objects.requireNonNull(AppointmentDAO.isAppointmentInNext15Minutes()).isEmpty()){
            System.out.println("Appointment within the next 15 minutes!");
        }
    }



//    ==================================================================================================================
//    ==================Customers=======================================================================================
//    ==================================================================================================================
    public void addCustomer(ActionEvent actionEvent) {
        modifyCustomer = null;
    }

    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        SingleSelectionModel<Tab> selectionModel = mainTabPane.getSelectionModel();
        modifyCustomer = customers_table.getSelectionModel().getSelectedItem();
        System.out.println(modifyCustomer.getCustomerName());

        GeneralController.addCloseableTabWithViewAndMoveTo(mainTabPane,"Update: "+modifyCustomer.getCustomerName(), "testTabPage");

//        Tab updateCustomer = new Tab("Update Customer", new Label(modifyCustomer.getCustomerName()));
//
//        mainTabPane.getTabs().add(updateCustomer);
//        selectionModel.select(updateCustomer);
//        mainTabPane.setSelectionModel(selectionModel);
//        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    public void deleteCustomer(ActionEvent actionEvent) {
    }



//    ==================================================================================================================
//    ==================Appointments====================================================================================
//    ==================================================================================================================
    public void addAppointment(ActionEvent actionEvent) {
        modifyAppointment = null;
    }

    public void updateAppointment(ActionEvent actionEvent) {
        try {
            modifyAppointment = appt_all_table.getSelectionModel().getSelectedItem();
            System.out.println(modifyAppointment.getApptTitle());
        }catch (NullPointerException n1){
            try {
                modifyAppointment = appt_this_month_table.getSelectionModel().getSelectedItem();
                System.out.println(modifyAppointment.getApptTitle());
            }catch (NullPointerException n2){
                try {
                    modifyAppointment = appt_this_week_table.getSelectionModel().getSelectedItem();
                    System.out.println(modifyAppointment.getApptTitle());
                }catch (NullPointerException n3){
                    n3.printStackTrace();
                }
            }
        }
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

//    ==================================================================================================================
//    ==================Getters & Setters===============================================================================
//    ==================================================================================================================
    public static Customer getModifyCustomer() {
        return modifyCustomer;
    }

    public static void setModifyCustomer(Customer modifyCustomer) {
        MainController.modifyCustomer = modifyCustomer;
    }
}
