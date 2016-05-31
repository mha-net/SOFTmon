package de.tuberlin.cit.softmon.util;

import net.floodlightcontroller.util.MACAddress;

public class MacAddress extends MACAddress implements Comparable<MacAddress> {

	public MacAddress (byte[] address) {
		super(address);
	}

	public MacAddress (String address) {
		super(stringToBytes(address));
	}
	
	public MacAddress (int[] address) {
		super(intToBytes(address));
	}

	public MacAddress (int byte0, int byte1, int byte2, int byte3, int byte4, int byte5) {
		super(intToBytes(new int[] {byte0, byte1, byte2, byte3, byte4, byte5}));
	}

	@Override
	public int compareTo(MacAddress o) {
		return (new Long(this.toLong())).compareTo(new Long(o.toLong()));
	}
	
	public static byte[] stringToBytes(String address) {
		return MACAddress.valueOf(address).toBytes();
	}
	
	public static byte[] intToBytes(int[] address) {
		byte[] bytes = new byte[address.length];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) address[i];
		}
		return bytes;
	}

}
