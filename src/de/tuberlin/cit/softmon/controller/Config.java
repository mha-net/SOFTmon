package de.tuberlin.cit.softmon.controller;

import java.awt.Color;

import de.tuberlin.cit.softmon.gui.MainWindow;
import de.tuberlin.cit.softmon.model.OfConstants;

public class Config {

	// Main Windows Settings
	public static final String WINDOW_TITLE = "SOFTmon";
	public static final int[] WINDOW_MIN_DIM = {800, 600};		// minimum size of MainWindow (with, height)
	public static final int[] WINDOW_POSITION = {100, 100};		// initial position of MainWindow upper left (x, y) 
	public static final boolean WINDOW_CENTERED = false;		// center window on screen
	public static final boolean WINDOW_CENTERED_TOP = true;		// center window horizontally
	
	public static final String LOOK_AND_FEEL = MainWindow.PLAF_WINDOWS; // supported: PLAF_WINDOWS, PLAF_NIMBUS
	
	public static final String REST_HOSTNAME = "192.168.235.20";
	
	public static final String VERSION_INFO = "OF13 1.0.8 WIN";
	public static final String BUILD_INFO = "20160302-3";
	
	public static final int CYCLE_TIME_INIT = 1000;
	public static final int CYCLE_TIME_MIN = 250;
	public static final int CYCLE_TIME_STEP = 250;

	public static final int VALUE_COUNT_INIT = 200;
	public static final int VALUE_COUNT_MIN = 100;
	public static final int VALUE_COUNT_MAX = 1000;
	public static final int VALUE_COUNT_STEP = 50;
	
	public static final boolean CONFIRM_EXIT = true;
	
	public static final int NETWORK_TIMEOUT = 500;
	
	public static final Color GREEN = new Color(0, 182, 0);
	public static final Color RED = new Color(232, 0, 0);
	
	public static final String DATE_FORMAT_FULL = "dd.MM.yyyy-HH:mm:ss.SSS";
	public static final String DATE_FORMAT_TIME = "HH:mm:ss.SSS";
	
	public static final String METRICS_TIME_BASE_FLOW = OfConstants.TIME_BASE_SYSTEM;

}
