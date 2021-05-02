package edu.wpi.MochaManticores.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class serverConnection implements Runnable {
    private String user;
    private Socket socket;
    private messageServer server;

    //datastreams
    DataInputStream inputStream;
    DataOutputStream outputStream;

    public serverConnection(Socket socket, messageServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run(){
        try{
            //get socket streams
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            while(true){
                Message msg = new Message(inputStream.readUTF());
                //TODO handle message types
                //NAME POST -> set serverConnection name
                //DATA GRAB -> return a full hashmap of relevant data
                //MSG POST -> post a message to server
                server.broadcast(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message msg){
        try {
            //Writes two bytes of length information to the output stream, followed by the modified UTF-8 representation
            // of every character in the string s
            outputStream.writeUTF(msg.toWriteFormat());
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
