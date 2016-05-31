package de.tuberlin.cit.softmon.gui;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.UIManager;

import de.tuberlin.cit.softmon.controller.Config;

@SuppressWarnings("serial")
public class AboutPanel extends JPanel {
	private JTextPane txtpnName;
	private JTextPane txtpnAuthor;

	/**
	 * Create the panel.
	 */
	public AboutPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(getTxtpnSoftmon());
		add(getTxtpnAuthorMarcHartung());

	}

	private JTextPane getTxtpnSoftmon() {
		if (txtpnName == null) {
			txtpnName = new JTextPane();
			txtpnName.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtpnName.setEditable(false);
			txtpnName.setBackground(UIManager.getColor("Button.background"));
			txtpnName.setText("SOFTmon\r\nSimple OpenFlow Traffic Monitor");
		}
		return txtpnName;
	}
	private JTextPane getTxtpnAuthorMarcHartung() {
		if (txtpnAuthor == null) {
			txtpnAuthor = new JTextPane();
			txtpnAuthor.setEditable(false);
			txtpnAuthor.setBackground(UIManager.getColor("Button.background"));
			txtpnAuthor.setText(" (Version: " + Config.VERSION_INFO + " - Build: " + Config.BUILD_INFO + ")\r\n\r\nAuthor: Marc Hartung\r\nmarc.hartung@studium.fernuni-hagen.de\r\n");
		}
		return txtpnAuthor;
	}
}
