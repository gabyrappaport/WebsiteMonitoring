package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import classes.Website;

/**
 * <p>
 * 
 * PeriodPanel class is a JPanel that is cut in two with a JSplitPane.
 * The upper panel contains the statistics over the last 10 minutes.
 * The lower panel contains the statistics over the last hour. 
 * </p>
 * <p>
 * There are two different ways to display the statistics : 
 * <ul>
 * <li> When the period equals the timeframe, you want to print the availability, max and avg response time in a table.
 * <li> When the period doesn't equal the timeframe, you can draw a line to display those statistics.
 * </ul>
 * That is why I decided to create two types of panels to display the statistics, so it is easily reusable if you want to change period or timeframes.
 * @see MonitorTableChartPanel
 * @see MonitorChartPanel
 * @author gabriellerappaport
 *
 */

public class PeriodPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public PeriodPanel(ArrayList<Integer> timeframes, Website website, Integer period, Integer refreshTime) {
		super();
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();

		if (period.equals(timeframes.get(0))) {
			panel0 = new MonitorTableChartPanel(website, period, refreshTime, timeframes.get(0));
		} else {
			panel0 = new MonitorChartPanel(website, period, refreshTime, timeframes.get(0));
		}

		if (period.equals(timeframes.get(1))) {
			panel1 = new MonitorTableChartPanel(website, period, refreshTime, timeframes.get(1));
		} else {
			panel1 = new MonitorChartPanel(website, period, refreshTime, timeframes.get(1));
		}

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel0, panel1);
		this.add(split, BorderLayout.CENTER);

		JSplitPane s = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel1, new JPanel());
		this.add(s, BorderLayout.SOUTH);

	}
}
