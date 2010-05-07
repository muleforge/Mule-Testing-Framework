package org.mule.tool.mtf.file;

import java.io.Serializable;
import java.util.Date;

public class Flight implements Serializable
{
	public static final int NON_DEPART = 0;
	public static final int DEPARTED   = 1;
	
	private static final long serialVersionUID = -4614530625389728283L;
	
	private long flightId;
	private Date date;
	private int nbr;
	private String origin;
	private String destination;
    private int departed;
    
	public Flight(long flightId, Date date, int nbr, String origin,
			String destination, int departed) {
		super();
		this.flightId = flightId;
		this.date = date;
		this.nbr = nbr;
		this.origin = origin;
		this.destination = destination;
		this.departed = departed;
	}

	public long getFlightId() {
		return flightId;
	}

	public void setFlightId(long flightId) {
		this.flightId = flightId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNbr() {
		return nbr;
	}

	public void setNbr(int nbr) {
		this.nbr = nbr;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getDeparted() {
		return departed;
	}

	public void setDeparted(int departed) {
		this.departed = departed;
	}
 
	public String toString(){
		return
		"Flight[#:"
		+ nbr
		+ " Date:"
		+ date
		+ " Orig:"
		+ origin
		+ " Dest:"
		+ destination
		+ " ]";
		
	}
    
	public String toFileFormat(){
		return 
		flightId
		+ "~"
		+ date.getTime()
		+ "~"
		+ nbr
		+ "~"
		+ origin
		+ "~"
		+ destination
		+ "~"
		+ departed;
	}
	
	public static Flight fromFileFormat(String line){
		String[] fields = line.split("~");
		
		return new Flight(
			Long.parseLong(fields[0]),             // long flightId
			new Date(Long.parseLong(fields[1])),   // Date date (from long value)
			Integer.parseInt(fields[2]),           // int flight number
			fields[3],                             // origin
			fields[4],                             // destination
			Integer.parseInt(fields[5]));          // departed
	}
}
