package edu.wpi.MochaManticores.Services;

public class EmergencyRequest extends ServiceRequest {
    private final int numPeopleNeeded;
    private final String location;
    private final boolean gurney;

    public EmergencyRequest(String RequestID, String employee, boolean completed, int numPeopleNeeded, String location, boolean gurney) {
        super(employee, completed, RequestID);
        if(RequestID.equals("")){
            this.RequestID = generateRequestID(ServiceRequestType.Emergency);
        }else{
            this.RequestID = RequestID;
        }
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
