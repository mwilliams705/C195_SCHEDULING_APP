package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Country;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    public static ObservableList<Country> getAllCountries(){
        String getStatement = "select Country_ID,Country from countries;";
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        Country countryResult;


        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                countryResult = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country")
                );
                countryList.add(countryResult);
            }
            return countryList;
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }

    }

    public static ObservableList<String> getAllCountriesAsText(){
        String getStatement = "select Country_ID,Country from countries;";
        ObservableList<String> countryList = FXCollections.observableArrayList();


        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                countryList.add(rs.getString("Country"));
            }
            return countryList;
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }

    }

    public static String getCountryFromCountry_ID(int id){
        String getStatement = "select Country from countries where Country_ID = ?;";



        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getString("Country");
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }

    }


    public static Integer getCountry_IDfromCountry(String country){
        String getStatement = "select Country_ID from countries where lower(Country) = ?;";

        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,country);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            return rs.getInt("Country_ID");
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }
    }
}
