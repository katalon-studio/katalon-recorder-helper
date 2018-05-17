package com.katalon.katalonrecorder.helper;

import java.util.List;

public class CommandResource {
    private String command;
    private List<String> output;

    public CommandResource() {
    }

    public CommandResource(String command, List<String> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }
}
