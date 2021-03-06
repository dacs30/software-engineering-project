package edu.wpi.MochaManticores.messaging;

import edu.wpi.MochaManticores.connectionUtil;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class messageServer implements Runnable{
    List<serverConnection> connectionsList = new ArrayList<>();
    HashMap<String, LinkedList<Message>> messageHistory = new HashMap<>();
    //TODO database refreshing

    @Override
    public void run() {
        try {
            // create server socket
            System.out.println("| Connecting at port " + connectionUtil.getPort());
            ServerSocket serverSocket = new ServerSocket(connectionUtil.getPort());
            serverSocket.setSoTimeout(200);

            // add client loop
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    serverConnection connection = new serverConnection(socket, this);
                    connectionsList.add(connection);

                    System.out.println("| Socket connection accepted: " + socket.toString());

                    Thread thread = new Thread(connection);
                    thread.start();
                }catch(SocketTimeoutException Ignored){}
            }

        } catch (IOException e) {
            System.out.println("\u001B[41m" +"| Connecting failed" + "\u001B[46m");
            e.printStackTrace();
        }
    }

    public void shutdown(){
        Message msg = new Message("SERVER","ALL","NONE", Message.msgType.SHUTDOWN);
        for(serverConnection connect : this.connectionsList){
            connect.sendMessage(msg);
        }
    }

    public void sendUpdate(Message msg){
        for(serverConnection connect : this.connectionsList){
            if(connect.getUser() != null && !connect.getUser().equals(msg.sender)){
                connect.sendMessage(msg);
            }
        }
    }

    public void msgpost(Message msg) {
        for(serverConnection connect : this.connectionsList){
            if(connect.getUser() != null && (connect.getUser().equals(msg.sender) | connect.getUser().equals(msg.target))) {
                connect.sendMessage(msg);
            }
        }
        if(msg.TYPE == Message.msgType.MSGPOST){
            store(msg);
        }
    }

    public void stopConnection(serverConnection connection){
        connectionsList.remove(connection);
    }

    public void datagrab(Message msg) {
        if(messageHistory.containsKey(msg.sender)) {
            for (Message m : messageHistory.get(msg.sender)) {
                m.TYPE = Message.msgType.DATAGRAB;

                //send to original requester
                for(serverConnection connect : this.connectionsList){
                    if(connect.getUser() != null && connect.getUser().equals(msg.sender)) {
                        connect.sendMessage(m);
                    }
                }
            }
        }
    }

    private void store(Message msg){
        if(messageHistory.containsKey(msg.target)){
            messageHistory.get(msg.target).add(msg);
        }else{
            LinkedList<Message> list = new LinkedList<>();
            list.add(msg);
            messageHistory.put(msg.target,list);
        }

        if(messageHistory.containsKey(msg.sender)){
            messageHistory.get(msg.sender).add(msg);
        }else{
            LinkedList<Message> list = new LinkedList<>();
            list.add(msg);
            messageHistory.put(msg.sender,list);
        }

    }

}
