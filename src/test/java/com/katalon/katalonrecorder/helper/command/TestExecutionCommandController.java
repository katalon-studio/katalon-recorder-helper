package com.katalon.katalonrecorder.helper.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestExecutionCommandController {
    ExecutionCommandController controller;

    @Before
    public void initialize() {
        controller = new ExecutionCommandController();
    }

    @Test
    public void testExecuteShOnWindows() throws IOException, InterruptedException {
        if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            CommandResource cmdResult = controller.executeSh("something", new String[]{"something"});
            String result = cmdResult.getOutput().get(0);
            Assert.assertEquals("Cannot execute SH on Windows !", result);
        }
    }

    @Test
    public void testExecuteShOnMac() throws IOException, InterruptedException {
        if(!System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            String param1 = "first string";
            String param2 = "second string";
            String param3 = "third string";
            ClassLoader classLoader = getClass().getClassLoader();
            File shFile = new File(classLoader.getResource("echo_sh.sh").getFile());
            Runtime.getRuntime().exec("chmod u+x " + shFile.getAbsolutePath());
            CommandResource cmdResource = controller.executeSh(shFile.getAbsolutePath(), new String[]{param1, param2, param3});
            Assert.assertEquals(3, cmdResource.getOutput().size());
            Assert.assertEquals(String.join(" ", param1, param2, param3)
                    , cmdResource.getOutput().get(0));
        }
    }
}
