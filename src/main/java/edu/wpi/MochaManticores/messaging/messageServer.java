package edu.wpi.MochaManticores.messaging;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class messageServer implements Runnable{
    public List<serverConnection> connectionsList = new ArrayList<>();
    public List<Thread> threadList = new ArrayList<>();
    HashMap<String, LinkedList<Message>> messageHistory = new HashMap<>();
    public boolean running = true;
    //TODO database refreshing

    @Override
    public void run() {
        try {
            FileWriter myWriter = new FileWriter("SERVER.txt");
            // create server socket
            ServerSocket serverSocket = new ServerSocket(connectionUtil.port);
            serverSocket.setSoTimeout(100);

            // add client loop
            while (running) {
                myWriter.write("SERVER run" + System.currentTimeMillis() + '\n');
                myWriter.flush();

                try {
                    Socket socket = serverSocket.accept();
                    serverConnection connection = new serverConnection(socket, this);
                    connectionsList.add(connection);

                    Thread thread = new Thread(connection);
                    thread.start();
                    threadList.add(thread);

                }catch(SocketTimeoutException e){
                    //do nothing
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
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
