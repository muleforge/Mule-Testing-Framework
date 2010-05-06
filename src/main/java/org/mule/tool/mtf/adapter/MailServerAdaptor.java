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

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;


public class MailServerAdaptor implements Device{

	private GreenMail device = null;
	private Logger log = Logger.getLogger(MailServerAdaptor.class);
	
	private String userEmail;
	private String userPassword;
	
	private boolean isRunning = false;
	
	public static final int SMTP_PORT = 3025;
	
	public MailServerAdaptor(String userEmail, String userPassword){
		this.userEmail    = userEmail;
		this.userPassword = userPassword;
	}
	
	public void initialize() {
		ServerSetup setup = new ServerSetup(SMTP_PORT, null, "smtp");
		device = new GreenMail(setup);
		device.setUser(userEmail, userPassword);
	}

	public boolean isRunning() {
		if(!isRunning)
			return false;
		
		return true;
	}

	public void pause(String component) {
		log.warn("Pausing not suported by Green Mail");
	}

	public void start() {
		if(device == null)
			initialize();
		device.start();
		isRunning = true;
	}

	public void stop() {
		if(device != null){
			device.stop();
			device = null;
		}
		isRunning = false;
	}

	public Object getDevice() {
		return device;
	}

	public void dispose() {
		if(isRunning)
			stop();
	}

}
