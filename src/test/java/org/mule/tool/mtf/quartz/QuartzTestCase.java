package org.mule.tool.mtf.quartz;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.logging.LoggingAssertions;
import org.mule.tool.mtf.logging.MessageKey;

public class QuartzTestCase extends AbstractMule221Test {
	
	private static final String RESOURCE_DIR = "src/test/resources";

	@BeforeClass
	public static void globalSetup(){
		addLocalMuleService("mule", RESOURCE_DIR+"/quartz/mule2x-quartz.xml");
	}
	
	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(QuartzTestCase.class);
	}
	
	@Test
	public void polledEvents() throws Exception{
		MessageKey msgKey = new MessageKey(PollingComponent.class,"INFO");
		Thread.sleep(5000);
		
		LoggingAssertions.assertMinNumberOfLogs(msgKey, 2);
		
		LoggingAssertions.assertContains(msgKey, "polling");
	}
	
	// This test will fail if you don't set the time correctly on the quartz cron expression
	// to happen within 60 seconds of the current time on your system.
	@Test 
	public void timedEvent() throws Exception{
		MessageKey msgKey = new MessageKey(TimedComponent.class,"INFO");
		Thread.sleep(60000);
		
		LoggingAssertions.assertMinNumberOfLogs(msgKey, 1);
		
		LoggingAssertions.assertContains(msgKey, "timed");
	}

}
