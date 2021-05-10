package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.Exceptions.InvalidLoginException;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class EmployeeEditorController extends  SceneController{

    @FXML
    private JFXTextField newUser;

    @FXML
    private JFXTextField oldPass;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private JFXTextField newPass;

    @FXML
    private JFXTextField first;

    @FXML
    private JFXTextField last;

    @FXML
    private JFXButton userButton;

    @FXML
    private JFXComboBox typePicker;

    @FXML
    private JFXToggleButton adminToggle;


    @FXML
    private Label title;

    boolean adding;


    public void initialize() throws InvalidElementException {
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitWidth(width);
        backgroundIMG.setFitHeight(height);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());
        Employee selected = DatabaseManager.getEmpManager().getElement(App.getCurrentUsername());
        boolean admin = selected.isAdmin();

        System.out.println(hashPassword("1234"));

        if(!admin){
            adminToggle.setDisable(true);
            typePicker.setDisable(true);
        }
        adminToggle.setSelected(admin);
        first.setText(selected.getFirstName());
        last.setText(selected.getLastName());

        typePicker.getItems().clear();
        typePicker.getItems().addAll(Employee.employeeType.values());
        typePicker.getSelectionModel().select(selected.getType());


    }

    public void cancel(ActionEvent actionEvent) {
        back();
    }

    public void submitForm(ActionEvent actionEvent) throws InvalidElementException {
        if(!adding){
            String username;
            String pass;
            Employee loggedIn;

            loggedIn = DatabaseManager.getEmployee(App.getCurrentUsername());

            if (newUser.getText().equals("")) {
                username = loggedIn.getUsername();
            } else {
                username = newUser.getText();
            }

            if (newPass.getText().equals("")) {
                pass = loggedIn.getPassword();
            } else {
                pass = newPass.getText();
            }

            try {
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
                            hashPassword(pass),
                            first.getText(),
                            last.getText(),
                            (Employee.employeeType) typePicker.getSelectionModel().getSelectedItem(),
                            loggedIn.getID(),
                            adminToggle.isSelected(),
                            loggedIn.isCovidStatus(),
                            loggedIn.getParkingSpace()));
            App.setCurrentUsername(null);
            changeSceneTo("loginPage");
        }else {
            String username;
            String password;
            Employee toAdd = null;
            try {
                DatabaseManager.getEmpManager().getElement(newUser.getText());
                newUser.setText("");
                RequiredFieldValidator userTaken = new RequiredFieldValidator();
                newUser.getValidators().add(userTaken);
                userTaken.setMessage("User already in system");
                oldPass.setText("");
                newPass.setText("");
                newUser.validate();
                return;
            } catch (InvalidElementException ex) {
                username = newUser.getText();
                password = oldPass.getText();
                if (!password.equals(newPass.getText())) {
                    newUser.setText("");
                    RequiredFieldValidator passwordMismatch = new RequiredFieldValidator();
                    oldPass.getValidators().add(passwordMismatch);
                    passwordMismatch.setMessage("Passwords do not match");
                    oldPass.setText("");
                    newPass.setText("");
                    oldPass.validate();
                    return;
                }
                toAdd = new Employee(
                        username,
                        hashPassword(password),
                        first.getText(),
                        last.getText(),
                        (Employee.employeeType) typePicker.getSelectionModel().getSelectedItem(),
                        0,
                        adminToggle.isSelected(),
                        false,
                        null);

                DatabaseManager.addEmployee(toAdd);
                back();
            }
        }
    }

    public void addUser(ActionEvent e){
        title.setText("Add Employee");
        first.setText("");
        last.setText("");
        userButton.setVisible(false);
        userButton.setDisable(true);
        adding = true;
        newUser.setPromptText("Username");
        oldPass.setPromptText("Password");
        newPass.setPromptText("Confirm Password");
        typePicker.getSelectionModel().select(Employee.employeeType.DEFAULT);
        adminToggle.setSelected(false);
    }
}
