package de.tuberlin.cit.softmon.model;

public class OfLink implements Comparable<OfLink> {
	
	private String m_srcDpid;
	private String m_srcPort;
	private String m_dstDpid;
	private String m_dstPort;
	private String m_type;
	private String m_direction;
	
	public OfLink(String srcDpid, String srcPort, String dstDpid, String dstPort, String type, String direction) {
		this.m_srcDpid = srcDpid;
		this.m_srcPort = srcPort;
		this.m_dstDpid = dstDpid;
		this.m_dstPort = dstPort;
		this.m_type = type;
		this.m_direction = direction;
	}

	public String getSrcDpid() {
		return m_srcDpid;
	}

	public String getSrcPort() {
		return m_srcPort;
	}

	public String getDstDpid() {
		return m_dstDpid;
	}

	public String getDstPort() {
		return m_dstPort;
	};

	public String getType() {
		return m_type;
	}

	public String getDirection() {
		return m_direction;
	}

	public int compareTo(OfLink o) {
		// field order for sorting: srcDpid, srcPort, dstDpid, dstPort
		int compare = this.m_srcDpid.compareTo(o.getSrcDpid());
		if (compare == 0) compare = this.m_srcPort.compareTo(o.getSrcPort());
		if (compare == 0) compare = this.m_dstDpid.compareTo(o.getDstDpid());
		if (compare == 0) compare = this.m_dstPort.compareTo(o.getDstPort());
		return compare;
	}
	
	// defines tree label format
	@Override
	public String toString() {
		return "[" + m_srcDpid + "-#" + m_srcPort + " : " + m_dstDpid + "-#" + m_dstPort + "]"; 
	}
	
	public static String key(String srcDpid, String srcPort, String dstDpid, String dstPort) {
		return srcDpid + OfConstants.KEY_DELIM + srcPort + OfConstants.KEY_DELIM + dstDpid + OfConstants.KEY_DELIM + dstPort;
	}
	
	public static String key(String srcDpid, int srcPort, String dstDpid, int dstPort) {
		return key(srcDpid, Integer.toString(srcPort), dstDpid, Integer.toString(dstPort));
	}

}
