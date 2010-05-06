package org.mule.tool.mtf.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class HashMapLog {

    private static HashMap<MessageKey,List<String>> messages = new HashMap<MessageKey,List<String>>();
	
	public static void log(MessageKey key, String message){
        if(messages.containsKey(key))
        	messages.get(key).add(message);
        else{
        	List<String> newList = new ArrayList<String>();
        	newList.add(message);
        	messages.put(key, newList);
        }
	}

	public static int getNumberMessages(MessageKey key){
    	if(messages.containsKey(key))
    		return messages.get(key).size();
    	else
    		return 0;
    }
    
    public static List<String> getMessages(MessageKey key){
    	if(messages.containsKey(key))
    		return messages.get(key);
    	else
    		// So client does not have to worry about null
    		return new ArrayList<String>();
    }
    
    public static void clearAll() {
        messages.clear();
    }
	
	// Do not instantiate
	private HashMapLog(){
		
	}
}
