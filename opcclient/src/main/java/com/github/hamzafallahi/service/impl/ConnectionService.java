package com.github.hamzafallahi.service.impl;

import jakarta.annotation.PostConstruct;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);
    private OpcUaClient client;

    @PostConstruct
    public void init() throws Exception {
        logger.info("Initializing OPC UA Client...");

        // Create the OPC UA client
        client = OpcUaClient.create(
                "opc.tcp://DESKTOP-2DL61M3:53530/OPCUA/SimulationServer",
                endpoints -> endpoints.stream()
                        .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getUri()))
                        .findFirst(),
                configBuilder -> configBuilder.build()
        );

        // Connect to the server
        client.connect().get();
    }

    public OpcUaClient getClient() {
        return client;
    }
}
