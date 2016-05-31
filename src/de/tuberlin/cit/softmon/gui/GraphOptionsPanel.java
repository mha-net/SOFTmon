package de.tuberlin.cit.softmon.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

import de.tuberlin.cit.softmon.controller.Config;

@SuppressWarnings("serial")
public class GraphOptionsPanel extends JPanel {

	public static final String SPINNER_VALUE_COUNT = "ValueCount";
	public static final String SPINNER_CYCLE_TIME = "CycleTime";

	private JSpinner m_spinnerCycleTime;
	private JSpinner m_spinnerValueCount;

	public GraphOptionsPanel() {

		// title
		setBorder(new TitledBorder(null, "Graph Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		// layout
		GridBagLayout gbl_graphOptionsPanel = new GridBagLayout();
		gbl_graphOptionsPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_graphOptionsPanel.rowHeights = new int[] { 0, 0 };
		gbl_graphOptionsPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_graphOptionsPanel.rowWeights = new double[] { 0.0, 0.0 };

		setLayout(gbl_graphOptionsPanel);

		// label "Refresh Time"
		GridBagConstraints gbc_lblCycleTime = new GridBagConstraints();
		gbc_lblCycleTime.insets = new Insets(0, 5, 5, 5);
		gbc_lblCycleTime.gridx = 0;
		gbc_lblCycleTime.gridy = 0;
		JLabel lblCycleTime = new JLabel("Refresh Time:");
		add(lblCycleTime, gbc_lblCycleTime);

		// spinner cycle time
		GridBagConstraints gbc_cycleTimeSpinner = new GridBagConstraints();
		gbc_cycleTimeSpinner.insets = new Insets(1, 0, 6, 5);
		gbc_cycleTimeSpinner.gridx = 1;
		gbc_cycleTimeSpinner.gridy = 0;
		m_spinnerCycleTime = new JSpinner();
		m_spinnerCycleTime.setToolTipText(
				"Min.: " + Config.CYCLE_TIME_MIN + " ms, Step: " + Config.CYCLE_TIME_STEP + " ms");
		m_spinnerCycleTime.setMinimumSize(new Dimension(80, 20));
		m_spinnerCycleTime.setPreferredSize(new Dimension(80, 20));
		m_spinnerCycleTime.setName(SPINNER_CYCLE_TIME);
		m_spinnerCycleTime.setModel(new SpinnerNumberModel(new Integer(Config.CYCLE_TIME_INIT), new Integer(Config.CYCLE_TIME_MIN),
				null, new Integer(Config.CYCLE_TIME_STEP)));
		add(m_spinnerCycleTime, gbc_cycleTimeSpinner);

		// label refresh time units
		GridBagConstraints gbc_lblCycleTimeUnits = new GridBagConstraints();
		gbc_lblCycleTimeUnits.insets = new Insets(0, 0, 5, 15);
		gbc_lblCycleTimeUnits.gridx = 2;
		gbc_lblCycleTimeUnits.gridy = 0;
		JLabel lblCycleTimeUnits = new JLabel("ms");
		add(lblCycleTimeUnits, gbc_lblCycleTimeUnits);

		// label "Shown Values"
		GridBagConstraints gbc_lblValueCount = new GridBagConstraints();
		gbc_lblValueCount.anchor = GridBagConstraints.EAST;
		gbc_lblValueCount.insets = new Insets(0, 5, 5, 5);
		gbc_lblValueCount.gridx = 0;
		gbc_lblValueCount.gridy = 1;
		JLabel lblValueCount = new JLabel("Shown Values:");
		add(lblValueCount, gbc_lblValueCount);

		// spinner value count
		GridBagConstraints gbc_spinnerValueCount = new GridBagConstraints();
		gbc_spinnerValueCount.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerValueCount.gridx = 1;
		gbc_spinnerValueCount.gridy = 1;
		m_spinnerValueCount = new JSpinner();
		m_spinnerValueCount.setToolTipText(
				"Min.: " + Config.VALUE_COUNT_MIN + ", Max.: " + Config.VALUE_COUNT_MAX + ", Step: " + Config.CYCLE_TIME_STEP);

		m_spinnerValueCount.setName(SPINNER_VALUE_COUNT);
		m_spinnerValueCount.setModel(new SpinnerNumberModel(Config.VALUE_COUNT_INIT, Config.VALUE_COUNT_MIN, Config.VALUE_COUNT_MAX, Config.VALUE_COUNT_STEP));
		m_spinnerValueCount.setMinimumSize(new Dimension(80, 20));
		m_spinnerValueCount.setPreferredSize(new Dimension(80, 20));
		add(m_spinnerValueCount, gbc_spinnerValueCount);
	}
	
	public void addSpinnerListener(ChangeListener listener) {
		m_spinnerCycleTime.addChangeListener(listener);
		m_spinnerValueCount.addChangeListener(listener);
	}
	
	public int getCycleTime() {
		return (int) m_spinnerCycleTime.getValue();
	}
	
	public int getValueCount() {
		return (int) m_spinnerValueCount.getValue();
	}

}
