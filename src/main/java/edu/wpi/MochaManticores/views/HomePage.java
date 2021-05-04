package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import edu.wpi.MochaManticores.views.landingPageController;

import java.io.IOException;

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

    public void goToMedicineOrder(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("medicineDelivery");
    }
    public void goToInternalTransport(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("internalTransportation"); }

    public void goToCovidSurvey(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("covidSurvey");
    }
    public void goToChatPage(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("clientPage");
    }
    public void goToMapPage(MouseEvent mouseEvent){
        super.changeSceneTo("mapPage");
    }
}
