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
        String getStatement = "select Division,Division_ID,Country_ID from first_level_divisions where country_id = ?;";
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

    public static ObservableList<FirstLevelDivision> getAllDivisions(){
        String getStatement = "SELECT Division_ID,Division,Country_ID FROM first_level_divisions";
        ObservableList<FirstLevelDivision> divisionResults = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();

            while (rs.next()){
                FirstLevelDivision division = new FirstLevelDivision(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID")
                );
                divisionResults.add(division);
            }
        }catch (SQLException sqlException){
            System.out.println("Couldnt get all divisions");
            return null;
        }

        return divisionResults;

    }

    public static FirstLevelDivision getDivisionById(int id){
        String getStatement = "select Division_Id, Division, Country_Id from first_level_divisions where division_id = ?;";
        try {

            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1,id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                return new FirstLevelDivision(
                        rs.getInt("Division_Id"),
                        rs.getString("Division"),
                        rs.getInt("Country_Id")
                );

            }

        }catch (SQLException s){
            s.printStackTrace();
            return null;
        }
        return null;
    }
}
