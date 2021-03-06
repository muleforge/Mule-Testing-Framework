/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.mule.api.mtf.Device;
import org.mule.module.client.MuleClient;
import org.mule.tool.mtf.adapter.ActiveMqAdaptor;
import org.mule.tool.mtf.adapter.MuleAdaptor;
import org.mule.tool.mtf.adapter.RemoteLogAdaptor;
import org.mule.tool.mtf.classpath.RuntimeClasspath;

public abstract class AbstractMuleTest {

	protected static long pauseAfterTestComplete = 0;
	protected static DeviceManager manager = new DeviceManager();
	protected static Logger logger = null;
	protected static MuleClient client = null;

	public abstract void initializeLogging();
	
	@AfterClass
	public static void abstractTeardown() throws Exception{
		manager.dispose(pauseAfterTestComplete);
		if(logger != null)
			logger.info("Disposed of Test Devices");
		
	}
	
	public AbstractMuleTest(){
		initializeLogging();
	}
	
	protected static void addDevice(final String alias, final Device device){
		manager.addDevice(alias, device);
	}
	
	protected static void addJmsDevice(){
		addDevice("activemq", new ActiveMqAdaptor(MTFConstants.ACTIVE_MQ_URL,MTFConstants.ACTIVE_MQ_DATA_DIR));
	}
	
	protected static void addRemoteLog4jListener(){
		// Create a listener for remote logging from our mule container
		manager.addDevice("log4j-receiver", new RemoteLogAdaptor(MTFConstants.REMOTE_LOG4J_PORT));
	}
	
	protected static Device addRemoteMuleService(final String alias,  final String configFile, RuntimeClasspath classpath){
		// Create mule containers in a separate process space
		Device processDevice = new MuleAdaptor(configFile,classpath);
		addDevice(alias, processDevice);
		
		// Wait for mule to startup
		try{
			Thread.sleep(5000);
		}catch(Exception ex){
			// Interrupted sleep, what can we do?
		}
		
		return processDevice;
	}	
}
