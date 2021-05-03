package edu.wpi.MochaManticores.database;


import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.Exceptions.InvalidPermissionsException;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import java.io.*;
import java.sql.*;

public class EmployeeManager extends Manager<Employee>{
    private static String Employee_csv_path = "data/bwMEmployees.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";

    EmployeeManager(Connection connection, String Employee_csv_path){
        this.connection = connection;
        if(Employee_csv_path != null){
            this.Employee_csv_path = Employee_csv_path;
        }
    }

    /*
     function loadFromCSV()
     load elements from the CSV
     */
    public void loadFromCSV(){
        //loads database
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Employee_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                Employee employee = new Employee(row[0],row[1],row[2], row[3],row[4],row[5],row[6], row[7], row[8]);
                addElement(employee);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO add exceptions for duplicate username handling
    /*
    function: addElement(Employee)
    add element to database
     */
    public void addElement(Employee employee){
        try{
            String sql = "INSERT INTO EMPLOYEES (username, password, fisrtName, lastName, employeeType,ID, AdminLevel, covidStatus, parkingSpot) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, employee.getUsername());
            pstmt.setString(2, employee.getPassword());
            pstmt.setString(3, employee.getFirstName());
            pstmt.setString(4, employee.getLastName());
            pstmt.setString(5, Employee.getStringFromType(employee.getType()));
            pstmt.setInt(6, employee.getID());
            pstmt.setBoolean(7, employee.isAdmin());
            pstmt.setBoolean(8, employee.isCovidStatus());
            pstmt.setString(9, employee.getParkingSpace());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //TODO safe deletes, better handing of execptions
    /*
    function: delElement(s)
    deletes element of given ID string
     */
    public void delElement(String username) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM EMPLOYEES WHERE username=?");
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    }

    //TODO add functionality to check if editedEmployee is valid before deleting old value
    /*
    function: modElement(s,edgeSuper)
    modifies element of ID s to become element EdgeSuper
     */
    public void modElement(String old_username, Employee editedEmployee) throws SQLException {
        delElement(old_username);
        addElement(editedEmployee);
    }

    /*
    function: saveElements()
    saves elements to given CSV file
     */
    public void saveElements() throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(Employee_csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM EMPLOYEES";
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = results.getMetaData();

        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            sb.append(rsmd.getColumnName(i));
            sb.append(",");
        }
        sb.append("\n");
        while (results.next()) {
            //writing to csv file
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                sb.append(results.getString(i));
                sb.append(",");
            }
            sb.append("\n");
        }
        results.close();
        pw.write(sb.toString());
        pw.close();
    }

    /*
    function: getElement()
    returns employee object, specified by ID
     */
    public Employee getElement(String username) throws InvalidElementException {
        try {
            String sql = "SELECT * FROM EMPLOYEES WHERE USERNAME =?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) {
                throw new InvalidElementException();
            }

            Employee employee = new Employee(result.getString(1), result.getString(2), result.getString(3),
                    result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9));
            return employee;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
     function:  checkEmployeeLogin()
     checks user password and clearance, throws invalid login if pass is wrong, throws invalid element if user doesnt exist
     @return Employee
     */
    public Employee checkEmployeeLogin(String username,String password) throws InvalidLoginException, InvalidElementException {
        Employee emp = getElement(username);

        if(emp.getPassword() == null | emp.getPassword().equals("")){
            return emp;
        }

        //TODO passwords are currently stored in plain text
        if(!emp.getPassword().equals(password)){
            throw new InvalidLoginException();
        }

        // if valid login, return the employee object
        return emp;
    }

    /*
     function:  checkAdminLogin()
     checks user password and clearance, throws invalid login if pass is wrong, throws invalid element if user doesnt exist,
     Throws invalid permissions if user is not an admin
     @return Employee
     */
    public Employee checkAdminLogin(String username, String password) throws InvalidLoginException, InvalidPermissionsException, InvalidElementException {
        Employee emp = getElement(username);

        if(!emp.getPassword().equals(password)){
            throw new InvalidLoginException();
        }

        if(!emp.isAdmin()){
            throw new InvalidPermissionsException();
        }

        return emp;
    }

    /*
    function: getCSV_path()
    getter for CSV_path
    return string
     */
    public String getCSV_path() {
        return Employee_csv_path;
    }

    /*
    function setCSV_path()
    setter for CSV_path
     */
    public void setCSV_path(String employee_csv_path) {
        Employee_csv_path = employee_csv_path;
    }

    /*
    function: cleanTable()
    saves and empties database table
     */
    public void cleanTable() throws SQLException {
        String sql = "DELETE FROM EMPLOYEES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();
    }
}
