package de.tuberlin.cit.softmon.rest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import de.tuberlin.cit.softmon.model.OfConstants;
import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfLink;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;
import de.tuberlin.cit.softmon.model.OfTableCounter;
import de.tuberlin.cit.softmon.model.OfTopology;
import de.tuberlin.cit.softmon.rest.floodlight.FloodlightClient;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.Aggregate;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.Desc;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.Features;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.Flow;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.Port;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.PortDesc;
import de.tuberlin.cit.softmon.rest.floodlight.model.stats.PortReply;
import de.tuberlin.cit.softmon.rest.floodlight.model.topo.Link;
import de.tuberlin.cit.softmon.rest.floodlight.model.topo.Switch;


public class FloodlightRestConnector extends ARestConnector{
	
	static final String CONNECTOR_DESCRIPTION = "Floodlight 1.0";
	
	static final boolean FILTER_MISSING_IP = true;		// filter out flows without IP addresses
	static final boolean FILTER_MISSING_MAC = true;		// filter out flows without MAC addresses
	
	private String m_controllerHostame = "127.0.0.1";
	private String m_controllerPort = "8080";
	private String m_controllerBaseUrl = "/wm";
	
	private FloodlightClient m_client; 

	private int m_flowMatchIndex = OfConstants.FLOW_MATCH_UNDEFINED;		// last flow matching index
	
	public FloodlightRestConnector() {
		this.m_client = new FloodlightClient(m_controllerHostame, m_controllerPort, m_controllerBaseUrl);
	}

	public FloodlightRestConnector(String hostname) {
		this.m_controllerHostame = hostname;
		this.m_client = new FloodlightClient(m_controllerHostame, m_controllerPort, m_controllerBaseUrl);
	}

	public FloodlightRestConnector(String hostname, String port, String baseUrl) {
		this.m_controllerHostame = hostname;
		this.m_controllerPort = port;
		this.m_controllerBaseUrl = baseUrl;
		this.m_client = new FloodlightClient(m_controllerHostame, m_controllerPort, m_controllerBaseUrl);
	}
	
	public OfTopology getTopology() {

		// get switches and links
		OfTopology topology = getBasicTopology();
		
		// set controller info
		topology.setControllerHostname(m_controllerHostame);
		topology.setControllerDescription(CONNECTOR_DESCRIPTION);
		
		// get and add attributes per switch
		setSwitchAttributes(topology);
		
		// get and add ports per switch
		setSwitchPorts(topology);
		
		// get and add flows per switch
		setSwitchFlows(topology);
		
		return topology;
	}
	
