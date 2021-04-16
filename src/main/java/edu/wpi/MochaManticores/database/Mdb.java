package edu.wpi.MochaManticores.database;


import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;


public class Mdb extends Thread{

    private static DatabaseMetaData meta;
    private static Connection connection = null;
    public static String JDBC_URL = "jdbc:derby:Mdatabase;create=true";


    public static void nodeStartup(Connection connection) throws SQLException {
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
                NodeManager.loadFromCSV(connection);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void edgeStartup(Connection connection) throws SQLException {
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
                EdgeManager.loadFromCSV(connection);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void databaseStartup() throws InterruptedException, FileNotFoundException, SQLException {
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
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }


        //create data tables
            Connection finalConnection = connection;
            Thread nodeThread = new Thread(() -> {
                try {
                    nodeStartup(finalConnection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Thread edgeThread = new Thread(() -> {
                try {
                    edgeStartup(finalConnection);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            nodeThread.start();
            edgeThread.start();

            nodeThread.join();
            edgeThread.join();
    }

    public static void databaseShutdown() throws InterruptedException, FileNotFoundException, SQLException {

    }




    public static void showMenu() {
        System.out.print("1 - Node Information\n" +
                "2 - Update Node Coordinates\n" +
                "3 - Update Node Location Long Name\n" +
                "4 - Edge Information\n" +
                "5 - Exit Program\n");
    }

    public static void commandLineMenu(String[] args, Connection connection) throws FileNotFoundException, SQLException {
        if(args.length != 1) {
            showMenu();
        }
        else {
            Scanner scanner = new Scanner(System.in);
            int inputVal = Integer.parseInt(args[0]);
            switch(inputVal) {
                case 1:
                    NodeManager.showNodeInformation(connection);
                    break;
                case 2:
                    System.out.print("Enter NodeID of the Node's Coordinates to be Changed: \n");
                    String idForCoordinates = scanner.nextLine();
                    System.out.print("Enter a new X Coordinate: \n");
                    int xcoord = scanner.nextInt();
                    System.out.print("Enter a new Y Coordinate: \n");
                    int ycoord = scanner.nextInt();
                    NodeManager.updateNodeCoords(connection, idForCoordinates, xcoord, ycoord);
                    System.out.printf("Node with id %s has been updated!\n", idForCoordinates);
                    break;
                case 3:
                    System.out.print("Enter NodeID of the Node who's Long Name You'd Like to Change:\n");
                    String idForName = scanner.nextLine();
                    System.out.print("Enter New Name:\n");
                    String newName = scanner.nextLine();
                    NodeManager.updateNodeName(connection, idForName, newName);
                    System.out.printf("Node with id %s has been updated with a new name\n", idForName);
                    break;
                case 4:
                    EdgeManager.showEdgeInformation(connection);
                    break;
                case 5:
                    System.out.println("Exiting Program!");
                    return;
                default:
                    System.out.println("Something seems to have gone wrong!");

            }
        }
    }
}