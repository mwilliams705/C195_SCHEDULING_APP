package main.Controller;

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
import main.Model.Country;
import main.Model.Customer;
import main.Model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Michael Williams - 001221520
 *
 * This class controls and handles all processes related to the 'CustomerForm.fxml' page.
 */
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

    CustomerInterface updateCustomer = ()-> new Customer(
            Integer.parseInt(id_textfield.getText()),
            name_textfield.getText(),
            address_textfield.getText(),
            zipcode_textfield.getText(),
            phone_textfield.getText(),
            division_choicebox.getValue().getDivisionId()
    );

    CustomerInterface addCustomer = ()-> new Customer(
            name_textfield.getText(),
            address_textfield.getText(),
            zipcode_textfield.getText(),
            phone_textfield.getText(),
            division_choicebox.getValue().getDivisionId()
    );

    /**
     * Initializes the Customer Form view and populates the choice boxes with data from the Country and
     * FirstLevelDivision database tables.
     *<br>
     * LAMBDA EXPRESSION in this method filters divisions shown in the Division choicebox based on the selection
     * made in the Country choicebox
     * @param url
     * @param resourceBundle
     */
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


            //        Lambda Expression selects which divisions will be shown based on the country selection(observableValue).
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

    /**
     * This method validates form completion and saves a new customer to the database if no data was brought over
     * from the Main view. Otherwise, the user will be updated based on the information brought over from the main view.
     *
     * LAMBDA EXPRESSION in this method simplifies the creation of Customer objects to be stored or updated in the database.
     * @param actionEvent
     * @throws IOException
     */
    public void save(ActionEvent actionEvent) throws IOException {



                if (customerToModify != null) {

                try{
                    isFormComplete();

                    Customer c = updateCustomer.newCustomer();

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
                        Customer c = addCustomer.newCustomer();

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

    /**
     * This method will cancel the addition of a new customer and move the user back to the main view.
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {

        GeneralController.changePage(actionEvent,"Main");

    }


//    ==================================================================================================================
//    ==================Getters & Setters===============================================================================
//    ==================================================================================================================

    /**
     *
     * @return customerToModify
     */
    public Customer getCustomerToModify() {
        return customerToModify;
    }


    /**
     *
     * @param customerToModify customerToModify
     */
    public void setCustomerToModify(Customer customerToModify) {
        this.customerToModify = customerToModify;
    }


    /**
     * This method checks that no form fields were left blank.
     * @return
     * @throws NullPointerException
     */
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

    /**
     * This method filters through the country data and stops once a country with the chosen ID is found.
     * @param id
     * @return
     */
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

    /**
     * This method filters through the FirstLevelDivision data and stops once a division with the chosen ID is found.
     * @param id
     * @return
     */
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

    /**
     * This method filters through the FirstLevelDivision data and stops once a country with the chosen Country_ID is found.
     * @param id
     * @return
     */
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
