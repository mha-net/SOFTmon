package de.tuberlin.cit.softmon.gui;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public abstract class ATopologyTreeRenderer extends DefaultTreeCellRenderer {
	
	protected ImageIcon getRootIcon() {
		ImageIcon icon = null;
		URL imageURL = ATopologyTreeRenderer.class.getResource("ico/Controller.png");
		
		if (imageURL != null) {
		    icon = new ImageIcon(imageURL);
		}
		return icon;
		//return new ImageIcon("resources/ico/Controller.png");
	}

	protected ImageIcon getTreeIcon() {
		ImageIcon icon = null;
		URL imageURL = ATopologyTreeRenderer.class.getResource("ico/Switch.png");
		
		if (imageURL != null) {
		    icon = new ImageIcon(imageURL);
		}
		return icon;
		//return new ImageIcon("resources/ico/Switch.png");
	}

	protected abstract ImageIcon getItemIcon(); 

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean isLeaf, int row, boolean focused) {
		Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		if (row == 0)
			setIcon(getRootIcon());
		else if (isLeaf)
			setIcon(getItemIcon());
		else
			setIcon(getTreeIcon());
		return component;
	}
}
