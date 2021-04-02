package main.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Controller.Util.GeneralController;
import main.DAO.CountryDAO;
import main.DAO.CustomerDAO;
import main.DAO.FirstLevelDivisionDAO;
import main.Exception.ValidationException;
import main.Model.Contact;
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

        for (FirstLevelDivision d: divisions){
            division_choicebox.getItems().add(d);
        }
        setCustomerToModify(MainController.getModifyCustomer());



        if (customerToModify != null){

            headerLbl.setText("Update Customer");
            id_textfield.setText(String.valueOf(customerToModify.getCustomerId()));
            name_textfield.setText(customerToModify.getCustomerName());
            address_textfield.setText(customerToModify.getCustomerAddress());
            zipcode_textfield.setText(customerToModify.getCustomerZipcode());
            phone_textfield.setText(customerToModify.getCustomerPhone());
            currentCountryLbl.setText("Country (Current Selection: "+customerToModify.getCustomerCountryText()+")");
            country_choicebox.setValue(getCountryById(customerToModify.getCustomerCountry()));

            division_choicebox.setItems(getDivisionsByCountryId(customerToModify.getCustomerCountry()));

            division_choicebox.setValue(getDivisionById(customerToModify.getCustomerDivision()));

            currentDivisionLbl.setText("State/Division (Current Selection: "+customerToModify.getCustomerDivisionText()+")");


            //        Lambda Expression selects which divisions will be shown based on the country selection.
            country_choicebox.getSelectionModel().selectedItemProperty().addListener((observableValue, country, t1) -> {
                divisionsByCountryId.setAll(getDivisionsByCountryId(observableValue.getValue().getCountryId()));
                division_choicebox.getItems().removeAll();
                division_choicebox.setItems(divisionsByCountryId);
            });

        }
        else {
            headerLbl.setText("Add Customer");

            division_choicebox.setDisable(true);
            //        Lambda Expression selects which divisions will be shown based on the country selection.
            country_choicebox.getSelectionModel().selectedItemProperty().addListener((observableValue, country, t1) -> {
                divisionsByCountryId.setAll(getDivisionsByCountryId(observableValue.getValue().getCountryId()));
                division_choicebox.setDisable(false);
                division_choicebox.getItems().removeAll();
                division_choicebox.setItems(divisionsByCountryId);
            });
        }
    }


    public void save(ActionEvent actionEvent) throws IOException {



                if (customerToModify != null) {

                try{
                    isFormComplete();
                    Customer c = new Customer(
                            Integer.parseInt(id_textfield.getText()),
                            name_textfield.getText(),
                            address_textfield.getText(),
                            zipcode_textfield.getText(),
                            phone_textfield.getText(),
                            division_choicebox.getValue().getDivisionId()


                    );

                    try {
                        c.isValid();
                        CustomerDAO.updateCustomer(c);
                        GeneralController.changePage(actionEvent,"Main");
                    }catch (ValidationException v){
                        Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR,"Validation Error","Wrong Input", v.getMessage());
                        alert.showAndWait();
                    }
                }catch (NullPointerException n){
                    Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR, "Validation Error", "Invalid Form Data", n.getMessage());
                    alert.showAndWait();
                }

                }
                else {

                    try {
                        isFormComplete();
                        Customer c = new Customer(
                                name_textfield.getText(),
                                address_textfield.getText(),
                                zipcode_textfield.getText(),
                                phone_textfield.getText(),
                                division_choicebox.getValue().getDivisionId()
                        );

                        try {
                            c.isValid();
                            CustomerDAO.addCustomer(c);
                            GeneralController.changePage(actionEvent,"Main");
                        }catch (ValidationException v){
                            Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR, "Validation Error", "Wrong Input", v.getMessage());
                            alert.showAndWait();
                        }



                    }catch (NullPointerException n){
                        Alert alert = GeneralController.alertUser(Alert.AlertType.ERROR, "Validation Error", "Invalid Form Data", n.getMessage());
                        alert.showAndWait();
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




    public boolean isFormComplete() throws NullPointerException{

        if (name_textfield.getText().equals("")){
            throw new NullPointerException("Name field cannot be empty");
        }
        if (address_textfield.getText().equals("")){
            throw new NullPointerException("Address field cannot be empty");
        }
        if (zipcode_textfield.getText().equals("")){
            throw new NullPointerException("Zipcode field cannot be empty");
        }
        if (phone_textfield.getText().equals("")){
            throw new NullPointerException("Phone field cannot be empty");
        }
        if (country_choicebox.getValue().toString().equals("")){
            throw new NullPointerException("Country choice field cannot be empty");
        }
        if (division_choicebox.getValue().toString().equals("")){
            throw new NullPointerException("Division choice field cannot be empty");
        }


        return true;
    }

    private Country getCountryById(int id){
        Country country = null;

        for (Country c :
                countries) {
            if (c.getCountryId() != id){
                continue;
            }else {
                country = c;
            }
        }
        return country;
    }

    private FirstLevelDivision getDivisionById(int id){
        FirstLevelDivision fld = null;
        for (FirstLevelDivision f :
                divisions) {
            if (f.getDivisionId() != id){
                continue;
            }else {
                fld = f;
            }
        }
        return fld;
    }

    private ObservableList<FirstLevelDivision> getDivisionsByCountryId(int id){
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        for (FirstLevelDivision f: divisions
             ) {
            if (f.getCountryId() != id){
                continue;
            }else {
                fldList.add(f);
            }
        }
        return fldList;
    }
}
