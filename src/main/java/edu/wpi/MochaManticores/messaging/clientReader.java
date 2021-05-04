package edu.wpi.MochaManticores.messaging;

import javafx.application.Platform;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
public class clientReader implements Runnable{
    Socket socket;
    messageClient client;
    DataInputStream input;

    //Constructor
    public clientReader(Socket socket, messageClient client){
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            try{
                input = new DataInputStream(socket.getInputStream());
                Message msg = new Message(input.readUTF());
                postMessage(msg);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void postMessage(Message msg){
        Platform.runLater(() -> {
            client.textField.appendText("[" + msg.sender + "]" + " [" + msg.target + "] " + msg.body + "\n");
        });
    }
}
