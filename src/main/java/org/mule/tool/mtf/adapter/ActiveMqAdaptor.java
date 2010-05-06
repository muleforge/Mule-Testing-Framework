package org.mule.tool.mtf.adapter;

import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import org.mule.api.mtf.Device;


public class ActiveMqAdaptor implements Device{

	private BrokerService activeMq = null;
	private Logger log = Logger.getLogger(ActiveMqAdaptor.class);
	private String hostPort;
	
	public ActiveMqAdaptor(String hostPort){
		this.hostPort = hostPort;
	}
	
	public void initialize() {
		try {
			activeMq = new BrokerService();
			activeMq.addConnector(hostPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isRunning() {
		if(activeMq == null)
			return false;
		
		return activeMq.isStarted();
	}

	public void pause(String component) {
		log.warn("Pause not implemented with ActiveMq");
	}

	public void start() {
		try {
			if(activeMq == null)
				initialize();
			activeMq.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			if(activeMq != null)
				activeMq.stop();
			activeMq = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getDevice() {
		return activeMq;
	}

	public void dispose() {
		if(activeMq != null)
			stop();
	}
}
