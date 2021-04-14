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
    double height = App.getPrimaryStage().getScene().getHeight();
    double width = App.getPrimaryStage().getScene().getWidth();

    @FXML
    protected void returnToMain(){
        changeSceneTo("landingPage");
    }

    @FXML
    protected void changeSceneTo(String scene){
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

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }


    protected void back(){
        String scene = "loginPage";
        scenes.pop();
        try{
            scene = scenes.pop();
        } catch (EmptyStackException ignored){}
        changeSceneTo(scene);
    }

    protected void backMulti(int num){
        for (int i = 0; i < num; i++) {
            back();
        }
    }

    public void exitApp(ActionEvent e){
        Platform.exit();
    }
}