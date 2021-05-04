package edu.wpi.MochaManticores.messaging;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.views.SceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class messageClient extends SceneController {
    @FXML
    public JFXTextArea textField;
    @FXML
    private JFXButton sendBTN;
    @FXML
    private TextField tgt;
    @FXML
    private TextField msgs;

    // Connection
    Socket socket = null;
    DataOutputStream output = null;
    clientReader reader = null;
    //user data
    public String user = App.getCurrentUsername();

    public void initialize(){
        //TODO update GUI to include tabs


        try {
            // on startup check if there is an existing host
            try {
                socket = new Socket(connectionUtil.host, connectionUtil.port);
            }catch(IOException e){
                socket.close();
                textField.appendText(e.toString() + '\n');
                return;
            }


            //Connection successful
            textField.appendText("Connected. \n");

            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());

            //create a thread in order to read message from server continuously
            reader = new clientReader(socket, this, App.getCurrentUsername());
            Thread thread = new Thread(reader);
            thread.start();

            //send a data grab request
            Message dataGrab = new Message(user, "dataGrab", "null", Message.msgType.DATAGRAB);
            output.writeUTF(dataGrab.toWriteFormat());
            output.flush();

        } catch (IOException ex) {
            textField.appendText(ex.toString() + '\n');
        }
    }

    public void loadConversation(String target) {
        textField.clear();
        if (reader.messageHistory.containsKey(tgt.getText())) {
            for (Message m : reader.messageHistory.get(target)) {
                Platform.runLater(() -> {
                    textField.appendText("[" + m.sender + "]" + " [" + m.target + "] " + m.body + "\n");
                });
            }
        }
    }


    public void updateScreen() {
        if (reader.messageHistory.containsKey(tgt.getText())) {
            loadConversation(tgt.getText());
        } else {
            textField.clear();
            Platform.runLater(() -> {
                textField.appendText(user + "'s current conversations: \n");
            });
            for (String name : reader.messageHistory.keySet()) {
                Platform.runLater(() -> {
                    textField.appendText(name + "\n");
                });
            }
        }
    }

    public void sendEvent(ActionEvent actionEvent) {
        try {
            String message = msgs.getText().trim();
            String target = tgt.getText().trim();

            //if message is empty, just return : don't send the message
            if (message.length() == 0 | target.length() == 0) {
                return;
            }

            //format into msg type
            Message msg = new Message(user, target, message);

            //load the convo of who we are sending to
            loadConversation(target);


            output.writeUTF(msg.toWriteFormat());
            output.flush();

            //clear the textfield
            msgs.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
