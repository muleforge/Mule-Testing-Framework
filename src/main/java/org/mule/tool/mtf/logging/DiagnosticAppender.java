package org.mule.tool.mtf.logging;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class DiagnosticAppender extends AppenderSkeleton{
	
    protected void append(LoggingEvent event) {
        String category = event.getLoggerName().toString();
        String level    = event.getLevel().toString();  
        String message  = event.getMessage().toString();
        
        HashMapLog.log(new MessageKey(category,level),message);
    }
    
    public void close() {
    }
    
    public boolean requiresLayout() {
    	return false;
    }

}