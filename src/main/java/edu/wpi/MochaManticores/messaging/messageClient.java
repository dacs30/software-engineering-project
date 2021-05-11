package edu.wpi.MochaManticores.messaging;

import edu.wpi.MochaManticores.App;
import edu.wpi.MochaManticores.Exceptions.InvalidElementException;
import edu.wpi.MochaManticores.connectionUtil;
import edu.wpi.MochaManticores.database.DatabaseManager;
import edu.wpi.MochaManticores.database.Employee;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import org.apache.derby.iapi.sql.conn.ConnectionUtil;

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
        try {
            if (prevUser != null) {
                Message shutdown = new Message(prevUser, "SHUTDOWN", "null", Message.msgType.SHUTDOWN);
                output.writeUTF(shutdown.toWriteFormat());
                output.flush();
            }
        } catch (Exception e) {
        }


        if(serverThread != null) {
            server.shutdown();
            serverThread.stop();
        }
    }

    public void startServer(){
        //try remote connection first, if unavaliable then set connection util to use local params
        try{
            socket = new Socket(connectionUtil.getHost(),connectionUtil.getPort());
            socket.close();
        }catch (IOException ex){
            //remote connection failed, perfom local connection attempt
            connectionUtil.local = true;
            try {
                socket = new Socket(connectionUtil.getHost(), connectionUtil.getPort());
                socket.close();
            }catch(IOException e){
                // no server, start server
                server = new messageServer();
                serverThread = new Thread(server);
                serverThread.start();
            }
        }
    }

    public void startClient(){
        try {
            //stop old client if still running
            if(prevUser != null) {
                //shutdown message
                Message shutdown = new Message(prevUser, "SHUTDOWN", "null", Message.msgType.SHUTDOWN);
                output.writeUTF(shutdown.toWriteFormat());
                output.flush();
            }

            // on startup check if there is an existing host
            try {
                socket = new Socket(connectionUtil.getHost(), connectionUtil.getPort());
            }catch(IOException e){
                e.printStackTrace();
                return;
            }

            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());

            //create a thread in order to read message from server continuously
            reader = new clientReader(socket, App.getCurrentUsername(),this);
            readerThread = new Thread(reader);
            readerThread.start();

            //send a data grab request
            Message dataGrab = new Message(App.getCurrentUsername(), "dataGrab", "null", Message.msgType.DATAGRAB);
            output.writeUTF(dataGrab.toWriteFormat());
            output.flush();

            //set new user and log them in
            prevUser = App.getCurrentUsername();
            try {
                Employee emp = DatabaseManager.getEmployee(prevUser);
                emp.setLoggedIN(true);
                DatabaseManager.modEmployee(prevUser, emp);
            }catch (InvalidElementException x){}

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMsg(Message msg){
        try {
            output.writeUTF(msg.toWriteFormat());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
