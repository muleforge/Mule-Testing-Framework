/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.classpath;

import java.io.File;

import org.apache.log4j.Logger;

public class RuntimeClasspath {

	private static Logger logger = Logger.getLogger(RuntimeClasspath.class);
	
	private StringBuffer classPath = new StringBuffer();
	
// Building up the classpath	
	
	public void addJarsFromDirectory(String theDir){
		classPath.append(getClasspathJars(theDir));
	}
	
	public void addJarsFromDirectories(String[] theDirs){
		classPath.append(getClasspathJars(theDirs));
	}
	
	public void addDirectory(String theDir){
		classPath.append(theDir+";");
	}
	
	public void addJarFile(String theJar){
		classPath.append(theJar+";");
	}

// Getting the final classpath
	
	public String getClassPath(){
		logger.info("Size of Classpath:" + classPath.toString().length());
		return classPath.toString();
	}
	
// Helper methods	
	
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
