package de.tuberlin.cit.softmon.chart;

import de.tuberlin.cit.softmon.model.AOfCounter;

public interface ICounterDestination<T extends AOfCounter> {
	
	public void setCounter(T counter);

}
