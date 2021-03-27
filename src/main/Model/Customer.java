package main.Model;

public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerZipcode;
    private String customerPhone;
    private int customerDivision;
    private String customerDivisionText;

    public Customer(int customerId, String customerName, String customerAddress, String customerZipcode, String customerPhone, int customerDivision) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipcode = customerZipcode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
    }

    public Customer(int customerId, String customerName, String customerAddress, String customerZipcode, String customerPhone, String customerDivision) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipcode = customerZipcode;
        this.customerPhone = customerPhone;
        this.customerDivisionText = customerDivision;
    }

    public Customer(int customerId, String customerName, String customerAddress, String customerZipcode, String customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipcode = customerZipcode;
        this.customerPhone = customerPhone;
    }

    public Customer(String customerName, String customerAddress, String customerZipcode, String customerPhone, int customerDivision) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipcode = customerZipcode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
    }

    public Customer(String customerName, String customerAddress, String customerZipcode, String customerPhone, String customerDivisionText) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZipcode = customerZipcode;
        this.customerPhone = customerPhone;
        this.customerDivisionText = customerDivisionText;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerZipcode() {
        return customerZipcode;
    }

    public void setCustomerZipcode(String customerZipcode) {
        this.customerZipcode = customerZipcode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getCustomerDivision() {
        return customerDivision;
    }

    public void setCustomerDivision(int customerDivision) {
        this.customerDivision = customerDivision;
    }

    public String getCustomerDivisionText() {
        return customerDivisionText;
    }

    public void setCustomerDivisionText(String customerDivisionText) {
        this.customerDivisionText = customerDivisionText;
    }
}
