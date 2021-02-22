package com.katalon.katalonrecorder.helper.command;

import com.katalon.katalonrecorder.helper.LogHelper;
import org.slf4j.Logger;
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
    private static final Logger log = LogHelper.getLogger();

    @RequestMapping("/execute")
    public CommandResource execute(@NonNull @RequestParam("cmd") String cmd) throws IOException, InterruptedException {
        log.info(String.format("Process the command: '%s'", cmd));
        return new CommandResource(cmd, processCmd(cmd));
    }

    @RequestMapping("/execute_sh")
    public CommandResource executeSh(@NonNull @RequestParam("sh") String sh, @RequestParam("params") String[] params) throws IOException, InterruptedException {
        log.info(String.format("Process the command: '%s' with parameters: '%s'", sh, params));
        return new CommandResource(sh, processSh(sh, params));
    }

    private List<String> processSh(String sh, String[] params) throws IOException, InterruptedException {
        Process process;
        boolean isWindows =
                System.getProperty("os.name").toLowerCase().startsWith("windows");
        if(isWindows) {
            String noResponseMsg = "Cannot execute SH on Windows !";
            log.warn(noResponseMsg);
            return Collections.singletonList(noResponseMsg);
        }
        StringBuilder sb = new StringBuilder();
        for(String param : params) {
            sb.append(" ").append(param);
        }
        ProcessBuilder pb = new ProcessBuilder(sh, sb.toString());
        process = pb.start();
        List<String> commandOutput = new ArrayList<>();
        InputStreamExecutor inputStreamExecutor =
                new InputStreamExecutor(process.getInputStream(), commandOutput::add);
        Executors.newSingleThreadExecutor().submit(inputStreamExecutor);
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            log.info(String.format("Output:\n%s", String.join("\n", commandOutput)));
            return commandOutput;
        } else {
            String noResponseMsg = "Process may failed caused by no response";
            log.warn(noResponseMsg);
            return Collections.singletonList(noResponseMsg);
        }
    }

    private List<String> processCmd(String command) throws IOException, InterruptedException {
        Process process;
        boolean isWindows =
                System.getProperty("os.name").toLowerCase().startsWith("windows");
        String completedCommand
                = isWindows
                    ? String.format("cmd.exe /c %s", command)
                    : String.format("sh -c %s", command);
        log.info(String.format("The actual command is: '%s'", completedCommand));
        process = Runtime.getRuntime().exec(completedCommand);
        List<String> commandOutput = new ArrayList<>();
        InputStreamExecutor inputStreamExecutor =
                new InputStreamExecutor(process.getInputStream(), line -> {
                    commandOutput.add(line);
                });
        Executors.newSingleThreadExecutor().submit(inputStreamExecutor);
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            log.info(String.format("Output:\n%s", String.join("\n", commandOutput)));
            return commandOutput;
        } else {
            String noResponseMsg = "Process may failed caused by no response";
            log.warn(noResponseMsg);
            return Collections.singletonList(noResponseMsg);
        }
    }
}
