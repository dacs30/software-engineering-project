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

    public void loadFromCSV(){
        //loads database
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Employee_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                Employee employee = new Employee(row[0],row[1],row[2], row[3],row[4],row[5],row[6]);
                addElement(employee);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO add exceptions for duplicate username handling
    @Override
    public void addElement(Employee employee){
        try{
            String sql = "INSERT INTO EMPLOYEES (username, password, fisrtName, lastName, employeeType,ID, AdminLevel) " +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, employee.getUsername());
            pstmt.setString(2, employee.getPassword());
            pstmt.setString(3, employee.getFirstName());
            pstmt.setString(4, employee.getLastName());
            pstmt.setString(5, employee.getStringFromType(employee.getType()));
            pstmt.setInt(6, employee.getID());
            pstmt.setBoolean(7, employee.isAdmin());
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //TODO safe deletes, better handing of execptions
    public void delElement(String username) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM EMPLOYEES WHERE username=?");
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    }

    //TODO add functionality to check if editedEmployee is valid before deleting old value
    public void modElement(String old_username, Employee editedEmployee) throws SQLException {
        delElement(old_username);
        addElement(editedEmployee);
    }

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
                    result.getString(4), result.getString(5), result.getString(6), result.getString(7));
            return employee;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Employee checkEmployeeLogin(String username,String password) throws InvalidLoginException, InvalidElementException {
        Employee emp = getElement(username);

        //TODO passwords are currently stored in plain text
        if(!emp.getPassword().equals(password)){
            throw new InvalidLoginException();
        }

        // if valid login, return the employee object
        return emp;
    }

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

    public String getCSV_path() {
        return Employee_csv_path;
    }

    public void setCSV_path(String employee_csv_path) {
        Employee_csv_path = employee_csv_path;
    }
    public void cleanTable() throws SQLException {
        String sql = "DELETE FROM EMPLOYEES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();
    }
}
