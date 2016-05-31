package de.tuberlin.cit.softmon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.tuberlin.cit.softmon.model.OfTopology;

@SuppressWarnings("serial")
public class TopologyPanel extends JPanel {
	
	public static final String REFRESH_BUTTON_TITLE = "Refresh Toplogy";
	public static final String SHOW_BUTTON_TITLE = "Show >>";
	public static final String RESET_BUTTON_TITLE = "Clear >>";

	private static final String PORT_TREE_TITLE = "Ports per Switch";
	private static final String FLOW_TREE_TITLE = "Flows per Switch";
	private static final String LINK_TREE_TITLE = "Interconnects";

	public static final int PORT_TAB = 0; 
	public static final int FLOW_TAB = 1;
	public static final int LINK_TAB = 2;
	
	public static final String PORT_TREE = "PortTree";
	public static final String FLOW_TREE = "FlowTree";
	public static final String LINK_TREE = "LinkTree";

	private static final int DEFAULT_WIDTH = 295;  
	private static final int DEFAULT_HEIGHT = 400;
	
	private static final int TREE_AUTOEXPANSION_THRESHOLD = 10; 
	
	private TreeModel m_portTreeModel;
	private TreeModel m_flowTreeModel;
	private TreeModel m_linkTreeModel;
	
	private JTabbedPane m_topologyTabbedPane;
	
	private JTree m_portTree;
	private JTree m_flowTree;
	private JTree m_linkTree;
	
	private DetailsPanel m_portDetailsPanel;
	private DetailsPanel m_flowDetailsPanel;
	private DetailsPanel m_linkDetailsPanel;
	
	JTextArea m_txtDetails;
	
	private JButton m_btnRefresh;
	private JButton m_btnShowGraph;
	private JButton m_btnResetGraph;
	
