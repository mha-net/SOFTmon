package de.tuberlin.cit.softmon.rest;

import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;

public abstract class ARestConnector {

	public abstract OfPortCounter getOfPortCounter(OfPort port);
	
	public abstract OfSwitchCounter getOfSwitchCounter(OfSwitch swtch);
	
	public abstract OfFlowCounter getOfFlowCounter(OfFlow flow);
	
	
}
