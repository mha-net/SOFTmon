package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Match {
	public String in_port;
	
	public String eth_vlan_vid;
	
	public String eth_dst;
	public String eth_src;
	public String eth_type;
	public String ipv4_src;
	public String ipv4_dst;
	
	public String ipv6_src;
	public String ipv6_dst;

	public String ip_dscp;
	public String ip_ecn;
	
	public String ip_proto;
	public String ip_tos;

	//public String tp_src;
	//public String tp_dst;
	
	public String tcp_src;
	public String tcp_dst;

	public String udp_src;
	public String udp_dst;

	public String sctp_src;
	public String sctp_dst;

	
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "["
				+ "in_port=" + in_port
				+ ", eth_vlan_vid=" + eth_vlan_vid
				+ ", eth_dst=" + eth_dst
				+ ", eth_src=" + eth_src
				+ ", eth_type=" + eth_type
				+ ", ipv4_src=" + ipv4_src
				+ ", ipv4_dst=" + ipv4_dst
				+ ", ipv6_src=" + ipv6_src
				+ ", ipv6_dst=" + ipv6_dst
				+ ", ip_dscp=" + ip_dscp
				+ ", ip_ecn=" + ip_ecn
				+ ", ip_proto=" + ip_proto
				+ ", ip_tos=" + ip_tos
				//+ ", tp_src=" + tp_src
				//+ ", tp_dst=" + tp_dst
				+ ", tcp_src=" + tcp_src
				+ ", tcp_dst=" + tcp_dst
				+ ", udp_src=" + udp_src
				+ ", udp_dst=" + udp_dst
				+ ", sctp_src=" + sctp_src
				+ ", sctp_dst=" + sctp_dst
        		+ "]";
    }

}
