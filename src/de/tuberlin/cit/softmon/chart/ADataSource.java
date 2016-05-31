package de.tuberlin.cit.softmon.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import de.tuberlin.cit.softmon.model.OfConstants;
import de.tuberlin.cit.softmon.rest.ARestConnector;

@SuppressWarnings("serial")
public abstract class ADataSource extends Timer implements ActionListener {

	private String m_timeBaseType = OfConstants.TIME_BASE_SYSTEM; // default time base
	
	public ADataSource(int initialDelay, ARestConnector restConnector) {
		super(initialDelay, null);
	}
	
	public abstract void actionPerformed(ActionEvent event);
	
	public abstract void addMetricsDestination(IMetricsDestination<?> dest);
	
	public abstract void addCounterDestination(ICounterDestination<?> dest);
	
	public abstract Object getDataSourceItem();
	
	public abstract void setCurrentPortSpeed(long portSpeedBitsPerSec);
	
	public void setTimeBase(String timeBase) {
		this.m_timeBaseType = timeBase;
	}
	
	public String getTimeBase() {
		return this.m_timeBaseType;
	}
	
	public boolean isValid(long counter) {
		return (!(counter == OfConstants.COUNTER_UNDEFINED));
	}

}
