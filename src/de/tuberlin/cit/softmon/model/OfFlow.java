package de.tuberlin.cit.softmon.model;

import de.tuberlin.cit.softmon.util.Ip4Address;
import de.tuberlin.cit.softmon.util.MacAddress;

public class OfFlow implements Comparable<OfFlow>{

	private String m_switchDpid;
	private int m_tableId;

	private Ip4Address m_ipv4Src;
	private Ip4Address m_ipv4Dst;
	private MacAddress m_ethSrc;
	private MacAddress m_ethDst;
	private int m_ethType; 
	
	public int m_ipProto;

	public int m_tpSrc;
	public int m_tpDst;

	private int m_inPort;

	private String m_action;
	private int m_outPort;

	private String m_oFversion;
	private String m_cookie;
	private int m_priority;
	private int m_idleTimeoutSec;
	private int m_hardTimeoutSec;
	private int m_flags;
	
	// to do
	//public String ipv6_src;
	//public String ipv6_dst;
	//public int eth_vlan_vid;
	//public int ip_tos;
	//public String ip_dscp; ?
	//public String ip_ecn; ?

	
	public OfFlow() {
	}
	
	public OfFlow(String switchDpid, String tableId, String ipv4Src, String ipv4Dst, 
			String ethSrc, String ethDst, String ethType, String ipProto, int tpSrc, int tpDst, int inPort, String action, int outPort, 
			String oFversion, String cookie, int priority, 
			int idleTimeoutSec, int hardTimeoutSec, int flags) {

		this.m_switchDpid = switchDpid;
		this.m_tableId = Integer.decode(tableId);
		this.m_ipv4Src = new Ip4Address(ipv4Src);
		this.m_ipv4Dst = new Ip4Address(ipv4Dst);
		this.m_ethSrc = new MacAddress(ethSrc);
		this.m_ethDst = new MacAddress(ethDst);
		this.m_ethType = Integer.decode(ethType); 
		this.m_ipProto = Integer.decode(ipProto);
		this.m_tpSrc = tpSrc;
		this.m_tpDst = tpDst;
		this.m_inPort = inPort;
		this.m_action = action;
		this.m_outPort = outPort;
		this.m_oFversion = oFversion;
		this.m_cookie = cookie;
		this.m_priority = priority;
		this.m_idleTimeoutSec = idleTimeoutSec;
		this.m_hardTimeoutSec = hardTimeoutSec;
		this.m_flags = flags;
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

	public Ip4Address getIpv4Src() {
		return m_ipv4Src;
	}

	public String getIpv4SrcString() {
		String ipv4Src = OfConstants.IP4_UNDEFINED_STRING;
		if (!m_ipv4Src.equals(new Ip4Address(OfConstants.IP4_ADDR_UNDEFINED))) ipv4Src = m_ipv4Src.toString();  
		return ipv4Src;
	}

	public Ip4Address getIpv4Dst() {
		return m_ipv4Dst;
	}

	public String getIpv4DstString() {
		String ipv4Dst = OfConstants.IP4_UNDEFINED_STRING;
		if (!m_ipv4Dst.equals(new Ip4Address(OfConstants.IP4_ADDR_UNDEFINED))) ipv4Dst = m_ipv4Dst.toString();  
		return ipv4Dst;
	}

	public MacAddress getEthSrc() {
		return m_ethSrc;
	}

	public String getEthSrcString() {
		String ethSrc = OfConstants.ETH_UNDEFINED_STRING;
		if (!m_ethSrc.equals(new MacAddress(OfConstants.ETH_ADDR_UNDEFINED))) ethSrc = m_ethSrc.toString();
		return ethSrc;
	}

	public MacAddress getEthDst() {
		return m_ethDst;
	}

	public String getEthDstString() {
		String ethDst = OfConstants.ETH_UNDEFINED_STRING;
		if (!m_ethDst.equals(new MacAddress(OfConstants.ETH_ADDR_UNDEFINED))) ethDst = m_ethDst.toString(); 
		return ethDst;
	}

	public int getEthType() {
		return m_ethType;
	}

	public String getEthTypeString() {
		String ethType = OfConstants.ETHTYPE_UNDEFINED_STRING;
		if (m_ethType != Integer.decode(OfConstants.ETHTYPE_UNDEFINED)) ethType = "0x" + Integer.toHexString(m_ethType);
		return ethType;
	}

	public int getIpProto() {
		return m_ipProto;
	}

	public String getIpProtoString() {
		String ipProto = OfConstants.IP_PROTO_UNDEFINED_STRING;
		if (this.m_ipProto != Integer.decode(OfConstants.IP_PROTO_UNDEFINED)) ipProto = "0x" + Integer.toHexString(this.m_ipProto);
		return ipProto;
	}

	public int getTpSrc() {
		return m_tpSrc;
	}

	public String getTpSrcString() {
		String tpSrc = OfConstants.TP_PORT_UNDEFINED_STRING;
		if (this.m_tpSrc != OfConstants.TP_PORT_UNDEFINED) tpSrc = Integer.toString(this.m_tpSrc);
		return tpSrc;
	}

	public int getTpDst() {
		return m_tpDst;
	}

	public String getTpDstString() {
		String tpDst = OfConstants.TP_PORT_UNDEFINED_STRING;
		if (this.m_tpDst != OfConstants.TP_PORT_UNDEFINED) tpDst = Integer.toString(this.m_tpDst);
		return tpDst;
	}

	public int getInPort() {
		return m_inPort;
	}

	public String getInPortString() {
		String inPort = OfConstants.PORT_UNDEFINED_STRING;
		if (m_inPort != OfConstants.PORT_UNDEFINED) inPort = Integer.toString(m_inPort); 
		return inPort;
	}

	public String getAction() {
		return m_action;
	}
	
	public int getOutPort() {
		return m_outPort;
	}

	public String getOutPortString() {
		String outPort = OfConstants.PORT_UNDEFINED_STRING;
		if (m_outPort != OfConstants.PORT_UNDEFINED) {
			switch (m_outPort) {
			case OfConstants.PORT_ALL:
				outPort = OfConstants.PORT_ALL_STRING;
				break;
			case OfConstants.PORT_CONTROLLER:
				outPort = OfConstants.PORT_CONTROLLER_STRING;
				break;
			case OfConstants.PORT_LOCAL:
				outPort = OfConstants.PORT_LOCAL_STRING;
				break;
			case OfConstants.PORT_TABLE:
				outPort = OfConstants.PORT_TABLE_STRING;
				break;
			case OfConstants.PORT_IN_PORT:
				outPort = OfConstants.PORT_IN_PORT_STRING;
				break;
			default:
				outPort = (new Integer(m_outPort)).toString();
			}
		}
		return outPort;
	}

	public String getOfVersion() {
		return m_oFversion;
	}

	public String getCookie() {
		return m_cookie;
	}

	public int getPriority() {
		return m_priority;
	}

	public int getIdleTimeoutSec() {
		return m_idleTimeoutSec;
	}

	public int getHardTimeoutSec() {
		return m_hardTimeoutSec;
	}

	public int getFlags() {
		return m_flags;
	}

	@Override
	public int compareTo(OfFlow o) {
		// field order for sorting: tableId, ipv4Src, ipv4Dst, ipProto, tpSrc, tpDst
		int compare = (new Integer(this.m_tableId)).compareTo(new Integer(o.getTableId()));
		if (compare == 0) compare = this.m_ipv4Src.compareTo(o.getIpv4Src());
		if (compare == 0) compare = this.m_ipv4Dst.compareTo(o.getIpv4Dst());
		if (compare == 0) compare = (new Integer(this.m_ipProto)).compareTo((new Integer(o.getIpProto())));
		if (compare == 0) compare = (new Integer(this.m_tpSrc)).compareTo((new Integer(o.getTpSrc())));
		if (compare == 0) compare = (new Integer(this.m_tpDst)).compareTo((new Integer(o.getTpDst())));
		return compare;
	}
	
	// defines tree label format
	@Override
	public String toString() {
		return "[" + m_ipv4Src.toString() + ":" + this.getTpSrcString() + " : " + m_ipv4Dst.toString() + ":" + this.getTpDstString() + "]";
	}
	
}
