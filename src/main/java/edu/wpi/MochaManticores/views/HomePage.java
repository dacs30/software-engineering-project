package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class HomePage extends SceneController{
    @FXML
    private ImageView backgroundIMG;

    @FXML
    private void initialize(){
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitWidth(width);
        backgroundIMG.setFitHeight(height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

    }


}
