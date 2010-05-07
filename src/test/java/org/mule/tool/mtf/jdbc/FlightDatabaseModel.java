package org.mule.tool.mtf.jdbc;

import java.util.HashMap;

import org.mule.api.mtf.DatabaseModel;


public class FlightDatabaseModel implements DatabaseModel{

	private HashMap<String,String> namedQueries = new HashMap<String,String>();	
	
	// Constant refers to named query within Model
	public static final String FLOWN_COUNT = "FlownCount";
	public static final String NEW_FLIGHTS = "NewFlights";
	
	public FlightDatabaseModel(){
		namedQueries.put("FlownCount", "select * from flight where departed = 2");
		namedQueries.put("NewFlights", "select * from flight where flight_id > 4");
	}

	public String[] getInitialDataSqls() {
		return new String[]{
		   	"insert into flight values (1,'2009-01-01',1234,'DAL','HOU',1)",
		   	"insert into flight values (2,'2009-01-02',1234,'DAL','HOU',1)",
		   	"insert into flight values (3,'2009-01-03',1234,'DAL','HOU',0)",
		   	"insert into flight values (4,'2009-01-03',5678,'HOU','ABQ',0)"
		};
	}

	public String[] getResetSqls() {
		return new String[]{
			"update flight set departed = 1 where flight_id in (1,2)",
			"delete from flight where flight_id > 4"
		};
	}

	public String[] getTableSqls() {
		
		String flightTable = 
			"CREATE TABLE flight ("
				+	"flight_id bigint NOT NULL,"
				+	"date DATE NOT NULL,"
				+	"flight_nbr VARCHAR(4) NOT NULL,"
				+	"orig VARCHAR(3) NOT NULL,"
				+	"dest VARCHAR(3) NOT NULL,"
			    +   "departed int default 0,"
			    +   "primary key (flight_id) )";

		return new String[] {flightTable};
	}

	public String getQuery(String name) {
		return namedQueries.get(name);
	}

	public String getDbConnectionString() {
		return "jdbc:hsqldb:hsql://localhost/xdb";
	}

	public String getDbDataPath() {
		return "./target/flightdb";
	}

	public String getDbName() {
		return "xdb";
	}

	public String getUserName() {
		return "sa";
	}

	public String getPassword() {
		return "";
	}


}
