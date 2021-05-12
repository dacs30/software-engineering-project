package edu.wpi.MochaManticores.Services;

import com.jfoenix.controls.JFXComboBox;

public class ExternalTransportation extends ServiceRequest{
    private final String patientRoom;
    private final String currentRoom;
    private final String externalRoom;
    private final String transportationMethod;
    private JFXComboBox employeeAssigned;

    public ExternalTransportation(String RequestID, String employee, boolean completed, String patientRoom, String currentRoom,
                                  String externalRoom, String transportationMethod) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.ExternalTransportation);
        }
        this.patientRoom = patientRoom;
        this.currentRoom = currentRoom;
        this.externalRoom = externalRoom;
        this.transportationMethod = transportationMethod;
    }

    public String getPatientRoom() {
        return patientRoom;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public String getExternalRoom() {
        return externalRoom;
    }

    public String getTransportationMethod() {
        return transportationMethod;
    }
}
