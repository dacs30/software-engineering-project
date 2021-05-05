package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Services.FoodDelivery;
import edu.wpi.MochaManticores.Services.ServiceRequestType;

import java.io.*;
import java.sql.*;

public class FoodDeliveryManager extends Manager<FoodDelivery> {
    private static String csv_path = "data/services/FoodDelivery.csv";
    private static Connection connection = null;
    private static final String CSVdelim = ",";
    private static final ServiceRequestType type = ServiceRequestType.FoodDelivery;

    FoodDeliveryManager(Connection connection, String csv_path){
        this.connection = connection;
        if(csv_path != null){
            this.csv_path = csv_path;
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

                FoodDelivery temp = new FoodDelivery("",row[1],Boolean.parseBoolean(row[2]),
                        row[3],row[4],row[5]);
                addElement(temp);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    void addElement(FoodDelivery v) {
        v.setRequestID(v.generateRequestID(this.type));
        addElement_db(v);
        addElement_map(v);
    }

    void addElement_db(FoodDelivery temp) {

        try {
            String sql = "INSERT INTO FOODDEL (RequestID, EmpID, completed, dietaryPreferences, allergies, menu)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, temp.getRequestID());
            pstmt.setString(2, temp.getEmployee());
            pstmt.setBoolean(3, temp.getCompleted());
            pstmt.setString(4,temp.getDietaryPreference());
            pstmt.setString(5, temp.getAllergies());
            pstmt.setString(6, temp.getMenu());
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    void addElement_map(FoodDelivery temp) {
        if(!DatabaseManager.getServiceMap().containsRequest(this.type, temp.RequestID)) {
            DatabaseManager.getServiceMap().addRequest(this.type,temp);
        }
        else {
            System.out.printf("This node %s already exists\n", temp.RequestID);
        }
    }

    @Override
    void delElement(String ID) throws SQLException {
        //remove node from database
        PreparedStatement pstmt = connection.prepareStatement("DELETE FROM FOODDEL WHERE RequestID=?");
        pstmt.setString(1, ID);
        pstmt.executeUpdate();

        // remove node from map
        DatabaseManager.getServiceMap().delRequest(this.type,ID);
    }

    @Override
    void modElement(String ID, FoodDelivery temp) throws SQLException {
        try {
            delElement(ID);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        addElement(temp);
    }

    @Override
    void saveElements() throws FileNotFoundException, SQLException {
        PrintWriter pw = new PrintWriter(new File(csv_path));
        StringBuilder sb = new StringBuilder();

        String sql = "SELECT * FROM FOODDEL";
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
    FoodDelivery getElement(String ID) throws InvalidElementException {
        // unlike employeeManager, we get nodes from the map so that they include neighbors
        if(DatabaseManager.getServiceMap().containsRequest(this.type,ID)){
            return (FoodDelivery) DatabaseManager.getServiceMap().getRequest(type,ID); //TODO DOES THIS CAST BREAK THINGS
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
        //TODO: implement clean table
    }

    public void updateElementMap() throws SQLException {
        String sql = "SELECT * FROM FOODDEL";
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            FoodDelivery temp = new FoodDelivery(result.getString(1),result.getString(2),
                    Boolean.parseBoolean(result.getString(3)), result.getString(4), result.getString(5),
                    result.getString(6));
            addElement_map(temp);
        }
    }
}
