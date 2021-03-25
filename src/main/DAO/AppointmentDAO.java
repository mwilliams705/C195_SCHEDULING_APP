package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Appointment;
import main.Model.Customer;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {

    public static Appointment getAppointment(int id){

        String getCustomerStatement = "SELECT * FROM appointments where Customer_ID = ?";
        Appointment appointmentResult;

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getCustomerStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            ResultSet rs = ps.getResultSet();
            appointmentResult = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getInt("Contact_ID"),
                    rs.getString("Type"),
                    rs.getString("Start"),
                    rs.getString("End"),
                    rs.getInt("Customer_ID")
            );

            return appointmentResult;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    public static ObservableList<Appointment> getAllAppointmentsThisWeek(){
        String getStatement = "select Appointment_ID,Title,Description,Location,Contact_Id,Type,Start,End,Customer_ID\n" +
                "from appointments where Start >CURDATE() and Start < CURDATE() + interval 7 day;";
        Appointment appointmentWeekResult;
        ObservableList<Appointment> allAppointmentsThisWeek = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                appointmentWeekResult = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getString("Start"),
                        rs.getString("End"),
                        rs.getInt("Customer_ID")
                );
               allAppointmentsThisWeek.add(appointmentWeekResult);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allAppointmentsThisWeek;
    }

    public static ObservableList<Appointment> getAllAppointmentsThisMonth(){
        String getStatement = "select Appointment_ID,Title,Description,Location,Contact_Id,Type,Start,End,Customer_ID\n" +
                "from appointments where Start >CURDATE() and Start < CURDATE() + interval 30 day;";
        Appointment appointmentMonthResult;
        ObservableList<Appointment> allAppointmentsThisWeek = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                appointmentMonthResult = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getString("Start"),
                        rs.getString("End"),
                        rs.getInt("Customer_ID")
                );
                allAppointmentsThisWeek.add(appointmentMonthResult);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allAppointmentsThisWeek;
    }


    public static ObservableList<Appointment> isAppointmentInNext15Minutes(){
        String getStatement = "select Appointment_ID,Title,Description,Location,Type,Start,End,Customer_ID\n" +
                "from appointments where Start >= curtime() and Start <= curtime() + interval 15 minute ;";
        Appointment appointmentWeekResult;
        ObservableList<Appointment> allAppointmentsSoon = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                appointmentWeekResult = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getString("Start"),
                        rs.getString("End"),
                        rs.getInt("Customer_ID")
                );
                allAppointmentsSoon.add(appointmentWeekResult);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allAppointmentsSoon;



    }
}
