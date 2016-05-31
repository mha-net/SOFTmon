package de.tuberlin.cit.softmon.controller;

import java.awt.EventQueue;

public class Main {
	static MainController m_controller;
	
	public static void main(String[] args) {

		// run controller in EventQueue 
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					m_controller = new MainController();
					m_controller.showView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
