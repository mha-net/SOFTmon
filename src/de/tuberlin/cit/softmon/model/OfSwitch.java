package de.tuberlin.cit.softmon.model;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class OfSwitch implements Comparable<OfSwitch> {

	private String m_switchDpid;

	private String m_ipAddress;
	
	private Collection<OfPort> m_portList;
	private Collection<OfFlow> m_flowList; 

	private long m_connectedSince;
	
	private String m_version;
	private String m_manufacturerDescription;
	private String m_hardwareDescription;
	private String m_softwareDescription;
	private String m_serialNumber;
	private String m_datapathDescription;
	
	private String m_capabilities;
	private int m_buffers;
	private int m_tables;
	
	public OfSwitch() {
	}
	
 	public OfSwitch(String switchDpid) {
		this.m_switchDpid = switchDpid;
	}

	public void setIpAddress(String ipAddress) {
		this.m_ipAddress = ipAddress;
	}

	public void setPortList(Collection<OfPort> portList) {
		this.m_portList = portList;
	}

	public void setFlowList(Collection<OfFlow> flowList) {
		this.m_flowList = flowList;
	}
	
	public void setConnectedSince(long connectedSince) {
		this.m_connectedSince = connectedSince;
	}

	public void setSwitchDescription(String version, String manufacturerDescription, 
			String hardwareDescription, String softwareDescription, String serialNumber, String datapathDescription) {
		this.m_version = version;
		this.m_manufacturerDescription = manufacturerDescription;
		this.m_hardwareDescription = hardwareDescription;
		this.m_softwareDescription = softwareDescription;
		this.m_serialNumber = serialNumber;
		this.m_datapathDescription = datapathDescription;
	}
	
	public void setSwitchFeatures(String capabilities, int buffers, int tables, String version) {
		this.m_capabilities = capabilities;
		this.m_buffers = buffers;
		this.m_tables = tables;
		this.m_version = version;
	}

	public String getSwitchDpid() {
		return m_switchDpid;
	}

	public String getIpAddress() {
		return m_ipAddress;
	}

	public Collection<OfPort> getPortList() {
		return m_portList;
	}

	public Collection<OfFlow> getFlowList() {
		return m_flowList;
	}
	
	public long getConnectedSince() {
		return m_connectedSince;
	}

	public String getConnectedSinceString() {
    	Date date = new Date();
    	date.setTime(m_connectedSince);
    	return new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss.SSS").format(date);
	}

	
	public OfPort getPort(String portNumber) {
		Iterator<OfPort> iterPort = m_portList.iterator();
		while (iterPort.hasNext()) {
			OfPort port = iterPort.next();
			if (port.getPortNumber().equalsIgnoreCase(portNumber)) return port;
		}
		return null;
	}
	
	public OfPort getPort(int portNumber) {
		return getPort(Integer.toString(portNumber));
	}
	
	// returns *first* flow which matches src and dst IP
	public OfFlow getFlow(String ipSrc, String ipDst) {
		Iterator<OfFlow> iterFlow = m_flowList.iterator();
		while (iterFlow.hasNext()) {
			OfFlow flow = iterFlow.next();
			if (flow.getIpv4SrcString().equalsIgnoreCase(ipSrc) &&
					flow.getIpv4DstString().equalsIgnoreCase(ipDst) ) 
				return flow;
		}
		return null;
	}
	
	public OfFlow getFlow(String ethSrc, String ethDst, String ethType) {
		Iterator<OfFlow> iterFlow = m_flowList.iterator();
		while (iterFlow.hasNext()) {
			OfFlow flow = iterFlow.next();
			if (flow.getEthSrcString().equalsIgnoreCase(ethSrc) &&
					flow.getEthDstString().equalsIgnoreCase(ethDst) &&
					flow.getEthTypeString().equalsIgnoreCase(ethType)) 
				return flow;
		}
		return null;
	}
	
	public String getVersion() {
		return m_version;
	}

	public String getManufacturerDescription() {
		return m_manufacturerDescription;
	}

	public String getHardwareDescription() {
		return m_hardwareDescription;
	}

	public String getSoftwareDescription() {
		return m_softwareDescription;
	}

	public String getSerialNumber() {
		return m_serialNumber;
	}

	public String getDatapathDescription() {
		return m_datapathDescription;
	}

	public String getCapabilities() {
		return m_capabilities;
	}

	public int getBuffers() {
		return m_buffers;
	}

	public int getTables() {
		return m_tables;
	}


	
	public int compareTo(OfSwitch o) {
		return this.m_switchDpid.compareTo(o.getSwitchDpid());
	}

	// defines tree label format
	@Override
	public String toString() {
		return m_switchDpid;
	}
}
