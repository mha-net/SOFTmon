package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class PortDesc {
	public String portNumber;
	public String hardwareAddress;
	public String name;
	public int config;
	public int state;
	public int currentFeatures;
	public int advertisedFeatures;
	public int supportedFeatures;
	public int peerFeatures;
	public int currSpeed;
	public int maxSpeed;

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[portNumber=" + portNumber + ", hardwareAddress=" + hardwareAddress + 
        		", name=" + name + ", config=" + config + ", state=" + state + ", currentFeatures=" + currentFeatures + 
        		", advertisedFeatures=" + advertisedFeatures + ", supportedFeatures=" + supportedFeatures +
        		", peerFeatures=" + peerFeatures + ", currSpeed=" + currSpeed + ", maxSpeed=" + maxSpeed + "]";
    }
}
