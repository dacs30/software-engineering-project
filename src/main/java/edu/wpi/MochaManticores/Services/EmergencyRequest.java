package edu.wpi.MochaManticores.Services;

public class EmergencyRequest extends ServiceRequest {
    private int numPeopleNeeded;
    private String location;
    private boolean gurney;

    public EmergencyRequest(boolean employee, boolean completed, int row, int numPeopleNeeded, String location, boolean gurney) {
        super(employee, completed, row);
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
        String[] arr = new String[4];
        arr[0] = String.valueOf(ServiceMap.Emergency);
        arr[1] = String.valueOf(this.numPeopleNeeded);
        arr[2] = this.location;
        arr[3] = String.valueOf(this.gurney);
        return arr;
    }
}
