package main.Util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06xQp";

    private static final String jdbcURL = protocol+vendorName+ipAddress+dbName;

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = "U06xQp";

    private static Connection conn = null;

    public static void startConnection(){
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL,username,Password.getPassword());
            System.out.println("Connection started!");
        }catch (SQLException s){
            System.out.println("SQL Exception");
            s.printStackTrace();
        }catch (ClassNotFoundException c){
            System.out.println("MYSQLJDBCDriver not found");
        }
    }

    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection(){
        try {
            conn.close();
        }catch (Exception ignored){

        }
    }
}
