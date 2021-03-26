package main.Controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TestTabPageController implements Initializable {
    public Label onlyLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onlyLabel.setText(MainController.getModifyCustomer().getCustomerName());
    }
}
