package edu.wpi.MochaManticores.database;

import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.Exceptions.InvalidPermissionsException;
import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
import edu.wpi.MochaManticores.Nodes.EdgeSuper;
import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.Services.ServiceMap;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.messaging.Message;
import javafx.scene.shape.Mesh;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class DatabaseManager{
    private static Connection connection = null;
    private static Mdb mdb = null;
    private static EmployeeManager empManager = null;
    private static NodeManager nodeManager = null;
    private static EdgeManager edgeManager = null;

    public static EmergencyManager emergencyManager = null;
    public static ExtTransportManager extTransportManager = null;
    public static IntTransportManager intTransportManager = null;
    public static FoodDeliveryManager foodDeliveryManager = null;
    public static FloralDeliveryManager floralDeliveryManager = null;
    public static SanitationServiceManager sanitationServiceManager = null;
    public static ReligiousManager religiousManager = null;
    public static MedicineRequestManager medicineRequestManager = null;
    public static LaundryManager laundryManager = null;
    public static LanguageInterpreterManager languageInterpreterManager = null;
    public static COVIDmanager coviDmanager = null;

    private static ServiceMap serviceMap = null;

    // ==== Mdb methods ==== //

    /*  function: startup()
     *  starts the database, should only be run on app startup
     *  @return void
     */
    public static void startup(){
        try{
            getMdb().databaseStartup(); // start with server connection

            //TODO is this useful?
            for (sel s : sel.values()) {
                refreshTable(s);
            }

        }catch(InterruptedException | SQLException e){
            e.printStackTrace();
        }
    }

    /*  function: shutdown()
     *  closes the database connection and saves CSV's
     *  @return void
     */
    public static void shutdown(){
        getMdb().databaseShutdown();
    }

    // ==== Manager Methods === //

    /*  function: resetTable(sel, string)
     *  resets the selected table to the CSV at the path given.
     *  @return void
     */
    public static void resetTable(sel s, String newPath){
        try {
            Manager man = getManager(s);
            System.out.println("resetting Table: " + s);
            man.saveElements();
            man.cleanTable();
            man.setCSV_path(newPath);
            man.loadFromCSV();
        }catch(SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void refreshTable(sel s){
        try {
            Manager man = getManager(s);
            System.out.println("resetting Table: " + s);
            man.cleanMap();
            man.updateElementMap();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /* function: addRequest()
     * adds request to specified manager table
     * @ return void
     */
    public static void addRequest(sel s, ServiceRequest request){
        getManager(s).addElement(request);
        sendUpdate(s);
    }

    /*  function: addNode()
     *  adds a node to the database and MapSuper()
     *  @return void
     */
    public static void addNode(NodeSuper node){
        getNodeManager().addElement(node);
        sendUpdate(sel.NODE);
    }

    /*  function: addEdge()
     *  adds an Edge to the database and EdgeMapSuper()
     *  @return void
     */
    public static void addEdge(EdgeSuper edge){
        getEdgeManager().addElement(edge);
        sendUpdate(sel.EDGE);
    }

    /*  function: addEmployee()
     *  adds an employee to the database
     *  @return void
     */
    public static void addEmployee(Employee employee){
        getEmpManager().addElement(employee);
        sendUpdate(sel.EMPLOYEE);
    }

    /*  function: addElement()
     *  deletes a selected element with a given ID
     *  @return void
     */
    public static void delElement(sel s, String ID) {
        try {
            getManager(s).delElement(ID);
            sendUpdate(s);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /*  function:  modRequest()
     *  swaps a specifed node with a new node
     *  @return void
     */
    public static void modRequest(sel s, String ID, ServiceRequest request){
        try{
            getManager(s).modElement(ID,request);
            sendUpdate(s);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*  function:  modNode()
     *  swaps a specifed node with a new node
     *  @return void
     */
    public static void modNode(String ID, NodeSuper newNode){
        try{
            getNodeManager().modElement(ID,newNode);
            sendUpdate(sel.NODE);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*  function:  modEdge()
     *  swaps a specifed edge with a new edge
     *  @return void
     */
    public static void modEdge(String ID, EdgeSuper newEdge){
        try{
            getEdgeManager().modElement(ID,newEdge);
            sendUpdate(sel.EDGE);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*  function:  modEmployee()
     *  swaps a specifed employee with a new employee
     *  @return void
     */
    public static void modEmployee(String ID, Employee newEmployee){
        try{
            getEmpManager().modElement(ID,newEmployee);
            sendUpdate(sel.EMPLOYEE);
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /* function: getRequest()
     * returns a request object, you will need to cast it
     * return ServiceRequest
     */
    public static ServiceRequest getRequest(sel s, String ID) throws InvalidElementException {
        return (ServiceRequest) getManager(s).getElement(ID);  // TODO GENERALIZE ALL MANAGERS
    }

    /*  function:  getNode()
     *  returns a node specifed by ID
     *  @return NodeSuper
     */
    public static NodeSuper getNode(String ID) throws InvalidElementException {
        return getNodeManager().getElement(ID);
    }

    /*  function:  getEdge()
     *  returns a Edge specifed by ID
     *  @return EdgeSuper
     */
    public static EdgeSuper getEdge(String ID) throws InvalidElementException {
        return getEdgeManager().getElement(ID);
    }
    /*  function:  getEmployee()
     *  returns a Employee specifed by ID
     *  @return Employee
     */
    public static Employee getEmployee(String ID) throws InvalidElementException {
        return getEmpManager().getElement(ID);
    }

    /*  function:  getCSV_path()
     *  gets the operating CSV path of selection
     *  @return string
     */
    public static String getCSV_path(sel s){
        return getManager(s).getCSV_path();
    }

    /* function: getElementIDs()
     * returns a linked list of strings that contains all node names
     */
    public static LinkedList<Pair<String,String>> getElementIDs(){
        //TODO abstract to work with all types
        return getNodeManager().getElementIDs();
    }


    // ==== Employee specials ==== //

    /*  function:  checkEmployeeLogin()
     *  checks user password and clearance, throws invalid login if pass is wrong, throws invalid element if user doesnt exist
     *  @return Employee
     */
    public static Employee checkEmployeeLogin(String usr, String pass) throws InvalidLoginException, InvalidElementException {
        return getEmpManager().checkEmployeeLogin(usr,pass);
    }

    /* function: getEmployeeNamesList()
     * returns a linked list of strings that contains all employee names
     */
    public static LinkedList<String> getEmployeeNames(){
        //TODO abstract to work with all types
        return getEmpManager().getEmployeeNames();
    }

    /*  function:  checkAdminLogin()
     *  checks user password and clearance, throws invalid login if pass is wrong, throws invalid element if user doesnt exist,
     *  Throws invalid permissions if user is not an admin
     *  @return Employee
     */
    public static Employee checkAdminLogin(String usr, String pass) throws InvalidLoginException, InvalidPermissionsException, InvalidElementException{
        return getEmpManager().checkAdminLogin(usr,pass);
    }

    /*  function:  getManager()
     *  gets a manager based on a selection, we are using this to abstract the manager classes in the API,
     *  @return Manager
     */
    public static Manager getManager(sel s){
        switch(s){
            case NODE:
                return getNodeManager();
            case EDGE:
                return getEdgeManager();
            case EMPLOYEE:
                return getEmpManager();
            case InternalTransportation:
                return getIntTransportManager();
            case ExternalTransportation:
                return getExtTransportManager();
            case FloralDelivery:
                return getFloralDeliveryManager();
            case FoodDelivery:
                return getFoodDeliveryManager();
            case SanitationServices:
                return getSanitationServices();
            case Emergency:
                return getEmergencyManager();
            case ReligiousRequest:
                return getReligiousManager();
            case LanguageInterperter:
                return getLanguageInterpreterManager();
            case Medicine:
                return getMedicineRequestManager();
            case Laundry:
                return getLaundryManager();
            case COVID:
                return getCOVIDManager();
            default:
                System.out.println("No Manager Found");
                return null;
        }
    }

    public static sel getManagerFromString(String s) {
        switch (s) {
            case "NODE":
                return sel.NODE;
            case "EDGE":
                return sel.EDGE;
            case "EMPLOYEE":
                return sel.EMPLOYEE;
            case "InternalTransportation":
                return sel.InternalTransportation;
            case "ExternalTransportation":
                return sel.ExternalTransportation;
            case "FloralDelivery":
                return sel.FloralDelivery;
            case "FoodDelivery":
                return sel.FoodDelivery;
            case "SanitationServices":
                return sel.SanitationServices;
            case "Emergency":
                return sel.Emergency;
            case "ReligiousRequest":
                return sel.ReligiousRequest;
            case "LanguageInterperter":
                return sel.LanguageInterperter;
            case "Medicine":
                return sel.Medicine;
            case "Laundry":
                return sel.Laundry;
            case "COVID":
                return sel.COVID;
            default:
                System.out.println("No Manager Found");
                return null;
        }
    }

    public static String getStringFromManager(sel s) {
        switch (s) {
            case NODE:
                return "NODE";
            case EDGE:
                return "EDGE";
            case EMPLOYEE:
                return "EMPLOYEE";
            case InternalTransportation:
                return "InternalTransportation";
            case ExternalTransportation:
                return "ExternalTransportation";
            case FloralDelivery:
                return "FloralDelivery";
            case FoodDelivery:
                return "FoodDelivery";
            case SanitationServices:
                return "SanitationServices";
            case Emergency:
                return "Emergency";
            case ReligiousRequest:
                return "ReligiousRequest";
            case LanguageInterperter:
                return "LanguageInterperter";
            case Medicine:
                return "Medicine";
            case Laundry:
                return "Laundry";
            case COVID:
                return "COVID";
            default:
                System.out.println("No Manager Found");
                return null;
        }
    }

    // ==== Private DB methods ==== //

    private static void loadFromCSV(sel s){
        try{
            getManager(s).loadFromCSV();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    /*  function:  saveElements()
     *  Saves database of sel table to selected managers current CSV path
     *  @return void
     */
    private static void saveElements(sel s){
        try {
            getManager(s).saveElements();
        }catch(FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    /*  function:  cleanTable()
     *  deletes all elements from selected manager's table
     *  @return Employee
     */
    private static void cleanTable(sel s){
        try {
            getManager(s).cleanTable();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /*  function:  setCSV_path()
     *  updates selected manager's csv path
     *  @return Employee
     */
    private static void setCSV_path(sel s, String path){
        getManager(s).setCSV_path(path);
    }

    //sends update request through current client
    private static void sendUpdate(sel s){
        Message msg = new Message(App.getCurrentUsername(),getStringFromManager(s),"NULL", Message.msgType.UPDATE);
        App.getClient().sendMsg(msg);
    }

    // ==== getters and setters ===== //
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        DatabaseManager.connection = connection;
    }

    public static  Mdb getMdb(){
        if(mdb == null){
            mdb = new Mdb();
        }
        return mdb;
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

    public static ServiceMap getServiceMap() {
        if(serviceMap == null){
            serviceMap = new ServiceMap();
        }
        return serviceMap;
    }

    public static EmergencyManager getEmergencyManager() {
        if(emergencyManager == null){
            emergencyManager = new EmergencyManager(connection, null);
        }
        return emergencyManager;
    }

    public static ExtTransportManager getExtTransportManager() {
        if(extTransportManager == null){
            extTransportManager = new ExtTransportManager(connection, null);
        }
        return extTransportManager;
    }

    public static IntTransportManager getIntTransportManager() {
        if(intTransportManager == null){
            intTransportManager = new IntTransportManager(connection, null);
        }
        return intTransportManager;
    }

    public static FloralDeliveryManager getFloralDeliveryManager() {
        if(floralDeliveryManager == null){
            floralDeliveryManager = new FloralDeliveryManager(connection, null);
        }
        return floralDeliveryManager;
    }

    public static FoodDeliveryManager getFoodDeliveryManager() {
        if(foodDeliveryManager == null){
            foodDeliveryManager = new FoodDeliveryManager(connection, null);
        }
        return foodDeliveryManager;
    }

    public static SanitationServiceManager getSanitationServices() {
        if(sanitationServiceManager == null){
            sanitationServiceManager = new SanitationServiceManager (connection, null);
        }
        return sanitationServiceManager;
    }

    public static ReligiousManager getReligiousManager() {
        if(religiousManager == null){
            religiousManager = new ReligiousManager(connection, null);
        }
        return religiousManager;
    }

    public static MedicineRequestManager getMedicineRequestManager() {
        if(medicineRequestManager == null){
            medicineRequestManager = new MedicineRequestManager(connection, null);
        }
        return medicineRequestManager;
    }

    public static LaundryManager getLaundryManager() {
        if(laundryManager == null){
            laundryManager = new LaundryManager(connection, null);
        }
        return laundryManager;
    }

    public static LanguageInterpreterManager getLanguageInterpreterManager() {
        if(languageInterpreterManager == null){
            languageInterpreterManager = new LanguageInterpreterManager(connection, null);
        }
        return languageInterpreterManager;
    }

    public static COVIDmanager getCOVIDManager() {
        if(coviDmanager == null){
            coviDmanager = new COVIDmanager(connection, null);
        }
        return coviDmanager;
    }


}
