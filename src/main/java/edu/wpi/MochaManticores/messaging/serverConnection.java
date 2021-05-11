package edu.wpi.MochaManticores.messaging;

import edu.wpi.MochaManticores.database.DatabaseManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class serverConnection implements Runnable {
    private String user;
    private final Socket socket;
    private final messageServer server;

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
            boolean running = true;
            while(running){
                Message msg = new Message(inputStream.readUTF());
                // shutdown procedure here
                switch (msg.TYPE){
                    case MSGPOST:
                        System.out.println(" User: [" + user + "] MESSAGE POST to target: [" + msg.target + "]");
                        server.msgpost(msg);
                        break;
                    case UPDATE:
                        System.out.println(" User: [" + user + "] UPDATE REQUEST");
                        server.sendUpdate(msg);
                        break;
                    case DATAGRAB:
                        user = msg.sender;
                        System.out.println(" User: [" + user + "] DATAGRAB REQUEST");
                        server.datagrab(msg);
                        break;
                    case SHUTDOWN:
                        sendMessage(msg);
                        System.out.println(" User: [" + user + "] SHUTDOWN REQUEST");
                        inputStream = null;
                        outputStream = null;
                        running = false;
                        break;
                }
            }

        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void sendMessage(Message msg){
        try {
            //Writes two bytes of length information to the output stream, followed by the modified UTF-8 representation
            // of every character in the string s
            if(outputStream != null) {
                outputStream.writeUTF(msg.toWriteFormat());
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser(){
        return this.user;
    }

}
