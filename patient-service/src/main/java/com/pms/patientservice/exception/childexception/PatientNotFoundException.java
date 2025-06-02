package com.pms.patientservice.exception.childexception;

import java.util.UUID;

public class PatientNotFoundException  extends RuntimeException{
    public PatientNotFoundException(String message, UUID id){ super(message);}
}
