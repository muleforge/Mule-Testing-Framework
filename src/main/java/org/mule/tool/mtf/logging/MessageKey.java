package org.mule.tool.mtf.logging;

import java.io.Serializable;

public class MessageKey implements Serializable{
	
	private static final long serialVersionUID = -6533580012612138030L;
	
	public static final String DEBUG = "DEBUG";
	public static final String INFO  = "INFO";
	public static final String WARN  = "WARN";
	public static final String ERROR = "ERROR";
	public static final String FATAL = "FATAL";
	
	private String category;
	private String level;

	public MessageKey(Class clazz,String level){
		super();
		this.category = clazz.getPackage().getName() + "." + clazz.getSimpleName();
		this.level    = level;
	}
	
	public MessageKey(String category, String level) {
		super();
		this.category = category;
		this.level    = level;
	}

	public String getCategory() {
		return category;
	}

	public String getLevel() {
		return level;
	}

	public String toString(){
		return "MessageKey["+category+","+level+"]";
	}
	
	public boolean equals(Object obj){
		if(this==obj)
			return true;
		
		if((obj==null) || (obj.getClass() != this.getClass()))
			return false;
		
		MessageKey other = (MessageKey) obj;
		if(this.category.equals(other.category) && this.level.equals(other.level))
			return true;
		
		return false;
	}
	
	public int hashCode(){
		int hash = 7;
		hash = 31 * hash + (null == category ? 0 : category.hashCode());
		hash = 31 * hash + (null == level ? 0 : level.hashCode());
		return hash;
	}
	
}
