package de.tuberlin.cit.softmon.model;

import java.util.Date;

public class OfPortMetrics extends AOfMetrics {

	private double m_rxPacketsPerSec;
	private double m_txPacketsPerSec;
	private double m_rxBytesPerSec;
	private double m_txBytesPerSec;
	private double m_rxDroppedPerSec;
	private double m_txDroppedPerSec;
	private double m_rxErrorsPerSec;
	private double m_txErrorsPerSec;
	private double m_rxFrameErrorsPerSec;
	private double m_rxOverrunErrorsPerSec;
	private double m_rxCRCErrorsPerSec;
	private double m_collisionsPerSec;

	private float m_rxUsage; // (value: 0.0 .. 1.0)
	private float m_txUsage; // (value: 0.0 .. 1.0)

	private long m_timeStampMillis;
	private long m_timeStampRef = 0;  // time stamp reference

	public OfPortMetrics(long timeStamp) {
		setEmptyValues();
		this.m_timeStampMillis = timeStamp;
	}

	public OfPortMetrics(Date timeStamp) {
		setEmptyValues();
		this.m_timeStampMillis = timeStamp.getTime();
	}

	public OfPortMetrics(double rxPacketsPerSec, double txPacketsPerSec, double rxBytesPerSec, double txBytesPerSec,
			double rxDroppedPerSec, double txDroppedPerSec, double rxErrorsPerSec, double txErrorsPerSec,
			double rxFrameErrorsPerSec, double rxOverrunErrorsPerSec, double rxCRCErrorsPerSec, double collisionsPerSec,
			float rxUsage, float txUsage, long timeStamp) {

		this.m_rxPacketsPerSec = rxPacketsPerSec;
		this.m_txPacketsPerSec = txPacketsPerSec;
		this.m_rxBytesPerSec = rxBytesPerSec;
		this.m_txBytesPerSec = txBytesPerSec;
		this.m_rxDroppedPerSec = rxDroppedPerSec;
		this.m_txDroppedPerSec = txDroppedPerSec;
		this.m_rxErrorsPerSec = rxErrorsPerSec;
		this.m_txErrorsPerSec = txErrorsPerSec;
		this.m_rxFrameErrorsPerSec = rxFrameErrorsPerSec;
		this.m_rxOverrunErrorsPerSec = rxOverrunErrorsPerSec;
		this.m_rxCRCErrorsPerSec = rxCRCErrorsPerSec;
		this.m_collisionsPerSec = collisionsPerSec;
		this.m_rxUsage = rxUsage;
		this.m_txUsage = txUsage;
		this.m_timeStampMillis = timeStamp;
	}

	public OfPortMetrics(double rxPacketsPerSec, double txPacketsPerSec, double rxBytesPerSec, double txBytesPerSec,
			double rxDroppedPerSec, double txDroppedPerSec, double rxErrorsPerSec, double txErrorsPerSec,
			double rxFrameErrorsPerSec, double rxOverrunErrorsPerSec, double rxCRCErrorsPerSec, double collisionsPerSec,
			float rxUsage, float txUsage, Date timeStamp) {

		this.m_rxPacketsPerSec = rxPacketsPerSec;
		this.m_txPacketsPerSec = txPacketsPerSec;
		this.m_rxBytesPerSec = rxBytesPerSec;
		this.m_txBytesPerSec = txBytesPerSec;
		this.m_rxDroppedPerSec = rxDroppedPerSec;
		this.m_txDroppedPerSec = txDroppedPerSec;
		this.m_rxErrorsPerSec = rxErrorsPerSec;
		this.m_txErrorsPerSec = txErrorsPerSec;
		this.m_rxFrameErrorsPerSec = rxFrameErrorsPerSec;
		this.m_rxOverrunErrorsPerSec = rxOverrunErrorsPerSec;
		this.m_rxCRCErrorsPerSec = rxCRCErrorsPerSec;
		this.m_collisionsPerSec = collisionsPerSec;
		this.m_rxUsage = rxUsage;
		this.m_txUsage = txUsage;
		this.m_timeStampMillis = timeStamp.getTime();
	}

