package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Services.LaundryRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;

import java.io.*;
import java.sql.*;

public class LaundryManager extends Manager<LaundryRequest> {
    private static String csv_path = "data/services/LaundryServices.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";
    private static final ServiceRequestType type = ServiceRequestType.Laundry;

    LaundryManager(Connection connection, String csv_path){
        LaundryManager.connection = connection;
        if(csv_path != null){
            LaundryManager.csv_path = csv_path;
        }
    }

    @Override
    void loadFromCSV() {
        //loads database and sets hashmap
        try{
            BufferedReader reader = new BufferedReader(new FileReader(csv_path));
            String line = reader.readLine();

            while (line != null){
                line = reader.readLine();
                if(line == null) break;
                String[] row = line.split(CSVdelim);

                LaundryRequest temp = new LaundryRequest("",row[1],Boolean.parseBoolean(row[2]),
                        row[3],row[4], Boolean.parseBoolean(row[5]),row[6], row[7], Integer.parseInt(row[8]));
                addElement(temp);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    void addElement(LaundryRequest v) {
        v.setRequestID(v.generateRequestID(type));
        addElement_db(v);
        addElement_map(v);
    }

    void addElement_db(LaundryRequest temp) {
        try {
            String sql = "INSERT INTO LAUNDRY (RequestID, EmpID, completed, patientName, soilLevel, delicates, " +
                    "washCycleTemperature, dryCycleTemperature, dryCycleNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, temp.getRequestID());
            pstmt.setString(2, temp.getEmployee());
            pstmt.setBoolean(3, temp.getCompleted());
            pstmt.setString(4,temp.getName());
            pstmt.setString(5, temp.getSoilLevel());
            pstmt.setString(6, String.valueOf(temp.isDelicates()));
            pstmt.setString(7, temp.getWashCycleTemperature());
            pstmt.setString(8, temp.getDryCycleTemperature());
            pstmt.setString(9, String.valueOf(temp.getDryCycleNumber()));
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    void addElement_map(LaundryRequest temp) {
        if(!DatabaseManager.getServiceMap().containsRequest(type, temp.RequestID)) {
            DatabaseManager.getServiceMap().addRequest(type,temp);
        }
        else {
            System.out.printf("This node %s already exists\n", temp.RequestID);
        }
    }

    @Override
    void delElement(String ID) throws SQLException {
        //remove node from database
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM LAUNDRY WHERE RequestID=?");
        pstmt.setString(1, ID);
        pstmt.executeUpdate();

        // remove node from map
        DatabaseManager.getServiceMap().delRequest(type,ID);
    }

    @Override
    void modElement(String ID, LaundryRequest temp) throws SQLException {
        delElement(ID);
        addElement(temp);
    }

    @Override
    void saveElements() throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM LAUNDRY";
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

    @Override
    LaundryRequest getElement(String ID) throws InvalidElementException {
        // unlike employeeManager, we get nodes from the map so that they include neighbors
        if(DatabaseManager.getServiceMap().containsRequest(type,ID)){
            return (LaundryRequest) DatabaseManager.getServiceMap().getRequest(type,ID); //TODO DOES THIS CAST BREAK THINGS
        }else{
            throw new InvalidElementException();
        }
    }

    @Override
    String getCSV_path() {
        return csv_path;
    }

    @Override
    void setCSV_path(String s) {
        csv_path = s;
    }

    @Override
    void cleanTable() throws SQLException {
        //add the clean table functionality
    }

    @Override
    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM LAUNDRY";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            LaundryRequest temp = new LaundryRequest(result.getString(1),result.getString(2),
                    Boolean.parseBoolean(result.getString(3)), result.getString(4), result.getString(5),
                    Boolean.parseBoolean(result.getString(6)),
                    result.getString(7),
                    result.getString(8),
                    Integer.parseInt(result.getString(9)));
            addElement_map(temp);
        }
    }
}
