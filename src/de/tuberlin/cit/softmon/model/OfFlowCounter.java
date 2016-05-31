package de.tuberlin.cit.softmon.model;

import java.util.Calendar;

public class OfFlowCounter extends AOfCounter implements Cloneable {

	private long m_packetCount;
	private long m_byteCount;
	private long m_durationSec;
	private long m_durationNsec;

	private long m_timeStampMillis;

	// Constructor for undefined counter values
	// without system time stamp (is generated automatically)
	public OfFlowCounter() {
		setUndefinedValues();
		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}

	// Constructor for undefined counter values with time stamp
	public OfFlowCounter(long timeStampMillis) {
		setUndefinedValues();
		this.m_timeStampMillis = timeStampMillis;
	}

	// Constructor without system time stamp (is generated automatically)
	public OfFlowCounter(long packetCount, long byteCount, long durationSec, long durationNsec) {

		this.m_packetCount = packetCount;
		this.m_byteCount = byteCount;
		this.m_durationSec = durationSec;
		this.m_durationNsec = durationNsec;

		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}

	// Constructor with system time stamp
	public OfFlowCounter(long packetCount, long byteCount, long durationSec, long durationNsec, long timeStampMillis) {

		this.m_packetCount = packetCount;
		this.m_byteCount = byteCount;
		this.m_durationSec = durationSec;
		this.m_durationNsec = durationNsec;

		this.m_timeStampMillis = timeStampMillis;
	}

	private void setUndefinedValues() {
		this.m_packetCount = OfConstants.COUNTER_UNDEFINED;
		this.m_byteCount = OfConstants.COUNTER_UNDEFINED;
		this.m_durationSec = OfConstants.COUNTER_UNDEFINED;
		this.m_durationNsec = OfConstants.COUNTER_UNDEFINED;
	}

	public void setTimeStamp(long timeStampMillis) {
		this.m_timeStampMillis = timeStampMillis;
	}

	public void setCounters(long packetCount, long byteCount, long durationSec, long durationNsec) {
		this.m_packetCount = packetCount;
		this.m_byteCount = byteCount;
		this.m_durationSec = durationSec;
		this.m_durationNsec = durationNsec;
	}

	public long getPacketCount() {
		return m_packetCount;
	}

	public long getByteCount() {
		return m_byteCount;
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
		return "[packetCount=" + m_packetCount + ", byteCount=" + m_byteCount + ", durationSec=" + m_durationSec
				+ ", durationNsec=" + m_durationNsec + ", timeStampMillis=" + m_timeStampMillis + "]";
	}

	@Override
	public OfFlowCounter clone() {
		try {
			return (OfFlowCounter) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
