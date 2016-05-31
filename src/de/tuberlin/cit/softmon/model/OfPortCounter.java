package de.tuberlin.cit.softmon.model;

import java.util.Calendar;

public class OfPortCounter extends AOfCounter implements Cloneable {

	private long m_receivePackets;
	private long m_transmitPackets;
	private long m_receiveBytes;
	private long m_transmitBytes;
	private long m_receiveDropped;
	private long m_transmitDropped;
	private long m_receiveErrors;
	private long m_transmitErrors;
	private long m_receiveFrameErrors;
	private long m_receiveOverrunErrors;
	private long m_receiveCRCErrors;
	private long m_collisions;
	private long m_durationSec;
	private long m_durationNsec;
	
	private long m_timeStampMillis;

	// Constructor for undefined counter values 
	// without system time stamp (is generated automatically)
	public OfPortCounter() {
		setUndefinedValues();
		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}

	// Constructor for undefined counter values with time stamp 
	public OfPortCounter(long timeStampMillis) {
		setUndefinedValues();
		this.m_timeStampMillis = timeStampMillis;
	}
	
	// Constructor without time stamp (is generated automatically)
	public OfPortCounter(long receivePackets, long transmitPackets, 
			long receiveBytes, long transmitBytes, long receiveDropped, long transmitDropped, 
			long receiveErrors, long transmitErrors, long receiveFrameErrors, long receiveOverrunErrors, 
			long receiveCRCErrors, long collisions, long durationSec, long durationNsec) {
		
		this.m_receivePackets = receivePackets;
		this.m_transmitPackets = transmitPackets;
		this.m_receiveBytes = receiveBytes;
		this.m_transmitBytes = transmitBytes;
		this.m_receiveDropped = receiveDropped;
		this.m_transmitDropped = transmitDropped;
		this.m_receiveErrors = receiveErrors;
		this.m_transmitErrors = transmitErrors;
		this.m_receiveFrameErrors = receiveFrameErrors;
		this.m_receiveOverrunErrors = receiveOverrunErrors;
		this.m_receiveCRCErrors = receiveCRCErrors;
		this.m_collisions = collisions;
		this.m_durationSec = durationSec;
		this.m_durationNsec = durationNsec;
		
		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}

	// Constructor with time stamp
	public OfPortCounter(long receivePackets, long transmitPackets, 
			long receiveBytes, long transmitBytes, long receiveDropped, long transmitDropped, 
			long receiveErrors, long transmitErrors, long receiveFrameErrors, long receiveOverrunErrors, 
			long receiveCRCErrors, long collisions, long durationSec, long durationNsec, long timeStampMillis) {
		
		this.m_receivePackets = receivePackets;
		this.m_transmitPackets = transmitPackets;
		this.m_receiveBytes = receiveBytes;
		this.m_transmitBytes = transmitBytes;
		this.m_receiveDropped = receiveDropped;
		this.m_transmitDropped = transmitDropped;
		this.m_receiveErrors = receiveErrors;
		this.m_transmitErrors = transmitErrors;
		this.m_receiveFrameErrors = receiveFrameErrors;
		this.m_receiveOverrunErrors = receiveOverrunErrors;
		this.m_receiveCRCErrors = receiveCRCErrors;
		this.m_collisions = collisions;
		this.m_durationSec = durationSec;
		this.m_durationNsec = durationNsec;
		
		this.m_timeStampMillis = timeStampMillis;
	}

	private void setUndefinedValues() {
		this.m_receivePackets = OfConstants.COUNTER_UNDEFINED;
		this.m_transmitPackets = OfConstants.COUNTER_UNDEFINED;
		this.m_receiveBytes = OfConstants.COUNTER_UNDEFINED;
		this.m_transmitBytes = OfConstants.COUNTER_UNDEFINED;
		this.m_receiveDropped = OfConstants.COUNTER_UNDEFINED;
		this.m_transmitDropped = OfConstants.COUNTER_UNDEFINED;
		this.m_receiveErrors = OfConstants.COUNTER_UNDEFINED;
		this.m_transmitErrors = OfConstants.COUNTER_UNDEFINED;
		this.m_receiveFrameErrors = OfConstants.COUNTER_UNDEFINED;
		this.m_receiveOverrunErrors = OfConstants.COUNTER_UNDEFINED;
		this.m_receiveCRCErrors = OfConstants.COUNTER_UNDEFINED;
		this.m_collisions = OfConstants.COUNTER_UNDEFINED;
		this.m_durationSec = OfConstants.COUNTER_UNDEFINED;
		this.m_durationNsec = OfConstants.COUNTER_UNDEFINED;
	}
	
