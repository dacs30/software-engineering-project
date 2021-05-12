package edu.wpi.MochaManticores;

import com.google.maps.GeoApiContext;
import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.Algorithms.PathPlanning;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.database.*;
import edu.wpi.MochaManticores.messaging.messageClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class App extends Application {

  private static Stage primaryStage;
  private static int clearenceLevel;
  private static NodeManager nodeManager;
  private static EdgeManager edgeManager;
  private static EmployeeManager employeeManager;
  private static PathPlanning algoType = new AStar2();
  private static GeoApiContext context;
  private static String currentUsername = null;
  private static messageClient client = new messageClient();

  public static messageClient getClient() {
    return client;
  }

  public static void setClient(messageClient client) {
    App.client = client;
  }
  public static GeoApiContext getContext() {
    return context;
  }

  public static String getCurrentUsername() {
    return App.currentUsername;
  }

  public static void setCurrentUsername(String currentUsername) {
    App.currentUsername = currentUsername;
  }

  public static PathPlanning getAlgoType() {
    return App.algoType;
  }

  public static void setAlgoType(PathPlanning algoType) {
    App.algoType = algoType;
  }

  public static NodeManager getNodeManager() {
    return nodeManager;
  }

  public static void setNodeManager(NodeManager nodeManager) {
    App.nodeManager = nodeManager;
  }

  public static EdgeManager getEdgeManager() {
    return edgeManager;
  }

  public static void setEdgeManager(EdgeManager edgeManager) {
    App.edgeManager = edgeManager;
  }

  public static EmployeeManager getEmployeeManager() {
    return employeeManager;
  }

  public static void setEmployeeManager(EmployeeManager employeeManager) {
    App.employeeManager = employeeManager;
  }

  public static int getClearenceLevel() {
    return clearenceLevel;
  }

  public static void setClearenceLevel(int clearenceLevel) {
    App.clearenceLevel = clearenceLevel;
  }

  @Override
  public void init() throws InterruptedException, FileNotFoundException, SQLException {
    System.out.println("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) {

    App.primaryStage = primaryStage;
    //App.context = new GeoApiContext.Builder().apiKey("AIzaSyAMDVoCK3Gv78bS9Y9H98IbWeAHzPfqHAk").build();

    //GoogleMapsAPI api = new GoogleMapsAPI();

    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/loadingPage.fxml")));
      Scene scene = new Scene(root);

      System.out.println("Starting Database");
      DatabaseManager.startup();
      client.startServer();
      System.out.println(DatabaseManager.getEmpManager().getEmployeeNames());

      //primaryStage.setMaximized(true);
      //primaryStage.setFullScreen(true);
      //primaryStage.setWidth(1280);
      //primaryStage.setHeight(800);
      primaryStage.setScene(scene);
      primaryStage.setMinHeight(800);
      primaryStage.setMinWidth(1280);
      primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
      primaryStage.getIcons().add(new Image("edu/wpi/MochaManticores/images/logo-small.png"));
      primaryStage.setTitle("Brigham and Women's Hospital Application");
      primaryStage.show();
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/loginPage.fxml")));
      App.getPrimaryStage().getScene().setRoot(root);

    } catch (IOException e) {
      e.printStackTrace();
      Platform.exit();
    }

  }

  public static Stage getPrimaryStage(){
    return primaryStage;
  }

  @Override
  public void stop() {
    //logout current user
    if(App.getCurrentUsername() != null){
      //log out previous user
      try {
        Employee emp = DatabaseManager.getEmployee(App.getCurrentUsername());
        emp.setLoggedIN(false);
        DatabaseManager.modEmployee(App.getCurrentUsername(), emp);
      }catch (InvalidElementException x){
        //x.printStackTrace();
      }
    }


    System.out.println("Shutting Down");
    DatabaseManager.shutdown();
    client.shutdown();
  }
}
