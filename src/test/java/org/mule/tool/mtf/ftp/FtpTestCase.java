package org.mule.tool.mtf.ftp;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.MTFConstants;
import org.mule.tool.mtf.adapter.FTPServerAdaptor;
import org.mule.tool.mtf.file.FileUtility;

public class FtpTestCase extends AbstractMule221Test {
	
	@BeforeClass
	public static void globalSetup(){
		manager.addDevice("ftp",new FTPServerAdaptor(MTFConstants.FTP_DATA_DIR,
				                                     MTFConstants.FTP_USERS,
				                                     MTFConstants.FTP_PORT));
		addLocalMuleService("mule", MTFConstants.RESOURCE_DIR+"/ftp/mule2x-ftp-filter.xml");
	}

	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(FtpTestCase.class);
	}
	
	@Test
	public void ftpFileNotMatchingCriteria() throws Exception{
		FileUtility.createMuleFile(MTFConstants.FTP_DATA_DIR+"/test.dat");
		
		Thread.sleep(10000);
		
		// In this case, the file will not match our criteria for *.xml
		// Therefore, it will not be processed
		FileUtility.validateFileAndCleanup(MTFConstants.FTP_DATA_DIR,1);
	}	

	@Test
	public void ftpFileMatchingCriteria() throws Exception{
		FileUtility.createMuleFile(MTFConstants.FTP_DATA_DIR+"/test.xml");
		
		Thread.sleep(10000);
		
		// In this case, the file will match our criteria for *.xml
		// Therefore, it will not be processed
		FileUtility.validateFileAndCleanup(MTFConstants.FTP_FILE_DIR,1);
		FileUtility.validateFileAndCleanup(MTFConstants.FTP_DATA_DIR,0);
		
	}	
}
