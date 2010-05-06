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

public class LoggingHelperRemote {

	private static final Logger log = Logger.getLogger(LoggingHelperRemote.class);

	public static void logInfo(){
		log.info("This is an informational message");
	}	
}
