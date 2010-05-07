package org.mule.tool.mtf.ftp;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.adapter.FTPServerAdaptor;
import org.mule.tool.mtf.file.TestFileUtility;

public class TestFileFilter extends AbstractMule221Test {
	
	@BeforeClass
	public static void globalSetup(){
		manager.addDevice("ftp",new FTPServerAdaptor("targer/data/ftp",2001));
		addLocalMuleService("mule", "src/test/resources/ftp/mule2x-ftp-filter.xml");
	}

	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(TestFileFilter.class);
	}
	
	@Test
	public void fileMatchingCriteria() throws Exception{
		TestFileUtility.createMuleFile("data/in/test.xml");
		
		Thread.sleep(10000); // give mule a chance to process the file
		
		TestFileUtility.validateFileAndCleanup("data/archive",1);
	}
	
	@Test
	public void ftpFileNotMatchingCriteria() throws Exception{
		TestFileUtility.createMuleFile("data/in/test.dat");
		
		Thread.sleep(10000);
		
		// In this case, the file will not match our criteria for *.xml
		// Therefore, it will not be processed
		TestFileUtility.validateFileAndCleanup("data/in",1);
	}	

	@Test
	public void ftpFileMatchingCriteria() throws Exception{
		TestFileUtility.createMuleFile("data/ftp/test.xml");
		
		Thread.sleep(10000);
		
		// In this case, the file will match our criteria for *.xml
		// Therefore, it will not be processed
		TestFileUtility.validateFileAndCleanup("data/archive",1);
		
	}	
}
