package de.tuberlin.cit.softmon.gui;

import java.net.URL;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class TopologyPortTreeRenderer extends ATopologyTreeRenderer {
	
	protected ImageIcon getItemIcon() {
		ImageIcon icon = null;
		URL imageURL = ATopologyTreeRenderer.class.getResource("ico/Port.png");
		
		if (imageURL != null) {
		    icon = new ImageIcon(imageURL);
		}
		return icon;
		//return new ImageIcon("resources/ico/Port.png");
	}

}
