package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class staffMainMenuController extends SceneController{


    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentPane;

    public void initialize(){
        double height = super.getHeight();
        double width = super.getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentPane.setPrefSize(width,height);
    }

    public void gotoEdge(ActionEvent e){
        super.changeSceneTo("edgesPage");
    }

    public void gotoNode(ActionEvent e){
        super.changeSceneTo("nodePage");
    }

    public void downloadCSVs(ActionEvent e) {
        String path = getPath();
        if(path.equals("")){

        }else{
            File dst = new File(path + "\\MapMNodes.csv");
            File dst2 = new File(path + "\\MapMEdges.csv");
            try{
                File source = new File("data/MapMNodes.csv");
                File source2 = new File("data/MapMEdges.csv");
                Files.copy(source.toPath(),dst.toPath(),REPLACE_EXISTING);
                Files.copy(source2.toPath(),dst2.toPath(),REPLACE_EXISTING);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public String getPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
        if(selectedDirectory!=null){
            String path = selectedDirectory.getAbsolutePath();
            return  selectedDirectory.getAbsolutePath();//TODO: check windows or UNIX and start at ~/Downloads or $USER/downloads
        }
        return "";
    }

    public void back(){
        super.back();
    }

}
