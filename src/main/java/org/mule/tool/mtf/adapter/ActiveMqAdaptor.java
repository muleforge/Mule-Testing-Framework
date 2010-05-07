/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tool.mtf.adapter;

import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import org.mule.api.mtf.Device;


public class ActiveMqAdaptor implements Device{

	private BrokerService activeMq = null;
	private Logger log = Logger.getLogger(ActiveMqAdaptor.class);
	private String hostPort;
	private String dataDirectory;
	
	public ActiveMqAdaptor(String hostPort,String dataDirectory){
		this.hostPort = hostPort;
		this.dataDirectory=dataDirectory;
	}
	
	public void initialize() {
		try {
			activeMq = new BrokerService();
			activeMq.addConnector(hostPort);
			activeMq.setDataDirectory(this.dataDirectory);
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
