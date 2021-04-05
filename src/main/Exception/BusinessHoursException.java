package main.Exception;

/**
 * This is a custom exception to handle the validation of appointments during business hours
 */
public class BusinessHoursException extends Exception{
    /**
     * This constructor provides the exception with a String which can be used to give the
     * user more information about an error.
     * @param message message
     */
    public BusinessHoursException(String message) {
        super(message);
    }
}