	public void setTimeStamp(long timeStampMillis) {
		this.m_timeStampMillis = timeStampMillis;
	}

	public void setCounters(long receivePackets, long transmitPackets, 
			long receiveBytes, long transmitBytes, long receiveDropped, long transmitDropped, 
			long receiveErrors, long transmitErrors, long receiveFrameErrors, long receiveOverrunErrors, 
			long receiveCRCErrors, long collisions, long durationSec, long durationNsec) {
		
		this.m_receivePackets = receivePackets;
		this.m_transmitPackets = transmitPackets;
		this.m_receiveBytes = receiveBytes;
		this.m_transmitBytes = transmitBytes;
		this.m_receiveDropped = receiveDropped;
		this.m_transmitDropped = transmitDropped;
		this.m_receiveErrors = receiveErrors;
		this.m_transmitErrors = transmitErrors;
		this.m_receiveFrameErrors = receiveFrameErrors;
		this.m_receiveOverrunErrors = receiveOverrunErrors;
		this.m_receiveCRCErrors = receiveCRCErrors;
		this.m_collisions = collisions;
		this.m_durationSec = durationSec;
		this.m_durationNsec = durationNsec;
	}

	public long getReceivePackets() {
		return m_receivePackets;
	}

	public long getTransmitPackets() {
		return m_transmitPackets;
	}

	public long getReceiveBytes() {
		return m_receiveBytes;
	}

	public long getTransmitBytes() {
		return m_transmitBytes;
	}

	public long getReceiveDropped() {
		return m_receiveDropped;
	}

	public long getTransmitDropped() {
		return m_transmitDropped;
	}

	public long getReceiveErrors() {
		return m_receiveErrors;
	}

	public long getTransmitErrors() {
		return m_transmitErrors;
	}

	public long getReceiveFrameErrors() {
		return m_receiveFrameErrors;
	}

	public long getReceiveOverrunErrors() {
		return m_receiveOverrunErrors;
	}

	public long getReceiveCRCErrors() {
		return m_receiveCRCErrors;
	}

	public long getCollisions() {
		return m_collisions;
	}

	public long getDurationSec() {
		return m_durationSec;
	}

	public long getDurationNsec() {
		return m_durationNsec;
	}

	// get duration in milliseconds (nSecs included)
	public long getDurationMillis() {
		// all counters undefined
		if (m_durationSec == OfConstants.COUNTER_UNDEFINED)
			return OfConstants.COUNTER_UNDEFINED;
		// only Nsec undefined: return rounded time counter (sec)
		else if (m_durationNsec == OfConstants.COUNTER_UNDEFINED)
			return (m_durationSec * 1000);
		// all counters defined
		else
			return (m_durationSec * 1000) + (m_durationNsec / 1000);
	}

	public long getTimeStamp() {
		return m_timeStampMillis;
	}
	
	@Override
	public String toString() {
		return "[receivePackets=" + m_receivePackets + ", transmitPackets=" + m_transmitPackets + 
				", receiveBytes=" + m_receiveBytes + ", transmitBytes=" + m_transmitBytes + 
				", receiveDropped=" + m_receiveDropped + ", transmitDropped=" + m_transmitDropped + 
				", receiveErrors=" + m_receiveErrors + ", transmitErrors=" + m_transmitErrors + 
				", receiveFrameErrors=" + m_receiveFrameErrors + ", receiveOverrunErrors=" + m_receiveOverrunErrors + 
				", receiveCRCErrors=" + m_receiveCRCErrors + ", collisions=" + m_collisions + 
				", durationSec=" + m_durationSec + ", durationNsec=" + m_durationNsec + 
				", timeStampMillis=" + m_timeStampMillis + "]";
	}

	@Override
	public OfPortCounter clone() {
		try {
			return (OfPortCounter) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
