package org.mule.tool.mtf.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleException;
import org.mule.api.mtf.DatabaseModel;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.adapter.HsqlAdaptor;
import org.mule.tool.mtf.database.DatabaseUtility;
import org.mule.tool.mtf.database.HsqlJDBCAdaptor;

import static org.junit.Assert.*;

public class JDBCInsertTestCase extends AbstractMule221Test{

	private static DatabaseModel dbModel = new FlightDatabaseModel();
	private static DatabaseUtility dbUtil = null;
	
	@BeforeClass
	public static void globalSetup() throws Exception{
		
		// Start database
		manager.addDevice("hsql", new HsqlAdaptor(dbModel));
		
		// Start Active MQ
		addJmsDevice();

		// Will create the database and setup data
		dbUtil = new DatabaseUtility(new HsqlJDBCAdaptor(dbModel),dbModel); 

		// Start Mule service which will handle JMS messages and create db records from them
		addLocalMuleService("mule", "src/test/resources/jdbc/mule2x-jdbc-insert.xml");
	}
	
	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(JDBCInsertTestCase.class);
	}
	
	@Test
	public void flightInsertionViaJMS(){
		
		sendJMSFlightCreationRequest(5);
		sendJMSFlightCreationRequest(6);
				
		// Wait
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Validate record creation
		assertEquals("Expected two records created from JMS messages",2,
				dbUtil.getCountFor(dbModel.getQuery(FlightDatabaseModel.NEW_FLIGHTS)));
	}

	private void sendJMSFlightCreationRequest(int flightId){
		Map<String,String> mapMessage = new HashMap<String,String>();
		mapMessage.put("flightId",Integer.toString(flightId));
		mapMessage.put("date","2009-01-01");
		mapMessage.put("flightNumber","123");
		mapMessage.put("origin","DAL");
		mapMessage.put("destination","HOU");
		mapMessage.put("departed","0");
		
		// Send messages
		try {
			client.dispatch("jms://JMS.FLIGHT.IN", mapMessage, null);
		} catch (MuleException e) {
			e.printStackTrace();
		}
	}


}

