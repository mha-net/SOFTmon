package de.tuberlin.cit.softmon.model;

import java.util.Calendar;

public class OfTableCounter {

	private String m_switchDpid;
	private int m_tableId;
	
	private long m_activeCount;
	private long m_lookUpCount;
	private long m_matchCount;
	
	private long m_timeStampMillis;
	

	// Constructor without system time stamp (is generated automatically)
	public OfTableCounter(String switchDpid, String tableId, long activeCount, 
			long lookUpCount, long matchCount) {

		this.m_switchDpid = switchDpid;
		this.m_tableId = Integer.decode(tableId);
		this.m_activeCount = activeCount;
		this.m_lookUpCount = lookUpCount;
		this.m_matchCount = matchCount;
		
		this.m_timeStampMillis = Calendar.getInstance().getTimeInMillis();
	}
	
	// Constructor with system time stamp
	public OfTableCounter(String switchDpid, String tableId, long activeCount, 
			long lookUpCount, long matchCount, long timeStampMillis) {
		
		this.m_switchDpid = switchDpid;
		this.m_tableId = Integer.decode(tableId);
		this.m_activeCount = activeCount;
		this.m_lookUpCount = lookUpCount;
		this.m_matchCount = matchCount;
		
		this.m_timeStampMillis = timeStampMillis;
	}

	public void setTimeStamp(long timeStampMillis) {
		this.m_timeStampMillis = timeStampMillis;
	}

	public String getSwitchDpid() {
		return m_switchDpid;
	}

	public int getTableId() {
		return m_tableId;
	}

	public String getTableIdString() {
		return "0x" + Integer.toHexString(m_tableId);
	}

	public long getActiveCount() {
		return m_activeCount;
	}

	public long getLookUpCount() {
		return m_lookUpCount;
	}

	public long getMatchCount() {
		return m_matchCount;
	}

	public long getTimeStamp() {
		return m_timeStampMillis;
	}
}
