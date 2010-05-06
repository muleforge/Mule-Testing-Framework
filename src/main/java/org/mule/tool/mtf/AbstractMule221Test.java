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

import org.mule.api.mtf.Device;
import org.mule.module.client.MuleClient;
import org.mule.tool.mtf.adapter.Mule221Adaptor;

public abstract class AbstractMule221Test extends AbstractMuleTest {

	protected static void addLocalMuleService(final String alias, final String configFile){
		// Create mule containers in a separate process space
		Device muleTcp = new Mule221Adaptor(configFile);
		addDevice(alias, muleTcp);
		
		// Wait for mule to startup
		try{
			Thread.sleep(5000);
		}catch(Exception ex){
			// Interrupted sleep, what can we do?
		}
		
		client = (MuleClient) muleTcp.getDevice();
	}
	
}
