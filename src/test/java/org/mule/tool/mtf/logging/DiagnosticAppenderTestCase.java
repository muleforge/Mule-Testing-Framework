/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.logging;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mule.tool.mtf.DeviceManager;
import org.mule.tool.mtf.MTFConstants;
import org.mule.tool.mtf.adapter.RemoteLogAdaptor;
import org.mule.tool.mtf.logging.MessageKey;

import org.mule.tool.mtf.logging.LoggingAssertions;

public class DiagnosticAppenderTestCase {
	private static final Logger log = Logger.getLogger(DiagnosticAppenderTestCase.class);
	private static DeviceManager manager = new DeviceManager();
	
	@BeforeClass
	public static void globalSetup(){
		manager.addDevice("remote-log4j",new RemoteLogAdaptor(MTFConstants.REMOTE_LOG4J_PORT));
		
		// Give SocketAppender chance to reconnect to server that we just started.
		sleep(2000);
	}
	
	@AfterClass
	public static void globalTeardown(){
		manager.dispose(1);
	}
	
	@Test
	public void infoMessages(){
		excerciseLogging("This is a message from the test case 12345", "12345","INFO");
	}
	
	@Test
	public void errorMessages(){
		excerciseLogging("This is a message from the test case 5678", "5678","ERROR");
	}
	
	@Test
	public void remoteLogging(){
		LoggingHelperRemote.logInfo();
		
		// Give a little lag time so log message will have arrived
		sleep(1000);
		
		MessageKey key = new MessageKey(LoggingHelperRemote.class,"INFO");
		LoggingAssertions.assertNumberLogs(key,1);
	}
	
	@Test
	public void messageStorm(){
		// These messages did not go to the console, but went to the diagostic logger
		// Which will allow 1 message through, but filter out the other 99 due to them
		// happening within the timeout window
		for(int i=0;i<100;i++){
			LoggingHelper.logError("Error#: "+i);
		}

		// Wait 5 seconds to ensure next error goes through due to timeout expiring
		sleep(5000);
		LoggingHelper.logError("Error X");
		LoggingHelper.logError("Error Y"); // Should be filtered
		
		MessageKey key = new MessageKey(LoggingHelper.class,"ERROR");
		LoggingAssertions.assertNumberLogs(key,2);
	}
	
	private void excerciseLogging(String message, String searchString, String logLevel){
		
		if(logLevel.equals("INFO"))
			log.info(message);
		
		if(logLevel.equals("ERROR"))
			log.error(message);
		
		// Our test enabled appender will tell us how many INFO messages were logged from this test class
		MessageKey key = new MessageKey(DiagnosticAppenderTestCase.class,logLevel);
		LoggingAssertions.assertNumberLogs(key,1);
		
		// Let's validate that the logged message contains the expected content
		// Our String [] contains 'keywords' which we expect our log message to contain
		// Over time, exact log verbiage may change, but keywords should remain.
		// This let's you enforce that the logging message contain specific pieces of critical information.
		LoggingAssertions.assertContains(key,0,new String[] {searchString, "message"});
	}

	private static void sleep(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
