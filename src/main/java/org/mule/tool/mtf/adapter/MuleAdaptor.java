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
import org.mule.tool.mtf.classpath.RuntimeClasspath;

public class MuleAdaptor extends AbstractProcessAdaptor implements Device{

	private Logger log = Logger.getLogger(MuleAdaptor.class);
	
	public MuleAdaptor(String muleConfig, RuntimeClasspath classpath) {
		String commandLine = 
			  "java -classpath "
			+ classpath.getClassPath() 
			+ " org.mule.MuleServer "
			+ " -config "
			+ muleConfig;
	
		log.info("Starting Remote Mule process: "+commandLine);
		processAdaptor = new ProcessAdaptor(commandLine);
				
	}

}
