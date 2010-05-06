package org.mule.tool.mtf.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.mule.api.mtf.Device;

public class ProcessAdaptor implements Device{

	private String commandLine;
	private Process process = null;
	private Logger log = Logger.getLogger(ProcessAdaptor.class);
	
	public ProcessAdaptor(String commandLine){
		this.commandLine = commandLine;
	}
	
	public void initialize() {
		log.info("Nothing to do, yet.  Process actually starts with start().");
	}

	public boolean isRunning() {
		if(process == null)
			return false;
		
		try{
			process.exitValue();
			return false;
		}catch(IllegalThreadStateException ex){
			// If a process is still running, this exception is thrown
			return true;
		}
	}

	public void pause(String component) {
		log.warn("Pause not implemented");
	}

	public void start() {
		if(process==null){
			try {
				process = Runtime.getRuntime().exec(commandLine);

				// Send output of this new process to current process
				new Thread(new LogStream(process.getInputStream())).start();
				new Thread(new LogStream(process.getErrorStream())).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		dispose();
	}
	
	public void dispose() {
		if(process != null)
			process.destroy();
		process = null;
		
		// Sleep a little, so client will be assured process is destroyed
		// upon the return of this call
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public Object getDevice() {
		return process;
	}
	
	private class LogStream implements Runnable{
		private InputStream inStream;
		
		public LogStream(InputStream inStream){
			this.inStream = inStream;
		}
	
		public void run() {
			String s = null;
			BufferedReader stdInput = new BufferedReader(new 
	                InputStreamReader(inStream));
			try{
				while (( s = stdInput.readLine()) != null) {
				    System.out.println(s);
				}
				stdInput.close();
			}catch(IOException ex){
				log.error("Failed to write");
			}
			
		}
	}
	
}
