package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.Algorithms.AStar2;
import edu.wpi.MochaManticores.Algorithms.Dijkstra;
import edu.wpi.MochaManticores.Algorithms.GBF;
import edu.wpi.MochaManticores.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EmployeeHomePage extends SceneController {

    @FXML
    public RadioButton radioAStar;
    @FXML
    public RadioButton radioDFS;
    @FXML
    public RadioButton radioBFS;
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

        ToggleGroup tg = new ToggleGroup();
        radioAStar.setToggleGroup(tg);
        radioBFS.setToggleGroup(tg);
        radioDFS.setToggleGroup(tg);

        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)tg.getSelectedToggle();

                if (rb.equals(radioAStar)){
                    App.setAlgoType(new AStar2());
                } else if (rb.equals(radioBFS)){
                    App.setAlgoType(new Dijkstra());
                } else if (rb.equals(radioDFS)){
                    App.setAlgoType(new GBF());
                }
            }
        });

    }
    public void goToMedicineDelivery(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("medicineDeliveryEmployee");
    }
    public void goToInternalTransportation(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("internalTransportation"); }

    public void goToCovidSurvey(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("covidSurvey");
    }

    public void goToChatPage(MouseEvent mouseEvent) throws IOException {
        super.changeWindowTo("clientPage");
    }

    public void goToMapEditor(MouseEvent mouseEvent) {
        changeSceneTo("employeeMapPage");
    }
}
