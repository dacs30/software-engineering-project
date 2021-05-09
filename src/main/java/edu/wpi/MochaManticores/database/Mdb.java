package edu.wpi.MochaManticores.database;


import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.FloralDelivery;
import edu.wpi.MochaManticores.Services.SanitationServices;
import org.apache.derby.drda.*;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class Mdb extends Thread{

    private DatabaseMetaData meta;
    private Connection connection = null;
    public String JDBC_EMBED = "jdbc:derby:Mdatabase;create=true";
    public String JDBC_SERVER = "jdbc:derby://localhost:1527/Mdatabase;create=true";

    /* function nodeStartup()
     * creates the node table if it does not already exist, then populates the table and map
     */
    public void nodeStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        //create data tables
        try {
            ResultSet rs = meta.getTables(null, "APP", "NODES", null);
            if (!rs.next()) {
                System.out.println("Creating Node Table");
                String sql = "CREATE TABLE NODES" +
                        "(nodeID CHAR(10) not NULL, " +
                        " xcoord INT, " +
                        " ycoord INT, " +
                        " floor VARCHAR(3), " +
                        " building VARCHAR(50), " +
                        " nodeType CHAR(4), " +
                        " longName VARCHAR(255)," +
                        " shortName VARCHAR(255), " +
                        " PRIMARY KEY (nodeID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getNodeManager().loadFromCSV();
            }else{
                DatabaseManager.getNodeManager().cleanTable();
                DatabaseManager.getNodeManager().loadFromCSV();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* function edgeStartup()
     * creates the edge table if it does not already exist, then populates the table and map
     */
    public void edgeStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EDGES", null);
            rs = meta.getTables(null, "APP", "EDGES", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Edges Table");
                sql = "CREATE TABLE EDGES" +
                        "(edgeID CHAR(21) not NULL, " +
                        " startNode CHAR(10), " +
                        " endNode CHAR(10), " +
                        " PRIMARY KEY (edgeID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getEdgeManager().loadFromCSV();
            }else{
                DatabaseManager.getEdgeManager().cleanTable();
                DatabaseManager.getEdgeManager().loadFromCSV();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* function employeeStartup()
     * creates the employee table if it does not already exist, then populates the table
     */
    public void employeeStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Employees Table");
                sql = "CREATE TABLE EMPLOYEES" +
                        "(username VARCHAR(21) not NULL, " +
                        " password VARCHAR(55), " +
                        " firstName VARCHAR(21), " +
                        " lastName VARCHAR(21), " +
                        " employeeType VARCHAR(21)," +
                        " ID INTEGER," +
                        " AdminLevel BOOLEAN," +
                        " covidStatus BOOLEAN," +
                        " parkingSpot VARCHAR (21)," +
                        " PRIMARY KEY (username))";
                stmt.executeUpdate(sql);
                DatabaseManager.getEmpManager().loadFromCSV();
            }else{
                DatabaseManager.getEmpManager().cleanTable();
                DatabaseManager.getEmpManager().loadFromCSV();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void EXTtransportStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "EXTTRANSPORT", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating External Transportation Request Table");
                sql = "CREATE TABLE EXTTRANSPORT" +
                        "(RequestID VARCHAR(21) not NULL, " +
                        " EmpID VARCHAR(21), " +
                        " completed BOOLEAN, " +
                        " patientRoom VARCHAR(21), " +
                        " currentRoom VARCHAR(21)," +
                        " externalRoom VARCHAR(21)," +
                        " transportationMethod VARCHAR(21)," +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getExtTransportManager().loadFromCSV();
            }else{
                DatabaseManager.getExtTransportManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void FloralDeliveryStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "FLORALDEL", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Floral Delivery Request Table");
                sql = "CREATE TABLE FLORALDEL" +
                        "(RequestID VARCHAR(21) not NULL, " +
                        " EmpID VARCHAR(21), " +
                        " completed BOOLEAN, " +
                        " roomNum VARCHAR(21), " +
                        " deliveryChoice VARCHAR(100), " +
                        " typeFlowers VARCHAR(40), " +
                        " vaseOptions VARCHAR(40)," +
                        " personalizedNote VARCHAR(200)," +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getFloralDeliveryManager().loadFromCSV();
            }else{
                DatabaseManager.getFloralDeliveryManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void FoodDeliveryStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "FOODDEL", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Food Delivery Request Table");
                sql = "CREATE TABLE FOODDEL" +
                        "(RequestID VARCHAR(21) not NULL, " +
                        " EmpID VARCHAR(21), " +
                        " completed BOOLEAN, " +
                        " dietaryPreferences VARCHAR(40), " +
                        " allergies VARCHAR(150), " +
                        " menu VARCHAR(30), " +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getFoodDeliveryManager().loadFromCSV();
            }else{
                DatabaseManager.getFoodDeliveryManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void InternalTransportationStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "INTTRANSPORT", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Internal Transportation Request Table");
                sql = "CREATE TABLE INTTRANSPORT" +
                        "(RequestID VARCHAR(21) not NULL, " +
                        " EmpID VARCHAR(21), " +
                        " completed BOOLEAN, " +
                        " patientID VARCHAR(21), " +
                        " numStaffNeeded INTEGER, " +
                        " Destination VARCHAR(21), " +
                        " TransportationMethod VARCHAR(21)," +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getIntTransportManager().loadFromCSV();
            }else{
                DatabaseManager.getIntTransportManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void SanitationServicesStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "SANITATIONSER", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Sanitation Services Request Table");
                sql = "CREATE TABLE SANITATIONSER" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        " EmpID VARCHAR(21), " +
                        " completed BOOLEAN, " +
                        "location VARCHAR(40)," +
                        " safetyHazards VARCHAR(50), " +
                        " sanitationType VARCHAR(50), " +
                        " equipmentNeeded VARCHAR(100), " +
                        " description VARCHAR(200)," +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getSanitationServices().loadFromCSV();
            }else{
                DatabaseManager.getSanitationServices().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void EmergencyRequestStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "EMGREQ", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Emergency Services Request Table");
                sql = "CREATE TABLE EMGREQ" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        " EmpID VARCHAR(21), " +
                        " completed BOOLEAN, " +
                        "numPeopleNeeded INTEGER," +
                        "location VARCHAR(50), " +
                        "gurney BOOLEAN, " +
                        "PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getEmergencyManager().loadFromCSV();
            }else{
                DatabaseManager.getEmergencyManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ReligiousRequestStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "RELREQ", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Religious Services Request Table");
                sql = "CREATE TABLE RELREQ" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        " EmpID VARCHAR(21)," +
                        " completed BOOLEAN," +
                        "reasonVisit VARCHAR(50)," +
                        "location VARCHAR(50), " +
                        "typeSacredPerson VARCHAR(50), " +
                        "PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getReligiousManager().loadFromCSV();
            }else{
                DatabaseManager.getReligiousManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void MedicineRequestStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "MEDREQ", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Medicine Services Request Table");
                sql = "CREATE TABLE MEDREQ" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        "EmpID VARCHAR(30)," +
                        "completed BOOLEAN," +
                        "typeMedicine VARCHAR(50)," +
                        "currentFeeling VARCHAR(200), " +
                        "allergies VARCHAR(100)," +
                        "patientRoom VARCHAR(50)," +
                        "PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getMedicineRequestManager().loadFromCSV();
            }else{
                DatabaseManager.getMedicineRequestManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void LaundryRequestStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "LAUNDRY", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Laundry Services Request Table");
                sql = "CREATE TABLE LAUNDRY" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        "EmpID VARCHAR(30)," +
                        "completed BOOLEAN," +
                        "patientName VARCHAR(50)," +
                        "soilLevel VARCHAR(10), " +
                        "delicates BOOLEAN," +
                        "washCycleTemperature VARCHAR(10)," +
                        "dryCycleTemperature VARCHAR(10)," +
                        "dryCycleNumber INTEGER," +
                        "PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getLaundryManager().loadFromCSV();
            }else{
                DatabaseManager.getLaundryManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void LanguageInterpreterStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "LANGINTREQ", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Language Services Request Table");
                sql = "CREATE TABLE LANGINTREQ" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        "EmpID VARCHAR(30)," +
                        "completed BOOLEAN," +
                        "room VARCHAR(21)," +
                        "languageOne VARCHAR(50), " +
                        "languageTwo VARCHAR(50)," +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getLanguageInterpreterManager().loadFromCSV();
            }else{
                DatabaseManager.getLanguageInterpreterManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void CovidSurveyStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "COVID", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating COVID survey response Table");
                sql = "CREATE TABLE COVID" +
                        "(RequestID VARCHAR(40) not NULL, " +
                        "EmpID VARCHAR(30)," +
                        "completed BOOLEAN," +
                        "name VARCHAR(30)," +
                        "DOB VARCHAR(10)," +
                        "SICK BOOLEAN," +
                        "VAXX BOOLEAN," +
                        "TRAVEL BOOLEAN," +
                        "TEST BOOLEAN," +
                        "CONATACT BOOLEAN," +
                        "SYMPTOMS VARCHAR(150)," +
                        "ADMIT BOOLEAN," +
                        " PRIMARY KEY (RequestID))";
                stmt.executeUpdate(sql);
                DatabaseManager.getCOVIDManager().loadFromCSV();
            }else{
                DatabaseManager.getCOVIDManager().updateElementMap();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    /*
    function embedded startup()
    starts the embedded database connection
     */
    public void embeddedStartup(){
        System.out.println("-------Embedded Apache Derby Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
            System.out.println("For IntelliJ do the following:");
            System.out.println("File | Project Structure, Modules, Dependency tab");
            System.out.println("Add by clicking on the green plus icon on the right of the window");
            System.out.println("Select JARs or directories. Go to the folder where the database JAR is located");
            System.out.println("Click OK, now you can compile your program and run it.");
            e.printStackTrace();
            return;
        }

        System.out.println("Apache Derby driver registered!\n");
        connection = null;

        try {
            connection = DriverManager.getConnection(JDBC_EMBED);
            DatabaseManager.setConnection(connection);
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    /*
    function serverStartup()
    starts Mdatabase with a server connection
     */
    // TODO update method for all connected databases using an observer model
    public void serverStartup() {
        System.out.println("-------Server-Client Apache Derby Connection--------");
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
            System.out.println("For IntelliJ do the following:");
            System.out.println("File | Project Structure, Modules, Dependency tab");
            System.out.println("Add by clicking on the green plus icon on the right of the window");
            System.out.println("Select JARs or directories. Go to the folder where the database JAR is located");
            System.out.println("Click OK, now you can compile your program and run it.");
            e.printStackTrace();
            return;
        }

        // start network server
        try {
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            } catch (Exception e) {
                e.printStackTrace();
        }

        System.out.println("Apache Derby driver registered!\n");
        connection = null;

        try {
            connection = DriverManager.getConnection(JDBC_SERVER);
            DatabaseManager.setConnection(connection);
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    /* function databaseStartup()
     * creates database connection and calls startup threads
     */
    public void databaseStartup(boolean embedded) throws InterruptedException, SQLException {
        if(embedded){
            embeddedStartup();
        }else{
            serverStartup();
        }
        //create hashmaps here
        DatabaseManager.getServiceMap();

        //create data tables
            Thread nodeThread = new Thread(() -> {
                try {
                    nodeStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread edgeThread = new Thread(() -> {
                try {
                    edgeStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread employeeThread = new Thread(() -> {
                try {
                    employeeStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread EXTtransportThread = new Thread(() -> {
                try {
                    EXTtransportStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread INTtransportThread = new Thread(() -> {
                try {
                    InternalTransportationStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread FloralDeliveryThread = new Thread(() -> {
                try {
                    FloralDeliveryStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread FoodDeliveryThread = new Thread(() -> {
                try {
                    FoodDeliveryStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread SanitationServicesThread = new Thread(() -> {
                try {
                    SanitationServicesStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread EmergencyRequestThread = new Thread(() -> {
                try {
                    EmergencyRequestStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread ReligiousRequestThread = new Thread(() -> {
                try {
                    ReligiousRequestStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread LanguageInterpreterThread = new Thread(() -> {
                try {
                    LanguageInterpreterStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread LaundryThread = new Thread(() -> {
                try {
                    LaundryRequestStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread MedicineThread = new Thread(() -> {
                try {
                    MedicineRequestStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread COVIDthread = new Thread(() -> {
                try {
                    CovidSurveyStartup();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });


            nodeThread.start();
            edgeThread.start();
            employeeThread.start();


            EXTtransportThread.start();
            FloralDeliveryThread.start();
            FoodDeliveryThread.start();
            SanitationServicesThread.start();
            INTtransportThread.start();
            EmergencyRequestThread.start();
            ReligiousRequestThread.start();
            LanguageInterpreterThread.start();
            LaundryThread.start();
            MedicineThread.start();
            COVIDthread.start();



            nodeThread.join();
            edgeThread.join();
            employeeThread.join();

            EXTtransportThread.join();
            INTtransportThread.join();
            FloralDeliveryThread.join();
            FoodDeliveryThread.join();
            SanitationServicesThread.join();
            EmergencyRequestThread.join();
            ReligiousRequestThread.join();
            LanguageInterpreterThread.join();
            LaundryThread.join();
            MedicineThread.join();
            COVIDthread.join();

            // updates the hm here because the data doesnt exist if we do it in the threads, where is map super created?
            DatabaseManager.getNodeManager().updateElementMap();
            DatabaseManager.getEdgeManager().updateElementMap();

    }

    /* function databaseShutdown()
     * clears connection and saves the tables.
     */
    public void databaseShutdown(){
        try {
            for(sel s : sel.values()){
                DatabaseManager.getManager(s).saveElements();
            }
            connection = null;
            DatabaseManager.setConnection(null);
        }catch(FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}