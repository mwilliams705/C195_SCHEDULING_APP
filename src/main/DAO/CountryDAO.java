package main.DAO;

import main.Model.Country;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    public static Country getAllCountries(){
        String getStatement = "select Country_ID,Country from countries;";
        Country countryResult;

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            countryResult = new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country")
            );
            return countryResult;
        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }

    }

}
