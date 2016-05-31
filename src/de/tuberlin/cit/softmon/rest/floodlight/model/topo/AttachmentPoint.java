package de.tuberlin.cit.softmon.rest.floodlight.model.topo;

public class AttachmentPoint {
	String switchDPID;
	int port;
	String errorStatus;
	
	public void setSwitchDPID(String switchDPID) {
		this.switchDPID = switchDPID;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[switchDPID=" + switchDPID + ", port=" + port + 
				", errorStatus=" + errorStatus + "]";
	}
}
