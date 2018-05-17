package com.katalon.katalonrecorder.helper;

public class ResponseMsg {
    private Object message;

    public ResponseMsg(Object msg) {
        this.message = msg;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
