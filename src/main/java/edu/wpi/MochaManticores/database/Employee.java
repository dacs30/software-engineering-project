package edu.wpi.MochaManticores.database;

public class Employee {
    //employee attributes
    public enum employeeType {DEFAULT};

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private employeeType type;
    private int ID;
    private boolean isAdmin;

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


    public static employeeType getTypeFromString(String type){
        employeeType empType;
        switch(type){
            default:
                empType = employeeType.DEFAULT;
                break;
        }

        return empType;
    }

    public static String getStringFromType(employeeType type){
        String empType;
        switch (type){
            default:
                empType = "DEFAULT";
                break;
        }

        return empType;
    }


}