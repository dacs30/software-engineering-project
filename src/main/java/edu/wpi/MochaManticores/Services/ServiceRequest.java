package edu.wpi.MochaManticores.Services;

import edu.wpi.MochaManticores.database.DatabaseManager;
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

    public String generateRequestID(ServiceRequestType type){
        int num = DatabaseManager.getServiceMap().getSize(type);
        switch(type){
            case Emergency:
                return "EMG"+num;
            case SanitationServices:
                return "SanSrv"+num;
            case FoodDelivery:
                return "FOOD"+num;
            case FloralDelivery:
                return "FlrDlv"+num;
            case ExternalTransportation:
                return "EXTtrans"+num;
            case InternalTransportation:
                return "INTtrans"+num;
            case ReligiousRequest:
                return "Relig"+num;
            case LanguageInterperter:
                return "LangIntp"+num;
            case Medicine:
                return "Med"+num;
            case Laundry:
                return "Laund"+num;
        }
        return "";
    }
}
