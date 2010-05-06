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
		addDevice("activemq", new ActiveMqAdaptor("tcp://localhost:61616"));
	}
	
	protected static void addRemoteLog4jListener(){
		// Create a listener for remote logging from our mule container
		manager.addDevice("log4j-receiver", new RemoteLogAdaptor(4000));
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
