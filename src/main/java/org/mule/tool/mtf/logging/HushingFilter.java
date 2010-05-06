/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.logging;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class HushingFilter extends Filter{
	private HashMap<HushKey,HushValue> hushs = new HashMap<HushKey,HushValue>();
	private boolean verbose = false;
	
	@Override
	public int decide(LoggingEvent event) {
        String category   = event.getLoggerName().toString();
        String methodName = event.getLocationInformation().getMethodName();
        String level      = event.getLevel().toString();  
        
        HushKey hushKey = new HushKey(category,methodName,level);
        if(hushs.containsKey(hushKey)){
        	HushValue value = hushs.get(hushKey);
        	boolean hasExpired = value.hasExpired();
        	value.refresh();
        	if(hasExpired){
        		if(verbose)
        			System.out.println(hushKey+" has been 'hushed'");
        		return Filter.DENY;
        	}else{
        		return Filter.ACCEPT;
        	}
        
        }
		return Filter.ACCEPT;
	}

	public void setVerbose(String verbosity){
    	if(verbosity.toLowerCase().equals("true"))
    		verbose = true;
    	else
    		verbose = false;
    }
    
	public void setHush(String settings){
    	String[] args = settings.split(",");
    	
    	if(args.length != 4)
    		throw new IllegalArgumentException("Expected 4 arguments. (ex. pkg.class,method,level,timeout(millis)");

    	String category = args[0].trim();
    	String method   = args[1].trim();
    	String level    = args[2].trim();
   		long timeout    = Long.parseLong(args[3].trim());

    	hushs.put(new HushKey(category,method,level), new HushValue(timeout,0L));
    	
    }
    // ***************************
    // Private Class HushKey
    // ***************************
    
    private class HushKey {
    	private String category;
    	private String level;
    	private String method;
    	
    	public HushKey(String category, String method, String level) {
    		this.category = category;
    		this.method   = method;
    		this.level    = level;
    	}

    	public String getCategory() {
    		return category;
    	}

    	public String getMethod() {
    		return method;
    	}

    	public String getLevel() {
    		return level;
    	}

    	public String toString(){
    		return "HushKey["+category+","+method+","+level+"]";
    	}
    	
    	public boolean equals(Object obj){
    		if(this==obj)
    			return true;
    		
    		if((obj==null) || (obj.getClass() != this.getClass()))
    			return false;
    		
    		HushKey other = (HushKey) obj;
    		if(this.category.equals(other.category) 
    				&& this.method.equals(other.method) 
    				&& this.level.equals(other.level))
    			return true;
    		
    		return false;
    	}
    	
    	public int hashCode(){
    		int hash = 7;
    		hash = 31 * hash + (null == category ? 0 : category.hashCode());
    		hash = 31 * hash + (null == method ? 0   : method.hashCode());
    		hash = 31 * hash + (null == level ? 0    : level.hashCode());
    		return hash;
    	}
    }
    
    // ***************************
    // Private Class HushValue
    // ***************************
    
    private class HushValue {
    	private long timeout;
    	private long lastTime;

    	public HushValue(long timeout, long lastTime){
    		this.timeout  = timeout;
    		this.lastTime = lastTime;
    	}

    	public long getLastTime() {
    		return lastTime;
    	}

    	public void setLastTime(long lastTime) {
    		this.lastTime = lastTime;
    	}

    	public long getTimeout() {
    		return timeout;
    	}	
    	
    	public void refresh(){
    		this.lastTime = new Date().getTime();
    	}
    	
    	public boolean hasExpired(){
    		Date currentDate = new Date();
    		long currentTime = currentDate.getTime();
    		
    		// If the difference between now and then is greater than our timeout value
    		if(currentTime-lastTime > timeout)
    			return true;
    		
    		return false;
    	}    

    	public String toString(){
    		return "HushValue["+timeout+","+lastTime+"]";
    	}
    }  
}