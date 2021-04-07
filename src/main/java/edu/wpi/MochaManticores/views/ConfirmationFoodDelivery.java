package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConfirmationFoodDelivery extends form{
//    Trying to add a time function to go back to the menu
//    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//    Runnable task = forceGoBack();
//    executor.schedule(task, 5, TimeUnit.SECONDS);
//    executor.shutdown();
//
//    public Runnable forceGoBack(){
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
//            App.getPrimaryStage().getScene().setRoot(root);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    public void confrimationEnded(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/MochaManticores/fxml/landingPage.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
