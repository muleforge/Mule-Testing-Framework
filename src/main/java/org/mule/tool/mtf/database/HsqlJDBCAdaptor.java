package org.mule.tool.mtf.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.mule.api.mtf.DatabaseAdaptor;
import org.mule.api.mtf.DatabaseModel;


public class HsqlJDBCAdaptor implements DatabaseAdaptor{
	private String connectionString;
	private String userName;
	private String password;
	
	public HsqlJDBCAdaptor(DatabaseModel dbModel) {
		this.connectionString = dbModel.getDbConnectionString();
		this.userName         = dbModel.getUserName();
		this.password         = dbModel.getPassword();
	}

	public void initialize() {
		try {
			Driver hsqlDriver = new org.hsqldb.jdbcDriver();
			DriverManager.registerDriver(hsqlDriver);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to registered Hypersonic driver with DriverManager");
		}
	}

	public Connection getConnection(){
		try {
			return DriverManager.getConnection(connectionString, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to establish connection to Hypersonic");
		}
	}

}
