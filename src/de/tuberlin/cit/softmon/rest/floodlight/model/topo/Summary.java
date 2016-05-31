package de.tuberlin.cit.softmon.rest.floodlight.model.topo;

public class Summary {
	public int numberOfSwitches;
	public int numberOfQuarantinePorts;
	public int numberOfInterSwitchLinks;
	public int numberOfhosts;
	
	public Summary() {
	}
	
	public Summary(int numberOfSwitches, int numberOfQuarantinePorts, int numberOfInterSwitchLinks, int numberOfhosts) {
		this.numberOfSwitches = numberOfSwitches;
		this.numberOfQuarantinePorts = numberOfQuarantinePorts;
		this.numberOfInterSwitchLinks = numberOfInterSwitchLinks;
		this.numberOfhosts = numberOfhosts;
	}
		
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[numberOfSwitches=" + numberOfSwitches + 
				", numberOfQuarantinePorts=" + numberOfQuarantinePorts + 
        		", numberOfInterSwitchLinks=" + numberOfInterSwitchLinks + 
        		", numberOfhosts=" + numberOfhosts + "]";
    }
		
}
