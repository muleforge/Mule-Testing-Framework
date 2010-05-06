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

import java.io.File;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.mule.api.mtf.Device;

public class FTPServerAdaptor implements Device{

	private FtpServer device = null;
	private int port;
	
	public FTPServerAdaptor(String rootDir, int port){
		this.port = port;
	}
	
	public void initialize() {
		FtpServerFactory serverFactory = new FtpServerFactory();
		
		// Helps us run on a non-privileged port so we don't have to run as root on Unix/Linux
		ListenerFactory factory = new ListenerFactory();
		factory.setPort(this.port);
		serverFactory.addListener("default", factory.createListener());
		
		// Setup test user
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		userManagerFactory.setFile(new File("ftpserver.users.properties"));
		userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());
		serverFactory.setUserManager(userManagerFactory.createUserManager());
		
		device = serverFactory.createServer();
	}

	public boolean isRunning() {
		if( device == null || device.isStopped() || device.isSuspended())
			return false;
		
		return true;
	}

	public void pause(String component) {
		device.suspend();
	}

	public void start() {
		try {
			if(device==null)
				initialize();
			device.start();
		} catch (FtpException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to start FTP Server: "+e.getMessage());
		}
	}

	public void stop() {
		if(device != null){
			device.stop();
			device = null;
		}
	}

	public Object getDevice() {
		return device;
	}

	public void dispose() {
		if(isRunning())
			stop();
	}

}
