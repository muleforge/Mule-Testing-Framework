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

import org.apache.log4j.Logger;
import org.mule.api.mtf.Device;
import org.mule.tool.mtf.logging.remote.RemoteLog4jReceiver;

public class RemoteLogAdaptor implements Device{

	private Thread receiverThread = null;
	private RemoteLog4jReceiver receiver = null;
	private Logger log = Logger.getLogger(RemoteLogAdaptor.class);

	private int wakeupMillis = 2000;
	
	public RemoteLogAdaptor(int port){
		receiver = new RemoteLog4jReceiver(port,wakeupMillis);
	}
	
	public void dispose() {
		if(receiverThread != null)
			receiver.stop();
	}

	public Object getDevice() {
		return receiver;
	}

	public void initialize() {
		// Nothing to do
	}

	public boolean isRunning() {
		if(receiverThread == null)
			return false;
		
		return receiverThread.isAlive();
	}

	public void pause(String component) {
		log.warn("Not applicable for this device");
	}

	public void start() {
    	receiverThread = new Thread(receiver);
    	receiverThread.start();
	}

	public void stop() {
		receiver.stop();
		receiverThread = null;
	}

}
