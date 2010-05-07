package org.mule.tool.mtf.jdbc;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.mtf.DatabaseModel;
import org.mule.component.simple.LogComponent;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.adapter.HsqlAdaptor;
import org.mule.tool.mtf.database.DatabaseUtility;
import org.mule.tool.mtf.database.HsqlJDBCAdaptor;
import org.mule.tool.mtf.logging.LoggingAssertions;
import org.mule.tool.mtf.logging.MessageKey;

import static org.junit.Assert.*;

public class TestJdbcSelect extends AbstractMule221Test{

	private static DatabaseModel dbModel = new FlightDatabaseModel(); 
	private static DatabaseUtility dbUtil = null;
	
	@BeforeClass
	public static void globalSetup() throws Exception{
		manager.addDevice("hsql", new HsqlAdaptor(dbModel));

		dbUtil = new DatabaseUtility(new HsqlJDBCAdaptor(dbModel),dbModel);
		
		addLocalMuleService("mule", "src/test/resources/jdbc/mule2x-jdbc-select.xml");
	}
	
	@AfterClass
	public static void globalTearDown(){
		dbUtil.reset();
	}
	
	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(TestJdbcSelect.class);
	}
	
	@Test
	public void processDepartedFlights() throws Exception{
		
		// Wait a few seconds to let mule process records
		Thread.sleep(10000);
		
		// Validate that the records have been processed and updated
		assertEquals("Expected two rows to have departed == 2",2,
				dbUtil.getCountFor(dbModel.getQuery(FlightDatabaseModel.FLOWN_COUNT)));
		
		// Validate 2 messages received from the log component
		MessageKey key = new MessageKey(LogComponent.class,"INFO");
		LoggingAssertions.assertNumberLogs(key, 2);
	}

}

