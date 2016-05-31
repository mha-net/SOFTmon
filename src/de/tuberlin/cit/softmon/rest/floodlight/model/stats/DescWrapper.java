package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class DescWrapper {
	public Desc desc;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[" + desc.toString() + "]";
    }

}
