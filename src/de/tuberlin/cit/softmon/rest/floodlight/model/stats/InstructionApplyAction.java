package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class InstructionApplyAction {
	public String actions;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[actions=\"" + actions + "\"]";
    }

}
