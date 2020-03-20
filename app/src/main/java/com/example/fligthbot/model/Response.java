package com.example.fligthbot.model;

public class Response {
    private String intent;
    private float confidence;
    private String fulfillmentText;


    // Getter Methods

    public String getIntent() {
        return intent;
    }

    public float getConfidence() {
        return confidence;
    }

    public String getFulfillmentText() {
        return fulfillmentText;
    }


    // Setter Methods

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public void setFulfillmentText(String fulfillmentText) {
        this.fulfillmentText = fulfillmentText;
    }


}
