package com.katalon.katalonrecorder.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {

    public static Logger getLogger() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        /*
         * stackTrace[0] is for Thread.currentThread().getStackTrace() stackTrace[1] is for this method log()
         */
        String className = stackTrace[2].getClassName();
        return LoggerFactory.getLogger(className);
    }
}
