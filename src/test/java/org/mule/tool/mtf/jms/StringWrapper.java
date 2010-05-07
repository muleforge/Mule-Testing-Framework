package org.mule.tool.mtf.jms;

import java.io.Serializable;

public class StringWrapper implements Serializable{
	private String message;
	
	public StringWrapper(String jmsTextMessage){
		this.message = jmsTextMessage;
	}
	
	public String getMessage(){
		return this.message;
	}

	public String toString(){
		return "StringWrapper: message="+message;
	}
}
