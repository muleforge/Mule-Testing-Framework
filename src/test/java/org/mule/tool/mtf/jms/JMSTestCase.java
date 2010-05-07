package org.mule.tool.mtf.jms;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleException;
import org.mule.tool.mtf.AbstractMule221Test;
import org.mule.tool.mtf.MTFConstants;
import org.mule.tool.mtf.logging.LoggingAssertions;
import org.mule.tool.mtf.logging.MessageKey;

public class JMSTestCase  extends AbstractMule221Test{
	
	@BeforeClass
	public static void globalSetup(){
		addJmsDevice();
		addLocalMuleService("mule",MTFConstants.RESOURCE_DIR+"/jms/mule2x-jms.xml");
		
	}

	@Override
	public void initializeLogging() {
		logger = Logger.getLogger(JMSTestCase.class);
	}
	
	@Test
	public void sendJmsMessage() throws Exception{
		try {
			// Using the client, which belongs to AbstractMule221Test, to send a message to ActiveMq
			client.dispatch(MTFConstants.JMS_QUEUE, "Here is another message", null);

			
		} catch (MuleException ex) {
			ex.printStackTrace();
			fail("Failed to send JMS message:"+ex.getMessage());
		}

		Thread.sleep(5000);
		
		// Validate transformer was called
		MessageKey transformerKey = new MessageKey(StringToWrapper.class,"INFO");
		LoggingAssertions.assertNumberLogs(transformerKey, 1);
		
		// Validate message was properly received by the component 
		MessageKey componentKey = new MessageKey(JmsComponent.class,"INFO");
		LoggingAssertions.assertContains(componentKey, "onWrapper");	
	}

}
