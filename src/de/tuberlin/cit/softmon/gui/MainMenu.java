package de.tuberlin.cit.softmon.gui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainMenu extends JMenuBar{

	public static final String MENU_ABOUT = "About...";
	public static final String MENU_EXIT = "Exit";
	
	private JMenuItem m_mnItemExit;
	private JMenuItem m_mnItemAbout;

	public MainMenu () {
		initMenu();
	}
	
	private void initMenu() {
		JMenu mnFile = new JMenu("File");
		add(mnFile);
		
		//JMenuItem mntmOpenConfig = new JMenuItem("Open Config...");
		//mnFile.add(mntmOpenConfig);
		
		//JMenuItem mntmSaveConfig = new JMenuItem("Save Config...");
		//mnFile.add(mntmSaveConfig);
		
		//JSeparator separator = new JSeparator();
		//mnFile.add(separator);
		
		m_mnItemExit = new JMenuItem(MENU_EXIT);
		mnFile.add(m_mnItemExit);
		
		//JMenu mnEdit = new JMenu("Edit");
		//menuBar.add(mnEdit);
		
		//JMenu mnView = new JMenu("View");
		//menuBar.add(mnView);
		
		JMenu mnHelp = new JMenu("Help");
		add(mnHelp);
		
		m_mnItemAbout = new JMenuItem(MENU_ABOUT);
		mnHelp.add(m_mnItemAbout);
	}

	public void addMainMenuListener(ActionListener listener) {
		m_mnItemExit.addActionListener(listener);
		m_mnItemAbout.addActionListener(listener);
	}
}
