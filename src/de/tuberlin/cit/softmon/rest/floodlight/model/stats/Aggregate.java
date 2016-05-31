package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Aggregate {
	public String version;
	public long flowCount;
	public long packetCount;
	public long byteCount;
	public long flags;
	
	public Aggregate() {
	}
	
	public Aggregate(String version, int flowCount, int packetCount, int byteCount, int flags) {
		this.version = version;
		this.flowCount = flowCount;
		this.packetCount = packetCount;
		this.byteCount = byteCount;
		this.flags = flags;
	}

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[version=" + version + ", flowCount=" + flowCount + 
        		", packetCount=" + packetCount + ", byteCount=" + byteCount + ", flags=" + flags + "]";
    }

}
