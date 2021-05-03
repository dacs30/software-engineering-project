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
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    //user data
    public String user = App.getCurrentUsername();

    public void initialize(){
        //TODO replace GUI with a hashtable visualization

        //TODO name post operations

        //TODO complex/ high volume data transfer

        try {
            // on startup check if there is an existing host
            try {
                socket = new Socket(connectionUtil.host, connectionUtil.port);
            }catch(IOException e){
                textField.appendText(e.toString() + '\n');
            }


            //Connection successful
            textField.appendText("Connected. \n");

            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());

            //create a thread in order to read message from server continuously
            clientReader task = new clientReader(socket, this);
            Thread thread = new Thread(task);
            thread.start();

            //send a data grab request
            Message dataGrab = new Message(user, "dataGrab", "null");
            output.writeUTF(dataGrab.toWriteFormat());
            output.flush();

        } catch (IOException ex) {
            textField.appendText(ex.toString() + '\n');
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

            //post on local gui
            Platform.runLater(() -> {
                //display the message in the textarea
                textField.appendText("[" + msg.sender + "]" + " [" + msg.target + "] " + msg.body + "\n");
            });

            output.writeUTF(msg.toWriteFormat());
            output.flush();

            //clear the textfield
            msgs.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
