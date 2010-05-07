package org.mule.tool.mtf.quartz;

import org.apache.log4j.Logger;

public class TimedComponent {
	
	private String message = "Default Message";

	private static Logger log = Logger.getLogger(TimedComponent.class);
	
	public void arbitraryMethod(){
		log.info(message);
	}

	public void setMessage(String message){
		this.message = message;
	}
	
}
