package edu.wpi.MochaManticores.Services;

import edu.wpi.MochaManticores.database.Employee;

import java.awt.image.ImageProducer;
import java.io.*;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public abstract class ServiceRequest {
    private String employee;
    private boolean completed;
    public String RequestID;
    private static String Services_csv_path = "data/services/";
    private static final String CSVdelim = ",";

    public boolean getCompleted(){
        return completed;
    }

    public void setCompleted(boolean b){
        completed = b;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public ServiceRequest(String employee, boolean completed, String RequestID) {
        this.employee = employee;
        this.completed = completed;
        this.RequestID = RequestID;
    }
}
