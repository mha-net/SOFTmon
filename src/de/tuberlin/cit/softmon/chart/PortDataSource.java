package de.tuberlin.cit.softmon.chart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import de.tuberlin.cit.softmon.model.OfConstants;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfPortMetrics;
import de.tuberlin.cit.softmon.rest.ARestConnector;

@SuppressWarnings("serial")
public class PortDataSource extends ADataSource implements ActionListener {

	private ARestConnector m_restConnector;
	private OfPort m_port;
	private long m_portSpeedBytesPerSec;

	private OfPortCounter m_previousPortCounter;
	private OfPortCounter m_currentPortCounter;
	private OfPortMetrics m_currentPortMetrics;

	private IMetricsDestination<OfPortMetrics> m_metricsDestination;
	private ICounterDestination<OfPortCounter> m_counterDestination;
	
	private int m_initialDelay;
	private long m_initTimestamp;
	
	public PortDataSource(int initialDelay, ARestConnector restConnector, OfPort port) {
		super(0, restConnector);
		this.m_initTimestamp = Calendar.getInstance().getTimeInMillis();
		this.m_initialDelay = initialDelay;
		this.m_restConnector = restConnector;
		this.m_port = port;
		this.m_portSpeedBytesPerSec = port.getCurrSpeedBitsPerSec() / 8;
		addActionListener(this);
	}

	@Override
	public void setCurrentPortSpeed(long portSpeedBitsPerSec) {
		// is not needed but has to be implemented
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (this.getDelay() == 0) this.setDelay(m_initialDelay);
		this.m_currentPortCounter = m_restConnector.getOfPortCounter(m_port);
		m_currentPortMetrics = calculatePortMetrics();
		m_currentPortMetrics.setTimeStampRef(m_initTimestamp);  // set time base reference
		//DataPrinter.printPortCounter(m_port,m_currentPortCounter);
		//DataPrinter.printPortMetrics(m_port, m_currentPortMetrics);

		if (m_metricsDestination != null) {
			m_metricsDestination.setMetrics(this.m_currentPortMetrics);
			m_metricsDestination.setCycleTime(this.getDelay());
		}
		if (m_counterDestination != null) m_counterDestination.setCounter(this.m_currentPortCounter);
	}

	@SuppressWarnings("unchecked")
	public void addMetricsDestination(IMetricsDestination<?> dest) {
		this.m_metricsDestination = (IMetricsDestination<OfPortMetrics>) dest;
	}
	
	@SuppressWarnings("unchecked")
	public void addCounterDestination(ICounterDestination<?> dest) {
		this.m_counterDestination = (ICounterDestination<OfPortCounter>) dest;
	}

	@Override
	public Object getDataSourceItem() {
		return this.m_port;
	}
	
