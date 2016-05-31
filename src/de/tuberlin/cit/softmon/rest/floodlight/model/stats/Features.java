package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Features {
	public String capabilities;
	public String dpid;
	public String buffers;
	public String tables;
	public String version;

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[capabilities=" + capabilities + ", dpid=" + dpid + 
        		", buffers=" + buffers + ", tables=" + tables +", version=" + version + "]";
		
    }

}
