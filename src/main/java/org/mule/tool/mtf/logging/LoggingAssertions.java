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

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;

public final class LoggingAssertions {

	public static int retryDelay = 1000;
	public static int retryTimes = 5;

	private static Logger logger = Logger.getLogger("AssertTools");
	
	/**
	 * Make sure the given log message contains all strings within the String array
	 * 
	 * @param MessageKey  uniquely identifies a logging entry.
	 * @param integer most likely, this key identifies a set of messages, which one in particular do you want.
	 * @param String [] list of strings you expect this message to contain.  Think keywords that are important,
	 * not necessarily the entire string verbatim.
	 */
	public static void assertContains(MessageKey key, int index ,String[] components){

		for(int i=0;i<=retryTimes;i++){
			List<String> messages = HashMapLog.getMessages(key);
			String message = messages.get(index);
			
			for(int j=0;i<components.length;j++){
				if(message.indexOf(components[j]) == -1){
					if(i == retryTimes )
						fail("Did not find '"+components[j]+"' in log message "+message);
				} else {
					return;
				}
			}
			
			try{
				Thread.sleep(retryDelay);
			}catch(InterruptedException ex){
				logger.warn("Thread.sleep interrupted while trying to wait between attempts");
			}

			logger.info("Will attempt to find key again.  Perhaps too soon for: "+key);
		}
	}
	
	/**
	 * Make sure that at least one of log messages (based on key provided) contains all strings within the String array
	 * 
	 * @param MessageKey  uniquely identifies a logging entry.
	 * @param String [] list of strings you expect this message to contain.  Think keywords that are important,
	 * not necessarily the entire string verbatim.
	 */
	public static void assertContains(MessageKey key, String keyword){

		for(int i=0;i<=retryTimes;i++){
			List<String> messages = HashMapLog.getMessages(key);
			
			for(String message:messages){
				if(message.indexOf(keyword) != -1)
					return;
			}
			
			if( i == retryTimes )
				fail("Did not find '"+keyword+"' in any log message of type "+key);
			
			try{
				Thread.sleep(retryDelay);
			}catch(InterruptedException ex){
				logger.warn("Thread.sleep interrupted while trying to wait between attempts");
			}

			logger.info("Will attempt to find key again.  Perhaps too soon for: "+key);
		}
	}
	
	
	/**
	 * Assert that the DiagnosticAppender contains the appropriate number of log entried for the given key.
	 * The number of entries must match exactly.
	 *  
	 * @param MessageKey uniquely identifies a logging entry
	 * @param numberExpected number of messages you expect key to match on
	 */
	public static void assertNumberLogs(MessageKey key, int numberExpected){
		
		for(int i=0;i<=retryTimes;i++){
			int numberLogs = HashMapLog.getNumberMessages(key);
			
			if(numberLogs == numberExpected )
				return;
			
			if( i == retryTimes){
				String message = "Unexpected number of "
					+ key.getLevel() 
					+ " message(s) "
					+ " from "
					+ key.getCategory()
					+ " expected "
					+ numberExpected
					+ " but there was "
					+ numberLogs;
				
				fail(message);
			}

			try{
				Thread.sleep(retryDelay);
			}catch(InterruptedException ex){
				logger.warn("Thread.sleep interrupted while trying to wait between attempts");
			}
		
			logger.info("Will attempt to find key again.  Perhaps too soon for: "+key);
		}
	}
	
	/**
	 * Assert that the DiagnosticAppender contains at least the minimum number of log entries for the given key.
	 * A greater number than is specified is ok and will not produce a failure.
	 * 
	 * @param key
	 * @param minimumNumberExpected
	 */
	public static void assertMinNumberOfLogs(MessageKey key, int minimumNumberExpected){

		for(int i=0;i<=retryTimes;i++){
			int numberLogs = HashMapLog.getNumberMessages(key);
	
			if(numberLogs >= minimumNumberExpected)
				return;
				
			if(i == retryTimes){
				String message = "Not enough instances of "
					+ key.getLevel() 
					+ " message(s) "
					+ " from "
					+ key.getCategory()
					+ " expected at least "
					+ minimumNumberExpected
					+ " but there was only "
					+ numberLogs;

				fail(message);
			}

			try{
				Thread.sleep(retryDelay);
			}catch(InterruptedException ex){
				logger.warn("Thread.sleep interrupted while trying to wait between attempts");
			}
			
			logger.info("Will attempt to find key again.  Perhaps too soon for: "+key);
		} // end for
	}


	/**
	 * Test to see if DiagnosticAppender contains at least the minimum number of log entries for the given key.
	 * A greater number is OK too
	 * 
	 * @param key
	 * @param minimumNumberExpected
	 * @return
	 */
	public static boolean isMinNumberOfLogs(MessageKey key, int minimumNumberExpected){
		for(int i=0;i<=retryTimes;i++){
			int numberLogs = HashMapLog.getNumberMessages(key);
	
			if(numberLogs < minimumNumberExpected && i == retryTimes)
				return false;
			
			try{
				Thread.sleep(retryDelay);
			}catch(InterruptedException ex){
				logger.warn("Thread.sleep interrupted while trying to wait between attempts");
			}

			logger.info("Will attempt to find key again.  Perhaps too soon for: "+key);
		}
		
       return true;
	}
}
