package com.katalon.katalonrecorder.helper;

public class ResponseMessenger {
    private Object message;

    public ResponseMessenger(Object msg) {
        this.message = msg;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
