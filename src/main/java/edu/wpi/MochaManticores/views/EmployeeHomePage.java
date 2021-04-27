package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EmployeeHomePage extends SceneController {

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
    public void goToMedicineDelivery(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("medicineDelivery");
    }
    public void goToInternalTransportation(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("internalTransportation"); }

    public void goToCovidSurvey(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("covidSurvey");
    }

    public void goToMapEditor(MouseEvent mouseEvent) {
        changeSceneTo("employeeMapPage");
    }
}
