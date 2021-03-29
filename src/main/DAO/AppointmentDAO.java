package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Appointment;
import main.Model.Customer;
import main.Util.DBConnector;
import main.Util.DBQuery;
import main.Util.TimeConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
                    rs.getTimestamp("Start"),
                    rs.getTimestamp("End"),
                    rs.getInt("Customer_ID")
            );

            return appointmentResult;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    public static ObservableList<Appointment> getAllAppointments(){
        String getStatement = "select Appointment_ID,Title,Description,Location,Contact_Id,Type,Start,End,Customer_ID from appointments;";
        Appointment appointmentAllResult;
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                appointmentAllResult = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID")
                );

                appointmentAllResult.setApptStart(Timestamp.valueOf(TimeConverter.utcToLocal(appointmentAllResult.getApptStart().toLocalDateTime())));
                appointmentAllResult.setApptEnd(Timestamp.valueOf(TimeConverter.utcToLocal(appointmentAllResult.getApptEnd().toLocalDateTime())));

                allAppointments.add(appointmentAllResult);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allAppointments;
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
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID")
                );
                appointmentWeekResult.setApptStart(Timestamp.valueOf(TimeConverter.utcToLocal(appointmentWeekResult.getApptStart().toLocalDateTime())));
                appointmentWeekResult.setApptEnd(Timestamp.valueOf(TimeConverter.utcToLocal(appointmentWeekResult.getApptEnd().toLocalDateTime())));

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
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
                        rs.getInt("Customer_ID")
                );
                appointmentMonthResult.setApptStart(Timestamp.valueOf(TimeConverter.utcToLocal(appointmentMonthResult.getApptStart().toLocalDateTime())));
                appointmentMonthResult.setApptEnd(Timestamp.valueOf(TimeConverter.utcToLocal(appointmentMonthResult.getApptEnd().toLocalDateTime())));

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
                        rs.getTimestamp("Start"),
                        rs.getTimestamp("End"),
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

    public static void deleteAppointment(int id){
        String getCustomerStatement = "DELETE FROM appointments where appointment_id = ?";

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getCustomerStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            if (ps.getUpdateCount()>0){
                System.out.println(ps.getUpdateCount() + " row(s) deleted.");

            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("No appointment deleted.");
        }
    }
}
