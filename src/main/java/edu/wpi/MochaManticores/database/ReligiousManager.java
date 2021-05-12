package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Services.InternalTransportation;
import edu.wpi.MochaManticores.Services.ReligiousRequest;
import edu.wpi.MochaManticores.Services.ServiceRequestType;

import java.io.*;
import java.sql.*;

public class ReligiousManager extends Manager<ReligiousRequest> {
    private static String csv_path = "data/services/ReligiousRequests.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";
    private static final ServiceRequestType type = ServiceRequestType.ReligiousRequest;

    ReligiousManager(Connection connection, String csv_path){
        ReligiousManager.connection = connection;
        if(csv_path != null){
            ReligiousManager.csv_path = csv_path;
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

                ReligiousRequest temp = new ReligiousRequest("",row[1],Boolean.parseBoolean(row[2]),
                        row[3],row[4],row[5]);
                addElement(temp);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    void addElement(ReligiousRequest v) {
        v.setRequestID(v.generateRequestID(type));
        addElement_db(v);
        addElement_map(v);
    }

    void addElement(ReligiousRequest temp, String ID){
        temp.setRequestID(ID);
        addElement_db(temp);
        addElement_map(temp);
    }

    void addElement_db(ReligiousRequest temp) {
        try {
            String sql = "INSERT INTO RELREQ (RequestID, EmpID, completed, reasonVisit, location, typeSacredPerson) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, temp.getRequestID());
            pstmt.setString(2, temp.getEmployee());
            pstmt.setBoolean(3, temp.getCompleted());
            pstmt.setString(4,temp.getReasonVisit());
            pstmt.setString(5, temp.getLocation());
            pstmt.setString(6, temp.getTypeSacredPerson());
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    void addElement_map(ReligiousRequest temp) {
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
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM RELREQ WHERE RequestID=?");
        pstmt.setString(1, ID);
        pstmt.executeUpdate();

        // remove node from map
        DatabaseManager.getServiceMap().delRequest(type,ID);
    }

    @Override
    void modElement(String ID, ReligiousRequest temp) throws SQLException {
        delElement(ID);
        addElement(temp , ID);
    }

    @Override
    void saveElements() throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM RELREQ";
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
    ReligiousRequest getElement(String ID) throws InvalidElementException {
        // unlike employeeManager, we get nodes from the map so that they include neighbors
        if(DatabaseManager.getServiceMap().containsRequest(type,ID)){
            return (ReligiousRequest) DatabaseManager.getServiceMap().getRequest(type,ID); //TODO DOES THIS CAST BREAK THINGS
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
        String sql = "DELETE FROM RELREQ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        int result = pstmt.executeUpdate();

        //clean hashmap
        DatabaseManager.getServiceMap().clearMap(ServiceRequestType.ReligiousRequest);
    }

    @Override
    void cleanMap(){
        DatabaseManager.getServiceMap().clearMap(ServiceRequestType.ReligiousRequest);
    }

    @Override
    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM RELREQ";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            ReligiousRequest temp = new ReligiousRequest(result.getString(1),result.getString(2),
                    Boolean.parseBoolean(result.getString(3)), result.getString(4), result.getString(5),
                    result.getString(6));
            addElement_map(temp);
        }
    }
}
