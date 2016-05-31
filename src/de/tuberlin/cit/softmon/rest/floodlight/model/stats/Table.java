package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Table {
	public String tableId;
	public int activeCount;
	public int lookUpCount;
	public int matchCount;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[tableId=" + tableId + ", activeCount=" +activeCount +
				", lookUpCount=" + lookUpCount + ", matchCount=" + matchCount + "]";
    }

}
