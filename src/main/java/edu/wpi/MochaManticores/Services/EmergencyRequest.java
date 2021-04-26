package edu.wpi.MochaManticores.Services;

public class EmergencyRequest extends ServiceRequest {
    private int numPeopleNeeded;
    private String location;
    private boolean gurney;

    public EmergencyRequest(String RequestID, String employee, boolean completed, int numPeopleNeeded, String location, boolean gurney) {
        super(employee, completed, RequestID);
        this.numPeopleNeeded = numPeopleNeeded;
        this.location = location;
        this.gurney = gurney;
    }

    public int getNumPeopleNeeded() {
        return numPeopleNeeded;
    }

    public String getLocation() {
        return location;
    }

    public boolean isGurney() {
        return gurney;
    }

}
