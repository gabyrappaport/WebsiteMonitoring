package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import classes.Website;
import monitor.MonitorStatistics;

/**
 * This class is a panel that put the http codes counts chart and availability, avg and max response code line at the right position on the panel
 * It retrieve the charts from the MonitorChart class.
 * It is called by PeriodPanel.
 * 
 * @see PeriodPanel
 * @see MonitorChart
 * @author gabriellerappaport
 *
 */
public class MonitorChartPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JFreeChart availabilityLineChart;
	private JFreeChart codesBarChart;
	private JFreeChart maxAvgLineChart;
	private Website website;

	public MonitorChartPanel(Website website, Integer period, Integer refreshTime, Integer timeframe) {
		super();
		this.website = website;
		MonitorStatistics monitorStatistics = new MonitorStatistics(website, period, timeframe);
		MonitorChart monitorChart = new MonitorChart(refreshTime, monitorStatistics);

		// Title Overall 10 minutes
		JLabel title = new JLabel("Statistics computed every " + String.valueOf(timeframe / 60) + " minutes");

		// Bar Chart Over last 10 minutes
		this.codesBarChart = monitorChart.getCodesBarChart();
		ChartPanel chartPanelCodes = new ChartPanel(this.codesBarChart);
		chartPanelCodes.setPreferredSize(new java.awt.Dimension(450, 320));

		// Availability every 2 minutes
		this.availabilityLineChart = monitorChart.getAvailabilityLineChart();
		ChartPanel chartPanelAvailability = new ChartPanel(this.availabilityLineChart);
		chartPanelAvailability.setPreferredSize(new java.awt.Dimension(450, 320));

		// Max and Average
		this.maxAvgLineChart = monitorChart.getMaxAvgLineChart();
		ChartPanel chartPanelMaxAvg = new ChartPanel(this.maxAvgLineChart);
		chartPanelMaxAvg.setPreferredSize(new java.awt.Dimension(450, 320));

		// LAYOUT

		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
		this.add(chartPanelAvailability, BorderLayout.EAST);
		this.add(chartPanelCodes, BorderLayout.WEST);
		this.add(chartPanelMaxAvg, BorderLayout.CENTER);
	}

	public Website getWebsite() {
		return this.website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}
