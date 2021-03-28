package main.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Controller.Util.GeneralController;
import main.DAO.CountryDAO;
import main.DAO.CustomerDAO;
import main.DAO.FirstLevelDivisionDAO;
import main.Model.Customer;

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
    public ChoiceBox<String> country_choicebox;
    public ChoiceBox<String> division_choicebox;

    public Customer customerToModify;
    public Label currentCountryLbl;
    public Label currentDivisionLbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        country_choicebox.setItems(CountryDAO.getAllCountriesAsText());

        country_choicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) ->
                division_choicebox.setItems(FirstLevelDivisionDAO.getDivisionsByCountry_IdAsText(observableValue.getValue().intValue()+1)));

        setCustomerToModify(MainController.getModifyCustomer());
        if (customerToModify != null){


            division_choicebox.setDisable(false);
            headerLbl.setText("Update Customer");
            id_textfield.setText(String.valueOf(customerToModify.getCustomerId()));
            name_textfield.setText(customerToModify.getCustomerName());
            address_textfield.setText(customerToModify.getCustomerAddress());
            zipcode_textfield.setText(customerToModify.getCustomerZipcode());
            phone_textfield.setText(customerToModify.getCustomerPhone());
            currentCountryLbl.setText("Country (Current Selection: "+customerToModify.getCustomerCountryText());
            country_choicebox.setValue(customerToModify.getCustomerCountryText());
            currentDivisionLbl.setText("State/Division (Current Selection: "+customerToModify.getCustomerDivisionText()+")");
            division_choicebox.setValue(getCustomerToModify().getCustomerDivisionText());



        }
        else {
            headerLbl.setText("Add Customer");
        }
    }




    public void save(ActionEvent actionEvent) throws IOException {
        if (customerToModify != null) {
            String val = country_choicebox.getValue();
            System.out.println(val);

                Customer c = new Customer(
                        Integer.parseInt(id_textfield.getText()),
                        name_textfield.getText(),
                        address_textfield.getText(),
                        zipcode_textfield.getText(),
                        phone_textfield.getText(),
                        division_choicebox.getValue(),
                        country_choicebox.getValue()


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
                        division_choicebox.getValue()
                );
                CustomerDAO.addCustomer(c);
                GeneralController.changePage(actionEvent,"Main");


            }catch (NullPointerException n){
                System.out.println("Null pointer exception adding new customer");
            }
        }

    }

    public Customer getCustomerToModify() {
        return customerToModify;
    }



    public void setCustomerToModify(Customer customerToModify) {
        this.customerToModify = customerToModify;
    }

    public void cancel(ActionEvent actionEvent) throws IOException {

        GeneralController.changePage(actionEvent,"Main");

    }
}
