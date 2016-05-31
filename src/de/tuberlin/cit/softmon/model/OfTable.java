package de.tuberlin.cit.softmon.model;

import java.util.Collection;

public class OfTable {
	
	private String m_switchDpid;
	private int m_tableId;
	private Collection<OfFlow> m_flowList;
	
	public OfTable(String switchDpid, int tableId) {
		this.m_switchDpid = switchDpid;
		this.m_tableId = tableId;
	}

	public OfTable(String switchDpid, int tableId, Collection<OfFlow> flowList) {
		this.m_switchDpid = switchDpid;
		this.m_tableId = tableId;
		this.m_flowList = flowList;
	}

	public void setFlowList(Collection<OfFlow> flowList) {
		this.m_flowList = flowList;
	}

	public Collection<OfFlow> getFlowList() {
		return m_flowList;
	}

	public String getSwitchDpid() {
		return m_switchDpid;
	}

	public int getTableId() {
		return m_tableId;
	}

}
