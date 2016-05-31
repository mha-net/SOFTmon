package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Queue {
	public int portNumber;
	public int queueId;
	public int transmitBytes;
	public int transmitPackets;
	public int transmitErrors;
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[portNumber=" + portNumber + ", queueId=" + queueId + 
        		", transmitBytes=" + transmitBytes + ", transmitPackets=" + transmitPackets + 
        		", transmitErrors=" + transmitErrors + "]";
	}
}
