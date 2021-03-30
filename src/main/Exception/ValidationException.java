package main.Exception;

/**
 * @author Michael Williams - 001221520
 *
 * This class controls and handles all validation when creating a Part or Product object
 * (Used in each objects 'isValid()' method).
 * Inherits from Exception class.
 */
public class ValidationException extends Exception {
    /**
     * This method passes the error string to the Exception class which handles all the functionality
     * of the exception itself.
     * @param s
     */
    public ValidationException(String s) {
        super(s);
    }
}