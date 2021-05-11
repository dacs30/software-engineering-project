package edu.wpi.MochaManticores.messaging;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.views.SceneController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.input.MouseEvent;

import java.util.Iterator;
import java.util.Map;

import java.util.HashMap;
import java.util.LinkedList;

import java.awt.*;
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
    @FXML
    private VBox publicChatBox;
    @FXML
    private VBox conversationsBox;
    @FXML
    private ScrollPane conversationsScrollPane;
    @FXML
    private  ScrollPane publicChatBoxScrollPane;
    // Connection & user data
    DataOutputStream output =  App.getClient().getOutput();
    clientReader reader = App.getClient().getReader();
    String user = App.getCurrentUsername();
    HashMap<String,LinkedList<Message>> messages = reader.getHistory();
    LinkedList<Target> targets = new LinkedList<>();
    Target selected;

    public class Target extends Text{

        private Map.Entry<String, LinkedList<Message>> m;

        public Target(Map.Entry<String, LinkedList<Message>> m){
            super();
            this.m = m;
            this.setText(m.getKey());
            this.setStyle("-fx-font-size: 25");
        }

        public String getMessenger(){
            return m.getKey();
        }

        public LinkedList<Message> getMessageHistory(){
            return m.getValue();
        }
    }

    public void initialize(){
        //TODO update GUI to include tabs
        double height = App.getPrimaryStage().getScene().getHeight();
        double width = App.getPrimaryStage().getScene().getWidth();
        backgroundIMG.setFitHeight(height);
        backgroundIMG.setFitWidth(width);

        publicChatBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            publicChatBoxScrollPane.setVvalue(newValue.doubleValue());
        });

        backgroundIMG.fitWidthProperty().bind(App.getPrimaryStage().widthProperty());
        backgroundIMG.fitHeightProperty().bind(App.getPrimaryStage().heightProperty());

        //must set GUI connected to false when leaving the page
        App.getClient().startGUI(this);

        publicChatBox.prefWidthProperty().bind(publicChatBoxScrollPane.widthProperty().subtract(20));

        publicChatBox.prefHeightProperty().bind(publicChatBoxScrollPane.heightProperty().subtract(20));

        conversationsBox.setPrefWidth(conversationsScrollPane.getWidth());

        publicChatBox.setAlignment(Pos.TOP_LEFT);

        updateConvos();


    }

    public void updateConvos(){
        for (Map.Entry<String, LinkedList<Message>> m : messages.entrySet()){
            Target t = new Target(m);
            boolean flag = false;
            for (Target c : targets){
                if (c.getMessenger().equals(t.getMessenger())){
                    flag = true;
                    break;
                }
            }
            if (flag){
                continue;
            }
            targets.add(t);
            HBox container = new HBox();
            container.getChildren().add(t);
            conversationsBox.getChildren().add(container);

            container.setOnMouseClicked((MouseEvent e) -> {
                System.out.println(t.getMessenger());
                System.out.println(t.getMessageHistory());

                if (selected != null && selected.getMessenger().equals(t.getMessenger())){
                    ((Node)e.getSource()).setStyle("-fx-background-color: transparent");
                    selected = null;
                    publicChatBox.getChildren().clear();
                    return;
                }

                selected = t;
                for (Node n : conversationsBox.getChildren()){
                    n.setStyle("-fx-background-color: transparent");
                }
                container.setStyle("-fx-background-color: red");
                loadConversation( t.getMessenger()/*((Target) ((HBox)e.getSource()).getChildren().get(0)).getMessenger()*/);
            });
        }
    }

    public void back(){
        App.getClient().closeGUI();
        super.back();
    }

    public void loadConversation(String target) {

        publicChatBox.getChildren().clear();
        if (reader.messageHistory.containsKey(target)) {
            for (Message m : reader.messageHistory.get(target)) {
                Platform.runLater(() -> {
                    HBox msgBox = new HBox(12);
                    if (m.sender.equals(App.getCurrentUsername())){
                        // create a new textField
                        TextFlow msgFromUser = new TextFlow();
                        Text msgText = new Text(m.body);
                        msgText.setFill(Paint.valueOf("WHITE"));
                        msgText.setStyle("-fx-font-size: 35");
                        msgFromUser.getChildren().add(msgText);
                        msgFromUser.getStyleClass().add("textFlow");
                        msgFromUser.setPadding(new Insets(0, 50,0,10));
                        msgBox.getChildren().add(msgFromUser);
                        msgBox.setAlignment(Pos.CENTER_RIGHT);
                    } else {
                        // create a new textField
                        TextFlow msgReceived = new TextFlow();
                        Text msgText = new Text(m.body);
                        msgText.setFill(Paint.valueOf("BLACK"));
                        msgText.setStyle("-fx-font-size: 35");
                        msgReceived.getChildren().add(msgText);
                        msgReceived.getStyleClass().add("textFlowFlipped");
                        msgReceived.setPadding(new Insets(0, 10,0,50));
                        msgBox.getChildren().add(msgReceived);
                        msgBox.setAlignment(Pos.CENTER_LEFT);
                    }
                    msgBox.getStyleClass().add("hboxMessages");
                    publicChatBox.getChildren().add(msgBox);

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
            String target;
            String message = msgs.getText().trim();
            if(selected==null){
                target = tgt.getText().trim();
            }else{
                if (!tgt.getText().trim().equals(selected.getMessenger())){
                    target = tgt.getText().trim();
                } else {
                    target = selected.getMessenger();
                }
            }


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
