package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;

public class SceneController {
    protected static Stack<String> scenes;

    @FXML
    protected void returnToMain(ActionEvent e){
        changeSceneTo(e,"landingPage");
    }

    @FXML
    protected void changeSceneTo(ActionEvent e, String scene){
        scenes.push(scene);
        String path = "/edu/wpi/MochaManticores/fxml/" + scene + ".fxml";
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void back(ActionEvent e){
        String scene = "landingPage";
        try{
            scene = scenes.pop();
        } catch (EmptyStackException ex){
            ex.printStackTrace();
        }
    }


}