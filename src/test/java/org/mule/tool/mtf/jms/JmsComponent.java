package org.mule.tool.mtf.jms;

import org.apache.log4j.Logger;

public class JmsComponent {
	
	private static Logger log;
	
	public JmsComponent(){
		log = Logger.getLogger(JmsComponent.class);
	}
	
	public void onJmsMessage(String message){
		log.info("onMessage(String) called: "+message);
	}
	
	public void onWrapper(StringWrapper wrapper){
		log.info("onWrapper(StringWrapper) called: "+wrapper);
	}
}
