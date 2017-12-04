package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import classes.Website;
import monitor.MonitorStatistics;

/**
 * This class is a panel that put the http codes counts chart and the table at the right position on the panel
 * It retrieve the charts from the MonitorTableChart class.
 * It is called by PeriodPanel
 * 
 * @see PeriodPanel
 * @see MonitorChartTable
 * 
 * @author gabriellerappaport
 *
 */
public class MonitorTableChartPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JFreeChart codesBarChart;
	private JTable table;

	public MonitorTableChartPanel(Website website, Integer period, Integer refreshTime, Integer timeframe) {
		super();
		MonitorStatistics monitorStatistics = new MonitorStatistics(website, period, timeframe);
		MonitorTableChart monitorTableChart = new MonitorTableChart(refreshTime, monitorStatistics);

		JLabel title = new JLabel("Statistics computed every " + String.valueOf(timeframe / 60) + " minutes");

		this.codesBarChart = monitorTableChart.getCodesBarChart();
		ChartPanel chartPanelCodes = new ChartPanel(this.codesBarChart);
		chartPanelCodes.setPreferredSize(new java.awt.Dimension(400, 300));

		this.table = monitorTableChart.getTable();
		JScrollPane t = new JScrollPane(this.table);
		t.setPreferredSize(new java.awt.Dimension(400, 250));
		// LAYOUT

		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
		this.add(t, BorderLayout.WEST);
		this.add(chartPanelCodes, BorderLayout.CENTER);
		this.add(new JLabel("  "), BorderLayout.EAST);
		this.add(new JLabel("  "), BorderLayout.SOUTH);
	}
}
