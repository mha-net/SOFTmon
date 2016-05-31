package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Desc {
	public String version;
	public String manufacturerDescription;
	public String hardwareDescription;
	public String softwareDescription;
	public String serialNumber;
	public String datapathDescription;

	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[version=" + version + ", manufacturerDescription=" + manufacturerDescription + 
        		", hardwareDescription=" + hardwareDescription + ", softwareDescription=" + softwareDescription 
        		+", serialNumber=" + serialNumber +", datapathDescription=" + datapathDescription + "]";
		
    }

}
