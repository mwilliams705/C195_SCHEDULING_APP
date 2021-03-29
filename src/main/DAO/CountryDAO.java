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
}
