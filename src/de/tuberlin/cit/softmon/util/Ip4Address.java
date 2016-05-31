package de.tuberlin.cit.softmon.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Ip4Address implements Comparable<Ip4Address> {

	public static final int IP_ADDRESS_LENGTH = 4;

	private Inet4Address m_ip4address;
	
	public Ip4Address(String address) {
		try {
			this.m_ip4address = (Inet4Address) Inet4Address.getByName(address);
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: " + e.getStackTrace());
		}
	}

	public Ip4Address(byte[] address) {
		try {
			this.m_ip4address = (Inet4Address) Inet4Address.getByAddress(address);
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: " + e.getStackTrace());
		}
	}

	public Ip4Address(int[] address) {
		byte[] bytes = new byte[IP_ADDRESS_LENGTH];
		for (int i = 0; i < IP_ADDRESS_LENGTH; i++) {
			bytes[i] = (byte) address[i];
		}
		try {
			this.m_ip4address = (Inet4Address) Inet4Address.getByAddress(bytes);
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: " + e.getStackTrace());
		}
	}

	public Ip4Address(int byte0, int byte1, int byte2, int byte3) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) byte0;
		bytes[1] = (byte) byte1;
		bytes[2] = (byte) byte2;
		bytes[3] = (byte) byte3;
		try {
			this.m_ip4address = (Inet4Address) Inet4Address.getByAddress(bytes);
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: " + e.getStackTrace());
		}
	}
	
	public long getAddressLong() {
		byte[] address = m_ip4address.getAddress();
		long addrLong = 0;
		for (int i = 0; i < IP_ADDRESS_LENGTH; i++) {
			long t = (address[i] & 0xffL) << (((IP_ADDRESS_LENGTH - 1) - i) * 8);
			addrLong |= t;
		}
		return addrLong;
	}

	public byte[] getAddressBytes() {
		return m_ip4address.getAddress();
	}
	
	public String getAddressString() {
		return m_ip4address.toString().split("/")[1];
	}
	
	public Inet4Address getInet4Address() {
		return m_ip4address;
	}

	@Override
	public int compareTo(Ip4Address o) {
		return (new Long(this.getAddressLong())).compareTo(new Long(o.getAddressLong()));
	}
	
	@Override
	public String toString() {
		return m_ip4address.toString().split("/")[1];
	}

	public boolean equals(Ip4Address o) {
		return m_ip4address.equals(o.getInet4Address());
	}
}
