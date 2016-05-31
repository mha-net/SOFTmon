package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class AggregateWrapper {
	public Aggregate aggregate;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[" + aggregate.toString() + "]";
    }

}
