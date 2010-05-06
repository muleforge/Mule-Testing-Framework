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

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.mule.api.mtf.Device;


public class DeviceManager {

	private HashMap<String,Device> devices = new HashMap<String,Device>();
	private ArrayList<String> deviceOrder = new ArrayList<String>();
	
	private static Logger logger = Logger.getLogger(DeviceManager.class);
	
	public void addDevice(String deviceName, Device device){
		devices.put(deviceName, device);
		
		// Maintain order for proper disposal
		deviceOrder.add(deviceName);
		
		device.initialize();
		device.start();
	}
	
	public void removeDevice(String deviceName){
		Device device  = getDevice(deviceName);
		
		// Shutdown device before removal
		device.stop();
		device.dispose();
		
		devices.remove(device);
		deviceOrder.remove(deviceName);
	}
	
	public boolean isRunning(String deviceName){
		Device device  = getDevice(deviceName);

		return device.isRunning();
	}

	public void stopDevice(String deviceName){
		Device device  = getDevice(deviceName);
		device.stop();
	}

	public void startDevice(String deviceName){
		Device device  = getDevice(deviceName);
		device.start();
	}
	/**
	 * Shutdown all devices.  But wait
	 * @param number of millis before dispose
	 */
	public void dispose(long sleepMillis){
		try {
			Thread.sleep(sleepMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Remove in reverse order allocated to avoid dependency exceptions
		for(int i=deviceOrder.size()-1;i >= 0;i--){
			String deviceName = deviceOrder.get(i);
			removeDevice(deviceName);
		}
	}
	
	private Device getDevice(String deviceName){
		if(!devices.containsKey(deviceName))
			throw new IllegalArgumentException("Unknown device: "+deviceName);
		
		return devices.get(deviceName);
	}
	
}
