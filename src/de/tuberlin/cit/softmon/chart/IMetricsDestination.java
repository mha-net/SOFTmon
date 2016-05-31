package de.tuberlin.cit.softmon.chart;

import de.tuberlin.cit.softmon.model.AOfMetrics;

public interface IMetricsDestination<T extends AOfMetrics> {

	public void setMetrics(T metrics);
	
	public void setCycleTime(int cycleTime);
	
}
