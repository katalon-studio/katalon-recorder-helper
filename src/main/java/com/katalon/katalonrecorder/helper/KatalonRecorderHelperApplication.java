package com.katalon.katalonrecorder.helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KatalonRecorderHelperApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(KatalonRecorderHelperApplication.class, args);
	}
}
