package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Flow {
	public String version;
	public String cookie;
	public String tableId;
	public long packetCount;
	public long byteCount;
	public int durationSeconds;
	public int priority;
	public int idleTimeoutSec;
	public int hardTimeoutSec;
	public int flags;
	public Match match;
	public Instruction instructions;

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[version=" + version + ", cookie=" + cookie + 
        		", tableId=" + tableId + ", packetCount=" + packetCount + ", byteCount=" + byteCount + 
        		", durationSeconds=" + durationSeconds + ", priority=" + priority + 
        		", idleTimeoutSec=" + idleTimeoutSec + ", hardTimeoutSec=" + hardTimeoutSec + 
        		", flags=" + flags + ", match=" + match + ", instructions=" + instructions + "]";
    }
	
}
