package de.tuberlin.cit.softmon.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.SortedSet;

import javax.swing.JPanel;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.IAxisScalePolicy;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.axis.AAxis;
import info.monitorenter.gui.chart.axis.AxisLinear;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterAutoUnits;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterDate;
import info.monitorenter.gui.chart.labelformatters.LabelFormatterSimple;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyMinimumViewport;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.util.Range;

@SuppressWarnings("serial")
public class DefaultChart extends JPanel {
	
	public static final String AXIS_X_FORMAT_TOD = "TimeOfDay";
	public static final String AXIS_X_FORMAT_MILLIS = "MilliSeconds";
	public static final String AXIS_X_FORMAT_NONE = "None";
	
	private static final Color COLOR_GRID = Color.LIGHT_GRAY;
	
	private static final boolean GRID_X = true;
	private static final boolean GRID_Y = true;
	
	private static final int CYCLE_TIME_DEFAULT = 500;
	private static final int VALUE_COUNT_DEFAULT = 100;
	
	private Chart2D m_chart;
	
	private int m_cycleTime;
	private int m_valueCount;
	
	private double m_rangeMin = 0.0;	// default minRange
	private double m_rangeMax = 100.0;	// default maxRange
	
	private long m_initTimeStamp;

	public DefaultChart() {
		this.m_cycleTime = CYCLE_TIME_DEFAULT;
		this.m_valueCount = VALUE_COUNT_DEFAULT;
		createChart();
	}
	
	public DefaultChart(int cycleTime, int valueCount) {
		this.m_cycleTime = cycleTime;
		this.m_valueCount = valueCount;
		createChart();
	}
	
	public DefaultChart(int cycleTime, int valueCount, double rangeMin, double rangeMax) {
		this.m_cycleTime = cycleTime;
		this.m_valueCount = valueCount;
		this.m_rangeMin = rangeMin;
		this.m_rangeMax = rangeMax;
		createChart();
	}

	private void createChart() {
		setLayout(new BorderLayout(0, 0));
		m_chart = new Chart2D();
		
	    // Configure grid
	    m_chart.setGridColor(COLOR_GRID);
	
	    // Close the box by using empty axes
	    AAxis<IAxisScalePolicy> axisXTop = new AxisLinear<IAxisScalePolicy>(new LabelFormatterDate(new SimpleDateFormat("")));
	    axisXTop.setPaintScale(false);
	    AAxis<IAxisScalePolicy> axisYRight = new AxisLinear<IAxisScalePolicy>(new LabelFormatterDate(new SimpleDateFormat("")));
	    axisYRight.setPaintScale(false);
	    m_chart.setAxisXTop(axisXTop, 0);
	    m_chart.setAxisYRight(axisYRight,0);
	    
	    setAxisX(AXIS_X_FORMAT_NONE, null);
	    setAxisY(null);
		
		add(m_chart);
	}

	private void initTrace(ITrace2D trace) {
		for (int i = 0; i < m_valueCount; i++) {
			long time = (-((m_valueCount - i) * m_cycleTime));

			double x = (double) time + m_initTimeStamp; 
			double y = 0.0;
			
			// Always start with 0.0
			if (i < (m_valueCount - 1)) {
				trace.addPoint(x, y);
			} else {
				trace.addPoint(x, 0.0);
			}
		}
	}
	
