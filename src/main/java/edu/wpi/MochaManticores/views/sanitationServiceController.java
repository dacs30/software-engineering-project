package edu.wpi.MochaManticores.views;

import edu.wpi.MochaManticores.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class sanitationServiceController extends form {

    @FXML
    private TextField loc;

    @FXML
    private TextField safetyHaz;

    @FXML
    private TextField type;

    @FXML
    private TextField equipment;

    @FXML
    private TextArea description;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void submit(ActionEvent e) {
        try {
            System.out.println(loc.getText());
            System.out.println(safetyHaz.getText());
            System.out.println(type.getText());
            System.out.println(equipment.getText());
            System.out.println(description.getText());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        returnToMain(e);
    }


    public void back(ActionEvent e) {
        try {
            loc.setText("");
            safetyHaz.setText("");
            type.setText("");
            equipment.setText("");
            description.setText("");

        }catch(Exception ex){
            ex.printStackTrace();
        }
        returnToMain(e);
    }
}

