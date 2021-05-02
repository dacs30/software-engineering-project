package edu.wpi.MochaManticores.messaging;

public class Message {
    public String sender;
    public String target;
    public String body;
    private String delim = "[|]+";

    public Message(String sender, String target, String body){
        this.sender = sender;
        this.target = target;
        this.body = body;
    }

    public Message(String writeFormat){
        // converts message from writeformat to a message object
        String[] tokens = writeFormat.split(delim);
        this.sender = tokens[0];
        this.target = tokens[1];
        this.body = tokens[2];
    }

    public String toWriteFormat(){
        return sender +"|"+target+"|"+body;
    }
}
