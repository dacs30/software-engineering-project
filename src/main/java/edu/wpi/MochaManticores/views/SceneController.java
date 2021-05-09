package edu.wpi.MochaManticores.views;

import com.jfoenix.controls.*;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.Mdb;
import edu.wpi.MochaManticores.messaging.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SceneController{
    protected static Stack<String> scenes = new Stack<>();
    double height = App.getPrimaryStage().getScene().getHeight();
    double width = App.getPrimaryStage().getScene().getWidth();
    private boolean dialogOpen = false;

    private static AnchorPane landingPageWindow;

    public AnchorPane getLandingPageWindow() {
        return landingPageWindow;
    }

    public void setLandingPageWindow(AnchorPane landingPageWindow) {
        SceneController.landingPageWindow = landingPageWindow;
    }

    @FXML
    protected void returnToMain(){
        changeSceneTo("landingPage");
    }

    @FXML
    protected void changeSceneTo(String scene){
        scenes.push(scene);
        String path = "/edu/wpi/MochaManticores/fxml/" + scene + ".fxml";
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            App.getPrimaryStage().getScene().setRoot(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void changeWindowTo(String scene) throws IOException {

        try {
            landingPageWindow.getChildren().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/wpi/MochaManticores/fxml/" + scene + ".fxml")));
        landingPageWindow.getChildren().add(root);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public AtomicBoolean loadYesNoDialog(StackPane dialogPane, String msg){

        AtomicBoolean answer = new AtomicBoolean(false);
        if(dialogOpen){
            return answer;
        }
        dialogPane.toFront();
        dialogPane.setDisable(false);
        //TODO Center the text of it.

        JFXDialogLayout message = new JFXDialogLayout();
        message.setMaxHeight(Region.USE_PREF_SIZE);
        message.setMaxHeight(Region.USE_PREF_SIZE);

        final Text hearder = new Text(msg);
        hearder.setStyle("-fx-font-weight: bold");
        hearder.setStyle("-fx-font-size: 30");
        hearder.setStyle("-fx-font-family: Roboto");
        hearder.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        final Text body = new Text("If it is, please, click yes to proceed.");
        body.setStyle("-fx-font-size: 15");
        body.setStyle("-fx-font-family: Roboto");
        body.setStyle("-fx-alignment: center");
        message.setHeading(hearder);

        message.setBody(body);
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        JFXButton yes = new JFXButton("YES");
        yes.setOnAction(event -> {
            answer.set(true);
            dialog.close();
            dialogPane.toBack();
        });

        JFXButton no = new JFXButton("NO");
        no.setOnAction(event -> {
            dialog.close();
            dialogPane.toBack();

        });

        dialog.setOnDialogClosed(event -> {
            answer.set(false);
            dialogPane.toBack();
        });

        message.setActions(yes, no);
        dialog.show();
        return answer;
    }

    protected void back(){
        String scene = "loginPage";
        try{
            scenes.pop();
            scene = scenes.pop();
        } catch (EmptyStackException ignored){}
        changeSceneTo(scene);
    }

    protected void back(int num){
        for (int i = 0; i < num; i++) {
            back();
        }
    }

    public void exitApp(ActionEvent e){
        Platform.exit();
    }

    public void loadErrorDialog(StackPane dialogPane, String msg){
        if(dialogOpen){
            return;
        }
        dialogPane.toFront();
        dialogPane.setDisable(false);
        JFXDialogLayout message = new JFXDialogLayout();
        message.setHeading(new Text("Oops!"));
        message.setBody(new Text(msg));
        JFXDialog dialog = new JFXDialog(dialogPane, message,JFXDialog.DialogTransition.CENTER);
        dialogOpen= true;
        JFXButton exit = new JFXButton("DONE");
        exit.setOnAction(event -> {
            dialog.close();
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        dialog.setOnDialogClosed(event -> {
            dialogOpen = false;
            dialogPane.setDisable(true);
            dialogPane.toBack();
        });
        EventHandler<KeyEvent> enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if(e.getCharacter().equals("\r")){
                    dialog.close();
                    dialogPane.setDisable(true);
                    dialogPane.toBack();
                }
            }
        };
        dialog.setOnKeyTyped(enter);

        message.setActions(exit);
        dialog.show();
    }

    public String getPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(App.getPrimaryStage());
        if(selectedDirectory!=null){
            String path = selectedDirectory.getAbsolutePath();
            return  selectedDirectory.getAbsolutePath();//TODO: check windows or UNIX and start at ~/Downloads or $USER/downloads
        }
        return "";
    }

    public void downloadCSV(ActionEvent e, String dstPath, String srcPath){
        String path = getPath();
        if(path.equals("")){

        }else{
            File dst = new File(path + dstPath);
            try{
                File source = new File(srcPath);
                Files.copy(source.toPath(),dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public String hashPassword(String plainText){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt, 65536, 128);

        SecretKeyFactory factory = null;

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = new byte[0];
        try {
            hashedPassword = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}