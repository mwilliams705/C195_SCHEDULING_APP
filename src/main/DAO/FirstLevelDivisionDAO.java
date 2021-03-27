package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.FirstLevelDivision;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDAO {

    public static ObservableList<FirstLevelDivision> getAllDivisionsByCountryId(int id){
        String getStatement = "select Division from first_level_divisions where country_id = ?;";
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();
        FirstLevelDivision fldResult;


        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1,id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                fldResult = new FirstLevelDivision(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID")
                );
                fldList.add(fldResult);
            }
            return fldList;
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }
    }

    public static ObservableList<String> getAllDivisionsByCountryIdAsText(int id){
        String getStatement = "select Division from first_level_divisions where country_id = ?;";
        ObservableList<String> fldList = FXCollections.observableArrayList();



        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1,id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){

                fldList.add(rs.getString("Division"));
            }
            return fldList;
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }
    }

    public static String getCountryAsText(int id){
        String getStatement = "select c.Country,d.division from first_level_divisions d join countries c where d.COUNTRY_ID = c.Country_ID and Division_ID = ?;";
        ObservableList<String> fldList = FXCollections.observableArrayList();



        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1,id);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            return rs.getString("Country");
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }
    }

    public static Integer getDivisionId(String divisionName){
        String getStatement = "select division_id from first_level_divisions where lower(division) = ?;";




        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,divisionName.toLowerCase());
            ps.execute();
            ResultSet rs = ps.getResultSet();

            return rs.getInt("division_id");
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }
    }

}
