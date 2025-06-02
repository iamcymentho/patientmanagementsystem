package com.pms.patientservice.exception.childexception;

public class EmailAlreadyExitsException  extends RuntimeException{
    public EmailAlreadyExitsException(String message){ super(message); }
}
