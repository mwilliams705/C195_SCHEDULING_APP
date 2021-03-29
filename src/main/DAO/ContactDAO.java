package main.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Model.Contact;
import main.Util.DBConnector;
import main.Util.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {

    public static ObservableList<Contact> getAllContacts(){
        String getStatement = "SELECT CONTACT_ID,Contact_Name,Email from contacts";
        ObservableList<Contact> contactResults = FXCollections.observableArrayList();

        try {
            DBQuery.setPreparedStatement(DBConnector.getConnection(),getStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()){
                Contact contact = new Contact(
                        rs.getInt("CONTACT_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email")
                );
                contactResults.add(contact);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
        return contactResults;
    }

}
