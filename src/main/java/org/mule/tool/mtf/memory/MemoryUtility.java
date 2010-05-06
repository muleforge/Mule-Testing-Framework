package org.mule.tool.mtf.memory;

public class MemoryUtility {

	private static Runtime runtime = Runtime.getRuntime();
	private static long lastMemoryReading = 0;
	
	public static long getFreeMemory(){
		lastMemoryReading = runtime.freeMemory(); 
		return lastMemoryReading;
	}
	
	public static long getTotalMemory(){
		return runtime.totalMemory();
	}
	
	public static long getMaxMemory(){
		return runtime.maxMemory();
	}
	
	/**
	 * Returns amount of memory consumed since last call to getFreeMemory() (or this method)
	 * @return long 
	 */
	public static long getMemoryConsumedSincePreviousCall(){
		long currentConsumption = runtime.freeMemory();
		long consumed = currentConsumption - lastMemoryReading;
		
		// Update in case user wants to make repeated calls to this method only
		lastMemoryReading = currentConsumption;
		
		return consumed;
	}
}