	private void setEmptyValues() {
		this.m_rxPacketsPerSec = 0.0;
		this.m_txPacketsPerSec = 0.0;
		this.m_rxBytesPerSec = 0.0;
		this.m_txBytesPerSec = 0.0;
		this.m_rxDroppedPerSec = 0.0;
		this.m_txDroppedPerSec = 0.0;
		this.m_rxErrorsPerSec = 0.0;
		this.m_txErrorsPerSec = 0.0;
		this.m_rxFrameErrorsPerSec = 0.0;
		this.m_rxOverrunErrorsPerSec = 0.0;
		this.m_rxCRCErrorsPerSec = 0.0;
		this.m_collisionsPerSec = 0.0;
		this.m_rxUsage = 0f;
		this.m_txUsage = 0f;
	}

	public void setTimeStampRef(long timeStampRef) {
		this.m_timeStampRef = timeStampRef;
	}
	
	public double getRxPacketsPerSec() {
		return m_rxPacketsPerSec;
	}

	public double getTxPacketsPerSec() {
		return m_txPacketsPerSec;
	}

	public double getRxBytesPerSec() {
		return m_rxBytesPerSec;
	}

	public double getTxBytesPerSec() {
		return m_txBytesPerSec;
	}

	public double getRxDroppedPerSec() {
		return m_rxDroppedPerSec;
	}

	public double getTxDroppedPerSec() {
		return m_txDroppedPerSec;
	}

	public double getRxErrorsPerSec() {
		return m_rxErrorsPerSec;
	}

	public double getTxErrorsPerSec() {
		return m_txErrorsPerSec;
	}

	public double getRxFrameErrorsPerSec() {
		return m_rxFrameErrorsPerSec;
	}

	public double getRxOverrunErrorsPerSec() {
		return m_rxOverrunErrorsPerSec;
	}

	public double getRxCRCErrorsPerSec() {
		return m_rxCRCErrorsPerSec;
	}

	public double getCollisionsPerSec() {
		return m_collisionsPerSec;
	}

	public float getRxUsage() {
		return m_rxUsage;
	}

	public float getTxUsage() {
		return m_txUsage;
	}

	public long getTimeStamp() {
		return this.m_timeStampMillis;
	}

	public Date getTimeStampDate() {
		return new Date(this.m_timeStampMillis);
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
				+ String.format("rxPacketsPerSec=%.3f", m_rxPacketsPerSec)
				+ String.format(", txPacketsPerSec=%.3f", m_txPacketsPerSec)
				+ String.format(", rxBytesPerSec=%.3f", m_rxBytesPerSec)
				+ String.format(", txBytesPerSec=%.3f", m_txBytesPerSec)
				+ String.format(", rxDroppedPerSec=%.3f", m_rxDroppedPerSec)
				+ String.format(", txDroppedPerSec=%.3f", m_txDroppedPerSec)
				+ String.format(", rxErrorsPerSec=%.3f", m_rxErrorsPerSec)
				+ String.format(", txErrorsPerSec=%.3f", m_txErrorsPerSec)
				+ String.format(", rxFrameErrorsPerSec=%.3f", m_rxFrameErrorsPerSec)
				+ String.format(", rxOverrunErrorsPerSec=%.3f", m_rxOverrunErrorsPerSec)
				+ String.format(", rxCRCErrorsPerSec=%.3f", m_rxCRCErrorsPerSec)
				+ String.format(", collisionsPerSec=%.3f", m_collisionsPerSec) 
				+ String.format(", rxUsage=%.8f", m_rxUsage)
				+ String.format(", txUsage=%.8f", m_txUsage) 
				+ String.format(", timeStampMillis=%d", m_timeStampMillis)
				+ String.format(", timeStampRel=%d", (m_timeStampMillis - m_timeStampRef))
				+ "]";
	}

}
