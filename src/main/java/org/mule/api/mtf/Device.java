/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.mtf;

public interface Device {

	/**
	 * Do tasks required for initialization after construction, but before start
	 */
	public void initialize();

	/**
	 * Start the device.  Device should be fully functional before returning from this method.
	 * Any paused device components should be started.
	 */
	public void start();
	
	/**
	 * If device has the concept of multiple parts which can be stopped.  This is applicable to 
	 * Mule endpoints which could be stopped independently.
	 * @param component
	 */
	public void pause(String component);
	
	/**
	 * Stop the device.  Services from the device should be unavailable before exiting this method
	 */
	public void stop();
	
	/**
	 * Cleanup any resources created by this device
	 */
	public void dispose();
	
	/**
	 * Is this device started and functioning properly
	 * @return boolean
	 */
	public boolean isRunning();
	
	/**
	 * Return the underlying device object.  Client must implicitly know what the actual class is
	 * and how to interact with it.
	 * @return
	 */
	public Object getDevice();
}
