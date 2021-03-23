package main.Exception;

import java.util.Locale;

public class LocaleNotDefinedException extends Exception{

    public LocaleNotDefinedException(String message){
        super(message);
    }
//    public LocaleNotDefinedException(String message,Locale locale){
//        super(message,locale);
//    }
}
