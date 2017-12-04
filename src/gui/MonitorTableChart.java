package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import monitor.MonitorStatistics;

/**
 * MonitorChartTable creates the charts that are displayed when the period equals the timeframe.
 * The charts are : 
 * <ul>
 * <li> a codesBarChart - HTTP codes count
 * <li> a table with the availability, maximum and average response time
 * </ul>
 * Those charts are updated at a rate defined in the period Map.
 * 
 * @author gabriellerappaport
 *
 */
public class MonitorTableChart {

	private JFreeChart codesBarChart;
	private DefaultCategoryDataset datasetBar;
	private MonitorStatistics monitorStatistics;

	private JTable table;

	public MonitorTableChart(Integer refreshTime, MonitorStatistics monitorStatistics) {
		super();
		this.monitorStatistics = monitorStatistics;
		this.table = new JTable(this.getTableModel());
		this.datasetBar = new DefaultCategoryDataset();

		this.updateBar(this.datasetBar);

		this.codesBarChart = this.codesBarChart("HTTP Status Code Count", this.datasetBar);

		// This Timer updates Table
		Timer timer = new Timer(refreshTime * 1000, new TimerListener());
		timer.start();

	}

	/**
	 * 
	 * @param chartTitle
	 * @param dataset
	 * @return a barChart
	 */
	private JFreeChart codesBarChart(String chartTitle, CategoryDataset dataset) {
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Codes", "Count", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}

	/**
	 *
	 * @return model of the statistics table
	 */
	private TableModel getTableModel() {
		String[] cols = { "Availability", "MaxResponse Time", "Average Response Time" };
		Object[][] data = { { this.getNewRow() } };
		DefaultTableModel model = new DefaultTableModel(data, cols);
		return model;
	}
	
	/**
	 * 
	 * @return rows in the table
	 */
	private Object[] getNewRow() {
		Object[] row = { 0, 0, 0 };
		try {
			Map<Date, Double> availabilities = this.monitorStatistics.availability();
			Map<Date, Integer> maximums = this.monitorStatistics.computeMaxResponseTime();
			Map<Date, Integer> avgs = this.monitorStatistics.computeAvgResponseTime();
			Set<Date> dates = availabilities.keySet();
			for (Date date : dates) {
				row = new Object[] { availabilities.get(date), maximums.get(date), avgs.get(date) };
			}
		} catch (IndexOutOfBoundsException e) {
			row = new Object[] { 0, 0, 0 };
		}
		return row;
	}

	/**
	 * Update the dataset for the BarChart
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
	 * Call the different update methods so the chart and table are up to date.
	 * @author gabriellerappaport
	 *
	 */
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel model = (DefaultTableModel) MonitorTableChart.this.table.getModel();
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				Object[] row = MonitorTableChart.this.getNewRow();
				model.setValueAt(row[0], i, 0);
				model.setValueAt(row[1], i, 1);
				model.setValueAt(row[2], i, 2);
			}
			MonitorTableChart.this.updateBar(MonitorTableChart.this.datasetBar);
		}
	}
	
	public JFreeChart getCodesBarChart() {
		return this.codesBarChart;
	}

	
	public JTable getTable() {
		return this.table;
	}
	
	public void setCodesBarChart(JFreeChart codesBarChart) {
		this.codesBarChart = codesBarChart;
	}

	public void setTable(JTable table) {
		this.table = table;
	}


}
