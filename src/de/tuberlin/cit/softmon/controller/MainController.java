package de.tuberlin.cit.softmon.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.tuberlin.cit.softmon.chart.ADataSource;
import de.tuberlin.cit.softmon.chart.FlowDataSource;
import de.tuberlin.cit.softmon.chart.PortDataSource;
import de.tuberlin.cit.softmon.chart.SwitchDataSource;
import de.tuberlin.cit.softmon.gui.AboutPanel;
import de.tuberlin.cit.softmon.gui.GraphOptionsPanel;
import de.tuberlin.cit.softmon.gui.GraphPanel;
import de.tuberlin.cit.softmon.gui.MainMenu;
import de.tuberlin.cit.softmon.gui.MainWindow;
import de.tuberlin.cit.softmon.gui.RestConnectorPanel;
import de.tuberlin.cit.softmon.gui.TopologyPanel;
import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfLink;
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfTopology;
import de.tuberlin.cit.softmon.rest.FloodlightRestConnector;
import de.tuberlin.cit.softmon.util.NetworkUtils;

public class MainController {

	private OfTopology m_topology; // corresponds to model (MVC)

	private MainWindow m_window; // corresponds to view (MVC)

	private FloodlightRestConnector m_restConnector;
	// private ARestConnector m_restConnector;

	private String m_selectedTreeName;
	private TreePath m_selectedTreePath;
	private Object m_selectedTreeNodeObject;
	private String m_selectedTreeNodeType;
	private String m_selectedTreeNodeParentSwitch;

	ADataSource m_dataSource;

	public MainController() {
		// create view (MVC)
		m_window = new MainWindow();
		addListeners();

		statusMessage("Application started.");
	}

	public void showView() {
		// show GUI
		m_window.setVisible(true);
	}

	// add listeners to view (MVC)
	private void addListeners() {
		m_window.addWindowListener(new MainWindowListener());
		m_window.getMainMenu().addMainMenuListener(new MainMenuListener());
		m_window.getRestConnectorPanel().addButtonListener(new RestConnectorPanelListener());
		m_window.getTopologyPanel().addButtonListener(new TopologyPanelListener());
		m_window.getTopologyPanel().addTreeSelectionListener(new TopologyTreeSelectionListener());
		m_window.getTopologyPanel().addChangeListener(new TopologyPanelChangeListener());
		// m_window.getTopologyPanel().addTreeMouseListener(new TopologyTreeMouseListener()); // for future use
		m_window.getGraphOptionsPanel().addSpinnerListener(new GraphPanelSpinnerListener());

		// dev only
		// m_window.getGraphPanel().addDevListener(new GraphPanelDevListener());
	}

	// application exit procedure
	private void exit() {
		int result = 0;
		if (Config.CONFIRM_EXIT)
			result = JOptionPane.showOptionDialog(m_window, "Are you sure?", "Confirm Exit",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (result == 0) {
			System.out.println("Application terminated properly.");
			m_window.dispose();
			System.exit(0);
		}
	}

	// initialize rest connector
	private boolean initRest(String hostname, String port, String baseUrl) {
		if (NetworkUtils.isReachable(hostname, Integer.parseInt(port), Config.NETWORK_TIMEOUT)) {
			m_restConnector = new FloodlightRestConnector(hostname, port, baseUrl);
			return true;
		} else {
			return false;
		}
	}

	// update topology from rest connector
	private void updateTopology() {
		m_topology = m_restConnector.getTopology();
	}

	// update selected tree path, type and user object
	private void updateTreeSelection(JTree tree, TreePath selectedTreePath) {
		// check if an item is selected in the current tree
		if (selectedTreePath == null) {
			// if nothing is selected
			m_selectedTreeNodeType = null;
		} else {
			// get selected element, tree name and element type
			DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) selectedTreePath.getLastPathComponent();
			String treeName = tree.getName();
			m_selectedTreeNodeType = TreeUtils.getNodeType(selectedTreeNode, treeName);

			// find and set associated switch (of selected port or flow)
			if (m_selectedTreeNodeType.equals(TreeUtils.NODE_TYPE_PORT)
					|| m_selectedTreeNodeType.equals(TreeUtils.NODE_TYPE_FLOW)) {
				m_selectedTreeNodeParentSwitch = selectedTreeNode.getParent().toString();
			} else {
				// links do not have an associated switch
				m_selectedTreeNodeParentSwitch = TreeUtils.NODE_TYPE_NONE;
			}
			// update (visibly) selected tree field
			switch (treeName) {
			case TopologyPanel.PORT_TREE:
				m_selectedTreeName = TopologyPanel.PORT_TREE;
				break;
			case TopologyPanel.FLOW_TREE:
				m_selectedTreeName = TopologyPanel.FLOW_TREE;
				break;
			case TopologyPanel.LINK_TREE:
				m_selectedTreeName = TopologyPanel.LINK_TREE;
				break;
			}
			// update selected object fields
			m_selectedTreePath = selectedTreePath;
			m_selectedTreeNodeObject = selectedTreeNode.getUserObject();
		}
	}

