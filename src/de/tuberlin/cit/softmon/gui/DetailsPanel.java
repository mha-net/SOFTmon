package de.tuberlin.cit.softmon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import de.tuberlin.cit.softmon.controller.PresentationUtils;

@SuppressWarnings("serial")
public class DetailsPanel extends JPanel {
	
	private static int DEFAULT_WIDTH = 300;
	private static int DEFAULT_HEIGHT = 238;
	private static int DEFAULT_TABSIZE = 16;
	
	private JTextArea m_txtDetails;
	
	
	public DetailsPanel() {
		setBorder(new TitledBorder(null, "Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		m_txtDetails = new JTextArea();
		m_txtDetails.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(5, 5, 5, 5)));
		m_txtDetails.setBackground(SystemColor.info);
		m_txtDetails.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)); 
		m_txtDetails.setTabSize(DEFAULT_TABSIZE); 

		m_txtDetails.setMargin(new Insets(5, 5, 5, 5));
		m_txtDetails.setAutoscrolls(true);
		m_txtDetails.setEditable(false);
		m_txtDetails.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		
		add(m_txtDetails);
	}
	
	public void setText(String text) {
		m_txtDetails.setText(text);
	}
	
	public void resetText() {
		m_txtDetails.setText("");
	}

	public void setList(String[][] keyValueList) {
		m_txtDetails.setText(PresentationUtils.formatKeyValueList(keyValueList));
	}

	public void setDetailsSize(int width, int height) {
		m_txtDetails.setMinimumSize(new Dimension(width, height));
		m_txtDetails.setPreferredSize(new Dimension(width, height));
	}
	
	public void setTabSize(int tabSize) {
		m_txtDetails.setTabSize(tabSize);
	}

}
