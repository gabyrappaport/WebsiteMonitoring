package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import monitor.MonitorStatistics;

/**
 * MonitorChart creates the charts that are displayed when the period doesn't equal the timeframe.
 * The charts are : 
 * <ul>
 * <li> a codesBarChart - HTTP codes count
 * <li> a availability line
 * <li> a graph with two lines : maximum and average response time
 * </ul>
 * Those charts are updated at a rate defined in the period Map.
 * 
 * @author gabriellerappaport
 *
 */
public class MonitorChart {

	private JFreeChart availabilityLineChart;
	private JFreeChart codesBarChart;
	private DefaultCategoryDataset datasetAvailability;
	private DefaultCategoryDataset datasetBar;
	private DefaultCategoryDataset datasetMaxAvg;
	private JFreeChart maxAvgLineChart;

	private MonitorStatistics monitorStatistics;

	public MonitorChart(Integer refreshTime, MonitorStatistics monitorStatistics) {
		super();

		this.monitorStatistics = monitorStatistics;
		this.datasetMaxAvg = new DefaultCategoryDataset();
		this.datasetAvailability = new DefaultCategoryDataset();
		this.datasetBar = new DefaultCategoryDataset();

		this.updateAvailability(this.datasetAvailability);
		this.updateMaxAvg(this.datasetMaxAvg);
		this.updateBar(this.datasetBar);

		// Codes Count Bar Chart
		this.codesBarChart = this.codesBarChart("HTTP Status Code Count", this.datasetBar);

		// Availability Line Chart
		this.availabilityLineChart = this.lineChart("Availability", this.datasetAvailability);

		// Max and Average Line Chart
		this.maxAvgLineChart = this.lineChart("Max and Avg Response Time", this.datasetMaxAvg);

		// This Timer updates Table
		Timer timer = new Timer(refreshTime * 1000, new TimerListener());
		timer.start();
	}

	/**
	 * 
	 * @param chartTitle
	 * @param dataset
	 * @return the Http codes count in a bar chart
	 */
	private JFreeChart codesBarChart(String chartTitle, CategoryDataset dataset) {
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Codes", "Count", dataset,
				PlotOrientation.VERTICAL, true, false, false);
		return barChart;
	}
	
	/**
	 * 
	 * @param chartTitle
	 * @param dataset
	 * @return the lineChart
	 */
	private JFreeChart lineChart(String chartTitle, CategoryDataset dataset) {
		JFreeChart lineChart = ChartFactory.createLineChart(chartTitle, "Date", "Count (Milliseconds)", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		return lineChart;
	}

	/**
	 * Update the availability dataset
	 * @param dataset
	 */
	private void updateAvailability(DefaultCategoryDataset dataset) {
		dataset.clear();
		Map<Date, Double> availabilities = this.monitorStatistics.availability();
		ArrayList<Date> dates = new ArrayList<>();
		dates.addAll(availabilities.keySet());
		Collections.sort(dates);
		for (Date date : dates) {
			dataset.setValue(availabilities.get(date), "availability",
					String.valueOf(date).split(" ")[1].split(":00.0")[0]);
		}
	}

	/**
	 * Update the HTTP codes counts dataset
	 * @param dataset
	 */
	private void updateBar(DefaultCategoryDataset dataset) {
		dataset.clear();
		Map<Date, Map<Integer, Integer>> countResponseCode = this.monitorStatistics.countResponseCode();
		ArrayList<Date> dates = new ArrayList<>();
		dates.addAll(countResponseCode.keySet());
		Collections.sort(dates);
		for (Date date : dates) {
			Set<Integer> codes = countResponseCode.get(date).keySet();
			for (Integer code : codes) {
				dataset.setValue(countResponseCode.get(date).get(code),
						String.valueOf(date).split(" ")[1].split(":00.0")[0], String.valueOf(code));
			}
		}
	}

	/**
	 * Update the reponse time dataset
	 * @param dataset
	 */
	private void updateMaxAvg(DefaultCategoryDataset dataset) {
		dataset.clear();
		Map<Date, Integer> maximums = this.monitorStatistics.computeMaxResponseTime();
		Map<Date, Integer> avgs = this.monitorStatistics.computeAvgResponseTime();
		ArrayList<Date> dates = new ArrayList<>();
		dates.addAll(maximums.keySet());
		Collections.sort(dates);
		for (Date date : dates) {
			dataset.setValue(maximums.get(date), "max Response Time",
					String.valueOf(date).split(" ")[1].split(":00.0")[0]);
			dataset.setValue(avgs.get(date), "avg Response Time", String.valueOf(date).split(" ")[1].split(":00.0")[0]);
		}
	}

	/**
	 * Call the different update methods so the charts are up to date.
	 * @author gabriellerappaport
	 *
	 */
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MonitorChart.this.updateAvailability(MonitorChart.this.datasetAvailability);
			MonitorChart.this.updateMaxAvg(MonitorChart.this.datasetMaxAvg);
			MonitorChart.this.updateBar(MonitorChart.this.datasetBar);
		}
	}
	
	public JFreeChart getAvailabilityLineChart() {
		return this.availabilityLineChart;
	}

	public JFreeChart getCodesBarChart() {
		return this.codesBarChart;
	}

	public JFreeChart getMaxAvgLineChart() {
		return this.maxAvgLineChart;
	}
	public void setAvailabilityLineChart(JFreeChart availabilityLineChart) {
		this.availabilityLineChart = availabilityLineChart;
	}

	public void setCodesBarChart(JFreeChart codesBarChart) {
		this.codesBarChart = codesBarChart;
	}

	public void setMaxAvgLineChart(JFreeChart maxAvgLineChart) {
		this.maxAvgLineChart = maxAvgLineChart;
	}

}
