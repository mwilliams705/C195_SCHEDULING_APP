package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Model.Report;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportTwoController implements Initializable {

    public TableView<Report> AC_Schedule_Table;
    public TableColumn<Report,String> ACcontactName;
    public TableColumn<Report,String> ACappointmentId;
    public TableColumn<Report,String> ACappointmentTitle;
    public TableColumn<Report,String> ACappointmentDesc;
    public TableColumn<Report,String> ACappointmentStart;
    public TableColumn<Report,String> ACappointmentEnd;
    public TableColumn<Report,String> ACcustomerId;

    public TableView<Report> DG_Schedule_Table;
    public TableColumn<Report,String> DGcontactName;
    public TableColumn<Report,String> DGappointmentId;
    public TableColumn<Report,String> DGappointmentTitle;
    public TableColumn<Report,String> DGappointmentDesc;
    public TableColumn<Report,String> DGappointmentStart;
    public TableColumn<Report,String> DGappointmentEnd;
    public TableColumn<Report,String> DGcustomerId;

    public TableView<Report> LL_Schedule_Table;
    public TableColumn<Report,String> LLcontactName;
    public TableColumn<Report,String> LLappointmentId;
    public TableColumn<Report,String> LLappointmentTitle;
    public TableColumn<Report,String> LLappointmentDesc;
    public TableColumn<Report,String> LLappointmentStart;
    public TableColumn<Report,String> LLappointmentEnd;
    public TableColumn<Report,String> LLcustomerId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildACScheduleReportTable();
        buildDGScheduleReportTable();
        buildLLScheduleReportTable();
    }

    public void buildACScheduleReportTable(){
        String getStatement = "select con.Contact_Name, apt.Appointment_ID , apt.Title, apt.Description,apt.Start,apt.End, Customer_ID\n" +
                "from appointments apt join contacts con on apt.Contact_ID = con.Contact_ID where apt.Contact_ID = 1 order by con.Contact_ID, apt.Start;";
        ObservableList<Report> reportResults = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs != null){
                while (rs.next()){
                    Report r = new Report(
                            rs.getString(1),
                            String.valueOf(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            String.valueOf(rs.getTimestamp(5)),
                            String.valueOf(rs.getTimestamp(6)),
                            String.valueOf(rs.getInt(7))
                    );
                    reportResults.add(r);
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }

        FilteredList<Report> reportFilteredList = new FilteredList<>(reportResults);
        AC_Schedule_Table.setItems(reportFilteredList);
        ACcontactName.setCellValueFactory(new PropertyValueFactory<>("var1"));
        ACappointmentId.setCellValueFactory(new PropertyValueFactory<>("var2"));
        ACappointmentTitle.setCellValueFactory(new PropertyValueFactory<>("var3"));
        ACappointmentDesc.setCellValueFactory(new PropertyValueFactory<>("var4"));
        ACappointmentStart.setCellValueFactory(new PropertyValueFactory<>("var5"));
        ACappointmentEnd.setCellValueFactory(new PropertyValueFactory<>("var6"));
        ACcustomerId.setCellValueFactory(new PropertyValueFactory<>("var7"));



    }
    public void buildDGScheduleReportTable(){
        String getStatement = "select con.Contact_Name, apt.Appointment_ID , apt.Title, apt.Description,apt.Start,apt.End, Customer_ID\n" +
                "from appointments apt join contacts con on apt.Contact_ID = con.Contact_ID where apt.Contact_ID = 2 order by con.Contact_ID, apt.Start;";
        ObservableList<Report> reportResults = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs != null){
                while (rs.next()){
                    Report r = new Report(
                            rs.getString(1),
                            String.valueOf(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            String.valueOf(rs.getTimestamp(5)),
                            String.valueOf(rs.getTimestamp(6)),
                            String.valueOf(rs.getInt(7))
                    );
                    reportResults.add(r);
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }

        FilteredList<Report> reportFilteredList = new FilteredList<>(reportResults);
        DG_Schedule_Table.setItems(reportFilteredList);
        DGcontactName.setCellValueFactory(new PropertyValueFactory<>("var1"));
        DGappointmentId.setCellValueFactory(new PropertyValueFactory<>("var2"));
        DGappointmentTitle.setCellValueFactory(new PropertyValueFactory<>("var3"));
        DGappointmentDesc.setCellValueFactory(new PropertyValueFactory<>("var4"));
        DGappointmentStart.setCellValueFactory(new PropertyValueFactory<>("var5"));
        DGappointmentEnd.setCellValueFactory(new PropertyValueFactory<>("var6"));
        DGcustomerId.setCellValueFactory(new PropertyValueFactory<>("var7"));



    }
    public void buildLLScheduleReportTable(){
        String getStatement = "select con.Contact_Name, apt.Appointment_ID , apt.Title, apt.Description,apt.Start,apt.End, Customer_ID\n" +
                "from appointments apt join contacts con on apt.Contact_ID = con.Contact_ID where apt.Contact_ID = 3 order by con.Contact_ID, apt.Start;";
        ObservableList<Report> reportResults = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs != null){
                while (rs.next()){
                    Report r = new Report(
                            rs.getString(1),
                            String.valueOf(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            String.valueOf(rs.getTimestamp(5)),
                            String.valueOf(rs.getTimestamp(6)),
                            String.valueOf(rs.getInt(7))
                    );
                    reportResults.add(r);
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }

        FilteredList<Report> reportFilteredList = new FilteredList<>(reportResults);
        LL_Schedule_Table.setItems(reportFilteredList);
        LLcontactName.setCellValueFactory(new PropertyValueFactory<>("var1"));
        LLappointmentId.setCellValueFactory(new PropertyValueFactory<>("var2"));
        LLappointmentTitle.setCellValueFactory(new PropertyValueFactory<>("var3"));
        LLappointmentDesc.setCellValueFactory(new PropertyValueFactory<>("var4"));
        LLappointmentStart.setCellValueFactory(new PropertyValueFactory<>("var5"));
        LLappointmentEnd.setCellValueFactory(new PropertyValueFactory<>("var6"));
        LLcustomerId.setCellValueFactory(new PropertyValueFactory<>("var7"));



    }


}
