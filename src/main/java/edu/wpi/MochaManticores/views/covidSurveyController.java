package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.sel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import java.util.Arrays;
import java.util.List;

public class covidSurveyController extends SceneController{

    @FXML
    private JFXTextField patientName;
    @FXML
    private JFXDatePicker dateOfBirthPicker;

    @FXML
    private JFXCheckBox yesSickCB;
    @FXML
    private JFXCheckBox noSickCB;

    private List<JFXCheckBox> sickAns;

    @FXML
    private JFXCheckBox yesVaccinatedCB;
    @FXML
    private JFXCheckBox noVaccinatedCB;

    private List<JFXCheckBox> vaccineAns;

    @FXML
    private JFXCheckBox yesContactCovidCB;
    @FXML
    private JFXCheckBox noContactCovidCB;

    private List<JFXCheckBox> covidContactAns;

    @FXML
    private JFXComboBox<JFXCheckBox> symptoms;

    @FXML
    private JFXCheckBox coughingBox;
    @FXML
    private JFXCheckBox fatigueBox;
    @FXML
    private JFXCheckBox headacheBox;
    @FXML
    private JFXCheckBox congestionBox;
    @FXML
    private JFXCheckBox soreThroatBox;
    @FXML
    private JFXCheckBox muscleAchesBox;
    @FXML
    private JFXCheckBox shortBreathBox;
    @FXML
    private JFXCheckBox nauseaBox;
    @FXML
    private JFXCheckBox lossOfSmellTasteBox;
    @FXML
    private JFXCheckBox palpitationsBox;
    @FXML
    private JFXCheckBox feverChillsBox;
    @FXML
    private JFXCheckBox diarrheaBox;

    @FXML
    private JFXCheckBox yesTravelBox;
    @FXML
    private JFXCheckBox noTravelBox;

    private List<JFXCheckBox> travelAns;

    @FXML
    private JFXCheckBox yesCovidTestBox;
    @FXML
    private JFXCheckBox noCovidTestBox;

    private List<JFXCheckBox> covidTestAns;

    @FXML
    private StackPane dialogPane;
    @FXML
    private JFXDialog yesNoDialog;
    @FXML
    private JFXDialog submitDialog;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private GridPane contentGrid;

    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXButton yesBtn;
    @FXML
    private JFXButton noBtn;

    @FXML
    private GridPane yesNoQuestion;
    @FXML
    private GridPane covidForm;


    @FXML
    private void initialize() {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);
        contentGrid.setPrefSize(width, height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        System.out.println(width);

        //yesNoQuestion.toFront();
        covidForm.setOnMouseClicked(event -> {
            System.out.println("Hi");
        });

        sickAns = Arrays.asList(yesSickCB, noSickCB);
        vaccineAns = Arrays.asList(yesVaccinatedCB, noVaccinatedCB);
        covidContactAns = Arrays.asList(yesContactCovidCB, noContactCovidCB);
        travelAns = Arrays.asList(yesTravelBox, noTravelBox);
        covidTestAns = Arrays.asList(yesCovidTestBox, noCovidTestBox);

        symptoms.getItems().clear();
        symptoms.getItems().addAll(coughingBox,fatigueBox,
                headacheBox,congestionBox,
                soreThroatBox,muscleAchesBox,
                shortBreathBox,nauseaBox,
                lossOfSmellTasteBox,palpitationsBox,feverChillsBox,diarrheaBox);
    }

    public void goBack(ActionEvent actionEvent) {
        back();
    }

    public void yesEvent() { loadCOVIDPositiveDialog();}

    public void noEvent() { loadNoDialog();}

