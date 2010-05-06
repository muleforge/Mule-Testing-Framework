package org.mule.tool.mtf.logging;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class HushingAppender extends AppenderSkeleton{

	private HashMap<HushKey,HushValue> hushs = new HashMap<HushKey,HushValue>();
	private Appender forwardTo = null;
	private boolean verbose = false;
	
    protected void append(LoggingEvent event) {
        String category   = event.getLoggerName().toString();
        String methodName = event.getLocationInformation().getMethodName();
        String level      = event.getLevel().toString();  
        
        HushKey hushKey = new HushKey(category,methodName,level);
        if(hushs.containsKey(hushKey)){
        	HushValue value = hushs.get(hushKey);
        	if(!value.hasExpired()){
        		if(verbose)
        			System.out.println(hushKey+" has been 'hushed'");
        		return;
        	}else{
            	forwardTo.doAppend(event);
        	}
        
        	value.refresh();
        }
    }
    
    public void close() {
    }
    
    public boolean requiresLayout() {
    	return false;
    }

    public void setVerbose(String verbosity){
    	if(verbosity.toLowerCase().equals("true"))
    		verbose = true;
    	else
    		verbose = false;
    }
    
    public void setForwardTo(String forwardToAppender){
    	forwardTo = Logger.getRootLogger().getAppender(forwardToAppender);
    	
    	if(forwardTo == null)
    		throw new IllegalArgumentException("Please double check your log4j.properties file. " 
    				+ "Could not find appender to forwardTo: "+forwardTo);
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