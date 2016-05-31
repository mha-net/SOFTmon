package de.tuberlin.cit.softmon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import de.tuberlin.cit.softmon.chart.DefaultChart;
import de.tuberlin.cit.softmon.chart.ICounterDestination;
import de.tuberlin.cit.softmon.chart.IMetricsDestination;
import de.tuberlin.cit.softmon.controller.Config;
import de.tuberlin.cit.softmon.controller.PresentationUtils;
import de.tuberlin.cit.softmon.model.OfSwitch;
import de.tuberlin.cit.softmon.model.OfSwitchCounter;
import de.tuberlin.cit.softmon.model.OfSwitchMetrics;
import info.monitorenter.gui.chart.ITrace2D;

@SuppressWarnings("serial")
public class SwitchCard extends JPanel 
	implements ICounterDestination<OfSwitchCounter>,
				IMetricsDestination<OfSwitchMetrics> {
	
	private CounterPanel m_switchCounterPanel;
	private JLabel m_lblSwitchTitle;

	private DefaultChart m_switchChart1;
	private DefaultChart m_switchChart2;
	private DefaultChart m_switchChart3;
	
	private ITrace2D m_traceBytesPerSec;
	private ITrace2D m_tracePacketsPerSec;
	private ITrace2D m_flowCount;

	public SwitchCard() {
		setBorder(new TitledBorder(null, "Switch Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagLayout gbl_switchCard = new GridBagLayout();
		gbl_switchCard.columnWidths = new int[]{0, 0};
		gbl_switchCard.rowHeights = new int[]{0, 0, 0, 0};
		gbl_switchCard.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_switchCard.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_switchCard);
		
		// title panel
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(SystemColor.activeCaption);
		titlePanel.setBorder(new EmptyBorder(2, 6, 2, 6));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.insets = new Insets(2, 3, 2, 3);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(titlePanel, gbc_lblTitle);
		titlePanel.setLayout(new BorderLayout(0, 0));
		
		// title label
		m_lblSwitchTitle = new JLabel(" ");
		m_lblSwitchTitle.setPreferredSize(new Dimension(300, 18));
		m_lblSwitchTitle.setForeground(SystemColor.text);
		m_lblSwitchTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		titlePanel.add(m_lblSwitchTitle);

		// chart panel
		JPanel chartsPanel = new JPanel();
		chartsPanel.setBorder(new CompoundBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
						"Metrics", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), 
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_switchChartsPanel = new GridBagConstraints();
		gbc_switchChartsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_switchChartsPanel.fill = GridBagConstraints.BOTH;
		gbc_switchChartsPanel.gridx = 0;
		gbc_switchChartsPanel.gridy = 1;
		add(chartsPanel, gbc_switchChartsPanel);
		chartsPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		// chart 1 
		m_switchChart1 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		chartsPanel.add(m_switchChart1);
		
		// chart 2
		m_switchChart2 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		chartsPanel.add(m_switchChart2);
		
		// chart 3
		m_switchChart3 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT, 0.0, 10.0);
		chartsPanel.add(m_switchChart3);
		
		// counter panel
		m_switchCounterPanel = new CounterPanel();
		GridBagConstraints gbc_switchCounterPanel = new GridBagConstraints();
		gbc_switchCounterPanel.fill = GridBagConstraints.BOTH;
		gbc_switchCounterPanel.gridx = 0;
		gbc_switchCounterPanel.gridy = 2;
		add(m_switchCounterPanel, gbc_switchCounterPanel);
		
		createTraces();
	}
	
	private void createTraces() {
		// chart 1
		m_traceBytesPerSec = m_switchChart1.addTrace("Byte Rate [Bytes/s]", "Bytes/s", Config.GREEN);
		
		// chart 2
		m_tracePacketsPerSec = m_switchChart2.addTrace("Packet Rate [Packets/s]", "Packets/s", Config.GREEN);

		// chart 3
		m_flowCount = m_switchChart3.addTrace("Flow Count [Flows]", "Flows", Config.GREEN);
	}
	
	public CounterPanel getCounterPanel() {
		return m_switchCounterPanel;
	}
	
	public void setTitle(OfSwitch swtch) {
		String portTitle = "Switch=" + swtch.getSwitchDpid();
		m_lblSwitchTitle.setText(portTitle);
	}
	
	public void resetTitle() {
		m_lblSwitchTitle.setText(" ");
	}

	@Override
	public void setCounter(OfSwitchCounter counter) {
		m_switchCounterPanel.setCounterList(PresentationUtils.createCounterLists(counter));	
	}

	@Override
	public void setMetrics(OfSwitchMetrics metrics) {
		m_traceBytesPerSec.addPoint(metrics.getTimeStampRel(), metrics.getBytesPerSec());		
		m_tracePacketsPerSec.addPoint(metrics.getTimeStampRel(), metrics.getPacketsPerSec());		
		m_flowCount.addPoint(metrics.getTimeStampRel(), metrics.getFlowCount());	
	}

	@Override
	public void setCycleTime(int cycleTime) {
		m_switchChart1.setCycleTime(cycleTime);
		m_switchChart2.setCycleTime(cycleTime);
		m_switchChart3.setCycleTime(cycleTime);
	}
	
	public void setValueCount(int valueCount) {
		m_switchChart1.setValueCount(valueCount);
		m_switchChart2.setValueCount(valueCount);
		m_switchChart3.setValueCount(valueCount);
	}

	public void initCharts() {
		m_switchChart1.initChartRel();
		m_switchChart2.initChartRel();
		m_switchChart3.initChartRel();
	}
}
