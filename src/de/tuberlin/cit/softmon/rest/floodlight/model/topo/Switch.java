package de.tuberlin.cit.softmon.rest.floodlight.model.topo;

public class Switch {
	public String inetAddress;
	public long connectedSince;
	public String switchDPID;

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[inetAddress=" + inetAddress + ", connectedSince=" + connectedSince + 
        		", switchDPID=" + switchDPID + "]";
    }
}
