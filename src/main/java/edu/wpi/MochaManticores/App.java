package edu.wpi.MochaManticores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import edu.wpi.MochaManticores.Services.ServiceMap;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import edu.wpi.MochaManticores.database.EdgeManager;
import edu.wpi.MochaManticores.database.EmployeeManager;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.database.NodeManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class App extends Application {

  private static Stage primaryStage;
  private static int clearenceLevel;
  private static NodeManager nodeManager;
  private static EdgeManager edgeManager;
  private static EmployeeManager employeeManager;
  //private static final String location = "edu/wpi/MochaManticores/images/";

  //Load in assets
  //public static final Image floor2 = new Image(location + "02_thesecondfloor.png");
  //public static final Image floor1 = new Image(location + "01_thefirstfloor.png");
  //public static final Image floor0 = new Image(location + "00_thegroundfloor.png");
  //public static final Image floorL1 = new Image(location + "00_thelowerlevel1.png");
  //public static final Image floorL2 = new Image(location + "00_thelowerlevel2.png");
  //public static final Image floor3 = new Image(location + "04_thethirdfloor.png");


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
    System.out.println("Starting Database");
    Mdb.databaseStartup();
    ServiceRequest.loadFromCSV(Mdb.getConnection());
  }


  @Override
  public void start(Stage primaryStage) {


    App.primaryStage = primaryStage;
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/loadingPage.fxml")));
      Scene scene = new Scene(root);
      //primaryStage.setMaximized(true);
      //primaryStage.setFullScreen(true);
      primaryStage.setScene(scene);
      primaryStage.setMinHeight(800);
      primaryStage.setMinWidth(1280);
      primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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
    System.out.println("Shutting Down");
    Mdb.databaseShutdown();
  }
}
