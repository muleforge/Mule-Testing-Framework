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
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.mtf.Device;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.module.client.MuleClient;


public class Mule221Adaptor implements Device{
	private MuleContext muleContext = null;
	private MuleClient client = null;
	private String muleConfig;
	private Logger log = Logger.getLogger(Mule221Adaptor.class);
	
	public Mule221Adaptor(String muleConfig){
		this.muleConfig = muleConfig;
	}
	
	public void initialize() {
		if(muleContext==null)
			try{
				DefaultMuleContextFactory muleContextFactory = new DefaultMuleContextFactory();
				if(muleConfig == null || muleConfig.length() == 0)
					muleContext = muleContextFactory.createMuleContext();
				else{
					SpringXmlConfigurationBuilder configBuilder = new SpringXmlConfigurationBuilder(muleConfig);
					muleContext = muleContextFactory.createMuleContext(configBuilder);
				}
				client = new MuleClient(muleContext);
			}catch(Exception ex){
				String message = "Failed to properly initialize MuleClient: "+ex.getMessage(); 
				log.error(message);
			}
	}

	public boolean isRunning() {
		if(muleContext == null){
			log.warn("Mule server was not properly initialized.  Cannot determine its state.");
			return false;
		}

		return muleContext.isStarted();
	}

	public void pause(String component) {
		log.warn("Not yet implemented");
	}

	public void start() {
		if(muleContext == null){
			log.warn("Mule server was not properly initialized.  Cannot start it.");
			return;
		}

		try {
			muleContext.start();
		} catch (MuleException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if(muleContext == null){
			log.warn("Mule server was not properly initialized.  Cannot stop it.");
			return;
		}
		
		try {
			muleContext.stop();
		} catch (MuleException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		if(muleContext == null){
			log.warn("Mule server was not properly initialized.  Cannot dispose of it.");
			return;
		}

		muleContext.dispose();
		muleContext=null;
	}

	public Object getDevice() {
		if(client == null){
			log.warn("Mule was not properly initialized, client is null");
		}
		
		return client;
	}

}
