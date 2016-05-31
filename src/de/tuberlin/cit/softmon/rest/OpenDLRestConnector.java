package de.tuberlin.cit.softmon.rest;

import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;
import de.tuberlin.cit.softmon.rest.opendl.OpenDLClient;

public class OpenDLRestConnector extends ARestConnector {
	
	OpenDLClient m_client;


	@Override
	public OfPortCounter getOfPortCounter(OfPort port) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfSwitchCounter getOfSwitchCounter(OfSwitch swtch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfFlowCounter getOfFlowCounter(OfFlow flow) {
		// TODO Auto-generated method stub
		return null;
	}

}
