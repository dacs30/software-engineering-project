package edu.wpi.MochaManticores.database;

public class Employee {
    //employee attributes
    public enum employeeType {DEFAULT,JANITOR,DOCTOR,NURSE,FLORIST,CHEF,STAFF,PATIENT};

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private employeeType type;
    private int ID;
    private boolean isAdmin;
    private boolean covidStatus;
    private String parkingSpace;

    public Employee () {}

    public Employee (String username, String password, String firstName, String lastName, employeeType type, int ID, boolean isAdmin){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.ID = ID;
        this.isAdmin = isAdmin;
    }

    // String only constructor
    public Employee(String username, String password, String firstName, String lastName, String type, String ID, String isAdmin){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = getTypeFromString(type);
        this.ID = Integer.parseInt(ID);
        this.isAdmin = Boolean.parseBoolean(isAdmin);
    }

    public Employee(String username, String password, String firstName, String lastName, String type, String ID, String isAdmin, String covidStatus, String parkingSpace){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = getTypeFromString(type);
        this.ID = Integer.parseInt(ID);
        this.isAdmin = Boolean.parseBoolean(isAdmin);
        this.covidStatus = Boolean.parseBoolean(covidStatus);
        this.parkingSpace = parkingSpace;
    }

    //GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public employeeType getType() {
        return type;
    }

    public void setType(employeeType type) {
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isCovidStatus() {
        return covidStatus;
    }

    public void setCovidStatus(boolean covidStatus) {
        this.covidStatus = covidStatus;
    }

    public String getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public static employeeType getTypeFromString(String type){
        employeeType empType;
        switch(type){
            case "JANITOR":
                empType = employeeType.JANITOR;
                break;
            case "DOCTOR":
                empType = employeeType.DOCTOR;
                break;
            case "NURSE":
                empType = employeeType.NURSE;
                break;
            case "FLORIST":
                empType = employeeType.FLORIST;
                break;
            case "CHEF":
                empType = employeeType.CHEF;
                break;
            case "STAFF":
                empType = employeeType.STAFF;
                break;
            case "PATIENT":
                empType = employeeType.PATIENT;
                break;
            default:
                empType = employeeType.DEFAULT;
                break;
        }

        return empType;
    }

    public static String getStringFromType(employeeType type){
        String empType;
        switch (type){
            case JANITOR:
                empType = "JANITOR";
                break;
            case DOCTOR:
                empType = "DOCTOR";
                break;
            case NURSE:
                empType = "NURSE";
                break;
            case FLORIST:
                empType = "FLORIST";
                break;
            case CHEF:
                empType = "CHEF";
                break;
            case STAFF:
                empType = "STAFF";
                break;
            case PATIENT:
                empType = "PATIENT";
                break;
            default:
                empType = "DEFAULT";
                break;
        }

        return empType;
    }


}