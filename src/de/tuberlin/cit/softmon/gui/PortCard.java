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
import de.tuberlin.cit.softmon.model.OfPort;
import de.tuberlin.cit.softmon.model.OfPortCounter;
import de.tuberlin.cit.softmon.model.OfPortMetrics;
import info.monitorenter.gui.chart.ITrace2D;

@SuppressWarnings("serial")
public class PortCard extends JPanel 
	implements ICounterDestination<OfPortCounter>,
				IMetricsDestination<OfPortMetrics> {
	
	private CounterPanel m_portCounterPanel;
	private JLabel m_lblPortTitle;
	
	private DefaultChart m_portChart1;
	private DefaultChart m_portChart2;
	private DefaultChart m_portChart3;
	
	private ITrace2D m_traceRxPacketsPerSec;
	private ITrace2D m_traceTxPacketsPerSec;

	private ITrace2D m_traceRxBytesPerSec;
	private ITrace2D m_traceTxBytesPerSec;
	
	private ITrace2D m_rxUsage;
	private ITrace2D m_txUsage;

	public PortCard() {
		setBorder(new TitledBorder(null, "Port Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagLayout gbl_portCard = new GridBagLayout();
		gbl_portCard.columnWidths = new int[]{0, 0};
		gbl_portCard.rowHeights = new int[]{0, 0, 0, 0};
		gbl_portCard.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_portCard.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_portCard);
		
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
		m_lblPortTitle = new JLabel(" ");
		m_lblPortTitle.setPreferredSize(new Dimension(300, 18));
		m_lblPortTitle.setForeground(SystemColor.text);
		m_lblPortTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		titlePanel.add(m_lblPortTitle);

		// chart panel
		JPanel chartsPanel = new JPanel();
		chartsPanel.setBorder(new CompoundBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
						"Metrics", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), 
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		GridBagConstraints gbc_chartsPanel = new GridBagConstraints();
		gbc_chartsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_chartsPanel.fill = GridBagConstraints.BOTH;
		gbc_chartsPanel.gridx = 0;
		gbc_chartsPanel.gridy = 1;
		add(chartsPanel, gbc_chartsPanel);
		chartsPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		// chart 1
		m_portChart1 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		chartsPanel.add(m_portChart1);
		
		// chart 2
		m_portChart2 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		chartsPanel.add(m_portChart2);
		
		// chart 3
		m_portChart3 = new DefaultChart(Config.CYCLE_TIME_INIT, Config.VALUE_COUNT_INIT);
		chartsPanel.add(m_portChart3);
		
		// counter panel
		m_portCounterPanel = new CounterPanel();
		GridBagConstraints gbc_portCounterPanel = new GridBagConstraints();
		gbc_portCounterPanel.fill = GridBagConstraints.BOTH;
		gbc_portCounterPanel.gridx = 0;
		gbc_portCounterPanel.gridy = 2;
		add(m_portCounterPanel, gbc_portCounterPanel);
		
		createTraces();
	}
	
	
	private void createTraces() {
		// chart 1		
		m_traceRxPacketsPerSec = m_portChart1.addTrace("RX Byte Rate [Bytes/s]", "Bytes/s", Config.GREEN);
		m_traceTxPacketsPerSec = m_portChart1.addTrace("TX Byte Rate [Bytes/s]", "Bytes/s", Config.RED);
		
		// chart 2
		m_traceRxBytesPerSec = m_portChart2.addTrace("RX Packet Rate [Packets/s]", "Packets/s", Config.GREEN);
		m_traceTxBytesPerSec = m_portChart2.addTrace("TX Packet Rate [Packets/s]", "Packets/s", Config.RED);
		
		// chart3
		m_rxUsage = m_portChart3.addTrace("RX Usage [%]", "%", Config.GREEN);
		m_txUsage = m_portChart3.addTrace("TX Usage [%]", "%", Config.RED);
	}
	
	public CounterPanel getCounterPanel() {
		return m_portCounterPanel;
	}
	
	public void setTitle(OfPort port) {
		String portTitle = "Switch=" + port.getSwitchDpid() + ", Port=" + port.toString();
		m_lblPortTitle.setText(portTitle);
	}
	
	public void resetTitle() {
		m_lblPortTitle.setText(" ");
	}

	@Override
	public void setCounter(OfPortCounter counter) {
		m_portCounterPanel.setCounterList(PresentationUtils.createCounterLists(counter));
	}
	
	@Override
	public void setMetrics(OfPortMetrics metrics) {
		m_traceRxPacketsPerSec.addPoint(metrics.getTimeStampRel(), metrics.getRxBytesPerSec());
		m_traceTxPacketsPerSec.addPoint(metrics.getTimeStampRel(), metrics.getTxBytesPerSec());
		
		m_traceRxBytesPerSec.addPoint(metrics.getTimeStampRel(), metrics.getRxPacketsPerSec());
		m_traceTxBytesPerSec.addPoint(metrics.getTimeStampRel(), metrics.getTxPacketsPerSec());

		m_rxUsage.addPoint(metrics.getTimeStampRel(), (double) metrics.getRxUsage() * 100);
		m_txUsage.addPoint(metrics.getTimeStampRel(), (double) metrics.getTxUsage() * 100);
		
		//printDebug(m_portChart1);
	}

	@Override
	public void setCycleTime(int cycleTime) {
		m_portChart1.setCycleTime(cycleTime);
		m_portChart2.setCycleTime(cycleTime);
		m_portChart3.setCycleTime(cycleTime);
	}

	public void setValueCount(int valueCount) {
		m_portChart1.setValueCount(valueCount);
		m_portChart2.setValueCount(valueCount);
		m_portChart3.setValueCount(valueCount);
	}

	public void initCharts() {
		m_portChart1.initChartRel();
		m_portChart2.initChartRel();
		m_portChart3.initChartRel();
	}
	
	public void printDebug(DefaultChart chart) {
		
		// testing
		System.out.println("AxisY=" + chart.getChart().getAxisY().getClass().getSimpleName());
		System.out.println("AxisY.getFormatter()=" + chart.getChart().getAxisY().getFormatter().getClass().getSimpleName());
		//System.out.println("RangePolicy=" + chart.getChart().getAxisY().getRangePolicy().getClass().getSimpleName());
		//System.out.println("Range.getMax()=" +chart.getChart().getAxisY().getRangePolicy().getRange().getMax());
		//System.out.println("Range.getMin()=" +chart.getChart().getAxisY().getRangePolicy().getRange().getMin());
		System.out.println("ScalePolicy=" +chart.getChart().getAxisY().getAxisScalePolicy().getClass().getSimpleName());
		System.out.println("width=" + chart.getChart().getWidth());
		System.out.println("XAxisWidth=" + chart.getChart().getXAxisWidth());
		System.out.println();

	}
}
