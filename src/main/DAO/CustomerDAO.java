package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Customer;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static Customer getCustomer(int id){
        String getCustomerStatement = "SELECT * FROM customers where Customer_ID = ?";
        Customer customerResult;

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getCustomerStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1,String.valueOf(id));
            ps.execute();
            ResultSet rs = ps.getResultSet();
            customerResult = new Customer(
                    rs.getInt("Customer_Id"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"));

            return customerResult;



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }

    }

    public static ObservableList<Customer> getAllCustomers(){
        String getCustomerStatement = "SELECT c.Customer_ID,c.Customer_Name,c.Address,c.Postal_Code,c.Phone, d.Division FROM customers c join first_level_divisions d on c.Division_ID = d.Division_ID";
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
                        rs.getString("Division")
                );
                allCustomers.add(customerResult);
            }



        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
        return allCustomers;
    }

//    public static boolean updateCustomer(Customer customer){
//
//    }
//
//    public static boolean deleteCustomer(int Id){
//
//    }

}
