/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tool.mtf.logging.remote;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;

public class RemoteLog4jReceiver implements Runnable{
    
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private boolean keepRunning = true;
    
    public RemoteLog4jReceiver(int portNumber,int wakeupMillis) {
        try {
            serverSocket = new ServerSocket(portNumber);
            serverSocket.setSoTimeout(wakeupMillis);
        }catch(Exception e) {
            System.err.println("Lost Contact with client: "+e.toString());
        }
    }
    
    public void run() {
    	keepRunning = true;
        while(keepRunning){
	        try {
	            	socket = serverSocket.accept();
		
	            	RemoteLog4jWorker worker = new RemoteLog4jWorker(
	            			socket.getInetAddress().toString(),    
	            			new ObjectInputStream(new BufferedInputStream(socket.getInputStream())));
	            	
	            	new Thread(worker).start();
	        }catch(Exception e) {
	            //System.out.println("RemoteLog4jReceiver: Lost Contact with client. This is OK");
	        }
        }
    }
    
    public synchronized void stop(){
    	keepRunning = false;
    }
}
