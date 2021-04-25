package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TranslatorController extends SceneController{

    ObservableList<String> availableLanguages = FXCollections
            .observableArrayList("English","Spanish","Mandarin");

    @FXML
    private JFXTextField roomNumber;
    @FXML
    private JFXComboBox languageOne;
    @FXML
    private JFXComboBox languageTwo;
    @FXML
    private void initialize() {
        languageOne.setItems(availableLanguages);
        languageTwo.setItems(availableLanguages);
    }

    public void cancelReq(ActionEvent actionEvent) {
        back();
    }

    public void submitReq(ActionEvent actionEvent) {
        back();
    }

}
