package de.tuberlin.cit.softmon.model;

import java.util.Date;

public class OfSwitchMetrics extends AOfMetrics {
	private long m_flowCount;
	private double m_packetsPerSec;
	private double m_bytesPerSec;

	private long m_timeStampMillis;
	private long m_timeStampRef = 0;  // time stamp reference
	
	public OfSwitchMetrics(long timeStamp) {
		this.m_flowCount = 0;
		this.m_packetsPerSec = 0.0;
		this.m_bytesPerSec = 0.0;
		this.m_timeStampMillis = timeStamp;
	}

	public OfSwitchMetrics(Date timeStamp) {
		this.m_flowCount = 0;
		this.m_packetsPerSec = 0.0;
		this.m_bytesPerSec = 0.0;
		this.m_timeStampMillis = timeStamp.getTime();
	}

	public OfSwitchMetrics(long flowCount, double packetsPerSec, double bytesPerSec, long timeStamp) {
		this.m_flowCount = flowCount;
		this.m_packetsPerSec = packetsPerSec;
		this.m_bytesPerSec = bytesPerSec;
		this.m_timeStampMillis = timeStamp;
	}

	public OfSwitchMetrics(long flowCount, double packetsPerSec, double bytesPerSec, Date timeStamp) {
		this.m_flowCount = flowCount;
		this.m_packetsPerSec = packetsPerSec;
		this.m_bytesPerSec = bytesPerSec;
		this.m_timeStampMillis = timeStamp.getTime();
	}

	public void setTimeStampRef(long timeStampRef) {
		this.m_timeStampRef = timeStampRef;
	}

	public long getTimeStamp() {
		return m_timeStampMillis;
	}

	public Date getTimeStampDate() {
		return new Date(m_timeStampMillis);
	}

	public long getFlowCount() {
		return m_flowCount;
	}

	public double getPacketsPerSec() {
		return m_packetsPerSec;
	}

	public double getBytesPerSec() {
		return m_bytesPerSec;
	}

	public long getTimeStampRel() {
		return (m_timeStampMillis - m_timeStampRef);
	}

	public Date getTimeStampRelDate() {
		return new Date((m_timeStampMillis - m_timeStampRef));
	}

	@Override
	public String toString() {
		return "[" 
				+ String.format("flowCount=%d", m_flowCount)
				+ String.format(", packetsPerSec=%.3f", m_packetsPerSec)
				+ String.format(", bytesPerSec=%.3f", m_bytesPerSec)
				+ String.format(", timeStampMillis=%d", m_timeStampMillis) 
				+ String.format(", timeStampRel=%d", (m_timeStampMillis - m_timeStampRef)) 
				+ "]";
	}
}
