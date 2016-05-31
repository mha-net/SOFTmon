package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Port {
	public String portNumber;
	public long receivePackets;
	public long transmitPackets;
	public long receiveBytes;
	public long transmitBytes;
	public long receiveDropped;
	public long transmitDropped;
	public long receiveErrors;
	public long transmitErrors;
	public long receiveFrameErrors;
	public long receiveOverrunErrors;
	public long receiveCRCErrors;
	public long collisions;
	public long durationSec;
	public long durationNsec;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[portNumber=" + portNumber + ", receivePackets=" + receivePackets + 
        		", transmitPackets=" + transmitPackets + ", receiveBytes=" + receiveBytes +  
        		", transmitBytes=" + transmitBytes + ", receiveDropped=" + receiveDropped + 
        		", transmitDropped=" + transmitDropped + ", receiveErrors=" + receiveErrors +
        		", transmitErrors=" + transmitErrors + ", receiveFrameErrors=" + receiveFrameErrors +
        		", receiveOverrunErrors=" + receiveOverrunErrors + ", receiveCRCErrors=" + receiveCRCErrors +
        		", collisions=" + collisions + ", durationSec=" + durationSec + ", durationNsec=" + durationNsec +
        		"]";
		
    }

}
