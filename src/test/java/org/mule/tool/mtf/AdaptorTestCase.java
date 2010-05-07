package org.mule.tool.mtf;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.api.mtf.Device;
import org.mule.tool.mtf.adapter.ActiveMqAdaptor;
import org.mule.tool.mtf.adapter.FTPServerAdaptor;
import org.mule.tool.mtf.adapter.HsqlAdaptor;
import org.mule.tool.mtf.adapter.MailServerAdaptor;
import org.mule.tool.mtf.adapter.Mule221Adaptor;

import org.mule.tool.mtf.adapter.RemoteLogAdaptor;
import org.mule.tool.mtf.jdbc.FlightDatabaseModel;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

public class AdaptorTestCase {


	@Test
	public void greenMailAdaptor(){
		Device adaptor = new MailServerAdaptor("james","james");
		adaptor.start();
		GreenMail device = (GreenMail) adaptor.getDevice();
		
		// Exercise functionality
	    GreenMailUtil.sendTextEmailTest("james@localhost.com", "from@localhost.com", "subject", "body");
	    
	    assertEquals("body", GreenMailUtil.getBody(device.getReceivedMessages()[0]));
		
	    // Reset things before going on to regular adaptor tests
	    adaptor.stop();
		 
		excerciseInProcessAdaptor(adaptor);
	}
	
	@Test
	public void apacheFtpServer(){
		Device adaptor = new FTPServerAdaptor(MTFConstants.FTP_DATA_DIR,MTFConstants.FTP_USERS,MTFConstants.FTP_PORT);
		excerciseInProcessAdaptor(adaptor);
	}
	
	@Test
	public void activeMqAdaptor() {
		Device adaptor = new ActiveMqAdaptor(MTFConstants.ACTIVE_MQ_URL,MTFConstants.ACTIVE_MQ_DATA_DIR);
		excerciseInProcessAdaptor(adaptor);
	}

	@Test
	public void muleAdaptor221(){
		String muleConfig =	MTFConstants.RESOURCE_DIR+"/mule2x-empty.xml";
		Mule221Adaptor adaptor = new Mule221Adaptor(muleConfig);
		excerciseInProcessAdaptor(adaptor);
	}

	@Test
	public void hsqlAdaptor(){
		HsqlAdaptor adaptor = new HsqlAdaptor(new FlightDatabaseModel());
		excerciseInProcessAdaptor(adaptor);
	}
	
	@Test
	public void remoteLogAdaptor(){
		RemoteLogAdaptor adaptor = new RemoteLogAdaptor(MTFConstants.REMOTE_LOG4J_PORT);
		excerciseInProcessAdaptor(adaptor);
	}
	
	private void sleep(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void excerciseSeperateProcessAdaptor(Device adaptor){
		adaptor.start();
		sleep(5000);
		
		assertTrue("Expected Adaptor to be running now",adaptor.isRunning());

		//adaptor.dumpStdout(200);
		adaptor.dispose();

		sleep(5000);
		
		assertFalse("Expected Adaptor to NOT be running now",adaptor.isRunning());
	}
	
	private void excerciseInProcessAdaptor(Device adaptor) {
		adaptor.initialize();
		adaptor.start();
		sleep(5000);
		
		assertTrue("Expected Adaptor to be running now",adaptor.isRunning());
		
		adaptor.stop();
		sleep(5000);
		
		assertFalse("Expected Adaptor to NOT be running now",adaptor.isRunning());
		
		adaptor.start();
		sleep(5000);

		assertTrue("Expected Adaptor to be running now",adaptor.isRunning());
		
		adaptor.dispose();
		sleep(7000);
		
		assertFalse("Expected Adaptor to NOT be running now",adaptor.isRunning());
	}

}
