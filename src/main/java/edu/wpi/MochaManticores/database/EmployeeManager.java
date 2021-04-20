package edu.wpi.MochaManticores.database;


import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.Exceptions.InvalidPermissionsException;
import edu.wpi.MochaManticores.Exceptions.InvalidUserException;
import java.io.*;
import java.sql.*;

public class EmployeeManager {
    private static String Employee_csv_path = "data/bwMEmployees.csv";
    private static final String CSVdelim = ",";

    public static void loadFromCSV(Connection connection){
        //loads database
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Employee_csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                Employee employee = new Employee(row[0],row[1],row[2], row[3],row[4],row[5],row[6]);
                addEmployee(connection, employee);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO add exceptions for duplicate username handling
    public static void addEmployee(Connection connection, Employee employee){
        try{
            String sql = "INSERT INTO EMPLOYEES (username, password, fisrtName, lastName, employeeType,ID, Admin) " +
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
    public static void delEmployee(Connection connection,String username) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM EMPLOYEES WHERE username=?");
        pstmt.setString(1, username);
        pstmt.executeUpdate();
    }

    //TODO add functionality to check if editedEmployee is valid before deleting old value
    public static void modEmployee(Connection connection, String old_username, Employee editedEmployee) throws SQLException {
        delEmployee(connection, old_username);
        addEmployee(connection, editedEmployee);
    }

    public static void saveEmployees(Connection connection) throws FileNotFoundException, SQLException {
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

    public static Employee getEmployee(Connection connection, String username) throws InvalidUserException {
        try {
            String sql = "SELECT * FROM EMPLOYEES WHERE USERNAME =?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) {
                throw new InvalidUserException();
            }

            Employee employee = new Employee(result.getString(1), result.getString(2), result.getString(3),
                    result.getString(4), result.getString(5), result.getString(6), result.getString(7));
            return employee;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Employee checkEmployeeLogin(Connection connection,String username,String password) throws InvalidLoginException, InvalidUserException {
        Employee emp = getEmployee(connection,username);

        //TODO passwords are currently stored in plain text
        if(!emp.getPassword().equals(password)){
            throw new InvalidLoginException();
        }

        // if valid login, return the employee object
        return emp;
    }

    public static Employee checkAdminLogin(Connection connection, String username, String password) throws InvalidLoginException, InvalidPermissionsException, InvalidUserException     {
        Employee emp = getEmployee(connection, username);

        if(!emp.getPassword().equals(password)){
            throw new InvalidLoginException();
        }

        if(!emp.isAdmin()){
            throw new InvalidPermissionsException();
        }

        return emp;
    }

    public static String getEmployee_csv_path() {
        return Employee_csv_path;
    }

    public static void setEmployee_csv_path(String employee_csv_path) {
        Employee_csv_path = employee_csv_path;
    }

    public static void cleanTable(Connection connection) throws SQLException {
        String sql = "DELETE FROM EMPLOYEES";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();
    }
}
