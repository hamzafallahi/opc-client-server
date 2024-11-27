package com.github.hamzafallahi;

import com.github.hamzafallahi.service.impl.ConnectionService;
import com.github.hamzafallahi.service.impl.NodeBrowserService;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = "com.github.hamzafallahi")
@EnableScheduling  // Enable Spring's scheduling functionality
@SpringBootApplication
public class SpringBootOpcClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootOpcClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOpcClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ConnectionService opcClient, NodeBrowserService nodeBrowserService) {
        return args -> {
            // Ensure client connection and logging
            if (opcClient.getClient() != null) {
                nodeBrowserService.browseNodes(UShort.valueOf(3)); // Browses nodes and stores them inside NodeBrowserService
                logger.info("OPC UA Client connected successfully.");
            }
        };
    }
}
