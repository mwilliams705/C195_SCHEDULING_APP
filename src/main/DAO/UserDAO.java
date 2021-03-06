package main.DAO;

import main.Model.User;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * This method finds a user from the database and recreates it with the user_id added to the object.
     * @param user User object to be used in querying the database
     * @return User object with User_ID included.
     */
    public static User getUser(User user){
        String getStatement = "Select User_ID,User_Name,Password from users where lower(User_Name) = ? and lower(Password) = ?;";

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, user.getUserName().toLowerCase());
            ps.setString(2, user.getUserPassword().toLowerCase());
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                return new User(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"));
            }


        }catch (SQLException sqlException){
            sqlException.printStackTrace();

        }
        return null;
    }

}

