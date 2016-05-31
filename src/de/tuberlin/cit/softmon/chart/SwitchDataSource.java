package de.tuberlin.cit.softmon.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import de.tuberlin.cit.softmon.model.OfConstants;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;
import de.tuberlin.cit.softmon.model.OfSwitchMetrics;
import de.tuberlin.cit.softmon.rest.ARestConnector;

@SuppressWarnings("serial")
public class SwitchDataSource extends ADataSource implements ActionListener {

	private ARestConnector m_restConnector;
	private OfSwitch m_switch;
	
	private OfSwitchCounter m_previousSwitchCounter;
	private OfSwitchCounter m_currentSwitchCounter;
	private OfSwitchMetrics m_currentSwitchMetrics;

	private IMetricsDestination<OfSwitchMetrics> m_metricsDestination;
	private ICounterDestination<OfSwitchCounter> m_counterDestination;
	
	private int m_initialDelay;
	private long m_initTimestamp;

	public SwitchDataSource(int initialDelay, ARestConnector restConnector, OfSwitch swtch) {
		super(0, restConnector);
		this.m_initTimestamp = Calendar.getInstance().getTimeInMillis();
		this.m_initialDelay = initialDelay;
		this.m_restConnector = restConnector;
		this.m_switch = swtch;
		addActionListener(this);
	}

	@Override
	public void setCurrentPortSpeed(long portSpeedBitsPerSec) {
		// is not needed but has to be implemented
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (this.getDelay() == 0) this.setDelay(m_initialDelay);
		this.m_currentSwitchCounter = m_restConnector.getOfSwitchCounter(m_switch);
		m_currentSwitchMetrics = calculateSwitchMetrics();
		m_currentSwitchMetrics.setTimeStampRef(m_initTimestamp);  // set time base reference
		//DataPrinter.printSwitchCounter(m_switch, m_currentSwitchCounter);
		//DataPrinter.printSwitchMetrics(m_switch, m_currentSwitchMetrics);
		
		if (m_metricsDestination != null) {
			m_metricsDestination.setMetrics(this.m_currentSwitchMetrics);
			m_metricsDestination.setCycleTime(this.getDelay());
		}
		if (m_counterDestination != null) m_counterDestination.setCounter(this.m_currentSwitchCounter);
	}
	
	@SuppressWarnings("unchecked")
	public void addMetricsDestination(IMetricsDestination<?> dest) {
		this.m_metricsDestination = (IMetricsDestination<OfSwitchMetrics>) dest;
	}
	
	@SuppressWarnings("unchecked")
	public void addCounterDestination(ICounterDestination<?> dest) {
		this.m_counterDestination = (ICounterDestination<OfSwitchCounter>) dest;
	}
	
	@Override
	public Object getDataSourceItem() {
		return this.m_switch;
	}
	
	private OfSwitchMetrics calculateSwitchMetrics() {
		
		// initialize first value
		if (m_previousSwitchCounter == null)
			m_previousSwitchCounter = m_currentSwitchCounter.clone();

		long deltaTime = m_currentSwitchCounter.getTimeStamp() - m_previousSwitchCounter.getTimeStamp();
		
		long flowCount = 0;
		double packetsPerSec = 0.0;
		double bytesPerSec = 0.0;
		
		if (!(deltaTime == OfConstants.COUNTER_UNDEFINED) && !(deltaTime == 0)) {
			if (isValid(m_currentSwitchCounter.getFlowCount())) {
				flowCount = m_currentSwitchCounter.getFlowCount();
			}
			
			if (isValid(m_currentSwitchCounter.getPacketCount()) && isValid(m_previousSwitchCounter.getPacketCount())) {
				packetsPerSec = (m_currentSwitchCounter.getPacketCount() - m_previousSwitchCounter.getPacketCount())
						/ (deltaTime / 1000.0);
			}

			if (isValid(m_currentSwitchCounter.getByteCount()) && isValid(m_previousSwitchCounter.getByteCount())) {
				bytesPerSec = (m_currentSwitchCounter.getByteCount() - m_previousSwitchCounter.getByteCount())
						/ (deltaTime / 1000.0);
			}
		}
		
		// eliminate negative values
		if (flowCount < 0) flowCount = 0;
		if (packetsPerSec < 0.0) packetsPerSec = 0;
		if (bytesPerSec < 0.0) bytesPerSec = 0.0;
		
		m_previousSwitchCounter = m_currentSwitchCounter.clone();

		return new OfSwitchMetrics(
				flowCount,
				packetsPerSec,
				bytesPerSec,
				m_currentSwitchCounter.getTimeStamp()
			);
	}

}
