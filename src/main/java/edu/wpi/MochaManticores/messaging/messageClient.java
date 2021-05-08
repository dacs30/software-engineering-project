package edu.wpi.MochaManticores.messaging;

import edu.wpi.MochaManticores.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class messageClient {
    //creates the reader, handles datagrab, handles shutdown and startup

    // Connection
    private Socket socket = null;
    private DataOutputStream output = null;
    private messageServer server = null;
    private Thread serverThread = null;
    private clientReader reader = null;
    private Thread readerThread = null;
    //user data
    public String prevUser = null;

    public void shutdown() {
        //shutdown server and client
        //stop old client if still running
        try{
            if(prevUser != null) {
                Message dataGrab = new Message(prevUser, "SHUTDOWN", "null", Message.msgType.SHUTDOWN);
                output.writeUTF(dataGrab.toWriteFormat());
                output.flush();
            }
        }catch(Exception e){}


        if(serverThread != null) {
            serverThread.stop();
        }
    }

    public void startServer(){
        try {
            socket = new Socket(connectionUtil.host, connectionUtil.port);
            socket.close();
        }catch(IOException e){
            // no server, start server
            server = new messageServer();
            serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public void startClient(){
        try {
            //stop old client if still running
            if(prevUser != null) {
                Message dataGrab = new Message(prevUser, "SHUTDOWN", "null", Message.msgType.SHUTDOWN);
                output.writeUTF(dataGrab.toWriteFormat());
                output.flush();
            }
            prevUser = App.getCurrentUsername();
            // on startup check if there is an existing host
            try {
                socket = new Socket(connectionUtil.host, connectionUtil.port);
            }catch(IOException e){
                e.printStackTrace();
                return;
            }

            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());

            //create a thread in order to read message from server continuously
            reader = new clientReader(socket, App.getCurrentUsername());
            readerThread = new Thread(reader);
            readerThread.start();

            //send a data grab request
            Message dataGrab = new Message(App.getCurrentUsername(), "dataGrab", "null", Message.msgType.DATAGRAB);
            output.writeUTF(dataGrab.toWriteFormat());
            output.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeGUI(){
        reader.stopGUI();
    }

    public void startGUI(messageClientPage page){
        reader.startGUI(page);
    }


    public DataOutputStream getOutput() {
        return output;
    }

    public clientReader getReader() {
        return reader;
    }

    public void setReader(clientReader reader) {
        this.reader = reader;
    }
}
