package de.tuberlin.cit.softmon.model;

public class OfPort implements Comparable<OfPort> {

	private String m_switchDpid;
	private String m_portNumber;

	private String m_hardwareAddress;
	private String m_name;
	private int m_config;
	private int m_state;
	private int m_currentFeatures;
	private int m_advertisedFeatures;
	private int m_supportedFeatures;
	private int m_peerFeatures;

	private int m_currSpeed;
	private int m_maxSpeed;
	
	public OfPort() {
	}
	
	public OfPort(String switchDpid, String portNumber, String hardwareAddress, String name, 
			int config, int state, int currentFeatures, int advertisedFeatures, int supportedFeatures, 
			int peerFeatures, int currSpeed, int maxSpeed) {
		
		this.m_switchDpid = switchDpid;
		this.m_portNumber = portNumber;
		this.m_hardwareAddress = hardwareAddress;
		this.m_name = name;
		this.m_config = config;
		this.m_state = state;
		this.m_currentFeatures = currentFeatures;
		this.m_advertisedFeatures = advertisedFeatures;
		this.m_supportedFeatures = supportedFeatures;
		this.m_peerFeatures = peerFeatures;
		this.m_currSpeed = currSpeed;		// (10 Gbit/s = 10.000.000) => units: 1.000 bits/s = kbits/s 
		this.m_maxSpeed = maxSpeed;
	}

	public String getSwitchDpid() {
		return m_switchDpid;
	}

	public String getPortNumber() {
		return m_portNumber;
	}

	public String getHardwareAddress() {
		return m_hardwareAddress;
	}

	public String getName() {
		return m_name;
	}

	public int getConfig() {
		return m_config;
	}

	public int getState() {
		return m_state;
	}

	public int getCurrentFeatures() {
		return m_currentFeatures;
	}

	public int getAdvertisedFeatures() {
		return m_advertisedFeatures;
	}

	public int getSupportedFeatures() {
		return m_supportedFeatures;
	}

	public int getPeerFeatures() {
		return m_peerFeatures;
	}

	public int getCurrSpeed() {
		return m_currSpeed;
	}

	public long getCurrSpeedBitsPerSec() {
		return ((long) m_currSpeed) * 1000;
	}

	public int getMaxSpeed() {
		return m_maxSpeed;
	}

	public long getMaxSpeedBitsPerSec() {
		return ((long) m_maxSpeed) * 1000;
	}

	public int compareTo(OfPort o) {
		return this.m_portNumber.compareTo(o.getPortNumber());
	}
	
	// defines tree label format
	@Override
	public String toString() {
		return "#" + m_portNumber + " (" + m_name + ")";
	}

}
