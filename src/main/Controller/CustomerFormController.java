package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Controller.Util.GeneralController;
import main.DAO.CountryDAO;
import main.DAO.CustomerDAO;
import main.DAO.FirstLevelDivisionDAO;
import main.Model.Country;
import main.Model.Customer;
import main.Model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    public Label headerLbl;
    public TextField id_textfield;
    public TextField name_textfield;
    public TextField address_textfield;
    public TextField zipcode_textfield;
    public TextField phone_textfield;
    public ChoiceBox<Country> country_choicebox;
    public ChoiceBox<FirstLevelDivision> division_choicebox;

    public Customer customerToModify;
    public Label currentCountryLbl;
    public Label currentDivisionLbl;

    ObservableList<Country> countries =FXCollections.observableArrayList();
    ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivision> divisionsByCountryId = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countries.setAll(CountryDAO.getAllCountries());
        divisions.setAll(FirstLevelDivisionDAO.getAllDivisions());

        for (Country c:countries){
            country_choicebox.getItems().add(c);
        }

        country_choicebox.getSelectionModel().selectedItemProperty().addListener((observableValue, number, t1) ->{
            divisionsByCountryId.setAll(FirstLevelDivisionDAO.getAllDivisionsByCountryId(observableValue.getValue().getCountryId()));
            division_choicebox.setItems(divisionsByCountryId);
        });
        setCustomerToModify(MainController.getModifyCustomer());

        if (customerToModify != null){

            headerLbl.setText("Update Customer");
            id_textfield.setText(String.valueOf(customerToModify.getCustomerId()));
            name_textfield.setText(customerToModify.getCustomerName());
            address_textfield.setText(customerToModify.getCustomerAddress());
            zipcode_textfield.setText(customerToModify.getCustomerZipcode());
            phone_textfield.setText(customerToModify.getCustomerPhone());
            currentCountryLbl.setText("Country (Current Selection: "+customerToModify.getCustomerCountryText()+")");
            country_choicebox.setValue(countries.get(customerToModify.getCustomerCountry()-1));
            currentDivisionLbl.setText("State/Division (Current Selection: "+customerToModify.getCustomerDivisionText()+")");

//            for (FirstLevelDivision d: divisions
//                 ) {if (d.getDivisionName().equals(customerToModify.getCustomerDivisionText())){
//                     division_choicebox.setValue();
//                System.out.println(d.getDivisionName());
//            }

//            }



        }
        else {
            headerLbl.setText("Add Customer");
        }
    }




    public void save(ActionEvent actionEvent) throws IOException {
        if (customerToModify != null) {
            String val = country_choicebox.getValue().getCountryName();
            System.out.println(val);

                Customer c = new Customer(
                        Integer.parseInt(id_textfield.getText()),
                        name_textfield.getText(),
                        address_textfield.getText(),
                        zipcode_textfield.getText(),
                        phone_textfield.getText(),
                        division_choicebox.getValue().getDivisionId()


                );

            System.out.println(c.getCustomerDivisionText());
            CustomerDAO.updateCustomer(c);
            GeneralController.changePage(actionEvent,"Main");
        }
        else {
            try {
                Customer c = new Customer(
                        name_textfield.getText(),
                        address_textfield.getText(),
                        zipcode_textfield.getText(),
                        phone_textfield.getText(),
//                        TODO: This needs to be converted to an integer
                        division_choicebox.getValue().getDivisionId()
                );
                CustomerDAO.addCustomer(c);
                GeneralController.changePage(actionEvent,"Main");


            }catch (NullPointerException n){
                System.out.println("Null pointer exception adding new customer");
            }
        }

    }


    public void cancel(ActionEvent actionEvent) throws IOException {

        GeneralController.changePage(actionEvent,"Main");

    }


//    ==================================================================================================================
//    ==================Getters & Setters===============================================================================
//    ==================================================================================================================

    public Customer getCustomerToModify() {
        return customerToModify;
    }



    public void setCustomerToModify(Customer customerToModify) {
        this.customerToModify = customerToModify;
    }

}
