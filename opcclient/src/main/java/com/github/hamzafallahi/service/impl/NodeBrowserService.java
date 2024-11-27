package com.github.hamzafallahi.service.impl;

import org.eclipse.milo.opcua.sdk.client.AddressSpace;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaObjectNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseResultMask;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseResult;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@Service
public class NodeBrowserService {

    private static final Logger logger = LoggerFactory.getLogger(NodeBrowserService.class);
    private final List<UaVariableNode> variableNodes = new ArrayList<>();

    @Autowired
    private ConnectionService connectionService;

    // Main browsing method
    public void browseNodes(UShort namespaceIndexFilter) throws Exception {
        OpcUaClient client = connectionService.getClient();
        AddressSpace addressSpace = client.getAddressSpace();
        UaNode rootNode = addressSpace.getNode(Identifiers.RootFolder);

        // Recursively browse from the root node
        browseRecursive(rootNode, addressSpace,namespaceIndexFilter);
    }

    // Recursive browsing method to explore the full node hierarchy
    private void browseRecursive(UaNode node, AddressSpace addressSpace,UShort namespaceIndexFilter) throws Exception {
        logger.info("Browsing node: {} with browse name: {}", node.getNodeId(), node.readBrowseName());

        // Fetch child nodes
        List<? extends UaNode> childNodes = addressSpace.browseNodes(node);

        for (UaNode childNode : childNodes) {
            if (childNode instanceof UaVariableNode) {
                UaVariableNode variableNode = (UaVariableNode) childNode;
                if (Objects.equals(variableNode.getNodeId().getNamespaceIndex(), namespaceIndexFilter)) {
                    variableNodes.add(variableNode);
                    logger.info("Node ID: {} , Browse name: {}", variableNode.getNodeId(), variableNode.readBrowseName());
                }
            }
            // Recursively browse further if this is not a leaf node
            browseRecursive(childNode, addressSpace, namespaceIndexFilter);
        }
    }
    public Optional<ReferenceDescription> getParentNode(UaNode node) throws ExecutionException, InterruptedException {
        OpcUaClient client = connectionService.getClient();  // Use the client from ConnectionService

        BrowseDescription browseDescription = new BrowseDescription(
                node.getNodeId(),
                BrowseDirection.Inverse, // Browse "upwards" for parent node
                Identifiers.References,
                true,
                Unsigned.uint(NodeClass.Object.getValue() | NodeClass.Variable.getValue()), // Browse objects or variables
                Unsigned.uint(BrowseResultMask.All.getValue())
        );

        // Perform the browse operation
        BrowseResult browseResult = client.browse(browseDescription).get();

        // Extract references from the BrowseResult
        List<ReferenceDescription> references = List.of(browseResult.getReferences());

        if (references.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(references.get(0)); // Return the first result, assuming it's the parent node
    }


    public List<UaVariableNode> getVariableNodes() {
        return variableNodes;
    }
    public UaNode getNodeByReference(ReferenceDescription reference) throws Exception {
        OpcUaClient client = connectionService.getClient();  // Get the client from ConnectionService

        // Resolve the nodeId from the reference and retrieve the UaNode
        return client.getAddressSpace()
                .getNode(reference.getNodeId().local(client.getNamespaceTable()).get())
                ; // This get() resolves the CompletableFuture<UaNode> to UaNode
    }
    public UaNode getNodeByNodeId(NodeId nodeId) throws Exception {
        OpcUaClient client = connectionService.getClient();  // Use the client from ConnectionService
        return client.getAddressSpace().getNode(nodeId);  // This resolves the CompletableFuture<UaNode> to UaNode
    }



}
