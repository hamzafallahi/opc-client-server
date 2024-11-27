/*
 *
 *  * Copyright (c) [2017] - [2024]
 *  * ADDIXO MEA (MOMSOFT) All rights reserved.
 *
 */

package com.github.hamzafallahi.opcserver.secureserver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.hamzafallahi.opcserver.secureserver.SecureServer;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.eclipse.milo.opcua.sdk.core.AccessLevel;
import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.ManagedNamespace;
import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.nodes.UaFolderNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.server.nodes.filters.AttributeFilters;
import org.eclipse.milo.opcua.sdk.server.util.SubscriptionModel;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 
 * @author Hamza Fallahi
 * This class implements the namespace to add object to the defined server
 *
 */
@Component("secure-server-namespace")
public class SecureServerNamespace extends ManagedNamespace {

	private final Log logger = LogFactory.getLog(getClass()); 

	private static final String ROOT_NODE_NAME = "Random-FOLDER";

	private final Random random = new Random();

	//Allow a client to subscribe a value
	private final SubscriptionModel subscriptionModel;
	
	@Autowired
	public SecureServerNamespace(SecureServer secureServer) {
		super(secureServer.getServer(), "urn:com:github:hamzafallahi:secure-server:random");
		this.subscriptionModel = new SubscriptionModel(secureServer.getServer(), this);
		
		this.startup();
	}

	//Startup this namespace
	@Override
	protected void onStartup() {
		super.onStartup();
		
		logger.info("Startup Secure Namespace");
		
		this.subscriptionModel.startup();

		// Create Root Folder Identifier
		NodeId rootNodeId = newNodeId(ROOT_NODE_NAME);

		//Create Root folder
		UaFolderNode rootNode = new UaFolderNode(getNodeContext(),   
				rootNodeId, 
				newQualifiedName(ROOT_NODE_NAME), 
				LocalizedText.english(ROOT_NODE_NAME)
			);

		//Add folder to node manager
		this.getNodeManager().addNode(rootNode);

		//Add the reference to root node
		rootNode.addReference(new Reference(
				rootNode.getNodeId(),
				Identifiers.Organizes,
				Identifiers.ObjectsFolder.expanded(),
				false
				));
		
		
		//Create Dynamic value folder and add it to rootNode as sub-folder
		this.createDynamicValuesController(rootNode);
	}


	private void createDynamicValuesController(UaFolderNode rootNode) {

		// Create dynamic Folder
		UaFolderNode dynamicFolder = new UaFolderNode(
				getNodeContext(),
				newNodeId(ROOT_NODE_NAME + "/Dynamic"),
				newQualifiedName("Dynamic"),
				LocalizedText.english("Dynamic")
		);

		getNodeManager().addNode(dynamicFolder);
		rootNode.addOrganizes(dynamicFolder);

		// Dynamic Rand 100
		{
			String name = "Rand100";
			// Define the value type of the node as integer @32bit
			NodeId typeId = Identifiers.Int32;
			// Define the node content as variable value
			Variant variant = new Variant(0);

			// Define the node as Variable Node
			UaVariableNode rand100Node = new UaVariableNode.UaVariableNodeBuilder(
					getNodeContext())
					.setNodeId(newNodeId(ROOT_NODE_NAME + "/Dynamic/" + name)) // Define the node name
					.setAccessLevel(AccessLevel.toValue(AccessLevel.READ_WRITE)) // Define that the node is accessible only in read
					.setBrowseName(newQualifiedName(name)) // setup the name
					.setDisplayName(LocalizedText.english(name))
					.setDescription(LocalizedText.english("Provide a random value in range 0 -100"))
					.setDataType(typeId) // setup the type
					.setTypeDefinition(Identifiers.BaseDataVariableType) // Setup the definition as variable types
					.build();

			// Set initial value
			rand100Node.setValue(new DataValue(variant));

			// Schedule a task to update the node's value every 1 second
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(() -> {
				int randomValue = this.random.nextInt(100); // Generate random value between 0 - 100
				rand100Node.setValue(new DataValue(new Variant(randomValue)));
				System.out.println("Updated Rand100 with value: " + randomValue);
			}, 0, 1, TimeUnit.SECONDS);

			// Add rand node to node manager
			getNodeManager().addNode(rand100Node);

			// Add rand node to dynamic folder
			dynamicFolder.addOrganizes(rand100Node);
		}
	}


	//Shutdown this namespace
	@Override
	protected void onShutdown() {
		logger.info("Shutdown Secure Namespace");
		subscriptionModel.shutdown();
		super.onShutdown();
	}

	@Override
	public void onDataItemsCreated(List<DataItem> dataItems) {
		logger.info("Create subscription ");
		this.subscriptionModel.onDataItemsCreated(dataItems);

	}

	@Override
	public void onDataItemsModified(List<DataItem> dataItems) {
		logger.info("Modify Subscription");
		this.subscriptionModel.onDataItemsModified(dataItems);

	}

	@Override
	public void onDataItemsDeleted(List<DataItem> dataItems) {
		logger.info("Delete Subscription");
		this.subscriptionModel.onDataItemsDeleted(dataItems);
	}

	@Override
	public void onMonitoringModeChanged(List<MonitoredItem> monitoredItems) {
		logger.info("Monitor Subscription");
		this.subscriptionModel.onMonitoringModeChanged(monitoredItems);

	}

}
