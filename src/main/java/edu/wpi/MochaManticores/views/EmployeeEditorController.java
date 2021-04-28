package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import edu.wpi.MochaManticores.database.EmployeeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;

public class EmployeeEditorController extends  SceneController{

    @FXML
    private JFXTextField newUser;

    @FXML
    private JFXTextField oldPass;

    @FXML
    private JFXTextField newPass;

    @FXML
    private Label user;


    public void initialize(){
        user.setText(App.getCurrentUsername());

    }

    public void submitForm(ActionEvent e) throws InvalidElementException {

        String username;
        String pass;
        Employee loggedIn;

        loggedIn = DatabaseManager.getEmployee(App.getCurrentUsername());

        if(newUser.getText().equals("")){
            username = loggedIn.getUsername();
        }else{
            username = newUser.getText();
        }

        if(newPass.getText().equals("")){
            pass = loggedIn.getPassword();
        }else{
            pass = newPass.getText();
        }

        try{
            DatabaseManager.checkEmployeeLogin(username, oldPass.getText());
        } catch (InvalidLoginException | InvalidElementException invalidLoginException) {
            newUser.setText("");
            oldPass.setText("");
            newPass.setText("");
            return;
        }
        DatabaseManager.modEmployee(loggedIn.getUsername(),
                new Employee(username,
                        pass,
                        loggedIn.getFirstName(),
                        loggedIn.getLastName(),
                        loggedIn.getType(),
                        loggedIn.getID(),
                        loggedIn.isAdmin()));
        App.setCurrentUsername(null);
        changeSceneTo("loginPage");

    }


    public void cancel(ActionEvent e){
        back();
    }


    public void cancel(javafx.event.ActionEvent actionEvent) {
        back();
    }

    public void submitForm(javafx.event.ActionEvent actionEvent) throws InvalidElementException {

        String username;
        String pass;
        Employee loggedIn;

        loggedIn = DatabaseManager.getEmployee(App.getCurrentUsername());

        if(newUser.getText().equals("")){
            username = loggedIn.getUsername();
        }else{
            username = newUser.getText();
        }

        if(newPass.getText().equals("")){
            pass = loggedIn.getPassword();
        }else{
            pass = newPass.getText();
        }

        try{
            DatabaseManager.checkEmployeeLogin(loggedIn.getUsername(), oldPass.getText());
        } catch (InvalidLoginException | InvalidElementException invalidLoginException) {
            newUser.setText("");
            oldPass.setText("");
            newPass.setText("");
            RequiredFieldValidator wrongCreditals = new RequiredFieldValidator();
            oldPass.getValidators().add(wrongCreditals);
            wrongCreditals.setMessage("Wrong password");
            oldPass.validate();
            return;
        }
        DatabaseManager.modEmployee(loggedIn.getUsername(),
                new Employee(username,
                        pass,
                        loggedIn.getFirstName(),
                        loggedIn.getLastName(),
                        loggedIn.getType(),
                        loggedIn.getID(),
                        loggedIn.isAdmin()));
        App.setCurrentUsername(null);
        changeSceneTo("loginPage");

    }
}
