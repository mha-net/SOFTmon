package de.tuberlin.cit.softmon.model;

import java.util.Calendar;

public class OfSwitchCounter extends AOfCounter implements Cloneable {

	private long m_flowCount;
	private long m_packetCount;
	private long m_byteCount;

	private long m_timeStampMillis;

	// Constructor for undefined counter values
	// without system time stamp (is generated automatically)
	public OfSwitchCounter() {
		setUndefinedValues();
		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}

	// Constructor for undefined counter values with time stamp
	public OfSwitchCounter(long timeStampMillis) {
		setUndefinedValues();
		this.m_timeStampMillis = timeStampMillis;
	}

	// Constructor without system time stamp (is generated automatically)
	public OfSwitchCounter(long flowCount, long packetCount, long byteCount) {
		this.m_flowCount = flowCount;
		this.m_packetCount = packetCount;
		this.m_byteCount = byteCount;

		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}

	// Constructor with time stamp
	public OfSwitchCounter(long flowCount, long packetCount, long byteCount, long timeStampMillis) {
		this.m_flowCount = flowCount;
		this.m_packetCount = packetCount;
		this.m_byteCount = byteCount;

		this.m_timeStampMillis = timeStampMillis;
	}

	private void setUndefinedValues() {
		this.m_flowCount = OfConstants.COUNTER_UNDEFINED;
		this.m_packetCount = OfConstants.COUNTER_UNDEFINED;
		this.m_byteCount = OfConstants.COUNTER_UNDEFINED;
	}

	public void setTimeStamp(long timeStampMillis) {
		this.m_timeStampMillis = timeStampMillis;
	}

	public void setCounters(long flowCount, long packetCount, long byteCount) {
		this.m_flowCount = flowCount;
		this.m_packetCount = packetCount;
		this.m_byteCount = byteCount;

	}

	public long getFlowCount() {
		return m_flowCount;
	}

	public long getPacketCount() {
		return m_packetCount;
	}

	public long getByteCount() {
		return m_byteCount;
	}

	public long getTimeStamp() {
		return m_timeStampMillis;
	}

	@Override
	public String toString() {
		return "[flowCount=" + m_flowCount + ", packetCount=" + m_packetCount + ", byteCount=" + m_byteCount
				+ ", timeStampMillis=" + m_timeStampMillis + "]";
	}

	@Override
	public OfSwitchCounter clone() {
		try {
			return (OfSwitchCounter) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
