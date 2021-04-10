package edu.wpi.MochaManticores.database;


import java.io.FileNotFoundException;
import java.sql.*;

public class Mdb {

    private static DatabaseMetaData meta;
    public static String JDBC_URL = "jdbc:derby:Mdatabase;create=true";
    private static final String Edge_csv_path = "data/edges.csv";
    private static final String Node_csv_path = "data/nodes.csv";


    public static void databaseStartup() throws SQLException, FileNotFoundException {
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
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL);
            meta = connection.getMetaData();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
        Statement stmt = connection.createStatement();
        CSVmanager nodeCSV = new CSVmanager(Node_csv_path);
        CSVmanager edgeCSV = new CSVmanager(Edge_csv_path);
        //create data tables
        try {
            ResultSet rs = meta.getTables(null, "APP", "NODES", null);
            if(!rs.next()) {
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
                nodeCSV.load_node_csv(connection);
            }
            nodeCSV.putNodesInMap(connection);
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
                edgeCSV.load_edges_csv(connection);
            }
            edgeCSV.updateEdgesInMap(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }


//        //Menu
//        if(args.length != 1) {
//            showMenu();
//        }
//        else {
//            Scanner scanner = new Scanner(System.in);
//            int inputVal = Integer.parseInt(args[0]);
//            switch(inputVal) {
//                case 1:
//                    NodeManager.showNodeInformation(nodeCSV.printNodes(connection));
//                    break;
//                case 2:
//                    System.out.print("Enter NodeID of the Node's Coordinates to be Changed: \n");
//                    String idForCoordinates = scanner.nextLine();
//                    System.out.print("Enter a new X Coordinate: \n");
//                    int xcoord = scanner.nextInt();
//                    System.out.print("Enter a new Y Coordinate: \n");
//                    int ycoord = scanner.nextInt();
//                    NodeManager.updateNode(connection, idForCoordinates, xcoord, ycoord);
//                    System.out.printf("Node with id %s has been updated!\n", idForCoordinates);
//                    break;
//                case 3:
//                    System.out.print("Enter NodeID of the Node who's Long Name You'd Like to Change:\n");
//                    String idForName = scanner.nextLine();
//                    System.out.print("Enter New Name:\n");
//                    String newName = scanner.nextLine();
//                    NodeManager.updateNodeName(connection, idForName, newName);
//                    System.out.printf("Node with id %s has been updated with a new name\n", idForName);
//                    break;
//                case 4:
//                    EdgeManager.showEdgeInformation(edgeCSV.printEdges(connection));
//                    break;
//                case 5:
//                    System.out.println("Exiting Program!");
//                    return;
//                default:
//                    System.out.println("Something seems to have gone wrong!");
//
//            }
//        }
    }

    public static void showMenu() {
        System.out.print("1 - Node Information\n" +
                "2 - Update Node Coordinates\n" +
                "3 - Update Node Location Long Name\n" +
                "4 - Edge Information\n" +
                "5 - Exit Program\n");
    }

    public static String getEdge_csv_path() {
        return Edge_csv_path;
    }

    public static String getNode_csv_path() {
        return Node_csv_path;
    }
}