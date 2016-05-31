package de.tuberlin.cit.softmon.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import de.tuberlin.cit.softmon.controller.Config;
import de.tuberlin.cit.softmon.rest.floodlight.FloodlightClient;

@SuppressWarnings("serial")
public class RestConnectorPanel extends JPanel {

	public static final String APPLY_BUTTON_TITLE = "Apply";

	private JTextField m_txtHostname;
	private JTextField m_txtBaseUrl;
	private JTextField m_txtPort;

	private JButton m_btnApply;
	private JTextField txtUsername;
	private JPasswordField passwordField;

	public RestConnectorPanel() {
		initGui();
	}

	private void initGui() {

		// set panel border
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Controller REST Connector",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		// configure panel layout
		GridBagLayout gbl_restConnectorPanel = new GridBagLayout();
		gbl_restConnectorPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_restConnectorPanel.rowHeights = new int[] { 0, 0 };
		gbl_restConnectorPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_restConnectorPanel.rowWeights = new double[] { 0.0, 0.0 };
		setLayout(gbl_restConnectorPanel);

		// add components
		// first row
		JLabel lblCtrlType = new JLabel("Type:");
		GridBagConstraints gbc_lblCtrlType = new GridBagConstraints();
		gbc_lblCtrlType.anchor = GridBagConstraints.EAST;
		gbc_lblCtrlType.insets = new Insets(0, 5, 5, 5);
		gbc_lblCtrlType.gridx = 0;
		gbc_lblCtrlType.gridy = 0;
		add(lblCtrlType, gbc_lblCtrlType);

		JComboBox<String> cbxControllerType = new JComboBox<>();
		GridBagConstraints gbc_cbxControllerType = new GridBagConstraints();
		gbc_cbxControllerType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbxControllerType.gridwidth = 2;
		gbc_cbxControllerType.insets = new Insets(0, 0, 5, 5);
		gbc_cbxControllerType.gridx = 1;
		gbc_cbxControllerType.gridy = 0;
		add(cbxControllerType, gbc_cbxControllerType);
		cbxControllerType.setMinimumSize(new Dimension(140, 22));
		cbxControllerType.setPreferredSize(new Dimension(140, 22));
		cbxControllerType.setModel(new DefaultComboBoxModel<String>(new String[] { FloodlightClient.CLIENT_TYPE + " " + FloodlightClient.CLIENT_VERSION + " (" + FloodlightClient.OF_VERSION + ")" }));

		JLabel lblHostname = new JLabel("Host:");
		GridBagConstraints gbc_lblHostname = new GridBagConstraints();
		gbc_lblHostname.anchor = GridBagConstraints.EAST;
		gbc_lblHostname.insets = new Insets(0, 0, 5, 5);
		gbc_lblHostname.gridx = 3;
		gbc_lblHostname.gridy = 0;
		add(lblHostname, gbc_lblHostname);

		m_txtHostname = new JTextField();
		GridBagConstraints gbc_txtHostname = new GridBagConstraints();
		gbc_txtHostname.gridwidth = 2;
		gbc_txtHostname.anchor = GridBagConstraints.WEST;
		gbc_txtHostname.insets = new Insets(0, 0, 5, 5);
		gbc_txtHostname.gridx = 4;
		gbc_txtHostname.gridy = 0;
		add(m_txtHostname, gbc_txtHostname);
		m_txtHostname.setText(Config.REST_HOSTNAME);
		m_txtHostname.setColumns(12);

		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 6;
		gbc_lblPort.gridy = 0;
		add(lblPort, gbc_lblPort);

		m_txtPort = new JTextField();
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPort.gridx = 7;
		gbc_txtPort.gridy = 0;
		add(m_txtPort, gbc_txtPort);
		m_txtPort.setText(FloodlightClient.DEFAULT_PORT);
		m_txtPort.setColumns(4);

		JLabel lblBaseUrl = new JLabel("URI:");
		GridBagConstraints gbc_lblBaseUrl = new GridBagConstraints();
		gbc_lblBaseUrl.anchor = GridBagConstraints.EAST;
		gbc_lblBaseUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblBaseUrl.gridx = 8;
		gbc_lblBaseUrl.gridy = 0;
		add(lblBaseUrl, gbc_lblBaseUrl);

		m_txtBaseUrl = new JTextField();
		GridBagConstraints gbc_txtBaseUrl = new GridBagConstraints();
		gbc_txtBaseUrl.insets = new Insets(0, 0, 5, 5);
		gbc_txtBaseUrl.gridx = 9;
		gbc_txtBaseUrl.gridy = 0;
		add(m_txtBaseUrl, gbc_txtBaseUrl);
		m_txtBaseUrl.setText(FloodlightClient.DEFAULT_BASE_URL);
		m_txtBaseUrl.setColumns(12);

		// second row
		JLabel lblUsername = new JLabel("User:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 1;
		add(lblUsername, gbc_lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setEnabled(false);
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(0, 0, 0, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 1;
		add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(12);
		
		JLabel lblPassword = new JLabel("Passwd:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblPassword.gridx = 3;
		gbc_lblPassword.gridy = 1;
		add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		passwordField.setColumns(12);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 0, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 4;
		gbc_passwordField.gridy = 1;
		add(passwordField, gbc_passwordField);
		
		m_btnApply = new JButton(APPLY_BUTTON_TITLE);
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConnect.gridx = 9;
		gbc_btnConnect.gridy = 1;
		add(m_btnApply, gbc_btnConnect);
		m_btnApply.setPreferredSize(new Dimension(90, 23));


	}

	public String getHostname() {
		return m_txtHostname.getText();
	}

	public void setHostname(String hostname) {
		this.m_txtHostname.setText(hostname);
	}

	public String getBaseUrl() {
		return m_txtBaseUrl.getText();
	}

	public void setBaseUrl(String baseUrl) {
		this.m_txtBaseUrl.setText(baseUrl);
	}

	public String getPort() {
		return m_txtPort.getText();
	}

	public void setPort(String port) {
		this.m_txtPort.setText(port);
	}

	public void addButtonListener(ActionListener listener) {
		m_btnApply.addActionListener(listener);
	}
}
