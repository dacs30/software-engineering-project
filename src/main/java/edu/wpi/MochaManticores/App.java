package edu.wpi.MochaManticores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import edu.wpi.MochaManticores.Nodes.MapSuper;
import edu.wpi.MochaManticores.Nodes.NodeSuper;
import edu.wpi.MochaManticores.database.CSVmanager;
import edu.wpi.MochaManticores.database.Mdb;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import edu.wpi.MochaManticores.database.CSVmanager;

public class App extends Application {

  private static Stage primaryStage;


  public static void setPrimaryStage(Stage primaryStage) {

  }


  @Override
  public void init() throws FileNotFoundException, SQLException {
    System.out.println("Starting Up");
    System.out.println("Starting Database");
    Mdb.databaseStartup();
    NodeSuper testNode = MapSuper.getMap().get("ACONF00103");
    System.out.println(testNode.getLongName());
  }

  @Override
  public void start(Stage primaryStage) {


    App.primaryStage = primaryStage;
    try {
      Parent root = FXMLLoader.load(getClass().getResource("fxml/mapPage.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.show();
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
  }
}
