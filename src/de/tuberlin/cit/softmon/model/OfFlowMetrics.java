package de.tuberlin.cit.softmon.model;

import java.util.Date;

public class OfFlowMetrics extends AOfMetrics {
	private double m_packetsPerSec;
	private double m_bytesPerSec;

	private float m_portUsage; // (value: 0.0 .. 1.0)
	
	private long m_timeStampMillis;
	private long m_timeStampRef = 0;  // time stamp reference
	
	public OfFlowMetrics(long timeStamp) {
		this.m_packetsPerSec = 0.0;
		this.m_bytesPerSec = 0.0;
		this.m_portUsage = 0f;
		this.m_timeStampMillis = timeStamp;
	}

	public OfFlowMetrics(Date timeStamp) {
		this.m_packetsPerSec = 0.0;
		this.m_bytesPerSec = 0.0;
		this.m_portUsage = 0f;
		this.m_timeStampMillis = timeStamp.getTime();
	}

	public OfFlowMetrics(double packetsPerSec, double bytesPerSec, float portUsage, long timeStamp) {
		this.m_packetsPerSec = packetsPerSec;
		this.m_bytesPerSec = bytesPerSec;
		this.m_portUsage = portUsage;
		this.m_timeStampMillis = timeStamp;
	}

	public OfFlowMetrics(double packetsPerSec, double bytesPerSec, float portUsage, Date timeStamp) {
		this.m_packetsPerSec = packetsPerSec;
		this.m_bytesPerSec = bytesPerSec;
		this.m_portUsage = portUsage;
		this.m_timeStampMillis = timeStamp.getTime();
	}
	
	public void setTimeStampRef(long timeStampRef) {
		this.m_timeStampRef = timeStampRef;
	}
	
	public long getTimeStamp() {
		return this.m_timeStampMillis;
	}
	
	public Date getTimeStampDate() {
		return new Date(this.m_timeStampMillis);
	}

	public double getPacketsPerSec() {
		return this.m_packetsPerSec;
	}
	
	public double getBytesPerSec() {
		return this.m_bytesPerSec;
	}

	public float getPortUsage() {
		return m_portUsage;
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
				+ String.format("packetsPerSec=%.3f", m_packetsPerSec) 
				+ String.format(", bytesPerSec=%.3f", m_bytesPerSec) 
				+ String.format(", portUsage=%.8f", m_portUsage)
				+ String.format(", timeStampMillis=%d", m_timeStampMillis) 
				+ String.format(", timeStampRel=%d", (m_timeStampMillis - m_timeStampRef))
				+ "]";
	}
}
