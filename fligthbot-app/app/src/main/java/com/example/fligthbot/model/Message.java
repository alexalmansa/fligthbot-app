package com.example.fligthbot.model;

public class Message {

    public static final int VIEW_TYPE_MESSAGE_SENT = 1;
    public static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public String message;
    public int sender;
    public String map;
    public String time;

    public Message(String message, int sender, String time){
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.map = null;
    }
    public Message(String message, int sender, String map, String time){
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.map = map;
    }
}
