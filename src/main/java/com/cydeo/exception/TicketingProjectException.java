package com.cydeo.exception;
//we create a custom exception here .
public class TicketingProjectException extends Exception{

    public TicketingProjectException(String message){
        super(message);
    }
}
