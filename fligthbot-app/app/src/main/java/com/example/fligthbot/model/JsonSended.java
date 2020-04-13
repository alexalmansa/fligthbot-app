package com.example.fligthbot.model;

import com.google.gson.annotations.SerializedName;

public class JsonSended {
    @SerializedName("User")
    String User;

    @SerializedName("Message")
    String Message;

    public void setUser(String user) {
        User = user;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
