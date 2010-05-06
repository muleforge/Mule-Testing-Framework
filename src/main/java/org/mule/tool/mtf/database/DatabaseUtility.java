/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.mule.api.mtf.DatabaseAdaptor;
import org.mule.api.mtf.DatabaseModel;

public class DatabaseUtility {

	private DatabaseModel dbModel     = null;
	private DatabaseAdaptor dbAdaptor = null; 
	
	private Logger logger = Logger.getLogger(DatabaseUtility.class);
	
	public DatabaseUtility(DatabaseAdaptor dbAdaptor, DatabaseModel dbModel){
		this.dbAdaptor = dbAdaptor;
		this.dbModel   = dbModel;
		
		dbAdaptor.initialize();
	
		initializeTable();
		initializeData();
	}
	
	public void reset(){
		executeUpdates(dbModel.getResetSqls());
	}

	public void executeQuery(String sql){
		executeUpdates(new String[] {sql});
	}
	
	public int getCountFor(String sql){

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		int rowCount = 0;
		try{
			conn = dbAdaptor.getConnection();
			stmt = conn.prepareStatement(sql);
			result = stmt.executeQuery();
			
			while(result.next())
				rowCount++;
			
			return rowCount;
		}catch(Exception ex){
			logger.error(ex.getMessage());
			//ex.printStackTrace();
			//throw new RuntimeException("Failed to execute JDBC commands");
			return 0;
		}finally{
			try{
				if(result != null)
					result.close();
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}catch(SQLException ex){
				//ex.printStackTrace();
				//throw new RuntimeException("Failed to properly close result, statement or connection");
				logger.error(ex.getMessage());
				return 0;
			}
		}
	}

	protected void initializeTable() {
		executeUpdates(dbModel.getTableSqls());
	}
	
	protected void initializeData(){
		executeUpdates(dbModel.getInitialDataSqls());
	}
	
	protected void executeUpdates(String[] updates){
		Connection conn = null;
		Statement stmt = null;
		
		try{
			conn = dbAdaptor.getConnection();
			stmt = conn.createStatement();

			for(int i=0;i<updates.length;i++)
				stmt.executeUpdate(updates[i]);
		}catch(Exception ex){
			//ex.printStackTrace();
			//throw new RuntimeException("Failed to execute update");
			logger.error(ex.getMessage());
		}finally{
			try{
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			}catch(SQLException ex){
				//ex.printStackTrace();
				//throw new RuntimeException("Failed to properly close statement or connection");
				logger.error(ex.getMessage());
			}
		}
	}
}
