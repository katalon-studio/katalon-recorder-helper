package com.katalon.katalonrecorder.helper.upload;

import com.katalon.katalonrecorder.helper.LogHelper;
import com.katalon.katalonrecorder.helper.ResponseMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

@RestController
public class UploadController {

    private Logger log = LogHelper.getLogger();
    
    @Value("${upload.timeout}")
    private Integer uploadTimeout;

    private void setClipboardData(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    private void uploadFile(String path) throws AWTException, InterruptedException {
        setClipboardData(path); // put file path into clipboard for pasting
        Robot robot = new Robot();
        pressEnter(robot); // open dialog
        Thread.sleep(uploadTimeout);
        pressCtrlV(robot); // paste file path
        Thread.sleep(uploadTimeout);
        pressEnter(robot); // close dialog
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
    public ResponseMessage upload(@RequestParam("path") String path) throws AWTException, InterruptedException {
        uploadFile(path);
        return new ResponseMessage("Upload successfully to the path: " + path);
    }
}
