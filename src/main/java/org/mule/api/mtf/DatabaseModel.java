/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.api.mtf;

public interface DatabaseModel {

	public String[] getTableSqls();
	
	public String[] getInitialDataSqls();
	
	public String[] getResetSqls();
	
	public String   getQuery(String name);
	
	public String getDbName();
	
	public String getDbDataPath();
	
	public String getDbConnectionString();
	
	public String getUserName();
	
	public String getPassword();

}
