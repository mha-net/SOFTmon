package de.tuberlin.cit.softmon.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import de.tuberlin.cit.softmon.controller.Config;
import de.tuberlin.cit.softmon.model.OfConstants;
import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfFlowMetrics;
import de.tuberlin.cit.softmon.rest.ARestConnector;

@SuppressWarnings("serial")
public class FlowDataSource extends ADataSource implements ActionListener {

	private ARestConnector m_restConnector;
	private OfFlow m_flow;
	private long m_portSpeedBytesPerSec;

	private OfFlowCounter m_previousFlowCounter;
	private OfFlowCounter m_currentFlowCounter;
	private OfFlowMetrics m_currentFlowMetrics;
	
	private IMetricsDestination<OfFlowMetrics> m_metricsDestination;
	private ICounterDestination<OfFlowCounter> m_counterDestination;
	
	private int m_initialDelay;
	private long m_initTimestamp;
	private long m_initTimeCounter = -1;
	
	private String m_timeBase;

	public FlowDataSource(int initialDelay, ARestConnector restConnector, OfFlow flow) {
		super(0, restConnector); // start without delay
		this.m_initTimestamp = Calendar.getInstance().getTimeInMillis();
		this.m_initialDelay = initialDelay;
		this.m_restConnector = restConnector;
		this.m_flow = flow;
		this.m_timeBase = Config.METRICS_TIME_BASE_FLOW;
		addActionListener(this);
	}
	
	@Override
	public void setCurrentPortSpeed(long portSpeedBitsPerSec) {
		this.m_portSpeedBytesPerSec = portSpeedBitsPerSec / 8;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (this.getDelay() == 0) this.setDelay(m_initialDelay);
		this.m_currentFlowCounter = m_restConnector.getOfFlowCounter(m_flow);
		
		// Initialize duration counter reference
		if (m_initTimeCounter == -1) {
			 m_initTimeCounter = m_currentFlowCounter.getDurationMillis();
		}
		
		m_currentFlowMetrics = calculateFlowMetrics();
		
		// Set time reference according to time base type 
		switch (m_timeBase) {
		case OfConstants.TIME_BASE_COUNTER:
			m_currentFlowMetrics.setTimeStampRef(m_initTimeCounter);  // set time base reference
			break;
		case OfConstants.TIME_BASE_SYSTEM:
			m_currentFlowMetrics.setTimeStampRef(m_initTimestamp);  // set time base reference
			break;
		}
		
		//DataPrinter.printFlowCounter(m_flow, m_currentFlowCounter);
		//DataPrinter.printFlowMetrics(m_flow, m_currentFlowMetrics);
		
		if (m_metricsDestination != null) {
			m_metricsDestination.setMetrics(this.m_currentFlowMetrics);
			m_metricsDestination.setCycleTime(this.getDelay());
		}
		if (m_counterDestination != null) m_counterDestination.setCounter(this.m_currentFlowCounter);
	}

	@SuppressWarnings("unchecked")
	public void addMetricsDestination(IMetricsDestination<?> dest) {
		this.m_metricsDestination = (IMetricsDestination<OfFlowMetrics>) dest;
	}
	
	@SuppressWarnings("unchecked")
	public void addCounterDestination(ICounterDestination<?> dest) {
		this.m_counterDestination = (ICounterDestination<OfFlowCounter>) dest;
	}
	
	@Override
	public Object getDataSourceItem() {
		return this.m_flow;
	}

	private OfFlowMetrics calculateFlowMetrics() {

		// initialize first value
		if (m_previousFlowCounter == null)
			m_previousFlowCounter = m_currentFlowCounter.clone();

		// For use of default time base (set in ADataSource
		//String timeBase = this.getTimeBase();

		long deltaTime = OfConstants.COUNTER_UNDEFINED;

		switch (m_timeBase) {
		case OfConstants.TIME_BASE_COUNTER:
			if (isValid(m_currentFlowCounter.getDurationMillis()) && isValid(m_previousFlowCounter.getDurationMillis()))
				deltaTime = m_currentFlowCounter.getDurationMillis() - m_previousFlowCounter.getDurationMillis();
			else
				m_initTimeCounter = -1; // reset init time counter
			
			// To Do: problems to solve
			// What to do if flow doesn't exist any more? --> Chart stops because there is no valid time base!
			
			break;
		case OfConstants.TIME_BASE_SYSTEM:
			deltaTime = m_currentFlowCounter.getTimeStamp() - m_previousFlowCounter.getTimeStamp();
			break;
		}

		double packetsPerSec = 0.0; // return 0 if counters are undefined
		double bytesPerSec = 0.0; 	// return 0 if counters are undefined
		float portUsage = 0f; 		// return 0 if counters are undefined

		if (!(deltaTime == OfConstants.COUNTER_UNDEFINED) && !(deltaTime == 0)) {
			if (isValid(m_currentFlowCounter.getPacketCount()) && isValid(m_previousFlowCounter.getPacketCount())) {
				packetsPerSec = (m_currentFlowCounter.getPacketCount() - m_previousFlowCounter.getPacketCount())
						/ (deltaTime / 1000.0);
			}
			if (isValid(m_currentFlowCounter.getByteCount()) && isValid(m_previousFlowCounter.getByteCount())) {
				bytesPerSec = (m_currentFlowCounter.getByteCount() - m_previousFlowCounter.getByteCount())
						/ (deltaTime / 1000.0);
			}
		}
		
		if (m_portSpeedBytesPerSec != 0) 
			portUsage = (float) (bytesPerSec / m_portSpeedBytesPerSec);
		
		// Eliminate negative values
		if (packetsPerSec < 0) packetsPerSec = 0;
		if (bytesPerSec < 0) bytesPerSec = 0;
		if (portUsage < 0) portUsage = 0;
		
		m_previousFlowCounter = m_currentFlowCounter.clone();

		// to do: differentiate returned time base (system vs. counter)?
		long timeBaseValue = -1;
		switch (m_timeBase) {
		case OfConstants.TIME_BASE_COUNTER:
			timeBaseValue = m_currentFlowCounter.getDurationMillis();
			break;
		case OfConstants.TIME_BASE_SYSTEM:
			timeBaseValue = m_currentFlowCounter.getTimeStamp();
			break;
		}
		
		return new OfFlowMetrics(
				packetsPerSec, 
				bytesPerSec, 
				portUsage,
				timeBaseValue
			);
	}

}
