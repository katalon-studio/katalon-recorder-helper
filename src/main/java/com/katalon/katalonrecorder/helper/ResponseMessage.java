package com.katalon.katalonrecorder.helper;

public class ResponseMessage {
    private Object message;

    public ResponseMessage(Object msg) {
        this.message = msg;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
