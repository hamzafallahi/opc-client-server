package com.github.hamzafallahi.service.impl;

import jakarta.annotation.PostConstruct;
import org.eclipse.milo.opcua.sdk.client.AddressSpace;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeValueWriterService {

    private static final Logger logger = LoggerFactory.getLogger(NodeValueWriterService.class);

    @Autowired
    private ConnectionService connectionService;

    @PostConstruct
    public void init() {
        try {
            AddressSpace addressSpace = connectionService.getClient().getAddressSpace();
            UaVariableNode testNode = (UaVariableNode) addressSpace.getNode(new NodeId(3, 1007));

            StatusCode statusCode = testNode.writeAttribute(
                    AttributeId.Value,
                    DataValue.valueOnly(new Variant(300.5))
            );

            if (statusCode.isGood()) {
                logger.info("Successfully wrote value to node.");
            } else {
                logger.error("Failed to write value to node: {}", statusCode);
            }

        } catch (Exception e) {
            logger.error("Error writing value to node: {}", e.getMessage(), e);
        }
    }
}
