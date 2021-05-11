package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AboutPageController extends SceneController{

    @FXML
    public Label aboutText;

    @FXML
    public JFXButton creditsButton;

    @FXML
    public ImageView backgroundIMG;

    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitWidth(width);
        backgroundIMG.setFitHeight(height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
        StringBuilder about = new StringBuilder();
        try {
            File aboutFile = new File("src/main/resources/edu/wpi/MochaManticores/about.txt");
            Scanner sc = new Scanner(aboutFile);
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                about.append(data + "\n");
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            about.append("Data not found");
        }
        aboutText.setText(about.toString());

    }

    public void back(ActionEvent e){
        super.back();
    }

    public void goToCredits(MouseEvent e) {changeSceneTo("creditsPage");}
}
