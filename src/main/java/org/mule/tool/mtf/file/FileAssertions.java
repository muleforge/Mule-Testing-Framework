/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.file;

import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public final class FileAssertions {
	
	public static void removeFile(String file) {
		File fileToDelete = new File(file);
		fileToDelete.delete();
	}

	public static void validateFileExists(String file) {
		File theFile = new File(file);
		if(!theFile.exists())
			fail("Expected to find file "+file);
	}

	public static void validateFileDoesNotExist(String file){
		File theFile = new File(file);
		if(theFile.exists())
			fail("The following file should not exists: "+file);
	}
	
	public static void createFile(String file) throws Exception{
		// Remove that which is not part of the XML
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new FileWriter(file));
			out.write("Sample data for file");
		}catch(Exception ex){
			fail("Looks like we failed to create sample file: "+ex.getMessage());
		}finally{
			if(out != null)
				out.close();
		}
	}

}
