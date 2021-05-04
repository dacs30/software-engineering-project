package edu.wpi.MochaManticores.messaging;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class messageServer implements Runnable{
    List<serverConnection> connectionsList = new ArrayList<>();
    //TODO add updateable hashmap, add database updates

    @Override
    public void run() {
        try {
            // create server socket
            ServerSocket serverSocket = new ServerSocket(connectionUtil.port);

            // add client loop
            while (true) {
                Socket socket = serverSocket.accept();
                serverConnection connection = new serverConnection(socket, this); //find a way to get username at startup
                connectionsList.add(connection);

                Thread thread = new Thread(connection);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void msgpost(Message msg) {
        for(serverConnection connect : this.connectionsList){
            connect.sendMessage(msg);
        }
    }

    public void datagrab(Message msg) {
        for(serverConnection connect : this.connectionsList){


        }
    }

}
