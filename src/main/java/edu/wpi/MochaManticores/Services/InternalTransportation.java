package edu.wpi.MochaManticores.Services;

public class InternalTransportation extends ServiceRequest {
    private String patientID;
    private int numStaffNeeded;
    private String destination;
    private String transportationMethod;

    public InternalTransportation(String patientID, int numStaffNeeded, String destination, String transportationMethod, boolean employee,
                                  boolean completed, int row) {
        super(employee, completed, row);
        this.patientID = patientID;
        this.numStaffNeeded = numStaffNeeded;
        this.destination = destination;
        this.transportationMethod = transportationMethod;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public int getNumStaffNeeded() {
        return numStaffNeeded;
    }

    public void setNumStaffNeeded(int numStaffNeeded) {
        this.numStaffNeeded = numStaffNeeded;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransportationMethod() {
        return transportationMethod;
    }

    public void setTransportationMethod(String transportationMethod) {
        this.transportationMethod = transportationMethod;
    }
}


