package de.tuberlin.cit.softmon.chart;

import de.tuberlin.cit.softmon.controller.PresentationUtils;
import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfFlowMetrics;
import de.tuberlin.cit.softmon.model.OfLink;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfPortMetrics;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;
import de.tuberlin.cit.softmon.model.OfSwitchMetrics;

public class DataPrinter {
	
	public static void printFlowCounter(OfFlow flow, OfFlowCounter flowCounter) {
		System.out.println(PresentationUtils.timeFormatFull(flowCounter.getTimeStamp()) + 
				": OfFlowCounter (dpid=" + flow.getSwitchDpid() +", flow=" + flow + "): " + flowCounter +
				", durationMillis=" + flowCounter.getDurationMillis());
	}

	public static void printFlowMetrics(OfFlow flow, OfFlowMetrics flowMetrics) {
		System.out.println(PresentationUtils.timeFormatFull(flowMetrics.getTimeStamp()) + 
				": OfFlowMetrics (dpid=" + flow.getSwitchDpid() +", flow=" + flow + "): " + flowMetrics);
	}

	public static void printPortCounter(OfPort port, OfPortCounter portCounter) {
		System.out.println(PresentationUtils.timeFormatFull(portCounter.getTimeStamp()) + 
				": OfPortCounter (dpid=" + port.getSwitchDpid() +", port=" + port + "): " + portCounter);
	}

	public static void printPortMetrics(OfPort port, OfPortMetrics portMetrics) {
		System.out.println(PresentationUtils.timeFormatFull(portMetrics.getTimeStamp()) + 
				": OfPortMetrics (dpid=" + port.getSwitchDpid() +", port=" + port + "): " + portMetrics);
	}

	public static void printSwitchCounter(OfSwitch ofSwitch, OfSwitchCounter switchCounter) {
		System.out.println(PresentationUtils.timeFormatFull(switchCounter.getTimeStamp()) + 
				": OfSwitchCounter (dpid=" + ofSwitch.getSwitchDpid() + 
				", ipAddress=" + ofSwitch.getIpAddress() + "): " + switchCounter);
	}

	public static void printSwitchMetrics(OfSwitch ofSwitch, OfSwitchMetrics switchMetrics) {
		System.out.println(PresentationUtils.timeFormatFull(switchMetrics.getTimeStamp()) + 
				": OfSwitchMetrics (dpid=" + ofSwitch.getSwitchDpid() + 
				", ipAddress=" + ofSwitch.getIpAddress() + "): " + switchMetrics);
	}

	public static void printLinkCounter(OfLink link, OfPortCounter portCounter) {
		System.out.println(PresentationUtils.timeFormatFull(portCounter.getTimeStamp()) + 
				": OfSwitchCounter (srcDpid=" + link.getSrcDpid() + 
				", dstDpid=" + link.getDstDpid() + "): " + portCounter);
	}

}