	// fill all traces with 0.0 values until initTime 
	private void initTraces(long initTime) {
		this.m_initTimeStamp = initTime;

		SortedSet<ITrace2D> traces = m_chart.getTraces();
		for (Iterator<ITrace2D> it = traces.iterator(); it.hasNext();) {
			Trace2DLtd trace = (Trace2DLtd) it.next();
			initTrace(trace);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setAxisX(String axisFormat, String title) {
		IAxis<IAxisScalePolicy> axisX = (IAxis<IAxisScalePolicy>)m_chart.getAxisX();
		
		switch (axisFormat) {
    	case AXIS_X_FORMAT_TOD:
    		axisX.setFormatter(new LabelFormatterDate(new SimpleDateFormat("HH:mm:ss")));
    		
    		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    		//sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    		//axisX.setFormatter(new LabelFormatterDate(sdf));
    		break;
    	
    	case AXIS_X_FORMAT_MILLIS:
    		axisX.setFormatter(new LabelFormatterSimple());
    		break;
    		
    	case AXIS_X_FORMAT_NONE:
    		//axisX.setFormatter(new LabelFormatterSimple());
    		//axisX.setFormatter(new LabelFormatterDate(new SimpleDateFormat("")));
    		//axisX.setFormatter(new LabelFormatterDate(new SimpleDateFormat("HH:mm:ss")));
    	    axisX.setPaintScale(false);
    		break;
		}
    	
		//axisX.setMinorTickSpacing((double) m_cycleTime / 10); 
		//axisX.setMajorTickSpacing((double) m_cycleTime / 10);
		//axisX.setStartMajorTick(true);
		
	    // Set manual scaling of x axis
	    //axisX.setAxisScalePolicy(new AxisScalePolicyManualTicks()); 
	    //System.out.println("ticks: " + m_cycleTime * m_valueCount / 10);
	    //axisX.setMinorTickSpacing(m_cycleTime * m_valueCount / 10);

	    //Range rangeX = new Range(0.0, 10.0);
	    //IRangePolicy rangePolicyX = new RangePolicyMinimumViewport(rangeX);
	    //rangePolicyX = new RangePolicyHighestValuesForcedMin(rangeX);
	    //axisX.setRangePolicy(new RangePolicyMinimumViewport(new Range(0.0, 1000000.0)));
	    
		axisX.setMinorTickSpacing(5.0);
		axisX.setStartMajorTick(true);
		
		axisX.setPaintGrid(GRID_X);
		
		IAxis.AxisTitle axisTitle = axisX.getAxisTitle();
	    axisTitle.setTitle(title);

	}
	
	@SuppressWarnings("unchecked")
	private void setAxisY(String title) {
		IAxis<IAxisScalePolicy> axisY = (IAxis<IAxisScalePolicy>)m_chart.getAxisY();

		//axisY.setRangePolicy(new RangePolicyFixedViewport(new Range(0.0, 100.0)));
		axisY.setRangePolicy(new RangePolicyMinimumViewport(new Range(m_rangeMin, m_rangeMax)));
		
		//axisY.setAxisScalePolicy(new AxisScalePolicyManualTicks());
		
		//axisY.setMajorTickSpacing(10.0);
		//double minTicks = (m_rangeMax - m_rangeMin) / 20;
		
		//axisY.setMinorTickSpacing(minTicks);
		axisY.setMinorTickSpacing(10); // this setting works without any logic and can only be found out by trial and error

		//axisY.setMajorTickSpacing(minTicks);
		axisY.setStartMajorTick(true);
		
		axisY.setFormatter(new LabelFormatterAutoUnits());
		
		/*
		NumberFormat numberFormat = NumberFormat.getInstance();
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.applyPattern("0000");
		axisY.setFormatter(new LabelFormatterNumber(decimalFormat));
		*/
		
		//AUnit unit = new UnitMega();
		//axisY.setFormatter(new LabelFormatterUnit(unit));
		
		axisY.setPaintGrid(GRID_Y);
	
	    IAxis.AxisTitle axisTitle = axisY.getAxisTitle();
	    axisTitle.setTitle(title);
	}
	
	// initialize chart with absolute time base (milliseconds since 1970)
	public void initChartAbs() {
		initTraces(Calendar.getInstance().getTimeInMillis());
	}
	
	// initialize chart with relative time base (starting with 0)
	public void initChartRel() {
		initTraces(0);
	}
	
	public Chart2D getChart() {
		return this.m_chart;
	}
	
	public void setCycleTime(int cycleTime) {
		this.m_cycleTime = cycleTime;
	}
	
	public void setValueCount(int valueCount) {
		this.m_valueCount = valueCount;
		SortedSet<ITrace2D> traces = m_chart.getTraces();
		for (Iterator<ITrace2D> it = traces.iterator(); it.hasNext();) {
			Trace2DLtd trace = (Trace2DLtd) it.next();
			trace.setMaxSize(valueCount);
		}
		// To Do: Recalculate axes?
	}
	
	public ITrace2D addTrace(String traceName, String traceUnits, Color traceColor) {
		ITrace2D trace = new Trace2DLtd(m_valueCount);
		trace.setColor(traceColor);
		trace.setName(traceName);
		//trace.setPhysicalUnits(null, traceUnits);
		m_chart.addTrace(trace);
		return trace;
	}

}
