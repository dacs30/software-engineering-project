package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;

public class emergencyForm extends form {

    @FXML
    private TextField room;

    @FXML
    private ComboBox numPeople;

    @FXML
    private CheckBox gurney;

    @FXML
    private Button submitButton;


    public void initialize() {
        numPeople.getItems().clear();
        numPeople.getItems().addAll("1", "2", "3");
        numPeople.getSelectionModel().select("1");
    }
    @FXML
    private void submit(ActionEvent e){
        try{
            System.out.println(room.getText());
            System.out.println(numPeople.getSelectionModel().getSelectedIndex()+1);
            System.out.println(gurney.isSelected());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        //System.exit(0);
        returnToMain(e);
    }
}
