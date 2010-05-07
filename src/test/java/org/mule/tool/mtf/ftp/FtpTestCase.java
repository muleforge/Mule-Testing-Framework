package org.mule.tool.mtf.ftp;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.adapter.FTPServerAdaptor;
import org.mule.tool.mtf.file.TestFileUtility;

public class FtpTestCase extends AbstractMule221Test {
	
	private static final String FTP_DIR      = "/data/ftp";
	private static final String FILE_DIR     = "/data/file";
	private static final String RESOURCE_DIR = "src/test/resources";
	private static final String FTP_USERS    = RESOURCE_DIR+"/ftpserver.users.properties";
	
	@BeforeClass
	public static void globalSetup(){
		manager.addDevice("ftp",new FTPServerAdaptor(FTP_DIR,FTP_USERS,2001));
		addLocalMuleService("mule", RESOURCE_DIR+"/ftp/mule2x-ftp-filter.xml");
	}

	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(FtpTestCase.class);
	}
	
	@Test
	public void ftpFileNotMatchingCriteria() throws Exception{
		TestFileUtility.createMuleFile(FTP_DIR+"/test.dat");
		
		Thread.sleep(10000);
		
		// In this case, the file will not match our criteria for *.xml
		// Therefore, it will not be processed
		TestFileUtility.validateFileAndCleanup(FTP_DIR,1);
	}	

	@Test
	public void ftpFileMatchingCriteria() throws Exception{
		TestFileUtility.createMuleFile(FTP_DIR+"/test.xml");
		
		Thread.sleep(10000);
		
		// In this case, the file will match our criteria for *.xml
		// Therefore, it will not be processed
		TestFileUtility.validateFileAndCleanup(FILE_DIR,1);
		
	}	
}
