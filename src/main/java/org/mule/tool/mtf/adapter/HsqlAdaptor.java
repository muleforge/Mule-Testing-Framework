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
import org.hsqldb.Server;
import org.mule.api.mtf.DatabaseModel;
import org.mule.api.mtf.Device;


public class HsqlAdaptor implements Device {

	private Server dbServer;
	private Logger log = Logger.getLogger(HsqlAdaptor.class);
	private String dbName;
	private String dbPath;
	
	public HsqlAdaptor(DatabaseModel model){
		this.dbName = model.getDbName();
		this.dbPath = model.getDbDataPath();
	}
	
	public void initialize() {
		dbServer = new Server();

		dbServer.setDatabaseName(0, dbName);
		dbServer.setDatabasePath(0, dbPath);
	}
	
	public void dispose() {
		dbServer.shutdown();
	}

	public void pause(String component) {
		log.warn("Pausing not suported by Hypersonic DB");
	}

	public void start() {
		dbServer.start();
	}

	public void stop() {
		dbServer.stop();
	}

	public boolean isRunning() {
		if(dbServer.getState() == 1)
			return true;
		else
			return false;
	}

	public Object getDevice() {
		return dbServer;
	}


}
