package main.DAO;

import main.Model.FirstLevelDivision;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDAO {

    public static FirstLevelDivision getAllDivisionsByCountryId(int id){
        String getStatement = "SELECT Division_ID,Division,COUNTRY_ID from first_level_divisions.sql where COUNTRY_ID = ? order by 3,2;";
        FirstLevelDivision fldResult;

        try{
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            ResultSet resultSet = ps.getResultSet();
            fldResult = new FirstLevelDivision(
                    resultSet.getInt("Division_ID"),
                    resultSet.getString("Division"),
                    resultSet.getInt("Country_ID")
            );
            return fldResult;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

}
