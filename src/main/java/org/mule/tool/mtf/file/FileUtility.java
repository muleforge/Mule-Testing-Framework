package org.mule.tool.mtf.file;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.apache.log4j.Logger;

public class FileUtility {

	private static Logger log =  Logger.getLogger(FileUtility.class);

	public static boolean createDirectory(String dirToCreate){
		return (new File(dirToCreate)).mkdirs();
	}
	
	public static void createFlightFile(){
		File newFile = new File("data/flight-load/flights.dat");
		Writer out;
		try {
			out = new BufferedWriter(new FileWriter(newFile));
			out.write(new Flight(0,new Date(),123,"DAL","HOU",0).toFileFormat()+"\n");
			out.write(new Flight(0,new Date(),456,"MDW","STL",0).toFileFormat()+"\n");
			out.write(new Flight(0,new Date(),789,"AUS","ELP",0).toFileFormat()+"\n");
			
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			fail("Unexpected Exception during file creation:"+ex.getMessage());
		}
		
	}
	
	public static void createMuleFile(String fileToCreate){
		log.info( "Testing Mule Processing a file and then moving it to " +
             "an archive directory");
		
		// Create a file in the input directory
		try{
			File newFile = new File(fileToCreate);
			Writer out = new BufferedWriter(
                    new FileWriter(newFile));
			out.write("AAAAABBBBBCCCCCDDDDDEEEEEFFFFF");
			out.close();
		}catch(Exception ex){
			fail("Failed to create file for mule to consume");
		}
	}

	public static void validateFileAndCleanup(String directoryToCheck, int expectedFileCount){
			File dir = new File(directoryToCheck);
			File[] files = dir.listFiles();
			
			assertNotNull("Invalid directory?  No files found at: "+directoryToCheck,files);
			
			int fileCount = files.length;
			for(int i=0;i<files.length;i++){
				log.info("Cleaning up file from test: "+files[i].getName());
				files[i].delete();
			}
			assertEquals( "Expecting "+expectedFileCount+" file(s) to be in "+directoryToCheck,
					expectedFileCount,fileCount);
		}
	}
