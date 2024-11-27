/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.opcserver;

import java.util.concurrent.ExecutionException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


import com.github.hamzafallahi.opcserver.secureserver.SecureServer;
@SpringBootApplication
@EnableScheduling
public class SpringBootOpcServerApplication {

	private final Log logger = LogFactory.getLog(SpringBootOpcServerApplication.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Start Spring Boot Application
		SpringApplication.run(SpringBootOpcServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {


            logger.info("Starting Secure Server");
            SecureServer secureServer = ctx.getBean(SecureServer.class);
            secureServer.startServer().get();

		};
	}
}
