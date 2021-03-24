package main.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQuery {

    private static PreparedStatement statement;

//    Create statement object
    public static void setPreparedStatement(Connection connection,String sqlStatement) throws SQLException {
          statement = connection.prepareStatement(sqlStatement);

    }

    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