    public void submitEvent(ActionEvent actionEvent) {
       /* if (!patientName.getText().isEmpty() && !dateOfBirthPicker.equals("")){
            sel s = sel.InternalTransportation;
            DatabaseManager.addRequest(s, new edu.wpi.MochaManticores.Services.CovidSurvey(
                    "",
                    "",
                    false,
                    patientName.getText(),
                    dateOfBirthPicker.getValue()
            ));
            System.out.println("Adds to database");
        } else if (patientName.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            patientName.getValidators().add(missingInput);
            missingInput.setMessage("Location is required");
            patientName.validate();
        } else if (dateOfBirthPicker.equals("")){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            dateOfBirthPicker.getValidators().add(missingInput);
            missingInput.setMessage("Safety Hazards are required");
            dateOfBirthPicker.validate();
        } */
            loadSubmitDialog();
    }

    public void loadSubmitDialog(){
            dialogPane.toFront();
            dialogPane.setDisable(false);
            JFXDialogLayout message = new JFXDialogLayout();
            message.setMaxHeight(Region.USE_PREF_SIZE);
            message.setMaxHeight(Region.USE_PREF_SIZE);

            final Text hearder = new Text("Your survey was submitted");
            hearder.setStyle("-fx-font-weight: bold");
            hearder.setStyle("-fx-font-size: 30");
            hearder.setStyle("-fx-font-family: Roboto");
            hearder.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            final Text body = new Text("Have a great day!");
            body.setStyle("-fx-font-size: 15");
            body.setStyle("-fx-font-family: Roboto");
            body.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            message.setBody(body);
            JFXDialog finalDialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
            JFXButton ok = new JFXButton("OK");
            ok.setOnAction(event -> {
                changeSceneTo("landingPage");
            });

            finalDialog.setOnDialogClosed(event -> {
                dialogPane.toBack();
                finalDialog.close();
            });

            message.setActions(ok);
            finalDialog.show();
    }
    public void loadCOVIDPositiveDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Your survey was submitted");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("When you plan to visit the hospital, please follow this path.");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(event -> {
            dialogPane.toBack();
            dialog.close();
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        message.setActions(ok);
        dialog.show();
    }
    public void loadNoDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Based off your answers");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("You may continue to fill out the rest of the survey.");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("OK");
        ok.setOnAction(event -> {
            dialog.close();
            contentGrid.toBack();
            yesNoQuestion.toBack();
            yesNoQuestion.setVisible(false);
            covidForm.toFront();
            covidForm.setVisible(true);
            contentGrid.toFront();
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();

        });

        message.setActions(ok);
        dialog.show();
    }

    public void setSingleSickAnswer(ActionEvent e){
        JFXCheckBox source = (JFXCheckBox) e.getSource();
        String pressed = source.getId();
        for (JFXCheckBox button : sickAns) {
            if (!button.getId().equals(pressed)) {
                button.setSelected(false);
            }
        }
    }

    public void setSingleVaccineAnswer(ActionEvent e){
        JFXCheckBox source = (JFXCheckBox) e.getSource();
        String pressed = source.getId();
        for (JFXCheckBox button : vaccineAns) {
            if (!button.getId().equals(pressed)) {
                button.setSelected(false);
            }
        }
    }
    public void setSingleCovidContactAnswer(ActionEvent e){
        JFXCheckBox source = (JFXCheckBox) e.getSource();
        String pressed = source.getId();
        for (JFXCheckBox button : covidContactAns) {
            if (!button.getId().equals(pressed)) {
                button.setSelected(false);
            }
        }
    }
    public void setSingleTravelAnswer(ActionEvent e){
        JFXCheckBox source = (JFXCheckBox) e.getSource();
        String pressed = source.getId();
        for (JFXCheckBox button : travelAns) {
            if (!button.getId().equals(pressed)) {
                button.setSelected(false);
            }
        }
    }
    public void setSingleCovidTestAnswer(ActionEvent e){
        JFXCheckBox source = (JFXCheckBox) e.getSource();
        String pressed = source.getId();
        for (JFXCheckBox button : covidTestAns) {
            if (!button.getId().equals(pressed)) {
                button.setSelected(false);
            }
        }
    }
}

