package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

import java.util.Arrays;

public class PortDescReply {
	public String version;
	public PortDesc[] portDesc;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[version=" + version + ", " + Arrays.toString(portDesc) + "]";
    }

}
