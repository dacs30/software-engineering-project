package edu.wpi.MochaManticores.messaging;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.views.SceneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.io.DataOutputStream;
import java.io.IOException;

public class messageClientPage extends SceneController {
    @FXML
    public JFXTextArea textField;
    @FXML
    private JFXButton sendBTN;
    @FXML
    private JFXTextField tgt;
    @FXML
    private JFXTextField msgs;
    @FXML
    private ImageView backgroundIMG;

    // Connection & user data
    DataOutputStream output =  App.getClient().getOutput();
    clientReader reader = App.getClient().getReader();
    String user = App.getCurrentUsername();

    public void initialize(){
        //TODO update GUI to include tabs
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        //must set GUI connected to false when leaving the page
        App.getClient().startGUI(this);

        Platform.runLater(() -> {
            textField.appendText("connected \n");
        });

    }

    public void back(){
        App.getClient().closeGUI();
        super.back();
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
            Message msg = new Message(user, target, message, Message.msgType.MSGPOST);

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
