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
import de.tuberlin.cit.softmon.model.OfFlow;
import de.tuberlin.cit.softmon.model.OfFlowCounter;
import de.tuberlin.cit.softmon.model.OfFlowMetrics;
import info.monitorenter.gui.chart.ITrace2D;

@SuppressWarnings("serial")
public class FlowCard extends JPanel 
	implements ICounterDestination<OfFlowCounter>, 
				IMetricsDestination<OfFlowMetrics> {
	
	private CounterPanel m_flowCounterPanel;
	private JLabel m_lblFlowTitle;
	
	private ITrace2D m_traceBytesPerSec;
	private ITrace2D m_tracePacketsPerSec;
	private ITrace2D m_traceUsage; // for future use

	private DefaultChart m_flowChart1;
	private DefaultChart m_flowChart2;
	private DefaultChart m_flowChart3; // for future use

	public FlowCard() {
		setBorder(new TitledBorder(null, "Flow Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagLayout gbl_flowCard = new GridBagLayout();
		gbl_flowCard.columnWidths = new int[]{0, 0};
		gbl_flowCard.rowHeights = new int[]{0, 0, 0, 0};
		gbl_flowCard.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_flowCard.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_flowCard);
		
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
		m_lblFlowTitle = new JLabel(" ");
		m_lblFlowTitle.setPreferredSize(new Dimension(300, 18));
		m_lblFlowTitle.setForeground(SystemColor.text);
		m_lblFlowTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		titlePanel.add(m_lblFlowTitle);

		// chart panel
		JPanel flowChartsPanel = new JPanel();
		flowChartsPanel.setBorder(new CompoundBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
						"Metrics", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), 
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_flowChartsPanel = new GridBagConstraints();
		gbc_flowChartsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_flowChartsPanel.fill = GridBagConstraints.BOTH;
		gbc_flowChartsPanel.gridx = 0;
		gbc_flowChartsPanel.gridy = 1;
		add(flowChartsPanel, gbc_flowChartsPanel);
		flowChartsPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		// chart 1
		m_flowChart1 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		flowChartsPanel.add(m_flowChart1);
		
		// chart 2
		m_flowChart2 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		flowChartsPanel.add(m_flowChart2);
		
		// chart 3: for future use
		//JPanel dummyPortChart3 = new JPanel();
		//dummyPortChart3.setBackground(SystemColor.window);
		//flowChartsPanel.add(dummyPortChart3);
		m_flowChart3 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		flowChartsPanel.add(m_flowChart3);
		
		// counter panel
		m_flowCounterPanel = new CounterPanel();
		GridBagConstraints gbc_flowCounterPanel = new GridBagConstraints();
		gbc_flowCounterPanel.fill = GridBagConstraints.BOTH;
		gbc_flowCounterPanel.gridx = 0;
		gbc_flowCounterPanel.gridy = 2;
		add(m_flowCounterPanel, gbc_flowCounterPanel);
		
		createTraces();
	}
	
	private void createTraces() {
		m_traceBytesPerSec = m_flowChart1.addTrace("Byte Rate [Bytes/s]", "Bytes/s", Config.GREEN);
		m_tracePacketsPerSec = m_flowChart2.addTrace("Packet Rate [Packets/s]", "Packet/s", Config.GREEN);
		m_traceUsage = m_flowChart3.addTrace("Port Usage [%]", "%", Config.GREEN);
	}
	
	public CounterPanel getCounterPanel() {
		return m_flowCounterPanel;
	}
	
	public void setTitle(OfFlow flow) {
		String portTitle = "Switch=" + flow.getSwitchDpid() + ", Flow=" + flow.toString();
		m_lblFlowTitle.setText(portTitle);
	}
	
	public void resetTitle() {
		m_lblFlowTitle.setText(" ");
	}

	@Override
	public void setCounter(OfFlowCounter counter) {
		m_flowCounterPanel.setCounterList(PresentationUtils.createCounterLists(counter));	
	}

	@Override
	public void setMetrics(OfFlowMetrics metrics) {
		m_traceBytesPerSec.addPoint(metrics.getTimeStampRel(), metrics.getBytesPerSec());
		m_tracePacketsPerSec.addPoint(metrics.getTimeStampRel(), metrics.getPacketsPerSec());
		m_traceUsage.addPoint(metrics.getTimeStampRel(), metrics.getPortUsage() * 100);
	}
	
	@Override
	public void setCycleTime(int cycleTime) {
		m_flowChart1.setCycleTime(cycleTime);
		m_flowChart2.setCycleTime(cycleTime);
		m_flowChart3.setCycleTime(cycleTime);
	}
	
	public void setValueCount(int valueCount) {
		m_flowChart1.setValueCount(valueCount);
		m_flowChart2.setValueCount(valueCount);
		m_flowChart3.setValueCount(valueCount);
	}

	public void initCharts() {
		m_flowChart1.initChartRel();
		m_flowChart2.initChartRel();
		m_flowChart3.initChartRel();
	}
	
}
