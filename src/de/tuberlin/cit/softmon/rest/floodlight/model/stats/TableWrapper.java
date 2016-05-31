package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

import java.util.Arrays;

public class TableWrapper {
	public String version;
	public Table[] table;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[version=" + version + ", " + Arrays.toString(table) + "]";
    }

}