	private OfPortMetrics calculatePortMetrics() {

		// initialize first value
		if (m_previousPortCounter == null)
			m_previousPortCounter = m_currentPortCounter.clone();

		String timeBase = this.getTimeBase();
		long deltaTime = OfConstants.COUNTER_UNDEFINED;

		switch (timeBase) {
		case OfConstants.TIME_BASE_COUNTER:
			if (isValid(m_currentPortCounter.getDurationMillis()) && isValid(m_previousPortCounter.getDurationMillis()))
				deltaTime = m_currentPortCounter.getDurationMillis() - m_previousPortCounter.getDurationMillis();
			break;
		case OfConstants.TIME_BASE_SYSTEM:
			deltaTime = m_currentPortCounter.getTimeStamp() - m_previousPortCounter.getTimeStamp();
			break;
		}

		// return 0 if counters are undefined
		double rxPacketsPerSec = 0.0;
		double txPacketsPerSec = 0.0;
		double rxBytesPerSec = 0.0;
		double txBytesPerSec = 0.0;
		double rxDroppedPerSec = 0.0;
		double txDroppedPerSec = 0.0;
		double rxErrorsPerSec = 0.0;
		double txErrorsPerSec = 0.0;
		double rxFrameErrorsPerSec = 0.0;
		double rxOverrunErrorsPerSec = 0.0;
		double rxCRCErrorsPerSec = 0.0;
		double collisionsPerSec = 0.0;
		float rxUsage = 0f; 
		float txUsage = 0f; 

		if (!(deltaTime == OfConstants.COUNTER_UNDEFINED) && !(deltaTime == 0)) {
			
			// rxPacketsPerSec
			if (isValid(m_currentPortCounter.getReceivePackets())
					&& isValid(m_previousPortCounter.getReceivePackets())) {
				rxPacketsPerSec = (m_currentPortCounter.getReceivePackets() - m_previousPortCounter.getReceivePackets())
						/ (deltaTime / 1000.0);
			}
			
			// txPacketsPerSec
			if (isValid(m_currentPortCounter.getTransmitPackets())
					&& isValid(m_previousPortCounter.getTransmitPackets())) {
				txPacketsPerSec = (m_currentPortCounter.getTransmitPackets() - m_previousPortCounter.getTransmitPackets())
						/ (deltaTime / 1000.0);
			}
			
			// rxBytesPerSec
			if (isValid(m_currentPortCounter.getReceiveBytes())
					&& isValid(m_previousPortCounter.getReceiveBytes())) {
				rxBytesPerSec = (m_currentPortCounter.getReceiveBytes() - m_previousPortCounter.getReceiveBytes())
						/ (deltaTime / 1000.0);
			}
			
			// txBytesPerSec
			if (isValid(m_currentPortCounter.getTransmitBytes())
					&& isValid(m_previousPortCounter.getTransmitBytes())) {
				txBytesPerSec = (m_currentPortCounter.getTransmitBytes() - m_previousPortCounter.getTransmitBytes())
						/ (deltaTime / 1000.0);
			}

			// rxDroppedPerSec
			if (isValid(m_currentPortCounter.getReceiveDropped())
					&& isValid(m_previousPortCounter.getReceiveDropped())) {
				rxDroppedPerSec = (m_currentPortCounter.getReceiveDropped() - m_previousPortCounter.getReceiveDropped())
						/ (deltaTime / 1000.0);
			}

			// txDroppedPerSec
			if (isValid(m_currentPortCounter.getTransmitDropped())
					&& isValid(m_previousPortCounter.getTransmitDropped())) {
				txDroppedPerSec = (m_currentPortCounter.getTransmitDropped() - m_previousPortCounter.getTransmitDropped())
						/ (deltaTime / 1000.0);
			}

			// rxErrorsPerSec
			if (isValid(m_currentPortCounter.getReceiveErrors())
					&& isValid(m_previousPortCounter.getReceiveErrors())) {
				rxErrorsPerSec = (m_currentPortCounter.getReceiveErrors() - m_previousPortCounter.getReceiveErrors())
						/ (deltaTime / 1000.0);
			}

			// txErrorsPerSec
			if (isValid(m_currentPortCounter.getTransmitErrors())
					&& isValid(m_previousPortCounter.getTransmitErrors())) {
				txErrorsPerSec = (m_currentPortCounter.getTransmitErrors() - m_previousPortCounter.getTransmitErrors())
						/ (deltaTime / 1000.0);
			}

			// rxFrameErrorsPerSec
			if (isValid(m_currentPortCounter.getReceiveErrors())
					&& isValid(m_previousPortCounter.getReceiveErrors())) {
				rxFrameErrorsPerSec = (m_currentPortCounter.getReceiveErrors() - m_previousPortCounter.getReceiveErrors())
						/ (deltaTime / 1000.0);
			}

			// rxOverrunErrorsPerSec
			if (isValid(m_currentPortCounter.getReceiveOverrunErrors())
					&& isValid(m_previousPortCounter.getReceiveOverrunErrors())) {
				rxOverrunErrorsPerSec = (m_currentPortCounter.getReceiveOverrunErrors() - m_previousPortCounter.getReceiveOverrunErrors())
						/ (deltaTime / 1000.0);
			}

			// rxCRCErrorsPerSec
			if (isValid(m_currentPortCounter.getReceiveCRCErrors())
					&& isValid(m_previousPortCounter.getReceiveCRCErrors())) {
				rxCRCErrorsPerSec = (m_currentPortCounter.getReceiveCRCErrors() - m_previousPortCounter.getReceiveCRCErrors())
						/ (deltaTime / 1000.0);
			}

			// collisionsPerSec
			if (isValid(m_currentPortCounter.getCollisions())
					&& isValid(m_previousPortCounter.getCollisions())) {
				collisionsPerSec = (m_currentPortCounter.getCollisions() - m_previousPortCounter.getCollisions())
						/ (deltaTime / 1000.0);
			}
			
			// rxUsage and txUsage
			if (m_portSpeedBytesPerSec != 0) {
				rxUsage = (float) (rxBytesPerSec / ((double) m_portSpeedBytesPerSec));
				txUsage = (float) (txBytesPerSec / ((double) m_portSpeedBytesPerSec));
			}
		}

		// To Do: eliminate negative values
		
		m_previousPortCounter = m_currentPortCounter.clone();
		
		// to do: time base (system vs. counter)?

		return new OfPortMetrics(
				rxPacketsPerSec, 
				txPacketsPerSec, 
				rxBytesPerSec, 
				txBytesPerSec,
				rxDroppedPerSec, 
				txDroppedPerSec, 
				rxErrorsPerSec, 
				txErrorsPerSec,
				rxFrameErrorsPerSec, 
				rxOverrunErrorsPerSec, 
				rxCRCErrorsPerSec, 
				collisionsPerSec,
				rxUsage, 
				txUsage, 
				m_currentPortCounter.getTimeStamp()
			);
	}
}
