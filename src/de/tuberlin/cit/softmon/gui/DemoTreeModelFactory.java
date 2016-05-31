package de.tuberlin.cit.softmon.gui;

import javax.swing.tree.DefaultMutableTreeNode;

public class DemoTreeModelFactory {

	public static DefaultMutableTreeNode createDemoPortTreeModel() {

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("192.168.235.20 [floodlight 1.0]");
		//DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Controller");
		DefaultMutableTreeNode node_1;
		node_1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:01 [s1]");
		node_1.add(new DefaultMutableTreeNode("port 1 --> 00:00:00:00:00:00:00:02 [s2], port 4"));
		node_1.add(new DefaultMutableTreeNode("port 2"));
		node_1.add(new DefaultMutableTreeNode("port 3"));
		node_1.add(new DefaultMutableTreeNode("port 4"));
		rootNode.add(node_1);
		node_1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:02 [s2]");
		node_1.add(new DefaultMutableTreeNode("port 1"));
		node_1.add(new DefaultMutableTreeNode("port 2"));
		node_1.add(new DefaultMutableTreeNode("port 3"));
		node_1.add(new DefaultMutableTreeNode("port 4"));
		rootNode.add(node_1);
		node_1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:03 [s3]");
		node_1.add(new DefaultMutableTreeNode("port 1"));
		node_1.add(new DefaultMutableTreeNode("port 2"));
		node_1.add(new DefaultMutableTreeNode("port 3"));
		node_1.add(new DefaultMutableTreeNode("port 4"));
		rootNode.add(node_1);

		return rootNode;
	}

	public static DefaultMutableTreeNode createDemoFlowTreeModel() {

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("192.168.235.20 [floodlight 1.0]");
		DefaultMutableTreeNode node_1;
		node_1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:01 [s1]");
		node_1.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.1, ipv4_dst: 10.0.0.9"));
		node_1.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.9, ipv4_dst: 10.0.0.1"));
		rootNode.add(node_1);
		node_1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:02 [s2]");
		node_1.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.1, ipv4_dst: 10.0.0.9"));
		rootNode.add(node_1);
		node_1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:03 [s3]");
		node_1.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.1, ipv4_dst: 10.0.0.9"));
		rootNode.add(node_1);

		return rootNode;
	}

	public static DefaultMutableTreeNode createDemoTableTreeModel() {

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("192.168.235.20 [floodlight 1.0]");
		DefaultMutableTreeNode node1, node2;
		node1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:01 [s1]");
		node2 = new DefaultMutableTreeNode("tableId 0x0");
		node2.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.1, ipv4_dst: 10.0.0.9"));
		node2.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.9, ipv4_dst: 10.0.0.1"));
		node1.add(node2);
		rootNode.add(node1);
		node1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:02 [s2]");
		node2 = new DefaultMutableTreeNode("tableId 0x0");
		node2.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.1, ipv4_dst: 10.0.0.9"));
		node1.add(node2);
		rootNode.add(node1);
		node1 = new DefaultMutableTreeNode("00:00:00:00:00:00:00:03 [s3]");
		node2 = new DefaultMutableTreeNode("tableId 0x0");
		node2.add(new DefaultMutableTreeNode("ipv4_src: 10.0.0.1, ipv4_dst: 10.0.0.9"));
		node1.add(node2);
		rootNode.add(node1);

		return rootNode;
	}

	public static DefaultMutableTreeNode createEmptyTreeModel() {

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("(No REST-Connection specified.)");

		return rootNode;
	}

}
