package org.mule.tool.mtf;

public final class MTFConstants {

	public static final String RESOURCE_DIR = "src/test/resources";
	public static final String TARGET_DIR   = "target";
	
	public static final String ACTIVE_MQ_URL      = "tcp://localhost:61616";
	public static final String ACTIVE_MQ_DATA_DIR = "target/activemqdata";

	public static final String JMS_QUEUE          = "jms://JMS.IN";
	
	public static final String FTP_USERS       = RESOURCE_DIR+"/ftpserver.users.properties";
	public static final String FTP_DATA_DIR    = TARGET_DIR+"/data/ftp";
	public static final int    FTP_PORT        = 2001;

	public static final int    REMOTE_LOG4J_PORT = 4000;

	public static final String FTP_FILE_DIR = TARGET_DIR+"/data/file";
	
	// Not to be instantiated
	private MTFConstants(){
		
	}
}
