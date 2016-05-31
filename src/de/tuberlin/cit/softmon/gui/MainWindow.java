package de.tuberlin.cit.softmon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import de.tuberlin.cit.softmon.controller.Config;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	// window configuration
	/*
	private static final String WINDOW_TITLE = "SOFTmon - Simple Open Flow Traffic Monitor";
	private static final int[] WINDOW_MIN_DIM = {1200, 800};		// minimum size of MainWindow (with, height)
	private static final int[] WINDOW_POSITION = {100, 100};	// initial position of MainWindow upper left (x, y) 
	private static final boolean WINDOW_CENTERED = false;		// center window on screen
	private static final boolean WINDOW_CENTERED_TOP = true;	// center window horizontally
	*/
	
	public static final String PLAF_WINDOWS = "PLAF.Windows";
	public static final String PLAF_NIMBUS = "PLAF.Nimbus";
	
	private JPanel m_contentPane;
	
	private MainMenu m_mainMenu;

	private RestConnectorPanel m_restConnectorPanel;
	private GraphOptionsPanel m_graphOptionsPanel;
	
	private TopologyPanel m_topologyPanel;
	private GraphPanel m_graphPanel;
	
	private JLabel m_lblStatusLine;
	
	//private OfTopology m_topology;
	
	public MainWindow() {
		setPLAF(Config.LOOK_AND_FEEL);
		initGui();
		
		// Set window icon
		URL imageURL = MainWindow.class.getResource("ico/SOFTmon.png");
		ImageIcon icon = new ImageIcon(imageURL);
		this.setIconImage(icon.getImage());
	}

	private void initGui() {
		// Configure Application Window
		setTitle(Config.WINDOW_TITLE + " (Version: " + Config.VERSION_INFO + " - Build: " + Config.BUILD_INFO + ")");
		setBounds(Config.WINDOW_POSITION[0], Config.WINDOW_POSITION[1], Config.WINDOW_MIN_DIM[0], Config.WINDOW_MIN_DIM[1]);
		setMinimumSize(new Dimension(Config.WINDOW_MIN_DIM[0], Config.WINDOW_MIN_DIM[1]));
		final Dimension d = this.getToolkit().getScreenSize();
	    if (Config.WINDOW_CENTERED_TOP) this.setLocation((int) ((d.getWidth() - this.getWidth()) / 2), 0);
		if (Config.WINDOW_CENTERED) this.setLocationRelativeTo(null);
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// Add Content Pane
		m_contentPane = new JPanel();
		m_contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		m_contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
		//m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_contentPane);
		m_contentPane.setLayout(new BorderLayout(0, 0));

		// Add GUI Components
		m_mainMenu = new MainMenu();
		setJMenuBar(m_mainMenu);

		// control panel (contains REST connector panel, graph panel) 
		JPanel controlPanel = createControlPanel();
		m_contentPane.add(controlPanel, BorderLayout.NORTH);
		
		m_contentPane.add(createStatusPanel(), BorderLayout.SOUTH);
		
		m_topologyPanel = new TopologyPanel();
		m_graphPanel = new GraphPanel();
		m_contentPane.add(createMainSplitPane(m_topologyPanel, m_graphPanel), BorderLayout.CENTER);
	}
	
	private void setPLAF(String plaf) {
		String plafString = "";
		
		switch (plaf) {
		case PLAF_WINDOWS:
			plafString = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			break;
		case PLAF_NIMBUS:
			plafString = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
			setSystemFonts(new Font("Tahoma", Font.PLAIN, 11));
			break;
		}
		
		try {
			UIManager.setLookAndFeel(plafString);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void setSystemFonts(Font defaultFont) {
		UIManager.put("Button.font", defaultFont);
		UIManager.put("ToggleButton.font", defaultFont);
		UIManager.put("RadioButton.font", defaultFont);
		UIManager.put("CheckBox.font", defaultFont);
		UIManager.put("ColorChooser.font", defaultFont);
		UIManager.put("ComboBox.font", defaultFont);
		UIManager.put("Label.font", defaultFont);
		UIManager.put("List.font", defaultFont);
		UIManager.put("MenuBar.font", defaultFont);
		UIManager.put("MenuItem.font", defaultFont);
		UIManager.put("RadioButtonMenuItem.font", defaultFont);
		UIManager.put("CheckBoxMenuItem.font", defaultFont);
		UIManager.put("Menu.font", defaultFont);
		UIManager.put("PopupMenu.font", defaultFont);
		UIManager.put("OptionPane.font", defaultFont);
		UIManager.put("Panel.font", defaultFont);
		UIManager.put("ProgressBar.font", defaultFont);
		UIManager.put("ScrollPane.font", defaultFont);
		UIManager.put("Viewport.font", defaultFont);
		UIManager.put("TabbedPane.font", defaultFont);
		UIManager.put("Table.font", defaultFont);
		UIManager.put("TableHeader.font", defaultFont);
		UIManager.put("TextField.font", defaultFont);
		UIManager.put("PasswordField.font", defaultFont);
		UIManager.put("TextArea.font", defaultFont);
		UIManager.put("TextPane.font", defaultFont);
		UIManager.put("EditorPane.font", defaultFont);
		UIManager.put("TitledBorder.font", defaultFont);
		UIManager.put("ToolBar.font", defaultFont);
		UIManager.put("ToolTip.font", defaultFont);
		UIManager.put("Tree.font", defaultFont);
		
		// To Do: add additional fonts?
		// Spinner
		// ComboBox
	}
	
	private JPanel createControlPanel() {
		// control panel (contains REST connector panel, graph panel) 
		JPanel controlPanel = new JPanel();
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[]{391, 0, 0};
		gbl_controlPanel.rowHeights = new int[]{82, 0};
		gbl_controlPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_controlPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		controlPanel.setLayout(gbl_controlPanel);
		
		m_restConnectorPanel = new RestConnectorPanel();
		GridBagConstraints gbc_m_restConnectorPanel = new GridBagConstraints();
		gbc_m_restConnectorPanel.insets = new Insets(0, 0, 0, 5);
		gbc_m_restConnectorPanel.fill = GridBagConstraints.BOTH;
		gbc_m_restConnectorPanel.gridx = 0;
		gbc_m_restConnectorPanel.gridy = 0;
		controlPanel.add(m_restConnectorPanel, gbc_m_restConnectorPanel);
		
		m_graphOptionsPanel = new GraphOptionsPanel();
		GridBagConstraints gbc_graphOptionsPanel = new GridBagConstraints();
		gbc_graphOptionsPanel.fill = GridBagConstraints.BOTH;
		gbc_graphOptionsPanel.gridx = 1;
		gbc_graphOptionsPanel.gridy = 0;
		controlPanel.add(m_graphOptionsPanel, gbc_graphOptionsPanel);
		return controlPanel;
	}
	
	private JPanel createStatusPanel() {
		JPanel statusLinePanel = new JPanel();
		statusLinePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		statusLinePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		
		m_lblStatusLine = new JLabel("Status Line");
		statusLinePanel.add(m_lblStatusLine);

		return statusLinePanel;
	}
	
	private JSplitPane createMainSplitPane(JPanel topologyPanel, JPanel graphPanel) {
		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setResizeWeight(0.1);
		
		mainSplitPane.setLeftComponent(topologyPanel);
		mainSplitPane.setRightComponent(graphPanel);
		
		return mainSplitPane;
	}

	public void setStatusMessage(String message) {
		m_lblStatusLine.setForeground(Color.BLACK);
		m_lblStatusLine.setText(message);
	}

	public void setErrorMessage(String message) {
		m_lblStatusLine.setForeground(Color.RED);
		m_lblStatusLine.setText(message);
	}

	public MainMenu getMainMenu() {
		return m_mainMenu;
	}

	public RestConnectorPanel getRestConnectorPanel() {
		return m_restConnectorPanel;
	}
	
	public GraphOptionsPanel getGraphOptionsPanel() {
		return m_graphOptionsPanel;
	}

	public TopologyPanel getTopologyPanel() {
		return m_topologyPanel;
	}
	
	public GraphPanel getGraphPanel() {
		return m_graphPanel;
	}
	
}
