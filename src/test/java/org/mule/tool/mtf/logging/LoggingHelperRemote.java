package org.mule.tool.mtf.logging;

import org.apache.log4j.Logger;

public class LoggingHelperRemote {

	private static final Logger log = Logger.getLogger(LoggingHelperRemote.class);

	public static void logInfo(){
		log.info("This is an informational message");
	}	
}
