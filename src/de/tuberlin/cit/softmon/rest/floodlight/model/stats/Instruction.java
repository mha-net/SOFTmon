package de.tuberlin.cit.softmon.rest.floodlight.model.stats;

public class Instruction {
	// kann das auch ein Array sein oder eine List?
	public InstructionApplyAction instruction_apply_actions;
	
	@Override
    public String toString() {
		return this.getClass().getSimpleName() + "[instruction_apply_actions=" + instruction_apply_actions + "]";
    }

}
