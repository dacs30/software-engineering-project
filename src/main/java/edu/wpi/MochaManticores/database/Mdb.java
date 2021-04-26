package edu.wpi.MochaManticores.database;


import edu.wpi.MochaManticores.App;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class Mdb extends Thread{

    private static DatabaseMetaData meta;
    private static Connection connection = null;
    public static String JDBC_URL = "jdbc:derby:Mdatabase;create=true";

    /* function nodeStartup()
     * creates the node table if it does not already exist, then populates the table and map
     */
    public static void nodeStartup() throws SQLException {
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
    public static void edgeStartup() throws SQLException {
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
    public static void employeeStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            if(!rs.next()) {
                String sql;
                System.out.println("Creating Employees Table");
                sql = "CREATE TABLE EMPLOYEES" +
                        "(username VARCHAR(21) not NULL, " +
                        " password VARCHAR(21), " +
                        " fisrtName VARCHAR(21), " +
                        " lastName VARCHAR(21), " +
                        " employeeType VARCHAR(21)," +
                        " ID INT," +
                        " AdminLevel BOOLEAN," +
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

    public static void EXTtransportStartup() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
            ResultSet rs = meta.getTables(null, "APP", "EMPLOYEES", null);
            rs = meta.getTables(null, "APP", "EMPLOYEES", null);
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
                //DatabaseManager.getEmpManager().loadFromCSV();
            }else{
                //DatabaseManager.getEmpManager().cleanTable();
                //DatabaseManager.getEmpManager().loadFromCSV();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /* function databaseStartup()
     * creates database connection and calls startup threads
     */
    public static void databaseStartup() throws InterruptedException, SQLException {
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
            connection = DriverManager.getConnection(JDBC_URL);
            DatabaseManager.setConnection(connection);
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }

        //create hashmaps here

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


            nodeThread.start();
            edgeThread.start();
            employeeThread.start();
            EXTtransportThread.start();

            nodeThread.join();
            edgeThread.join();
            employeeThread.join();
            EXTtransportThread.join();

            // updates the hm here because the data doesnt exist if we do it in the threads, where is map super created?
            DatabaseManager.getNodeManager().updateElementMap();
            DatabaseManager.getEdgeManager().updateElementMap();

    }

    /* function databaseShutdown()
     * clears connection and saves the tables.
     */
    public static void databaseShutdown(){
        try {
            DatabaseManager.getNodeManager().saveElements();
            DatabaseManager.getEdgeManager().saveElements();
            DatabaseManager.getEmpManager().saveElements();
            connection = null;
            DatabaseManager.setConnection(null);
        }catch(FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}