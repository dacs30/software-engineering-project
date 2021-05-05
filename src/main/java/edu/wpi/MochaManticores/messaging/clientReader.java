package edu.wpi.MochaManticores.messaging;

import javafx.application.Platform;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class clientReader implements Runnable{
    Socket socket;
    messageClient client;
    DataInputStream input;
    String username;
    public HashMap<String,LinkedList<Message>> messageHistory = new HashMap<>();

    //Constructor
    public clientReader(Socket socket, messageClient client, String username){
        this.client = client;
        this.socket = socket;
        this.username = username;
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
        store(msg);
        Platform.runLater(() -> {
            client.textField.appendText("[" + msg.sender + "]" + " [" + msg.target + "] " + msg.body + "\n");
        });
    }


    private void store(Message msg){
        // add messages to hashmap
        // store messages by conversation -> aka hash by whatever user we are not
        String hash = "";
        if(!msg.sender.equals(username)){
            //hash by sender
            hash = msg.sender;
        }else if(!msg.target.equals(username)){
            // hash by target
            hash = msg.target;
        }

        //if nothing matches throw it out
        if(hash.equals("")){
            return;
        }

        //hash the message into the linked list stored at that key value location
        if(messageHistory.containsKey(hash)){
            messageHistory.get(hash).add(msg);
        }else{
            LinkedList<Message> list = new LinkedList<>();
            list.add(msg);
            messageHistory.put(hash,list);
        }
    }
}
