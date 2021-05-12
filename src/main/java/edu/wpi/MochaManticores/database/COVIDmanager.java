package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Services.COVIDsurvey;
import edu.wpi.MochaManticores.Services.InternalTransportation;
import edu.wpi.MochaManticores.Services.ServiceRequestType;

import java.io.*;
import java.sql.*;

public class COVIDmanager extends Manager<COVIDsurvey> {
    private static String csv_path = "data/services/COVIDsurvey.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";
    private static final ServiceRequestType type = ServiceRequestType.COVID;

    COVIDmanager(Connection connection, String csv_path){
        COVIDmanager.connection = connection;
        if(csv_path != null){
            COVIDmanager.csv_path = csv_path;
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

                COVIDsurvey temp = new COVIDsurvey("",row[1],Boolean.parseBoolean(row[2]),row[3],row[4],
                        Boolean.parseBoolean(row[5]),Boolean.parseBoolean(row[6]),Boolean.parseBoolean(row[7]),
                        Boolean.parseBoolean(row[8]),Boolean.parseBoolean(row[9]),row[10],Boolean.parseBoolean(row[11]));
                addElement(temp);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    void addElement(COVIDsurvey temp) {
        temp.setRequestID(temp.generateRequestID(type));
        addElement_db(temp);
        addElement_map(temp);
    }

    void addElement(COVIDsurvey temp, String ID){
        temp.setRequestID(ID);
        addElement_db(temp);
        addElement_map(temp);
    }

    void addElement_db(COVIDsurvey temp) {
        try {
            String sql = "INSERT INTO COVID (RequestID, EmpID, Completed, NAME, DOB, SICK, VAXX, TRAVEL, TEST, CONATACT, SYMPTOMS, ADMIT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, temp.getRequestID());
            pstmt.setString(2, temp.getEmployee());
            pstmt.setBoolean(3, temp.getCompleted());
            pstmt.setString(4,temp.getName());
            pstmt.setString(5, temp.getDOB());
            pstmt.setBoolean(6, temp.isSick());
            pstmt.setBoolean(7, temp.isVaxx());
            pstmt.setBoolean(8, temp.isTravel());
            pstmt.setBoolean(9, temp.isTest());
            pstmt.setBoolean(10, temp.isContact());
            pstmt.setString(11, temp.getSymptoms());
            pstmt.setBoolean(12,temp.isAdmit());

            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    void addElement_map(COVIDsurvey temp) {
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
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM COVID WHERE RequestID=?");
        pstmt.setString(1, ID);
        pstmt.executeUpdate();

        // remove node from map
        DatabaseManager.getServiceMap().delRequest(type,ID);
    }

    @Override
    void modElement(String ID, COVIDsurvey temp) throws SQLException {
        delElement(ID);
        addElement(temp , ID);
    }

    @Override
    void saveElements() throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM COVID";
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
    COVIDsurvey getElement(String ID) throws InvalidElementException {
        // unlike employeeManager, we get nodes from the map so that they include neighbors
        if(DatabaseManager.getServiceMap().containsRequest(type,ID)){
            return (COVIDsurvey) DatabaseManager.getServiceMap().getRequest(type,ID); //TODO DOES THIS CAST BREAK THINGS
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
        String sql = "DELETE FROM COVID";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();

        //clean hashmap
        DatabaseManager.getServiceMap().clearMap(ServiceRequestType.COVID);
    }

    @Override
    void cleanMap(){
        DatabaseManager.getServiceMap().clearMap(ServiceRequestType.COVID);
    }

    @Override
    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM COVID";
        Statement stmt = connection.createStatement();
        ResultSet row = stmt.executeQuery(sql);
        while (row.next()) {
            COVIDsurvey temp = new COVIDsurvey(row.getString(1),row.getString(2),Boolean.parseBoolean(row.getString(3)),
                    row.getString(4),row.getString(5), Boolean.parseBoolean(row.getString(6)),Boolean.parseBoolean(row.getString(7)),
                    Boolean.parseBoolean(row.getString(8)), Boolean.parseBoolean(row.getString(9)),Boolean.parseBoolean(row.getString(10)),
                    row.getString(11),Boolean.parseBoolean(row.getString(12)));
            addElement_map(temp);
        }
    }
}
