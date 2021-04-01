package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Controller.LoginController;
import main.Controller.MainController;
import main.Model.Customer;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static void addCustomer(Customer customer){
        String getStatement = "insert into customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "values (?,?,?,?,NOW(),?,NOW(),?,?);";
        try{
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3,customer.getCustomerZipcode());
            ps.setString(4, customer.getCustomerPhone());
            ps.setString(5,LoginController.getGlobalUser().getUserName());
            ps.setString(6,LoginController.getGlobalUser().getUserName());
            ps.setInt(7,customer.getCustomerDivision());

            ps.execute();

            if (ps.getUpdateCount()>0){


                System.out.println(ps.getUpdateCount()+ " row(s) affected.");
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public static Customer getCustomer(int id){
        String getCustomerStatement = "SELECT * FROM customers where Customer_ID = ?";
        Customer customerResult;

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getCustomerStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()){
                customerResult = new Customer(
                        rs.getInt("Customer_Id"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"));

                return customerResult;
            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
return null;
    }

    public static ObservableList<Customer> getAllCustomers(){
        String getCustomerStatement = "SELECT Customer_ID,Customer_Name,Address,Postal_Code,Phone, division_id FROM customers";
        Customer customerResult;
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getCustomerStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();


            while (rs.next()){
                customerResult = new Customer(
                        rs.getInt("Customer_Id"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID")
                );
                allCustomers.add(customerResult);
            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allCustomers;
    }


    public static ObservableList<Customer> getAllCustomersWithDivisionAndCountryIdsAsText(){
        String getStatement = "SELECT c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone,f.division, co.Country\n" +
                "FROM customers c\n" +
                "    join first_level_divisions f on c.Division_ID = f.Division_ID\n" +
                "    join countries co on f.Country_ID = co.country_id;";
        Customer customerResult;
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();


            while (rs.next()){
                customerResult = new Customer(
                        rs.getInt("Customer_Id"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getString("Division"),
                        rs.getString("Country")

                );
                allCustomers.add(customerResult);
            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allCustomers;
    }


    public static ObservableList<Customer> getAllCustomersWithDivisionAndCountryIds(){
        String getStatement = "SELECT c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone,c.division_id, f.Country_Id\n" +
                "FROM customers c join first_level_divisions f on c.Division_ID = f.Division_ID;";
        Customer customerResult;
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();


            while (rs.next()){
                customerResult = new Customer(
                        rs.getInt("Customer_Id"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"),
                        rs.getInt("Country_ID")

                );
                allCustomers.add(customerResult);
            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allCustomers;
    }


    public static ObservableList<Customer> getAllCustomersWithDivisionAndCountries(){
        String getCustomerStatement = "SELECT c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone,f.division, co.Country,c.Division_ID,f.COUNTRY_ID\n" +
                "FROM customers c\n" +
                "    join first_level_divisions f on c.Division_ID = f.Division_ID\n" +
                "    join countries co on f.Country_ID = co.country_id;";
        Customer customerResult;
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getCustomerStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();


            while (rs.next()){
                customerResult = new Customer(
                        rs.getInt("Customer_Id"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getString("Division"),
                        rs.getString("Country"),
                        rs.getInt("Division_ID"),
                        rs.getInt("Country_ID")
                );
                allCustomers.add(customerResult);
            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allCustomers;
    }





    public static void updateCustomer(Customer customer){

        String getStatement = "update customers set Customer_Name = ?,\n" +
                "                     Address = ?,\n" +
                "                     Postal_Code = ?,\n" +
                "                     Phone = ?,\n" +
                "                     Last_Update = NOW(),\n" +
                "                     Last_Updated_By=?,\n" +
                "                     Division_Id = ?\n" +
                "                    where Customer_ID = ?;";


        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(), getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerZipcode());
            ps.setString(4, customer.getCustomerPhone());
            ps.setString(5, LoginController.getGlobalUser().getUserName());
            ps.setInt(6,customer.getCustomerDivision());
            ps.setInt(7, customer.getCustomerId());
            ps.execute();

            if (ps.getUpdateCount()>0){
                System.out.println(ps.getUpdateCount()+" row(s) affected.");
            }
        }catch (SQLException s){
            s.printStackTrace();

        }

    }

    public static String getDivisionName(int id){
        String getStatement = "select Division from first_level_divisions where Division_ID = ?;";
        String divisionResult = null;

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                divisionResult = rs.getString("Division");
            }

            return divisionResult;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
//
    public static void deleteCustomer(int Id){
        String getStatement = "delete from customers where Customer_ID = ?";

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1,Id);
            ps.execute();

            if (ps.getUpdateCount()>0){
                System.out.println(ps.getUpdateCount()+" row(s) affected.");
            }
        }catch (SQLException s){
            System.out.println("Nothing Deleted");
        }
    }

}
