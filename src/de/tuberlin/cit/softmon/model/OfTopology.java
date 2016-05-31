package de.tuberlin.cit.softmon.model;

import java.util.Iterator;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import de.tuberlin.cit.softmon.controller.TreeUtils;

public class OfTopology {

	private String m_controllerHostname;
	private String m_controllerDescription;
	
	private Map<String, OfSwitch> m_switchMap;
	private Map<String, OfLink> m_linkMap;
	
	public OfTopology() {
	}
	
	public OfTopology(Map<String, OfSwitch> switchMap, Map<String, OfLink> linkMap) {
		this.m_switchMap = switchMap;
		this.m_linkMap = linkMap;
	}
	
	public void setSwitchMap(Map<String, OfSwitch> switchMap) {
		this.m_switchMap = switchMap;
	}
	
	public void setLinkMap(Map<String, OfLink> linkMap) {
		this.m_linkMap = linkMap;
	}
	
	public void setControllerHostname(String controllerIp) {
		this.m_controllerHostname = controllerIp;
	}
	
	public void setControllerDescription(String description) {
		this.m_controllerDescription = description;
	}

	
	public String getControllerHostname() {
		return m_controllerHostname;
	}

	public String getControllerDescription() {
		return m_controllerDescription;
	}
	
	public Map<String, OfSwitch> getSwitchMap() {
		return this.m_switchMap;
	}

	public Map<String, OfLink> getLinkMap() {
		return this.m_linkMap;
	}
	
	public OfSwitch getSwitch(String dpid) {
		return m_switchMap.get(dpid);
	}
	
	// returns first link between src and dst switch
	public OfLink getLink(String srcDpid, String dstDpid) {
		// to do
		return null;
	}
	
	public OfLink getLink(String srcDpid, String srcPort, String dstDpid, String dstPort) {
		return m_linkMap.get(OfLink.key(srcDpid, srcPort, dstDpid, dstPort));
	}
	
	public OfLink getLink(String linkKey) {
		return m_linkMap.get(linkKey);
	}
	
	public OfPort getPort(String switchDpid, int portNumber) {
		OfSwitch swtch = m_switchMap.get(switchDpid);
		Iterator<OfPort> iterPort = swtch.getPortList().iterator();
		while (iterPort.hasNext()) {
			OfPort port = iterPort.next();
			if (Integer.parseInt(port.getPortNumber()) == portNumber) return port;
		}
		return null;
	}

	public String rootNode() {
		return m_controllerHostname + " : Floodlight 1.1 (OF_13)";
	}
	
	public DefaultMutableTreeNode getPortTreeModel() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootNode());
		
		for (Map.Entry<String, OfSwitch> entry : m_switchMap.entrySet()) {
			OfSwitch ofSw = entry.getValue();
			DefaultMutableTreeNode nodeLevel1 = new DefaultMutableTreeNode(ofSw);
			
			Iterator<OfPort> iterPort = ofSw.getPortList().iterator();
			if (iterPort.hasNext()) {
				while (iterPort.hasNext()) {
					OfPort port = iterPort.next();
					DefaultMutableTreeNode nodeLevel2 = new DefaultMutableTreeNode(port);
					nodeLevel1.add(nodeLevel2);
				}
			} else {
				DefaultMutableTreeNode nodeLevel2 = new DefaultMutableTreeNode(TreeUtils.NODE_LABEL_NO_PORTS);
				nodeLevel1.add(nodeLevel2);
			}
			rootNode.add(nodeLevel1);
		}
		return rootNode;
	}
	
	public DefaultMutableTreeNode getFlowTreeModel() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootNode());
		
		for (Map.Entry<String, OfSwitch> entry : m_switchMap.entrySet()) {
			OfSwitch ofSw = entry.getValue();
			DefaultMutableTreeNode nodeLevel1 = new DefaultMutableTreeNode(ofSw);

			Iterator<OfFlow> iterFlow = ofSw.getFlowList().iterator();
			if (iterFlow.hasNext()) {
				while (iterFlow.hasNext()) {
					OfFlow flow = iterFlow.next();
					DefaultMutableTreeNode nodeLevel2 = new DefaultMutableTreeNode(flow);
					nodeLevel1.add(nodeLevel2);
				}
			} else {
				DefaultMutableTreeNode nodeLevel2 = new DefaultMutableTreeNode(TreeUtils.NODE_LABEL_NO_FLOWS);
				nodeLevel1.add(nodeLevel2);
			}
			rootNode.add(nodeLevel1);
		}
		return rootNode;
	}

	public DefaultMutableTreeNode getLinkTreeModel() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootNode());
		
		for (Map.Entry<String, OfLink> entry : m_linkMap.entrySet()) {
			OfLink link = entry.getValue();
			DefaultMutableTreeNode nodeLevel1 = new DefaultMutableTreeNode(link);
			rootNode.add(nodeLevel1);
		}
		return rootNode;
	}
	
	@Override
	public String toString() {
		return "OfTopology[switchList" + m_switchMap + ", linkMap" + m_linkMap + "]"; 
	}

}
