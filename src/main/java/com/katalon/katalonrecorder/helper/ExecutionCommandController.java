package com.katalon.katalonrecorder.helper;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

@RestController
public class ExecutionCommandController {

    @RequestMapping("/execute")
    public CommandResource execute(@NonNull @RequestParam("cmd") String cmd) throws IOException, InterruptedException {
        return new CommandResource(cmd, processCmd(cmd));
    }

    private List<String> processCmd(String command) throws IOException, InterruptedException {
        Process process;
        boolean isWindows =
                System.getProperty("os.name").toLowerCase().startsWith("windows");
        if (isWindows) {
            process = Runtime.getRuntime()
                    .exec(String.format("cmd.exe /c %s", command));
        } else {
            process = Runtime.getRuntime()
                    .exec(String.format("sh -c %s", command));
        }
        List<String> commandOutput = new ArrayList<>();
        StreamGobbler streamGobbler =
                new StreamGobbler(process.getInputStream(), line -> {
                    commandOutput.add(line);
                    System.out.println(line);
                });
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return commandOutput;
        } else {
            return Collections.singletonList("Process may failed caused by no response");
        }
    }
}
