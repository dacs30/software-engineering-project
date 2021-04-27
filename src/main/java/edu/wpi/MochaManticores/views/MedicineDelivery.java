package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.ServiceMap;
import edu.wpi.MochaManticores.Services.ServiceRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MedicineDelivery {

    @FXML
    private GridPane contentGrid;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXComboBox<String> typeOfMedicineComboBx;

    @FXML
    private JFXCheckBox checkBox0, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;

    @FXML
    private JFXTextField allergies;

    @FXML
    private JFXTextField patientRoom;

    public void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        typeOfMedicineComboBx.getItems().clear();
        typeOfMedicineComboBx.getItems().addAll("Tylenol", "", "Menu 2", "Menu 3");
    }

    /**
     * Helper function to the submit funtion
     * @return true if at least one check box is marked
     */
    public boolean checkBoxesAreFilled(){
        return (checkBox0.isSelected() || checkBox1.isSelected() ||
                checkBox2.isSelected() || checkBox3.isSelected() ||
                checkBox4.isSelected() || checkBox5.isSelected());
    }

    public void submitForm(ActionEvent actionEvent) {
        // changeSceneTo(e, "mainMenu");
        if (checkBoxesAreFilled() && !typeOfMedicineComboBx.getSelectionModel().isEmpty() && !patientRoom.getText().isEmpty()){
//            ServiceRequest.addRequest(new edu.wpi.MochaManticores.Services.MedicineDelivery(App.getClearenceLevel()==1,
//                    false,
//                    0,
//                    typeOfMedicineComboBx.getSelectionModel().getSelectedItem(),
//                    allergies.getText(),
//                    patientRoom.getText()), ServiceMap.FoodDelivery);
            System.out.println("send to database");
        } else if (patientRoom.getText().isEmpty()){
            // if patient room is empty, generate error
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            patientRoom.getValidators().add(missingInput);
            missingInput.setMessage("Room number must be filled");
            patientRoom.validate();
        } else if (typeOfMedicineComboBx.getSelectionModel().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            typeOfMedicineComboBx.getValidators().add(missingInput);
            missingInput.setMessage("Type of medicine must be selected");
            typeOfMedicineComboBx.validate();
        }
    }

    public void loadHelpDialogue(MouseEvent mouseEvent) {
    }
}
