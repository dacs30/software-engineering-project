package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Objects;
import java.util.Stack;

public class SceneController {
    protected static Stack<String> scenes = new Stack<>();

    @FXML
    protected void returnToMain(ActionEvent e){
        changeSceneTo(e,"landingPage");
    }

    @FXML
    protected void changeSceneTo(ActionEvent e, String scene){
        scenes.push(scene);
        String path = "/edu/wpi/MochaManticores/fxml/" + scene + ".fxml";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    @FXML
//    private void advanceScene(ActionEvent e) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamname/fxml/Scene2.fxml"));
//            App.getPrimaryStage().getScene().setRoot(root);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//}
    protected void back(ActionEvent e){
        String scene = "loginPage";
        scenes.pop();
        try{
            scene = scenes.pop();
        } catch (EmptyStackException ignored){}
        changeSceneTo(e, scene);
    }


    public void exitApp(ActionEvent e){
        Platform.exit();
    }
}