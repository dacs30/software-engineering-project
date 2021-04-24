package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import javafx.embed.swt.SWTFXUtils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager{
    private static Connection connection = null;
    private static EmployeeManager empManager = null;
    private static NodeManager nodeManager = null;
    private static EdgeManager edgeManager = null;
    public enum selector {NODE,EDGE,EMPLOYEE};
    // maybe store maps here too
    // map super
    // edge map super

    /*
    (Mdb : public)
    start database
        - create db
        - get connection
        - create managers
        - create tables
            - populate tables
            - populate maps

    Shutdown database
        - save tables
        - clear connection
        - clear managers

    (manager : public)
    Reset Table
        - save table
        - clean table
        - reset filepath
        - load
        (if map) - update map

    addElement -> unique
    addElement_db  -> unique
    addElement_map  -> unique
    updateElement  -> unique
    delElement
    getElement  -> unique

    (manager : private)
    loadFromCSV
    saveToCSV
    showInfo
    cleanTable
    setElement_CSV_path

    (maps : public)
    getMap()
     */
    // ==== Mdb methods ==== //
    public static void startup(){
        try{
            Mdb.databaseStartup();
        }catch(InterruptedException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void shutdown(){
        Mdb.databaseShutdown();
    }

    // ==== Manager Methods === //

    public static void resetTable(selector s){
        getManager(s).
    }


    public static void addNode(NodeSuper node){
        getNodeManager().addElement(node);
    };
    public static void addEdge(EdgeSuper edge){
        getEdgeManager().addElement(edge);
    };
    public static void addEmployee(Employee employee){
        getEmpManager().addElement(employee);
    };

    public static void delElement(selector s, String ID) {
        try {
            getManager(s).delElement(ID);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void modNode(String ID, NodeSuper newNode){
        try{
            getNodeManager().modElement(ID,newNode);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static void modEdge(String ID, EdgeSuper newEdge){
        try{
            getEdgeManager().modElement(ID,newEdge);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static void modEmployee(String ID, Employee newEmployee){
        try{
            getEmpManager().modElement(ID,newEmployee);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveElements(selector s){
        try {
            getManager(s).saveElements();
        }catch(FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public static NodeSuper getNode(String ID) throws InvalidElementException {
        return getNodeManager().getElement(ID);
    }
    public static EdgeSuper getEdge(String ID) throws InvalidElementException {
        return getEdgeManager().getElement(ID);
    }
    public static Employee getEmployee(String ID) throws InvalidElementException {
        return getEmpManager().getElement(ID);
    }

    public static String getCSV_path(selector s){
        return getManager(s).getCSV_path();
    }

    public static void setCSV_path(selector s, String path){
        getManager(s).setCSV_path(path);
    }

    public static void cleanTable(selector s){
        try {
            getManager(s).cleanTable();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    // ==== Private DB methods ==== //
    private static Manager getManager(selector s){
        switch(s){
            case NODE:
                return getNodeManager();
            case EDGE:
                return getEdgeManager();
            case EMPLOYEE:
                return getEmpManager();
            default:
                return null;
        }
    }

    // ==== getters and setters ===== //
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        DatabaseManager.connection = connection;
    }

    public static EmployeeManager getEmpManager() {
        if(empManager == null){
            empManager = new EmployeeManager(connection,null);
        }
        return empManager;
    }

    public static NodeManager getNodeManager() {
        if(nodeManager == null){
            nodeManager = new NodeManager(connection, null);
        }
        return nodeManager;
    }

    public static EdgeManager getEdgeManager() {
        if(edgeManager == null){
            edgeManager = new EdgeManager(connection, null);
        }
        return edgeManager;
    }
}
