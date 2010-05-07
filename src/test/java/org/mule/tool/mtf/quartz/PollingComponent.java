package org.mule.tool.mtf.quartz;

import org.apache.log4j.Logger;

public class PollingComponent {

	private String message = "Default message";
	
	private static Logger log = Logger.getLogger(PollingComponent.class);
	
	public void onPolledEvent(){
		log.info(message);
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}
