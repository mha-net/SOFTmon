package de.tuberlin.cit.softmon.controller;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.tuberlin.cit.softmon.gui.TopologyPanel;

public class TreeUtils {

	public static final String NODE_LABEL_NO_PORTS = "(no port found)";
	public static final String NODE_LABEL_NO_FLOWS = "(no flow defined)";

	static final String NODE_TYPE_NONE = "(none)";
	static final String NODE_TYPE_CTRL = "Controller";
	static final String NODE_TYPE_SWITCH = "Switch";
	static final String NODE_TYPE_PORT = "Port";
	static final String NODE_TYPE_FLOW = "Flow";
	static final String NODE_TYPE_LINK = "Link";

	static String getNodeType(DefaultMutableTreeNode node, String treeName) {
		String nodeType = TreeUtils.NODE_TYPE_NONE;
		if (node.isRoot()) { 
			nodeType = TreeUtils.NODE_TYPE_CTRL;
		} else {
			if (!node.isLeaf()) {
				nodeType = TreeUtils.NODE_TYPE_SWITCH;
			} else {
				switch (treeName) {
				case TopologyPanel.PORT_TREE:
					nodeType = TreeUtils.NODE_TYPE_PORT;
					break;
				case TopologyPanel.FLOW_TREE:
					nodeType = TreeUtils.NODE_TYPE_FLOW;
					break;
				case TopologyPanel.LINK_TREE:
					nodeType = TreeUtils.NODE_TYPE_LINK;
					break;
				}
			}
		}
		return nodeType;
	}

	static boolean isInvalid(DefaultMutableTreeNode node) {
		return node.toString().equals(TreeUtils.NODE_LABEL_NO_FLOWS) || 
				node.toString().equals(TreeUtils.NODE_LABEL_NO_PORTS);
	}

	static boolean isValid(DefaultMutableTreeNode node) {
		return !isInvalid(node);
	}
	
	// invalid nodes are: root, "no flows", "no ports"
	static void dismissInvalidSelection(TreeSelectionEvent event) {
		TreePath selectedTreePath = event.getPath();
		DefaultMutableTreeNode selectedPathNode = (DefaultMutableTreeNode) selectedTreePath.getLastPathComponent();
		DefaultMutableTreeNode previousSelectedPathNode = null;
		
		// get previously selected item, if it exists
		if (event.getOldLeadSelectionPath() != null) 
			previousSelectedPathNode = (DefaultMutableTreeNode) event.getOldLeadSelectionPath().getLastPathComponent();
	
		// inhibit selection of root node
		if (selectedPathNode.isRoot()) { 
			if (previousSelectedPathNode != null) {
				// check if previously selected item was root 
				// (this is very important in order to avoid an endless loop!)
				if (!previousSelectedPathNode.isRoot()) { 
					// if not: select previously selected path
					((JTree) event.getSource()).setSelectionPath(event.getOldLeadSelectionPath());
				} // else do nothing (= no selection)
			} else { // previousSelectedPathNode is null
				// remove current selection (= no selection)
				((JTree) event.getSource()).removeSelectionPath(event.getPath());
			}
		}
		
		// inhibit selection of invalid node
		if (isInvalid(selectedPathNode)) {
			// if any item has been selected previously
			if (previousSelectedPathNode != null) {  
				// check if previously selected item was valid
				// (this is very important in order to avoid an endless loop!)
				if (isValid(previousSelectedPathNode)) {
					// select previously selected path
					((JTree) event.getSource()).setSelectionPath(event.getOldLeadSelectionPath());
				} // else do nothing (= no selection)
			// if no item has been selected yet
			} else { // previousSelectedPathNode is null
				// remove current selection (= no selection)
				((JTree) event.getSource()).removeSelectionPath(event.getPath());
			}
		}
	}

}
