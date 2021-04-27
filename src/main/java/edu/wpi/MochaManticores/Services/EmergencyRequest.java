package edu.wpi.MochaManticores.Services;

public class EmergencyRequest extends ServiceRequest {
    private int numPeopleNeeded;
    private String location;
    private boolean gurney;

    public EmergencyRequest(String employee, boolean completed, String reqID, int numPeopleNeeded, String location, boolean gurney) {
        super(employee, completed, reqID);
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

    public String[] getFields() {
        return new String[]{
                String.valueOf(ServiceMap.Emergency),
                String.valueOf(numPeopleNeeded),
                location,
                String.valueOf(gurney),
        };
    }
}
