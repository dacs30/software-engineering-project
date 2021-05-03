package edu.wpi.MochaManticores.messaging;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class messageClient extends Application {
    // User information
    private String User;
    //controls
    TextField txtName;
    TextField txtTarget;
    TextField txtInput;
    ScrollPane scrollPane;
    public TextArea txtAreaDisplay;

    // IO streams
    DataOutputStream output = null;

    @Override
    public void start(Stage primaryStage) {
        //pane to hold scroll pane and HBox
        VBox vBox = new VBox();

        scrollPane = new ScrollPane();   //pane to display text messages
        HBox hBox = new HBox(); //pane to hold input textfield and send button

        txtAreaDisplay = new TextArea();
        txtAreaDisplay.setEditable(false);
        scrollPane.setContent(txtAreaDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        //define textfield and button and add to hBox
        txtName = new TextField();
        txtName.setPromptText("Name");
        txtName.setTooltip(new Tooltip("Write your name. "));
        txtTarget = new TextField();
        txtTarget.setPromptText("Target");
        txtTarget.setTooltip(new Tooltip("To who "));
        txtInput = new TextField();
        txtInput.setPromptText("New message");
        txtInput.setTooltip(new Tooltip("Write your message. "));
        Button btnSend = new Button("Send");
        btnSend.setOnAction(new ButtonListener());

        hBox.getChildren().addAll(txtName, txtTarget, txtInput, btnSend);
        hBox.setHgrow(txtInput, Priority.ALWAYS);  //set textfield to grow as window size grows

        //set center and bottom of the borderPane with scrollPane and hBox
        vBox.getChildren().addAll(scrollPane, hBox);
        vBox.setVgrow(scrollPane, Priority.ALWAYS);

        //create a scene and display
        Scene scene = new Scene(vBox, 450, 500);
        primaryStage.setTitle("Client: JavaFx Text Chat App");
        primaryStage.setScene(scene);
        primaryStage.show();


        //TODO replace GUI with a hashtable visualization

        //TODO name post operations

        //TODO complex/ high volume data transfer



        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(connectionUtil.host, connectionUtil.port);

            //Connection successful
            txtAreaDisplay.appendText("Connected. \n");

            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());

            //create a thread in order to read message from server continuously
            clientReader task = new clientReader(socket, this);
            Thread thread = new Thread(task);
            thread.start();

        } catch (IOException ex) {

            txtAreaDisplay.appendText(ex.toString() + '\n');
        }
    }





    public static void main(){
        // on startup check if there is an existing host
        try {
            Socket temp = new Socket(connectionUtil.host, connectionUtil.port);
        }catch(IOException e){
            // no server, start server
            startServer();
        }
        launch();
    }





    public static void startServer(){
        try {
            messageServer server = new messageServer();
            Thread serverThread = new Thread(server);
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            try {
                //get username and message
                String username = txtName.getText().trim();
                String message = txtInput.getText().trim();
                String target = txtTarget.getText().trim();

                //if username is empty set it to 'Unknown'
                if (username.length() == 0) {
                    username = "Unknown";
                }
                //if message is empty, just return : don't send the message
                if (message.length() == 0) {
                    return;
                }

                Message msg = new Message(username,target,message);

                //post on local gui
                Platform.runLater(() -> {
                    //display the message in the textarea
                    txtAreaDisplay.appendText("[" + msg.sender + "]" + " [" + msg.target + "] " + msg.body + "\n");
                });

                //send message to server
                output.writeUTF(msg.toWriteFormat());
                output.flush();

                //clear the textfield
                txtInput.clear();
            } catch (IOException ex) {
                System.err.println(ex);
            }

        }
    }

    public String getUser() {
        return User;
    }
}
