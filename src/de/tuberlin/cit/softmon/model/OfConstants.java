package de.tuberlin.cit.softmon.model;

public class OfConstants {

	
	// undefined and virtual switch ports (number affects sort sequence)
	public static final int PORT_UNDEFINED = -1;
	public static final int PORT_ALL = -2;
	public static final int PORT_CONTROLLER = -10;
	public static final int PORT_LOCAL = -11;
	public static final int PORT_TABLE = -12;
	public static final int PORT_IN_PORT = -13;
	
	// string representations of virtual switch ports (standard = upper case?)
	public static final String PORT_ALL_STRING = "ALL";
	public static final String PORT_CONTROLLER_STRING = "CONTROLLER";
	public static final String PORT_LOCAL_STRING = "LOCAL";
	public static final String PORT_TABLE_STRING = "TABLE";
	public static final String PORT_IN_PORT_STRING = "IN_PORT";
	
	// undefined counter value
	public static final long COUNTER_UNDEFINED = -1;

	// undefined addresses and types
	public static final String IP4_ADDR_UNDEFINED = "0.0.0.0";
	public static final String ETH_ADDR_UNDEFINED = "00:00:00:00:00:00";
	public static final String ETHTYPE_UNDEFINED = "-0x01";
	public static final String IP_PROTO_UNDEFINED = "-0x01";
	public static final int TP_PORT_UNDEFINED = -1;
	
	
	// delimiter used to create composed keys for KeyMap
	public static final String KEY_DELIM = ",";
	
	// string representation of undefined parameters
	public static final String IP4_UNDEFINED_STRING = "*";
	public static final String ETH_UNDEFINED_STRING = "*";
	public static final String ETHTYPE_UNDEFINED_STRING = "*";
	public static final String PORT_UNDEFINED_STRING = "*";
	public static final String IP_PROTO_UNDEFINED_STRING = "*";
	public static final String TP_PORT_UNDEFINED_STRING = "*";
	
	// flow actions (standard = lower case?)
	public static final String OF_ACTION_OUTPUT = "output";
	public static final String OF_ACTION_LOCAL = "local";
	public static final String OF_ACTION_DROP = "drop";
	public static final String OF_ACTION_CONTROLLER = "controller";
	
	// undefined matches
	public static final int FLOW_MATCH_UNDEFINED = -1; 
	
	// time base types
	public static final String TIME_BASE_COUNTER = "COUNTER";
	public static final String TIME_BASE_SYSTEM = "SYSTEM";

}
