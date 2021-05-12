package edu.wpi.MochaManticores.database;


import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.Exceptions.InvalidPermissionsException;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Services.InternalTransportation;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;

public class EmployeeManager extends Manager<Employee>{
    private static String Employee_csv_path = "data/bwMEmployees.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";

    EmployeeManager(Connection connection, String Employee_csv_path){
        EmployeeManager.connection = connection;
        if(Employee_csv_path != null){
            EmployeeManager.Employee_csv_path = Employee_csv_path;
        }
    }

    /**
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

                Employee employee = new Employee(row[0],row[1],row[2], row[3],row[4],row[5],row[6], row[7], row[8], row[9]);
                addElement(employee);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO add exceptions for duplicate username handling
    /**
    function: addElement(Employee)
    add element to database
     */
    public void addElement(Employee employee){
        try{
            String sql = "INSERT INTO EMPLOYEES (username, password, firstName, lastName, employeeType,ID, AdminLevel, covidStatus, parkingSpot, loggedIN) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setBoolean(10, employee.isLoggedIN());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void addElement(Employee temp, String ID){

    }

    //TODO safe deletes, better handing of execptions
    /**
    function: delElement(s)
    deletes element of given ID string
     */
    public void delElement(String username) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM EMPLOYEES WHERE username=?");
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    }

    //TODO add functionality to check if editedEmployee is valid before deleting old value
    /**
    function: modElement(s,edgeSuper)
    modifies element of ID s to become element EdgeSuper
     */
    public void modElement(String old_username, Employee editedEmployee) throws SQLException {
        delElement(old_username);
        addElement(editedEmployee);
    }

    /**
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

    /**
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
                    result.getString(4), result.getString(5), result.getString(6), result.getString(7),
                    result.getString(8), result.getString(9), result.getString(10));
            return employee;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     function:  checkEmployeeLogin()
     checks user password and clearance, throws invalid login if pass is wrong, throws invalid element if user doesnt exist
     @return Employee
     */
    public Employee checkEmployeeLogin(String username,String password) throws InvalidLoginException, InvalidElementException {
        Employee emp = getElement(username);

        if(emp.getPassword() == null || emp.getPassword().equals("")){
            throw new InvalidLoginException();
        }

        String pass = emp.getPassword();
        String salt = pass.substring(0, pass.indexOf(":"));
        String hashPass = hashWithGivenSalt(password, salt);

        if(!pass.equals(hashPass)){
            throw new InvalidLoginException();
        }

        // if valid login, return the employee object
        return emp;
    }

    /**
     * hashWithGivenSalt
     * @param plainText
     * @param salt
     * @return hashed plainText with given salt
     */
    public String hashWithGivenSalt(String plainText, String salt){
        byte[] bSalt = fromHex(salt);

        KeySpec spec = new PBEKeySpec(plainText.toCharArray(), bSalt, 65536, 128);

        SecretKeyFactory factory = null;

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = new byte[0];
        try {
            hashedPassword = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.println(bytesToHex(bSalt) + " : " + bytesToHex(hashedPassword));

        return bytesToHex(bSalt) + ":" + bytesToHex(hashedPassword);
    }

    private static byte[] fromHex(String hex)
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * bytesToHex
     * @param bytes
     * @return hex representation of byte array
     */
    private static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
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

    /**
     * function: getEmployeeNames()
     * gives back a list of all employees in database
     * @return a LinkedList of Strings containing employee names
     */
    public LinkedList<String> getEmployeeNames() {
        LinkedList<String> names = new LinkedList<>();
        try {
            String sql = "SELECT username, employeeType FROM EMPLOYEES GROUP BY username, employeeType";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                if(!result.getString(2).equals("PATIENT")){
                    String temp = result.getString(1);
                    names.add(temp);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return names;
    }

    public String getEmployeeUsername(String name) {
        String[] nameArray = name.split(" ");
        String username = "";
        try {
            String sql = "SELECT username FROM EMPLOYEES WHERE firstName=? AND lastName=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nameArray[0]);
            pstmt.setString(2, nameArray[1]);
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) {
                throw new InvalidElementException();
            }

            username = result.getString(1);
        }catch(SQLException | InvalidElementException e){
            e.printStackTrace();
        }
        return username;
    }

    /**
    function: getCSV_path()
    getter for CSV_path
    return string
     */
    public String getCSV_path() {
        return Employee_csv_path;
    }

    /**
    function setCSV_path()
    setter for CSV_path
     */
    public void setCSV_path(String employee_csv_path) {
        Employee_csv_path = employee_csv_path;
    }

    /**
    function: cleanTable()
    saves and empties database table
     */
    public void cleanTable() throws SQLException {
        String sql = "DELETE FROM EMPLOYEES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();
    }

    @Override
    public void cleanMap(){
        //no map
    }

    @Override
    public void updateElementMap() throws SQLException {
        //no map
    }
}
