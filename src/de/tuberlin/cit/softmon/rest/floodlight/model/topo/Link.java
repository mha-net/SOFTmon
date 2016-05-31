package de.tuberlin.cit.softmon.rest.floodlight.model.topo;

public class Link {
	public String src_switch;
	public int src_port;
	public String dst_switch;
	public int dst_port;
	public String type;
	public String direction;

	public Link() {
		
	}
	
	public Link (String src_switch, int src_port, String dst_switch, int dst_port, String type, String direction) {
		this.src_switch = src_switch;
		this.src_port = src_port;
		this.dst_switch = dst_switch;
		this.dst_port = dst_port;
		this.type = type;
		this.direction = direction;
	}

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[src_switch=" + src_switch + ", src_port=" + src_port + 
        		", dst_switch=" + dst_switch + ", dst_port=" + dst_port + ", type=" + type + 
        		", direction=" + direction +"]";
    }
	
}
