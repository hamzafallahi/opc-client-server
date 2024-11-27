package com.github.hamzafallahi.service.impl;

import com.github.hamzafallahi.entity.OPCClientDATA;
import com.github.hamzafallahi.repository.DataStorageRepository;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NodeValueReaderService {

    private static final Logger logger = LoggerFactory.getLogger(NodeValueReaderService.class);

    @Autowired
    private NodeBrowserService nodeBrowserService;

    @Autowired
    private DataStorageRepository opcClientDATARepository;

    @Scheduled(fixedRate = 5000)
    public void readNodeValues() throws Exception {
        List<UaVariableNode> variableNodes = nodeBrowserService.getVariableNodes();

        if (!variableNodes.isEmpty()) {
            for (UaVariableNode node : variableNodes) {
                DataValue value = node.readValue();

                // Get current timestamp
                LocalDateTime timestamp = LocalDateTime.now();

                // Get full node path
                String fullNodePath = getFullNodePath(node);

                // Get the NodeId of the variable type
                NodeId variableTypeNodeId = node.getDataType();

                // Fetch the UaNode corresponding to the variable type
                UaNode variableTypeNode = nodeBrowserService.getNodeByNodeId(variableTypeNodeId);
                String variableTypeDisplayName = variableTypeNode.readDisplayName().getText();  // Get the display name of the variable type

                // Logging node information
                logger.info("Node ID: {}, Full Path: {}, Value: {}, Variable Type: {}, Timestamp: {}",
                        node.getNodeId(), fullNodePath, value.getValue(), variableTypeDisplayName, timestamp);

                // Create OPCClientDATA entity and save to MongoDB
                OPCClientDATA opcClientDATA = new OPCClientDATA();
                opcClientDATA.setNodeID(node.getNodeId().toString());
                opcClientDATA.setDisplayName(node.getDisplayName().getText());
                opcClientDATA.setValue(value.getValue().toString());
                opcClientDATA.setVariableType(variableTypeDisplayName);  // Save the display name of the variable type
                opcClientDATA.setTimestamp(timestamp);  // Add timestamp field to your entity
                opcClientDATA.setFullNodePath(fullNodePath); // Add fullNodePath field to your entity

                opcClientDATARepository.save(opcClientDATA);
            }
        } else {
            logger.warn("No variable nodes found.");
        }
    }

    // Recursively build the full node path
    private String getFullNodePath(UaVariableNode node) throws Exception {
        StringBuilder fullPath = new StringBuilder(node.readBrowseName().getName());

        // Use the NodeBrowserService to get the parent node
        Optional<ReferenceDescription> parentNodeReference = nodeBrowserService.getParentNode(node);

        while (parentNodeReference.isPresent()) {
            UaNode parentNode = nodeBrowserService.getNodeByReference(parentNodeReference.get());
            fullPath.insert(0, parentNode.readBrowseName().getName() + "/");

            // Get the parent node of the current parent
            parentNodeReference = nodeBrowserService.getParentNode(parentNode);
        }

        return fullPath.toString();
    }
}
