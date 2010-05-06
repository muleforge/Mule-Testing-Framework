package org.mule.tool.mtf.adapter;

import java.io.File;

import org.apache.log4j.Logger;
import org.mule.api.mtf.Device;

public abstract class AbstractProcessAdaptor implements Device{

	private static Logger logger = Logger.getLogger(AbstractProcessAdaptor.class);
	
	// Must be initialized by decendant's constructor
	protected ProcessAdaptor processAdaptor = null;

	public void dispose() {
		processAdaptor.dispose();
	}

	public Object getDevice() {
		return processAdaptor.getDevice();
	}

	public void initialize() {
		processAdaptor.initialize();
	}

	public boolean isRunning() {
		return processAdaptor.isRunning();
	}

	public void pause(String component) {
		processAdaptor.pause(component);
	}

	public void start() {
		processAdaptor.start();
	}

	public void stop() {
		processAdaptor.stop();
	}

	protected static String getClasspathJars(String[] theDirs){
		StringBuffer theJars = new StringBuffer();
		
		for(String theDir:theDirs)
			theJars.append(getClasspathJars(theDir));
		
		return theJars.toString();
	}
	
	protected static String getClasspathJars(String theDir){
		StringBuffer jars = new StringBuffer();
		
		// Iterate over jars to build classpath
		File dir = new File(theDir);
		String[] files = dir.list();
		
		if(files == null){
			logger.warn(theDir + " seems to be invalid, check the directory path and try again");
			return "";
		}

		for(String file: files){
			if(file.endsWith(".jar"))
				jars.append(theDir+file+";");
		}
		
		return jars.toString();
	}
}
