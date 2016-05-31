package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

import java.util.Arrays;

public class PortReply {
	public String version;
	public Port[] port;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[version=" + version + ", " + Arrays.toString(port) + "]";
    }

}
