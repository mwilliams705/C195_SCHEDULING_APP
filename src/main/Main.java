package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Util.DBConnector;
import main.Util.DBQuery;
import main.Util.LocaleTester;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) {

        LocaleTester.setLocaleAndTest(new Locale("fr"));

        DBConnector.startConnection();

//        String insertStatement = "INSERT INTO countries(Country,Create_Date,Created_By, Last_Updated_By)" +
//                " values(?,?,?,?)";
//        try {
//            DBQuery.setPreparedStatement(conn, insertStatement);
//            PreparedStatement ps = DBQuery.getPreparedStatement();
//
//
//            String countryName;
//            String createDate = "2021-03-24 00:00:00";
//            String createdBy = "Admin";
//            String lastUpdatedBy = "Admin";
//
//            Scanner in = new Scanner(System.in);
//            System.out.println("Country Name:");
//            countryName = in.nextLine();
//
//            ps.setString(1,countryName);
//            ps.setString(2,createDate);
//            ps.setString(3,createdBy);
//            ps.setString(4,lastUpdatedBy);
//
//            ps.execute();
//
//            if(ps.getUpdateCount()>0){
//                System.out.println(ps.getUpdateCount()+" row(s) affected.");
//            }else{
//                System.out.println("No rows affected.");
//            }
//
//
//        }catch (SQLException sqlException){
//            sqlException.getMessage();
//        }

        launch(args);
        DBConnector.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/LoginForm.fxml"));
        primaryStage.setTitle("C195 - Scheduling Service");
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
