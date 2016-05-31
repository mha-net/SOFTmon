package de.tuberlin.cit.softmon.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfLink;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;

public class PresentationUtils {

	public static String[][] createDetailsList(OfSwitch sw) {
		String[][] stringArray = {
				{"Switch DPID", sw.getSwitchDpid()},
				{"IP Address", sw.getIpAddress()}, 
				{"Conn. since", sw.getConnectedSinceString()}, 
				{"Version", sw.getVersion()}, 
				{"Manufacturer", sw.getManufacturerDescription()}, 
				{"Hardware", sw.getHardwareDescription()}, 
				{"Software", sw.getSoftwareDescription()}, 
				{"Serial No", sw.getSerialNumber()}, 
				{"DP Descr.", sw.getDatapathDescription()}, 
				{"Capabilities", sw.getCapabilities()}, 
				{"Buffers", Integer.toString(sw.getBuffers())}, 
				{"Tables", Integer.toString(sw.getTables())}
			};
		return stringArray;
	}

	public static String[][] createDetailsList(OfPort port) {
		String[][] stringArray = {
				{"Switch DPID", port.getSwitchDpid()},
				{"Port #", port.getPortNumber()},
				{"Port Name", port.getName()},
				{"HW Address", port.getHardwareAddress()},
				{"CurrSpd.[Mbps]", Long.toString(port.getCurrSpeedBitsPerSec() / 1000000)},
				//{"Current Speed", Integer.toString(port.getCurrSpeed())},
				{"MaxSpd.[Mbps]", Long.toString(port.getMaxSpeedBitsPerSec() / 1000000)},
				//{"MaxSpeed", Integer.toString(port.getMaxSpeed())},
				{"Config", Integer.toString(port.getConfig())},
				{"State", Integer.toString(port.getState())},
				{"Curr. Features", Integer.toString(port.getCurrentFeatures())},
				{"Adv. Features", Integer.toString(port.getAdvertisedFeatures())},
				{"Supp. Features", Integer.toString(port.getSupportedFeatures())},
				{"Peer Features", Integer.toString(port.getPeerFeatures())},
			};
		return stringArray;
	}

	public static String[][] createDetailsList(OfFlow flow) {
		String[][] stringArray = {
				{"Switch DPID", flow.getSwitchDpid()},
				{"Table ID", flow.getTableIdString()},
				{"In Port", flow.getInPortString()},
				{"Eth Type", flow.getEthTypeString()},
				{"IP Protocol", flow.getIpProtoString()},
				{"Src Port", flow.getTpSrcString()},
				{"Dst Port", flow.getTpDstString()},
				{"IPv4 Src", flow.getIpv4SrcString()},
				{"Ipv4 Dst", flow.getIpv4DstString()},
				{"Eth Src", flow.getEthSrcString()},
				{"Eth Dst", flow.getEthDstString()},
				{"Action", flow.getAction()},
				{"Out Port", flow.getOutPortString()},
				{"OF Version", flow.getOfVersion()},
				{"Cookie", flow.getCookie()},
				{"Priority", Integer.toString(flow.getPriority())},
				{"Idle Timeout", Integer.toString(flow.getIdleTimeoutSec())},
				{"Hard Timeout", Integer.toString(flow.getHardTimeoutSec())},
				{"Flags", Integer.toString(flow.getFlags())}
			};
		return stringArray;
	}

	public static String[][] createDetailsList(OfLink link) {
		String[][] stringArray = {
				{"Src DPID", link.getSrcDpid()},
				{"Src Port", link.getSrcPort()},
				{"Dst DPID", link.getDstDpid()},
				{"Dst Port", link.getDstPort()},
				{"Type", link.getType()},
				{"Direction", link.getDirection()}
			};
		return stringArray;
	}
	
	public static String[][][] createCounterLists(OfSwitchCounter switchCounter) {
		String[][][] stringArray = {
				// left area
				{
					{"packetCount", Long.toString(switchCounter.getPacketCount())},
					{"byteCount", Long.toString(switchCounter.getByteCount())},
					{"flowCount", Long.toString(switchCounter.getFlowCount())},
				},
				// right area (empty)
				{
					// must be present to avoid null pointer exception
				}
		};
		return stringArray;
	}

	public static String[][][] createCounterLists(OfPortCounter portCounter) {
		String[][][] stringArray = {
				// left area
				{
					{"rxPackets", Long.toString(portCounter.getReceivePackets())},
					{"rxBytes", Long.toString(portCounter.getReceiveBytes())},
					{"rxDropped", Long.toString(portCounter.getReceiveDropped())},
					{"rxErrors", Long.toString(portCounter.getReceiveErrors())},
					{"rxFrameErrors", Long.toString(portCounter.getReceiveFrameErrors())},
					{"rxOverrunErrs.", Long.toString(portCounter.getReceiveOverrunErrors())},
					{"rxCRCErrors", Long.toString(portCounter.getReceiveCRCErrors())},
					{"timeStamp", timeFormatShort(portCounter.getTimeStamp())}
				},
				// right area
				{
					{"txPackets", Long.toString(portCounter.getTransmitPackets())},
					{"txBytes", Long.toString(portCounter.getTransmitBytes())},
					{"txDropped", Long.toString(portCounter.getTransmitDropped())},
					{"txErrors", Long.toString(portCounter.getTransmitErrors())},
					{"collisions", Long.toString(portCounter.getCollisions())},
					{"durationSec", Long.toString(portCounter.getDurationSec())},
					{"durationNsec", Long.toString(portCounter.getDurationNsec())},
					{"timeStamp", Long.toString(portCounter.getTimeStamp())}
				}
		};
		return stringArray;
	}
	
	public static String[][][] createCounterLists(OfFlowCounter flowCounter) {
		String[][][] stringArray = {
				// left area
				{
					{"packetCount", Long.toString(flowCounter.getPacketCount())},
					{"byteCount", Long.toString(flowCounter.getByteCount())},
					{"durationSec", Long.toString(flowCounter.getDurationSec())},
					{"durationNsec", Long.toString(flowCounter.getDurationNsec())},
					{"timeStamp", Long.toString(flowCounter.getTimeStamp())},
					{"timeStamp", timeFormatShort(flowCounter.getTimeStamp())}
				},
				// right area (empty)
				{
					// must be present to avoid null pointer exception
				}
		};
		return stringArray;
	}

	public static String formatKeyValueList(String[][] list) {
		String string = "";
		for (int i = 0; i < list.length; i++) {
			String[] entry = list[i];
			string = string + entry[0] + ":\t" + entry[1];
			if (i < (list.length - 1)) string = string + "\n";
		}
		//System.out.println("formatList = \n\"" + string + "\"");
		return string;
	}
	
	public static String timeFormatFull(Date date) {
		return new SimpleDateFormat(Config.DATE_FORMAT_FULL).format(date);
	}
	
	public static String timeFormatFull(long milliseconds) {
    	Date date = new Date();
    	date.setTime(milliseconds);
    	return new SimpleDateFormat(Config.DATE_FORMAT_FULL).format(date);
	}
	
	public static String timeFormatShort(long milliseconds) {
		Date date = new Date();
		date.setTime(milliseconds);
		return new SimpleDateFormat(Config.DATE_FORMAT_TIME).format(date);
	}
}