	// show status message in status line
	public void statusMessage(String message) {
		m_window.setStatusMessage(message);
	}

	// show error message in status line
	public void errorMessage(String message) {
		m_window.setErrorMessage(message);
	}

	// nested listener classes

	// main window closing action is defined here
	class MainWindowListener extends WindowAdapter implements WindowListener {
		@Override
		public void windowClosing(WindowEvent e) {
			exit();
		}
	}

	// MainMenu actions are defined here
	class MainMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			// System.out.println("MainMenuListener: ActionEvent = " + command);
			switch (command) {
			case MainMenu.MENU_EXIT:
				exit();
				break;
			case MainMenu.MENU_ABOUT:
				JOptionPane.showMessageDialog(m_window, new AboutPanel(), "About", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}

	// RestConnectorPanel actions are defined here
	class RestConnectorPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch (command) {
			case RestConnectorPanel.APPLY_BUTTON_TITLE:
				String hostname = m_window.getRestConnectorPanel().getHostname();
				String port = m_window.getRestConnectorPanel().getPort();
				String baseUrl = m_window.getRestConnectorPanel().getBaseUrl();
				boolean success = initRest(hostname, port, baseUrl);
				if (success) {
					statusMessage("REST-Connection initialized: \"http://" + hostname + ":" + port + baseUrl + "\".");
					updateTopology();
					m_window.getTopologyPanel().refreshTopology(m_topology);
					m_selectedTreeNodeType = null; // reset selection
					m_window.getTopologyPanel().resetDetailsText();
				} else
					errorMessage("ERROR: Host is not reachable: \"http://" + hostname + ":" + port + "\".");
				break;
			}
		}
	}

	// TopologyPanel actions are defined here
	class TopologyPanelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			switch (command) {

			// actions of "Refresh" button
			case TopologyPanel.REFRESH_BUTTON_TITLE:
				if (m_restConnector == null) {
					statusMessage("ERROR: No REST-Connection specified.");
				} else {
					statusMessage("Updating topology...");
					updateTopology();
					m_window.getTopologyPanel().refreshTopology(m_topology);
					statusMessage("Topology update completed.");
					m_selectedTreeNodeType = null; // reset selection
					m_window.getTopologyPanel().resetDetailsText();
				}
				break;

			// actions of "Show" button
			case TopologyPanel.SHOW_BUTTON_TITLE:
				//System.out.println("Button \"" + TopologyPanel.SHOW_BUTTON_TITLE + "\" pressed.");
				int cycleTime = m_window.getGraphOptionsPanel().getCycleTime();
				if (!(m_selectedTreeNodeType == null)) {
					// status message
					if (m_selectedTreeNodeType.equals(TreeUtils.NODE_TYPE_SWITCH) || m_selectedTreeNodeType.equals(TreeUtils.NODE_TYPE_LINK)) {
						statusMessage("Traffic Monitoring of " + m_selectedTreeNodeType + " " + m_selectedTreeNodeObject
								+ " started.");
					} else {
						statusMessage("Traffic Monitoring of " + m_selectedTreeNodeType + " " + m_selectedTreeNodeObject
								+ " at Switch " + m_selectedTreeNodeParentSwitch + " started.");
					}

					switch (m_selectedTreeNodeType) {

					case TreeUtils.NODE_TYPE_SWITCH:
						OfSwitch ofSwitch = (OfSwitch) m_selectedTreeNodeObject;

						// set switch view on graph panel
						m_window.getGraphPanel().showPanel(GraphPanel.CARD_SWITCH);
						m_window.getGraphPanel().getSwitchCard().setTitle(ofSwitch);

						// check if data source switch has changed
						boolean dataSourceSwitchHasChanged = true;
						if (m_dataSource != null) {
							if (m_dataSource.getClass().equals(SwitchDataSource.class)) {
								OfSwitch dataSourceSwitch = (OfSwitch) m_dataSource.getDataSourceItem();
								if (dataSourceSwitch.getSwitchDpid().equalsIgnoreCase(ofSwitch.getSwitchDpid()))
									dataSourceSwitchHasChanged = false;
							}
						}

						if (dataSourceSwitchHasChanged) {
							if (m_dataSource != null)
								m_dataSource.stop();
							m_dataSource = new SwitchDataSource(cycleTime, m_restConnector, ofSwitch);

							// connect data source to counter panel
							m_dataSource.addCounterDestination(m_window.getGraphPanel().getSwitchCard());

							// connect data source to charts
							m_dataSource.addMetricsDestination(m_window.getGraphPanel().getSwitchCard());

							// start getting and calculating metrics
							m_window.getGraphPanel().getSwitchCard().initCharts();
							m_dataSource.start();
						}
						break;

					case TreeUtils.NODE_TYPE_PORT:
						OfPort ofPort = (OfPort) m_selectedTreeNodeObject;

						// set port view on graph panel
						m_window.getGraphPanel().showPanel(GraphPanel.CARD_PORT);
						m_window.getGraphPanel().getPortCard().setTitle(ofPort);

						// check if data source port has changed
						boolean dataSourcePortHasChanged = true;
						if (m_dataSource != null) {
							if (m_dataSource.getClass().equals(PortDataSource.class)) {
								OfPort dataSourcePort = (OfPort) m_dataSource.getDataSourceItem();
								if (dataSourcePort.getSwitchDpid().equalsIgnoreCase(ofPort.getSwitchDpid())
										&& dataSourcePort.getPortNumber().equalsIgnoreCase(ofPort.getPortNumber()))
									dataSourcePortHasChanged = false;
							}
						}

						if (dataSourcePortHasChanged) {
							if (m_dataSource != null)
								m_dataSource.stop();
							m_dataSource = new PortDataSource(cycleTime, m_restConnector, ofPort);

							// connect data source to counter panel
							m_dataSource.addCounterDestination(m_window.getGraphPanel().getPortCard());

							// connect data source to charts
							m_dataSource.addMetricsDestination(m_window.getGraphPanel().getPortCard());

							// start getting and calculating metrics
							m_window.getGraphPanel().getPortCard().initCharts();
							m_dataSource.start();
						}
						break;

					case TreeUtils.NODE_TYPE_FLOW:
						OfFlow ofFlow = (OfFlow) m_selectedTreeNodeObject;
						//System.out.println("selected flow: " + ofFlow);
						
						// set flow view on graph panel
						m_window.getGraphPanel().showPanel(GraphPanel.CARD_FLOW);
						m_window.getGraphPanel().getFlowCard().setTitle(ofFlow);

						// check if data source flow has changed
						boolean dataSourceFlowHasChanged = true;
						if (m_dataSource != null) {
							if (m_dataSource.getClass().equals(FlowDataSource.class)) {
								OfFlow dataSourceFlow = (OfFlow) m_dataSource.getDataSourceItem();
								if (dataSourceFlow.getSwitchDpid().equalsIgnoreCase(ofFlow.getSwitchDpid())
										&& dataSourceFlow.toString().equalsIgnoreCase(ofFlow.toString()))
									dataSourceFlowHasChanged = false;
							}
						}

						if (dataSourceFlowHasChanged) {
							if (m_dataSource != null)
								m_dataSource.stop();
							m_dataSource = new FlowDataSource(cycleTime, m_restConnector, ofFlow);
							
							// detect minimum current speed which is different from 0
							String switchDpid = ofFlow.getSwitchDpid();

							long inSpeed = 0;
							OfPort inPort = null;
							if (ofFlow.getInPort() != 0) {
								inPort = m_topology.getPort(switchDpid, ofFlow.getInPort());
								inSpeed = inPort.getCurrSpeedBitsPerSec();
							}
							
							long outSpeed = 0;
							OfPort outPort = null;;
							if (ofFlow.getOutPort() != 0) {
								outPort = m_topology.getPort(switchDpid, ofFlow.getOutPort());
								outSpeed = outPort.getCurrSpeedBitsPerSec();
							}
							
							long minSpeed; 
							if ((inSpeed != 0) && (outSpeed != 0))
								minSpeed = Math.min(inSpeed, outSpeed);
							else
								minSpeed = Math.max(inSpeed, outSpeed);
							
							// set current speed
							m_dataSource.setCurrentPortSpeed(minSpeed);
							
							// connect data source to counter panel
							m_dataSource.addCounterDestination(m_window.getGraphPanel().getFlowCard());

							// connect data source to charts
							m_dataSource.addMetricsDestination(m_window.getGraphPanel().getFlowCard());

							// start getting and calculating metrics
							m_window.getGraphPanel().getFlowCard().initCharts();
							m_dataSource.start();
						}
						break;

					case TreeUtils.NODE_TYPE_LINK:
						OfLink link = (OfLink) m_selectedTreeNodeObject;

						// get corresponding switch port
						OfSwitch srcSwitch = m_topology.getSwitch(link.getSrcDpid());
						OfPort srcPort = srcSwitch.getPort(link.getSrcPort());

						// set port view on graph panel
						m_window.getGraphPanel().showPanel(GraphPanel.CARD_PORT);
						m_window.getGraphPanel().getPortCard().setTitle(srcPort);

						// check if data source link (port) has changed
						boolean dataSourceLinkHasChanged = true;
						if (m_dataSource != null) {
							if (m_dataSource.getClass().equals(PortDataSource.class)) {
								OfPort dataSourcePort = (OfPort) m_dataSource.getDataSourceItem();
								if (dataSourcePort.getSwitchDpid().equalsIgnoreCase(srcPort.getSwitchDpid())
										&& dataSourcePort.getPortNumber().equalsIgnoreCase(srcPort.getPortNumber()))
									dataSourceLinkHasChanged = false;
							}
						}

						if (dataSourceLinkHasChanged) {
							if (m_dataSource != null)
								m_dataSource.stop();
							m_dataSource = new PortDataSource(cycleTime, m_restConnector, srcPort);

							// connect data source to counter panel
							m_dataSource.addCounterDestination(m_window.getGraphPanel().getPortCard());

							// connect data source to charts
							m_dataSource.addMetricsDestination(m_window.getGraphPanel().getPortCard());

							// start getting and calculating metrics
							m_window.getGraphPanel().getPortCard().initCharts();
							m_dataSource.start();
						}
						break;
					}
				} else {
					// do nothing
					errorMessage("ERROR: No item selected.");
				}
				break;

			// actions of "Clear" Button
			case TopologyPanel.RESET_BUTTON_TITLE:
				System.out.println("Button \"" + TopologyPanel.RESET_BUTTON_TITLE + "\" pressed.");
				if (m_dataSource != null)
					m_dataSource.stop();
				m_dataSource = null;
				// reset all counter panels oder switch case tree node type?
				m_window.getGraphPanel().getSwitchCard().resetTitle();
				m_window.getGraphPanel().getSwitchCard().getCounterPanel().resetCounterList();

				m_window.getGraphPanel().getPortCard().resetTitle();
				m_window.getGraphPanel().getPortCard().getCounterPanel().resetCounterList();

				m_window.getGraphPanel().getFlowCard().resetTitle();
				m_window.getGraphPanel().getFlowCard().getCounterPanel().resetCounterList();

				m_window.getGraphPanel().showPanel(GraphPanel.CARD_NO_SELECTION);
				statusMessage("Traffic Monitoring stopped."); 
				// selected tree node type isn't necessarily the type of the chart currently displayed!
				break;
			}
		}
	}

	// TopologyPanel tree selection actions are defined here
	class TopologyTreeSelectionListener implements TreeSelectionListener {
		@Override
		public void valueChanged(final TreeSelectionEvent event) {
			// inhibit selection of invalid nodes: 
			// this may affect selectedTreePath
			TreeUtils.dismissInvalidSelection(event);

			TreePath selectedTreePath = event.getPath();
			String treeName = ((JTree) event.getSource()).getName();
			DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) selectedTreePath.getLastPathComponent();
			Object treeObject = selectedTreeNode.getUserObject();

			// components for check if selection has changed
			boolean selectedTreePathIsDifferentFromPreviousTreePath = !selectedTreePath.equals(m_selectedTreePath);
			boolean selectedTreeIsDifferentFromPreviousTree = !treeName.toString().equals(m_selectedTreeName);
			boolean selectedNodeIsValid = TreeUtils.isValid(selectedTreeNode);
			boolean selectedNodeIsNotRoot = !selectedTreeNode.isRoot();

			boolean selectedNodeHasChanged = (selectedTreePathIsDifferentFromPreviousTreePath
					|| selectedTreeIsDifferentFromPreviousTree) && selectedNodeIsValid && selectedNodeIsNotRoot;

			if (selectedNodeHasChanged) {
				updateTreeSelection(((JTree) event.getSource()), selectedTreePath);

				// tree selection change actions are defined here
				switch (m_selectedTreeNodeType) {
				case TreeUtils.NODE_TYPE_SWITCH:
					m_window.getTopologyPanel().setDetailsList(m_selectedTreeName,
							PresentationUtils.createDetailsList((OfSwitch) treeObject));
					break;
				case TreeUtils.NODE_TYPE_PORT:
					m_window.getTopologyPanel().setDetailsList(m_selectedTreeName,
							PresentationUtils.createDetailsList((OfPort) treeObject));
					break;
				case TreeUtils.NODE_TYPE_FLOW:
					m_window.getTopologyPanel().setDetailsList(m_selectedTreeName,
							PresentationUtils.createDetailsList((OfFlow) treeObject));
					break;
				case TreeUtils.NODE_TYPE_LINK:
					m_window.getTopologyPanel().setDetailsList(m_selectedTreeName,
							PresentationUtils.createDetailsList((OfLink) treeObject));
					break;
				}
			}
		}
	}

	// update tree selection if Tree Tab on TopologyPanel is changed
	class TopologyPanelChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JTabbedPane selectedPane = (JTabbedPane) e.getSource();
			JTree tree = m_window.getTopologyPanel().getTree(selectedPane.getSelectedIndex());
			TreePath selectedTreePath = tree.getLeadSelectionPath();

			updateTreeSelection(tree, selectedTreePath);

			System.out.println("TopologyPanelChangeListener: selectedPane = " + selectedPane.getSelectedIndex()
					+ ", selectedTreePath = " + selectedTreePath);
		}
	}

	// for future use
	/*
	 * class TopologyTreeMouseListener extends MouseAdapter implements
	 * MouseListener {
	 * 
	 * @Override public void mousePressed(MouseEvent e) { System.out.println(
	 * "MouseEvent.getSource() = " + e.getSource()); if(e.getClickCount() == 1)
	 * { System.out.println("Single Klick!"); } else if(e.getClickCount() == 2)
	 * { System.out.println("Double Klick!"); } } }
	 */

	// GraphicPanel spinner actions are defined here
	class GraphPanelSpinnerListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			String sourceName = ((Component) e.getSource()).getName();
			System.out.println("ChangeEvent: sourceName = " + sourceName);
			switch (sourceName) {
			case GraphOptionsPanel.SPINNER_CYCLE_TIME:
				// System.out.println("CycleTime = " +
				// m_window.getGraphOptionsPanel().getCycleTime());
				int cycleTime = m_window.getGraphOptionsPanel().getCycleTime();
				if (m_dataSource != null)
					m_dataSource.setDelay(cycleTime);

				statusMessage("Cycle Time changed to " + cycleTime + " ms.");
				break;
			case GraphOptionsPanel.SPINNER_VALUE_COUNT:
				int valueCount = m_window.getGraphOptionsPanel().getValueCount();
				System.out.println("ValueCount = " + valueCount);
				m_window.getGraphPanel().getFlowCard().setValueCount(valueCount);
				m_window.getGraphPanel().getPortCard().setValueCount(valueCount);
				m_window.getGraphPanel().getSwitchCard().setValueCount(valueCount);

				statusMessage("Value Count changed to " + valueCount + ".");
				break;
			}
		}
	}

	/*
	 * // Dev only class GraphPanelDevListener implements ItemListener { public
	 * void itemStateChanged(ItemEvent itemEvent) {
	 * m_window.getGraphPanel().showPanel(((String) itemEvent.getItem())); } }
	 */

}
