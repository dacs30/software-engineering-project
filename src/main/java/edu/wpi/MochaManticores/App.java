package edu.wpi.MochaManticores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Stack;

import edu.wpi.MochaManticores.Nodes.EdgeMapSuper;
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
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import edu.wpi.MochaManticores.database.CSVmanager;

public class App extends Application {

  private static Stage primaryStage;
  private static int clearenceLevel;


  public static void setPrimaryStage(Stage primaryStage) {

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
  }


  @Override
  public void start(Stage primaryStage) {


    App.primaryStage = primaryStage;
    try {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/loadingPage.fxml")));
      Scene scene = new Scene(root);
      primaryStage.setMaximized(true);
      primaryStage.setFullScreen(true);
      primaryStage.setScene(scene);
      primaryStage.setMinHeight(primaryStage.getScene().getHeight());
      primaryStage.setMinWidth(primaryStage.getScene().getWidth());
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
  }
}
