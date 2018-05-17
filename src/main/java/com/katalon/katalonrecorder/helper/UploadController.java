package com.katalon.katalonrecorder.helper;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

@Controller
public class UploadController {

    private Logger log = LogHelper.getLogger();

    private void setClipboardData(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void uploadFile(String path) throws AWTException, InterruptedException {
        setClipboardData(path);
        Robot robot = new Robot();
        pressCtrlV(robot);
        Thread.sleep(3000);
        pressEnter(robot);
    }

    private void pressEnter(Robot robot) {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private void pressCtrlV(Robot robot) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(@RequestParam("path") String path) throws AWTException, InterruptedException {
        uploadFile(path);
    }
}
