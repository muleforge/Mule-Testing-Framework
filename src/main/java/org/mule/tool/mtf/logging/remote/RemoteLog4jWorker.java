/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.logging.remote;

import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.mule.tool.mtf.logging.HashMapLog;
import org.mule.tool.mtf.logging.MessageKey;

public final class RemoteLog4jWorker implements Runnable{

	private String remoteHost;
    private ObjectInputStream inStream = null;
    private LoggingEvent event = null;
    private Logger logger = Logger.getLogger(RemoteLog4jWorker.class);
    
	public RemoteLog4jWorker(String remoteHost, ObjectInputStream inStream){
		this.remoteHost = remoteHost;
		this.inStream   = inStream;
	}
	
	public void run() {

		String category = "";
        String level = "";  
        String message = "";
		
        try {
        	// Will keep running until client disconnects
            while(true) {
                event = (LoggingEvent)inStream.readObject();
                
                category = event.getLoggerName().toString();
                level    = event.getLevel().toString();  
                message  = "("+remoteHost+" )"+ event.getMessage().toString();
                //LocationInfo info = event.getLocationInformation();

                // NOTE: Might want to integrate remoteHost into key later.
                MessageKey key = new MessageKey(category,level);
                
                HashMapLog.log(key, message);
                //logger.info("Added via remote logging: "+key);
                
            }
        }catch(Exception e) {
        	String msg = "Error while processing ("+category+","+level+","+message+"): ";
            logger.warn(msg+e.toString());
        }
	}

}