	public TopologyPanel() {
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		
		// configure panel
		setLayout(new BorderLayout(0, 0));
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Topology", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		m_topologyTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(m_topologyTabbedPane, BorderLayout.CENTER);
		
		// port tree panel
		m_topologyTabbedPane.addTab(PORT_TREE_TITLE, null, createPortTreePanel(), null);

		// flow tree panel
		m_topologyTabbedPane.addTab(FLOW_TREE_TITLE, null, createFlowTreePanel(), null);

		// link tree panel
		m_topologyTabbedPane.addTab(LINK_TREE_TITLE, null, createLinkTreePanel(), null);

		// panel with buttons: Refresh, Show Graph
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		// set port tree model to empty topology 
		setPortTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode()), false);

		// set flow tree model to empty topology 
		setFlowTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode()), false);
		
		// set link tree model to empty topology 
		setLinkTreeModel(new DefaultTreeModel(new DefaultMutableTreeNode()), false);

		// show demo trees (for design with Window Builder)
		//setPortTreeModel(new DefaultTreeModel(DemoTreeModelFactory.createDemoPortTreeModel()));
		//setFlowTreeModel(new DefaultTreeModel(DemoTreeModelFactory.createDemoFlowTreeModel()));

	}

	private void setPortTreeModel(TreeModel treeModel, boolean showRootNode) {
		// set tree model
		m_portTreeModel = treeModel;
		m_portTree.setModel(m_portTreeModel);
		m_portTree.setRootVisible(showRootNode);
		
		expandTree(m_portTree);
	}
	
	private void setFlowTreeModel(TreeModel treeModel, boolean showRootNode) {
		// set tree model
		m_flowTreeModel = treeModel;
		m_flowTree.setModel(m_flowTreeModel);
		m_flowTree.setRootVisible(showRootNode);

		expandTree(m_flowTree);
	}
	
	private void setLinkTreeModel(TreeModel treeModel, boolean showRootNode) {
		// set tree model
		m_linkTreeModel = treeModel;
		m_linkTree.setModel(m_linkTreeModel);
		m_linkTree.setRootVisible(showRootNode);

		expandTree(m_flowTree);
	}

	@SuppressWarnings("unchecked")
	private void expandTree(JTree tree) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		for (Enumeration<TreeNode> childEnum = root.children(); childEnum.hasMoreElements();) {
			TreeNode childNode = (TreeNode) childEnum.nextElement();
			if (childNode.getChildCount() <= TREE_AUTOEXPANSION_THRESHOLD) {
				tree.expandPath(getTreePath(childNode));
			}
		}
	}
	
	private TreePath getTreePath(TreeNode treeNode) {
		List<TreeNode> treeNodes = new ArrayList<>();
		treeNodes.add(treeNode);
		TreeNode parentNode = treeNode.getParent();
		while (parentNode != null) {
			treeNodes.add(parentNode);
			parentNode = parentNode.getParent();
		}
		Collections.reverse(treeNodes);
		return new TreePath(treeNodes.toArray());
	}
	
	private JPanel createPortTreePanel() {
		// create tab
		JPanel portTreeTabbedPanel = new JPanel();
		portTreeTabbedPanel.setLayout(new BorderLayout(0, 0));
		portTreeTabbedPanel.setName(PORT_TREE);
		
		// create tree
		m_portTree = new JTree();
		m_portTree.setName(PORT_TREE);
		
		// set tree icons
		m_portTree.setCellRenderer(new TopologyPortTreeRenderer());
		
		// set single selection mode
		TreeSelectionModel portTreeSelectionModel = new DefaultTreeSelectionModel();
		portTreeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_portTree.setSelectionModel(portTreeSelectionModel);

		// create scroll pane and add tree
		JScrollPane portTreeScrollPane = new JScrollPane();
		portTreeTabbedPanel.add(portTreeScrollPane);
		portTreeScrollPane.setViewportView(m_portTree);

		// create details panel
		m_portDetailsPanel = new DetailsPanel();
		portTreeTabbedPanel.add(m_portDetailsPanel, BorderLayout.SOUTH);
		
		return portTreeTabbedPanel;
	}
	
	private JPanel createFlowTreePanel() {
		// create tab
		JPanel flowTreeTabbedPanel = new JPanel();
		flowTreeTabbedPanel.setLayout(new BorderLayout(0, 0));
		flowTreeTabbedPanel.setName(FLOW_TREE);

		//create tree
		m_flowTree = new JTree();
		m_flowTree.setName(FLOW_TREE);

		// set tree icons
		m_flowTree.setCellRenderer(new TopologyFlowTreeRenderer());
		
		// set single selection mode
		TreeSelectionModel flowTreeSelectionModel = new DefaultTreeSelectionModel();
		flowTreeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_flowTree.setSelectionModel(flowTreeSelectionModel);

		// create scroll pane and add tree
		JScrollPane flowScrollPane = new JScrollPane();
		flowTreeTabbedPanel.add(flowScrollPane, BorderLayout.CENTER);
		flowScrollPane.setViewportView(m_flowTree);
		
		// create details panel
		m_flowDetailsPanel = new DetailsPanel();
		//m_flowDetailsPanel.setDetailsSize(300, 200);
		flowTreeTabbedPanel.add(m_flowDetailsPanel, BorderLayout.SOUTH);
		
		return flowTreeTabbedPanel;
	}
	
	private JPanel createLinkTreePanel() {
		// create tab
		JPanel linkTreeTabbedPanel = new JPanel();
		linkTreeTabbedPanel.setLayout(new BorderLayout(0, 0));
		linkTreeTabbedPanel.setName(FLOW_TREE);
		
		//create tree
		m_linkTree = new JTree();
		m_linkTree.setName(LINK_TREE);

		// set tree icons
		m_linkTree.setCellRenderer(new TopologyLinkTreeRenderer());
		
		// set single selection mode
		TreeSelectionModel linkTreeSelectionModel = new DefaultTreeSelectionModel();
		linkTreeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_linkTree.setSelectionModel(linkTreeSelectionModel);

		// create scroll pane and add tree
		JScrollPane linkScrollPane = new JScrollPane();
		linkTreeTabbedPanel.add(linkScrollPane, BorderLayout.CENTER);
		linkScrollPane.setViewportView(m_linkTree);

		// create details panel
		m_linkDetailsPanel = new DetailsPanel();
		linkTreeTabbedPanel.add(m_linkDetailsPanel, BorderLayout.SOUTH);
	
		return linkTreeTabbedPanel;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		m_btnRefresh = new JButton(REFRESH_BUTTON_TITLE);
		buttonPanel.add(m_btnRefresh);
		
		m_btnShowGraph = new JButton(SHOW_BUTTON_TITLE);
		buttonPanel.add(m_btnShowGraph);
		
		m_btnResetGraph = new JButton(RESET_BUTTON_TITLE);
		buttonPanel.add(m_btnResetGraph);
		
		return buttonPanel;
	}
	
	public void refreshTopology(OfTopology topology) {
		setPortTreeModel(new DefaultTreeModel(topology.getPortTreeModel()), true);
		setFlowTreeModel(new DefaultTreeModel(topology.getFlowTreeModel()), true);
		setLinkTreeModel(new DefaultTreeModel(topology.getLinkTreeModel()), true);
	}
	
	public void addButtonListener(ActionListener listener) {
		m_btnRefresh.addActionListener(listener);
		m_btnShowGraph.addActionListener(listener);
		m_btnResetGraph.addActionListener(listener);
	}
	
	public void addTreeSelectionListener(TreeSelectionListener listener) {
		m_portTree.addTreeSelectionListener(listener);
		m_flowTree.addTreeSelectionListener(listener);
		m_linkTree.addTreeSelectionListener(listener);
	}
	
	public void addChangeListener(ChangeListener listener) {
		m_topologyTabbedPane.addChangeListener(listener);
	}
	
	public void addTreeMouseListener(MouseListener listener) {
		m_portTree.addMouseListener(listener);
		m_flowTree.addMouseListener(listener);
		m_linkTree.addMouseListener(listener);
	}
	
	public JTree getTree(int treeTab) {
		JTree tree = null;
		switch (treeTab) {
		case PORT_TAB:
			tree = m_portTree;
			break;
		case FLOW_TAB:
			tree = m_flowTree;
			break;
		case LINK_TAB:
			tree = m_linkTree;
			break;
		}
		return tree;
	}
	
	public void setDetailsText(String treeName, String text) {
		switch (treeName) {
		case PORT_TREE:
			m_portDetailsPanel.setText(text);
			break;
		case FLOW_TREE:
			m_flowDetailsPanel.setText(text);
			break;
		case LINK_TREE:
			m_linkDetailsPanel.setText(text);
			break;
		}
	}
	
	public void setDetailsList(String treeName, String[][] detailsKeyValueList) {
		switch (treeName) {
		case PORT_TREE:
			m_portDetailsPanel.setList(detailsKeyValueList);
			break;
		case FLOW_TREE:
			m_flowDetailsPanel.setList(detailsKeyValueList);
			break;
		case LINK_TREE:
			m_linkDetailsPanel.setList(detailsKeyValueList);
			break;
		}
	}
	
	public void resetDetailsText() {
		m_portDetailsPanel.resetText();
		m_flowDetailsPanel.resetText();
		m_linkDetailsPanel.resetText();
	}
	
}
