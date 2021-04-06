package main.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class handles creating a new PreparedStatement that is connecting to the database.
 */
public class DBQuery {

    private static PreparedStatement statement;

    /**
     * This static method takes a statement and joins it with the database connection. This is used in all
     * of the DAO classes to simplify the PreparedStatement variables
     * @param connection Database connection (DBConnection class)
     * @param sqlStatement SQL Statement to query the database
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection connection,String sqlStatement) throws SQLException {
          statement = connection.prepareStatement(sqlStatement);

    }

    /**
     * This method returns the prepared statement above to the PreparedStatements in the DAO classes. This is
     * provided to the PreparedStatement instances using data retrieved from the method above.
     * @return PreparedStatement object
     */
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
