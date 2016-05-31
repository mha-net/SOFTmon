package de.tuberlin.cit.softmon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
public class CounterPanel extends JPanel {
	
	private static int DEFAULT_WIDTH = 220;
	private static int DEFAULT_HEIGHT = 95;
	private static int DEFAULT_TABSIZE = 16;
	
	private JTextArea m_txtrCountersLeft;
	private JTextArea m_txtrCountersRight;

	public CounterPanel() {
		
		setBorder(new TitledBorder(null, "Counters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelPortCountersInfoArea = new JPanel();
		panelPortCountersInfoArea.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(3, 5, 3, 5)));
		panelPortCountersInfoArea.setBackground(SystemColor.info);
		add(panelPortCountersInfoArea);
		GridBagLayout gbl_panelPortCountersInfoArea = new GridBagLayout();
		gbl_panelPortCountersInfoArea.columnWidths = new int[]{0, 0, 0};
		gbl_panelPortCountersInfoArea.rowHeights = new int[]{0, 0};
		gbl_panelPortCountersInfoArea.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPortCountersInfoArea.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelPortCountersInfoArea.setLayout(gbl_panelPortCountersInfoArea);
		
		m_txtrCountersLeft = new JTextArea();
		m_txtrCountersLeft.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		m_txtrCountersLeft.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		m_txtrCountersLeft.setTabSize(DEFAULT_TABSIZE); 
		m_txtrCountersLeft.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		m_txtrCountersLeft.setBackground(SystemColor.info);
		//m_txtrCountersLeft.setText("Left");
		GridBagConstraints gbc_txtrLeft = new GridBagConstraints();
		gbc_txtrLeft.insets = new Insets(0, 0, 0, 5);
		gbc_txtrLeft.fill = GridBagConstraints.BOTH;
		gbc_txtrLeft.gridx = 0;
		gbc_txtrLeft.gridy = 0;
		panelPortCountersInfoArea.add(m_txtrCountersLeft, gbc_txtrLeft);
		
		m_txtrCountersRight = new JTextArea();
		m_txtrCountersRight.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		m_txtrCountersRight.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		m_txtrCountersRight.setTabSize(DEFAULT_TABSIZE); 
		m_txtrCountersRight.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		m_txtrCountersRight.setBackground(SystemColor.info);
		//m_txtrCountersRight.setText("Right");
		GridBagConstraints gbc_txtrRight = new GridBagConstraints();
		gbc_txtrRight.fill = GridBagConstraints.BOTH;
		gbc_txtrRight.gridx = 1;
		gbc_txtrRight.gridy = 0;
		panelPortCountersInfoArea.add(m_txtrCountersRight, gbc_txtrRight);
	}
	
	public void setCounterList(String[][][] keyValueLists) {
		m_txtrCountersLeft.setText(PresentationUtils.formatKeyValueList(keyValueLists[0]));
		m_txtrCountersRight.setText(PresentationUtils.formatKeyValueList(keyValueLists[1]));
	}
	
	public void resetCounterList() {
		m_txtrCountersLeft.setText("");
		m_txtrCountersRight.setText("");
	}
	
	public void setTabSize(int tabSize) {
		m_txtrCountersLeft.setTabSize(tabSize);
		m_txtrCountersRight.setTabSize(tabSize);
	}

	public void setCounterAreaSize(int width, int height) {
		m_txtrCountersLeft.setMinimumSize(new Dimension((width / 2), height));
		m_txtrCountersRight.setMinimumSize(new Dimension((width / 2), height));
		m_txtrCountersLeft.setPreferredSize(new Dimension((width / 2), height));
		m_txtrCountersRight.setPreferredSize(new Dimension((width / 2), height));
	}

}