	private OfTopology getBasicTopology() {
		Switch[] switches = null;
		Link[] links = null;
		Map<String, OfSwitch> switchMap = new TreeMap<>();
		Map<String, OfLink> linkMap = new TreeMap<>();
		try {
			switches = m_client.getSwitches();
			links = m_client.getLinks();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		if (!(switches == null)) {
			for (Switch sw : switches) {
				OfSwitch ofSw = new OfSwitch(sw.switchDPID);
				ofSw.setIpAddress(sw.inetAddress.toString().split("/")[1]);  // remove leading "/"
				ofSw.setConnectedSince(sw.connectedSince);
				switchMap.put(sw.switchDPID, ofSw);
			}
		} 
		if (!(links == null)) {
			for (Link lnk : links) {
				String key = OfLink.key(lnk.src_switch, lnk.src_port, lnk.dst_switch, lnk.dst_port);
			    OfLink value = new OfLink(lnk.src_switch, Integer.toString(lnk.src_port), lnk.dst_switch, Integer.toString(lnk.dst_port), lnk.type, lnk.direction);
				linkMap.put(key, value);
			}
		}
		return new OfTopology(switchMap, linkMap);
	}
	
	private void setSwitchAttributes(OfTopology topology) {
		Map<String, OfSwitch> switchMap = topology.getSwitchMap();
		Desc switchDesc = null;
		Features switchFeatures = null;

		for (Map.Entry<String, OfSwitch> entry : switchMap.entrySet()) {
			String dpid = entry.getKey();
			OfSwitch ofSw = entry.getValue();
			
			try {
				switchDesc = m_client.getSwitchDesc(dpid);
				switchFeatures = m_client.getSwitchFeatures(dpid);
			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				e.printStackTrace();
			}
			if (!(switchDesc == null)) {
				ofSw.setSwitchDescription(switchDesc.version, switchDesc.manufacturerDescription, switchDesc.hardwareDescription, 
						switchDesc.softwareDescription,	switchDesc.serialNumber, switchDesc.datapathDescription);
			}
			if (!(switchFeatures == null)) {
				ofSw.setSwitchFeatures(switchFeatures.capabilities, Integer.parseInt(switchFeatures.buffers), 
						Integer.parseInt(switchFeatures.tables), switchFeatures.version);
			}
		}
	}
	
	private void setSwitchPorts(OfTopology topology) {
		Map<String, OfSwitch> switchMap = topology.getSwitchMap();

		for (Map.Entry<String, OfSwitch> entry : switchMap.entrySet()) {
			String dpid = entry.getKey();
			OfSwitch ofSw = entry.getValue();
			Collection<OfPort> portList = getPortList(dpid);
			// to do: replace Collection by Map<String, OfPort>
			ofSw.setPortList(portList);
		}
	}
	
	private Collection<OfPort> getPortList(String dpid) {
		Collection<OfPort> portList = new TreeSet<>();
		// to do: replace Collection by Map<String, OfPort>
		PortDesc[] portDescs = null;
		
		try {
			portDescs = m_client.getPortDesc(dpid).portDesc;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}

		if (portDescs != null) {
			for (PortDesc port : portDescs) {
				portList.add(new OfPort(dpid, port.portNumber, port.hardwareAddress, port.name, port.config, port.state, 
						port.currentFeatures, port.advertisedFeatures, port.supportedFeatures, port.peerFeatures, 
						port.currSpeed, port.maxSpeed));
			}
		}
		return portList;
	}
	
	private void setSwitchFlows(OfTopology topology) {
		Map<String, OfSwitch> switchMap = topology.getSwitchMap();
		for (Map.Entry<String, OfSwitch> entry : switchMap.entrySet()) {
			String dpid = entry.getKey();
			OfSwitch ofSw = entry.getValue();
			Collection<OfFlow> flowList = getFlowList(dpid);
			// to do: replace Collection by Map<String, OfFlow>
			ofSw.setFlowList(flowList);
		}
	}
	
	private Collection<OfFlow> getFlowList(String dpid) {
		Collection<OfFlow> flowList = new TreeSet<>();
		// to do: replace Collection by Map<String, OfFlow>
		Flow[] flows = null;

		try {
			flows = m_client.getFlows(dpid);
		} catch (JsonParseException e) {
			System.out.println("JsonParseException: " + e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}
		if (flows != null) {
			for (Flow flow : flows) {

				// check if flow contains a defined match and a defined instruction
				if (isValid(flow)) {

					// ** ethType **
					// remove one leading "0x" if it exists twice (as in Floodlight 1.0)
					String ethType = flow.match.eth_type;
					if (ethType != null) {
						if (ethType.startsWith("0x0x")) {
							ethType = ethType.substring(2);
						}
					} else ethType = OfConstants.ETHTYPE_UNDEFINED;	
					
					// ** ipProto **
					// define values for missing IP proto
					String ipProto = OfConstants.IP_PROTO_UNDEFINED;
					if (flow.match.ip_proto != null) ipProto = flow.match.ip_proto;

					// ** tpSrc, tpDst **
					int[] tpPorts = getTpPorts(flow);
					int tpSrc = tpPorts[0];
					int tpDst = tpPorts[1];
					
					// define values for missing IP addresses
					String ipv4Src = OfConstants.IP4_ADDR_UNDEFINED;
					String ipv4Dst = OfConstants.IP4_ADDR_UNDEFINED;

					// define values for missing MAC addresses
					String ethSrc = OfConstants.ETH_ADDR_UNDEFINED;
					String ethDst = OfConstants.ETH_ADDR_UNDEFINED;
					
					if (flow.match.ipv4_src != null) ipv4Src = flow.match.ipv4_src;
					if (flow.match.ipv4_dst != null) ipv4Dst = flow.match.ipv4_dst;
					if (flow.match.eth_src != null) ethSrc = flow.match.eth_src;
					if (flow.match.eth_dst != null) ethDst = flow.match.eth_dst;
					
					// define value for missing in port
					int inPort = OfConstants.PORT_UNDEFINED;
					if (flow.match.in_port != null) inPort = Integer.parseInt(flow.match.in_port);
					
					// define value for missing out port
					int outPort = OfConstants.PORT_UNDEFINED;

					//parse instruction
					String[] parsedAction = flow.instructions.instruction_apply_actions.actions.split("="); 
					String action = parsedAction[0].toLowerCase();
					String actionArg = parsedAction[1].toLowerCase();
					switch (action) {
					case OfConstants.OF_ACTION_OUTPUT:
						switch (actionArg.toUpperCase()) {		// virtual ports are defined upper case?
						case OfConstants.PORT_ALL_STRING:
							// to do
							break;
						case OfConstants.PORT_CONTROLLER_STRING:
							outPort = OfConstants.PORT_CONTROLLER;
							break;
						case OfConstants.PORT_LOCAL_STRING:
							// to do
							break;
						case OfConstants.PORT_TABLE_STRING:
							// to do
							break;
						case OfConstants.PORT_IN_PORT_STRING:
							// to do
							break;
						default:
							try {
								outPort = Integer.parseInt(actionArg);
							} catch (NumberFormatException e) {
								System.out.println("Unknown virtual port: \"" + actionArg + "\"\nEcxeption: " + e.getMessage());
								e.printStackTrace();
							}
						}
						break;
					case OfConstants.OF_ACTION_CONTROLLER:
						// to do
						break;
					case OfConstants.OF_ACTION_DROP:
						// to do
						break;
					case OfConstants.OF_ACTION_LOCAL:
						// to do
						break;
					}

					/*
					System.out.println("flowList.add(" 
							+ dpid + ", " + flow.tableId + ", " + ipv4Src + ", " 
							+ ipv4Dst + ", " + ethSrc + ", " + ethDst + ", " 
							+ ethType + ", " + inPort + ", " + action + ", " 
							+ outPort + ", " + flow.version + ", " + flow.cookie + ", " 
							+ flow.priority + ", " + flow.idleTimeoutSec + ", " 
							+ flow.hardTimeoutSec + ", " + flow.flags
							+ ");");
					*/		
					
					flowList.add(new OfFlow(dpid, flow.tableId, ipv4Src, ipv4Dst, ethSrc, ethDst, 
							ethType, ipProto, tpSrc, tpDst, inPort, action, outPort, flow.version, flow.cookie, flow.priority, 
							flow.idleTimeoutSec, flow.hardTimeoutSec, flow.flags));
				} else {
					// to do: how to handle "invalid" flows?
				}
			}
		} 
		//System.out.println("flowList=" + flowList);
		return flowList;
	}
	
	private int[] getTpPorts(Flow flow) {
		// ** tpSrc, tpDst **
		// define values for missing tp_src and tp_dst port
		int tpSrc = OfConstants.TP_PORT_UNDEFINED;
		int tpDst = OfConstants.TP_PORT_UNDEFINED;
		if (flow.match.ip_proto != null) {
			// ip_proto = 0x6 (TCP)
			if (Integer.decode(flow.match.ip_proto) == 6) {
				if ((flow.match.tcp_src != null) && (flow.match.tcp_dst != null)) {
					tpSrc = Integer.parseInt(flow.match.tcp_src);
					tpDst = Integer.parseInt(flow.match.tcp_dst);
				} else {
					tpSrc = OfConstants.PORT_UNDEFINED;
					tpDst = OfConstants.PORT_UNDEFINED;
				}
			}
			// ip_proto = 0x11 (UDP)
			if (Integer.decode(flow.match.ip_proto) == 17) {
				if ((flow.match.udp_src != null) && (flow.match.udp_dst != null)) {
					tpSrc = Integer.parseInt(flow.match.udp_src);
					tpDst = Integer.parseInt(flow.match.udp_dst);
				} else {
					tpSrc = OfConstants.PORT_UNDEFINED;
					tpDst = OfConstants.PORT_UNDEFINED;
				}
			}
			// ip_proto = 0x84 (SCTP)
			if (Integer.decode(flow.match.ip_proto) == 132) {
				if ((flow.match.sctp_src != null) && (flow.match.sctp_dst != null)) {
					tpSrc = Integer.parseInt(flow.match.sctp_src);
					tpDst = Integer.parseInt(flow.match.sctp_dst);
				} else {
					tpSrc = OfConstants.PORT_UNDEFINED;
					tpDst = OfConstants.PORT_UNDEFINED;
				}
			}
		}
		return new int[] {tpSrc, tpDst};
	}

 	private boolean isValid(Flow flow) {
		// check if flow contains a defined match and a defined instruction
		// to do: support for virtual ports and special flows has to be added here
		return ((flow.match.in_port != null) &&									// in_port is defined 
				((flow.match.ipv4_src != null) || !(FILTER_MISSING_IP)) &&		// source IP is defined (only if FILTER_MISSING_IP == true), filters for example ARP flows (ethType = 0x806)
				((flow.match.ipv4_dst != null) || !(FILTER_MISSING_IP)) &&		// dest. IP is defined (only if FILTER_MISSING_IP == true), filters for example ARP flows (ethType = 0x806)
				((flow.match.eth_src != null) || !(FILTER_MISSING_MAC)) &&		// source MAC is defined (only if FILTER_MISSING_MAC == true)
				((flow.match.eth_dst != null) || !(FILTER_MISSING_MAC)) &&		// destination MAC is defined (only if FILTER_MISSING_MAC == true)
				(flow.instructions.instruction_apply_actions.actions != null)	// instruction is defined
				);
	}
	
	public OfSwitchCounter getOfSwitchCounter(OfSwitch sw) {
		return getOfSwitchCounter(sw.getSwitchDpid());
	}
	
	public OfSwitchCounter getOfSwitchCounter(String dpid) {
		Aggregate aggr = null;
		OfSwitchCounter counter;
		try {
			aggr = m_client.getSwitchAggregate(dpid);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		if (aggr != null) {
			counter = new OfSwitchCounter(aggr.flowCount, aggr.packetCount, aggr.byteCount);
		} else {
			counter = new OfSwitchCounter();
		}
		return counter;
	}

	public OfPortCounter getOfPortCounter(OfPort port) {
		return getOfPortCounter(port.getSwitchDpid(), port.getPortNumber());
	}
	
	public OfPortCounter getOfPortCounter(String dpid, String port) {
		
		PortReply portReply = null;
		OfPortCounter counter = null;
		try {
			portReply = m_client.getPortStatistics(dpid);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		// check if port stats list exists
		if (portReply != null) {
			Port statPort = null;
			for (Port p : portReply.port) {
				// find matching port number in port stats list
				if (port.equalsIgnoreCase(p.portNumber)) {
					statPort = p;
					break;
				}
			}
			// if matching port has been found
			if (statPort != null) {
				// create counter object
				counter = new OfPortCounter(statPort.receivePackets, 
						statPort.transmitPackets, statPort.receiveBytes, statPort.transmitBytes, statPort.
						receiveDropped, statPort.transmitDropped, statPort.receiveErrors, statPort.transmitErrors, 
						statPort.receiveFrameErrors, statPort.receiveOverrunErrors, statPort.receiveCRCErrors, 
						statPort.collisions, statPort.durationSec, statPort.durationNsec);
			} 
		} 
		// if no counter object has been created
		if (counter == null) {
			// create counter object with undefined values
			counter = new OfPortCounter();
		}
		
		return counter;
	}

	public OfFlowCounter getOfFlowCounter(OfFlow ofFlow) {
		Flow[] flows = null;
		OfFlowCounter counter;
		int matchIndex = -1;
		
		// Fix for increasing accuracy: time stamp is taken before REST-call
		long timeStamp = Calendar.getInstance().getTimeInMillis();
		
		try {
			flows = m_client.getFlows(ofFlow.getSwitchDpid());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		//long timeStamp = Calendar.getInstance().getTimeInMillis();
		
		if (flows != null) { // flow array is not empty

			// check flow match at stored index first (because it will most likely match)
			if ((m_flowMatchIndex != OfConstants.FLOW_MATCH_UNDEFINED)	// if stored index isn't undefined
					&& (m_flowMatchIndex < flows.length)				// and if stored index isn't out of range  
				) {
				if (matchesFlow(ofFlow, flows[m_flowMatchIndex])) {
					matchIndex = m_flowMatchIndex;						// match found at stored index
				}
			} 
			
			// if no match is found at stored index (or stored index is undefined):
			// search for match in current flow array
			if (matchIndex == -1) { 
				for (int i = 0; i < flows.length; i++) {
					if (matchesFlow(ofFlow, flows[i])) {
						matchIndex = i;
					}
				}
				m_flowMatchIndex = matchIndex;							// match found in current flow array
			} 
		} 
		
		// no match found
		if (matchIndex == -1) {
			// create empty counter object
			counter = new OfFlowCounter();		
		// match found
		} else {
			// create counter objects with current values
			
			//counter = new OfFlowCounter(flows[matchIndex].packetCount, flows[matchIndex].byteCount, flows[matchIndex].durationSeconds, 0); 	
			// increase accuracy
			counter = new OfFlowCounter(flows[matchIndex].packetCount, flows[matchIndex].byteCount, flows[matchIndex].durationSeconds, 0, timeStamp);
		}
		//System.out.println("matchIndex=" + matchIndex + ", flow=" + flows[matchIndex]);
		return counter;
	}
	
	// for future use
	public OfTableCounter getOfTableCounter(String dpid, String tableId) {
		// to do
		return null;
	}

	private boolean matchesFlow(OfFlow ofFlow, Flow flow) {
		String tableId = flow.tableId;
		
		String ipv4Src = flow.match.ipv4_src;
		if (ipv4Src == null) ipv4Src = OfConstants.IP4_ADDR_UNDEFINED;
		
		String ipv4Dst = flow.match.ipv4_dst;
		if (ipv4Dst == null) ipv4Dst = OfConstants.IP4_ADDR_UNDEFINED;

		String ethSrc = flow.match.eth_src;
		if (ethSrc == null) ethSrc = OfConstants.IP4_ADDR_UNDEFINED;

		String ethDst = flow.match.eth_dst;
		if (ethDst == null) ethDst = OfConstants.IP4_ADDR_UNDEFINED;

		String ethType = flow.match.eth_type;
		if (ethType == null) ethType = OfConstants.ETHTYPE_UNDEFINED;
		// remove leading "0x" (bug in Floodlight 1.0?)
		else if (ethType.startsWith("0x0x")) ethType = ethType.substring(2);  
		
		String ipProtoString = flow.match.ip_proto;
		if (ipProtoString == null) ipProtoString = OfConstants.IP_PROTO_UNDEFINED;
		int ipProto = Integer.decode(ipProtoString);
		
		int[] tpPorts = getTpPorts(flow);
		int tpSrc = tpPorts[0];
		int tpDst = tpPorts[1];
		
		String inPort = flow.match.in_port;
		if (inPort == null) inPort = OfConstants.PORT_UNDEFINED_STRING;
		// to do: set virtual port names to OfConstants.PORT_<VirtualPortName>_STRING
		
		return (ofFlow.getTableIdString().equals(tableId) &&
				ofFlow.getIpv4SrcString().equals(ipv4Src) &&
				ofFlow.getIpv4DstString().equals(ipv4Dst) &&
				ofFlow.getEthSrcString().equalsIgnoreCase(ethSrc) &&
				ofFlow.getEthDstString().equalsIgnoreCase(ethDst) &&
				ofFlow.getEthTypeString().equals(ethType) &&
				(ofFlow.getIpProto() == ipProto) && // needed?
				(ofFlow.getTpSrc() == tpSrc) &&
				(ofFlow.getTpDst() == tpDst) &&
				ofFlow.getInPortString().equals(inPort)
				);
	}
}
