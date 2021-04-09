package edu.wpi.MochaManticores;

import java.io.IOException;
import java.util.Stack;

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

public class App extends Application {

  private static Stage primaryStage;


  public static void setPrimaryStage(Stage primaryStage) {

  }


  @Override
  public void init() {
    System.out.println("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) {


    App.primaryStage = primaryStage;
    try {
      Parent root = FXMLLoader.load(getClass().getResource("fxml/genericForm.fxml"));
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
