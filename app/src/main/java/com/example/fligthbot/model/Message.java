package com.example.fligthbot.model;

public class Message {
    public String message;
    public int sender;
    public Message(String message, int sender){
        this.message = message;
        this.sender = sender;
    }
}
