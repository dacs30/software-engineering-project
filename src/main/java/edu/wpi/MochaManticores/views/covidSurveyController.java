package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Services.COVIDsurvey;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import edu.wpi.MochaManticores.database.sel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private JFXComboBox<String> symptoms;

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
    private ImageView helpButton1;
    @FXML
    private ImageView helpButton2;
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

        //System.out.println(width);

        //yesNoQuestion.toFront();
        //covidForm.setOnMouseClicked(event -> {
        //    System.out.println("Hi");
        //});

        sickAns = Arrays.asList(yesSickCB, noSickCB);
        vaccineAns = Arrays.asList(yesVaccinatedCB, noVaccinatedCB);
        covidContactAns = Arrays.asList(yesContactCovidCB, noContactCovidCB);
        travelAns = Arrays.asList(yesTravelBox, noTravelBox);
        covidTestAns = Arrays.asList(yesCovidTestBox, noCovidTestBox);

        symptoms.getItems().clear();
        symptoms.getItems().addAll("Coughing",
                "Fatigue",
                "Headache",
                "Congestion",
                "Sorethroat",
                "Muscle Aches",
                "Shortness of Breath",
                "Nausea/Vomiting",
                "Loss Of Smell or Taste",
                "Palpitations",
                "Fever/Chills",
                "Diarrhea",
                "None of the Above");
        symptoms.setVisibleRowCount(5);
        symptoms.setPromptText("-Select A Symptom-");

    }

    private boolean check(List<JFXCheckBox> list){
        return list.get(0).isSelected();
    }

    public void goBack(ActionEvent actionEvent) {
        back();
    }

    public void yesEvent() { loadCOVIDPositiveDialog();}

    public void noEvent() { loadNoDialog();}

    public void submitEvent(ActionEvent actionEvent) {
       if (!patientName.getText().isEmpty() && !dateOfBirthPicker.equals("")){
            sel s = sel.InternalTransportation;
            //TODO change to real employee
            COVIDsurvey covid = new COVIDsurvey("","employee",false,App.getCurrentUsername(),dateOfBirthPicker.getValue().toString(),
                    check(sickAns),check(vaccineAns),check(travelAns),check(covidTestAns),check(covidContactAns),symptoms.getValue(),true);

            DatabaseManager.addRequest(sel.COVID,covid);

        } else if (patientName.getText().isEmpty()){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            patientName.getValidators().add(missingInput);
            missingInput.setMessage("Name is required");
            patientName.validate();
        } else if (dateOfBirthPicker.equals("")){
            RequiredFieldValidator missingInput = new RequiredFieldValidator();
            dateOfBirthPicker.getValidators().add(missingInput);
            missingInput.setMessage("Date of Birth is required");
            dateOfBirthPicker.validate();
        }
       loadSubmitDialog();
    }

    public void helpEvent() { loadHelpDialogue();}

    public void helpSurveyEvent() { loadSurveyHelpDialogue();}

    public void loadSubmitDialog(){
            dialogPane.toFront();
            dialogPane.setDisable(false);
            JFXDialogLayout message = new JFXDialogLayout();
            message.setMaxHeight(Region.USE_PREF_SIZE);
            message.setMaxHeight(Region.USE_PREF_SIZE);

            final Text hearder = new Text("Your survey was submitted");
            hearder.setStyle("-fx-font-weight: bold");
            hearder.setStyle("-fx-font-size: 60");
            hearder.setStyle("-fx-font-family: Roboto");
            hearder.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            final Text body = new Text("Have a great day!");
            body.setStyle("-fx-font-size: 40");
            body.setStyle("-fx-font-family: Roboto");
            body.setStyle("-fx-alignment: center");
            message.setHeading(hearder);

            message.setBody(body);
            JFXDialog finalDialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
            JFXButton ok = new JFXButton("Done");
            ok.setStyle("-fx-font-size: 15");
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

    public void loadHelpDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Patient Name: Please put your name in the text field provided.\n" +
                                    "Have you been clinically diagnosed to have COVID-19?: \n" +
                                    "Please select YES if you have been declared already by a medical professional,\n" +
                                    "otherwise select NO and proceed with the rest of the survey.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Continue");
        cont.setStyle("-fx-font-size: 15");
        cont.setOnAction(event -> {
            dialog.close();
        });

        dialog.setOnDialogClosed(event -> {
            dialog.close();
        });

        message.setActions(cont);
        dialog.show();

    }

    public void loadSurveyHelpDialogue(){
        dialogPane.toFront();
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);
        message.setMaxHeight(Region.USE_COMPUTED_SIZE);

        final Text hearder = new Text("Help Page");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("Patient Name: Please put your name in the text field provided.\n" +
                "Patient DOB: Please put your date of birth.\n" +
                "Are you feeling sick today?: Please select YES or NO.\n" +
                "Are you here to take a COVID-19 test?: Please select YES if you are here to take the COVID-19 test, otherwise select NO.\n" +
                "Have you travelled in the past 14 days?: Please select YES if you have travelled across states or the world, otherwise select NO.\n" +
                "Are you fully vaccinated against COVID-19: Please select YES if you have been vaccinated, otherwise select NO.\n" +
                "Have you been in contact with anyone confirmed to be COVID-19 positive?: Please select YES, otherwise select NO.\n" +
                "Have you experienced any of the symptoms in the last 48 hours?: Please select one or more symptoms, otherwise select None of the Above.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");

        message.setBody(body);


        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);

        JFXButton cont = new JFXButton("Continue");
        cont.setStyle("-fx-font-size: 15");
        cont.setOnAction(event -> {
            dialog.close();
        });

        dialog.setOnDialogClosed(event -> {
            dialog.close();
        });

        message.setActions(cont);
        dialog.show();

    }

    public void loadCOVIDPositiveDialog(){
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text("Your survey was submitted");
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("When you plan to visit the hospital, please follow this path. Take a photo or screenshot if needed.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        Image img = new Image("edu/wpi/MochaManticores/images/EmergencyPath.jpg");
        final ImageView mapImage = new ImageView(img);
        mapImage.setFitWidth(900);
        mapImage.setFitHeight(500);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(mapImage);
        vbox.getChildren().add(body);
        message.setBody(vbox);

        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Done");
        ok.setStyle("-fx-font-size: 15");
        ok.setOnAction(event -> {
            dialogPane.toBack();
            dialog.close();
            changeSceneTo("landingPage");
        });

        dialog.setOnDialogClosed(event -> {
            dialogPane.toBack();
            dialog.close();
        });

        try { //TODO add this to covid table editor
            Employee temp = DatabaseManager.getEmpManager().getElement(App.getCurrentUsername());
            temp.setCovidStatus(true);
            DatabaseManager.getEmpManager().modElement(App.getCurrentUsername(), temp);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("no user to edit info of");
        }

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
        hearder.setStyle("-fx-font-size: 60");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("You may continue to fill out the rest of the survey.");
        body.setStyle("-fx-font-size: 40");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton ok = new JFXButton("Continue");
        ok.setStyle("-fx-font-size: 15");
        ok.setOnAction(event -> {
            dialog.toBack();
            dialog.setVisible(false);
            covidForm.toFront();
            yesNoQuestion.toBack();
            yesNoQuestion.setVisible(false);
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

