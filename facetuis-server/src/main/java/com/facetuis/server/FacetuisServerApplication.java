package com.facetuis.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@SpringBootApplication
@PropertySource(value={"facetuis.properties"},encoding = "utf-8",ignoreResourceNotFound = true)
@EnableScheduling
@EnableAsync
public class FacetuisServerApplication {

	private static final Logger logger = Logger.getGlobal();



	public static void main(String[] args) {
		SpringApplication.run(FacetuisServerApplication.class, args);
		printLogo();
	}

	private static void printLogo() {
		logger.info("//////////////////////////FACETUIS.COM//////////////////////////////");
		logger.info("///////////////////--------------------------///////////////////////");
		logger.info("////////////////////////////启动成功////////////////////////////////");
	}
}
