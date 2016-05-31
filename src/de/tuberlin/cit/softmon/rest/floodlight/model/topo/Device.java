package de.tuberlin.cit.softmon.rest.floodlight.model.topo;

import java.util.Arrays;

public class Device {
	public String entityClass;
	public String[] mac;
	public String[] ipv4;
	public String[] ipv6;
	public String[] vlan;
	public AttachmentPoint[] attachmentPoint;
	public long lastSeen;
	public String dhcpClientName;
	
	@Override
    public String toString() {
		String attachmentPointString[] = new String[attachmentPoint.length];
		for (int i = 0; i < attachmentPoint.length; i++)
			attachmentPointString[i] = attachmentPoint[i].toString(); 
			
		
		return this.getClass().getSimpleName() + "[entityClass=" + entityClass + ", mac[]=" + Arrays.toString(mac) + 
        		", ipv4[]=" + Arrays.toString(ipv4) + ", ipv6[]=" + Arrays.toString(ipv6) + ", vlan[]=" + Arrays.toString(vlan) + 
        		", attachmentPoint[]=" + Arrays.toString(attachmentPointString) + ", lastSeen=" + lastSeen + 
        		", dhcpClientName=" + dhcpClientName + "]";
    }

}
